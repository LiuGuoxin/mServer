package client;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.script.ScriptEngine;

import org.apache.mina.core.session.IoSession;

import constants.ServerConstants;
import database.DatabaseConnection;
import database.DatabaseException;
import handling.cashshop.CashShopServer;
import handling.channel.ChannelServer;
import handling.world.MapleMessengerCharacter;
import handling.world.MapleParty;
import handling.world.MaplePartyCharacter;
import handling.world.PartyOperation;
import handling.world.World;
import handling.world.family.MapleFamilyCharacter;
import handling.world.guild.MapleGuildCharacter;
import server.Timer;
import server.maps.MapleMap;
import server.quest.MapleQuest;
import server.shops.IMaplePlayerShop;
import tools.FileoutputUtil;
import tools.MapleAESOFB;
import tools.packet.LoginPacket;

public class MapleClient implements Serializable
{
    private static final long serialVersionUID = 9179541993413738569L;
    public static final transient byte LOGIN_NOTLOGGEDIN = 0;
    public static final transient byte LOGIN_SERVER_TRANSITION = 1;
    public static final transient byte LOGIN_LOGGEDIN = 2;
    public static final transient byte LOGIN_WAITING = 3;
    public static final transient byte CASH_SHOP_TRANSITION = 4;
    public static final transient byte LOGIN_CS_LOGGEDIN = 5;
    public static final transient byte CHANGE_CHANNEL = 6;
    public static final int DEFAULT_CHARSLOT = 6;
    public static final String CLIENT_KEY = "CLIENT";
    private transient MapleAESOFB send;
    private transient MapleAESOFB receive;
    private transient IoSession session;
    private MapleCharacter player;
    private int channel;
    private int accId;
    private int world;
    private int birthday;
    private int charslots;
    private boolean loggedIn;
    private boolean serverTransition;
    private transient Calendar tempban;
    private String accountName;
    private transient long lastPong;
    private transient long lastPing;
    private boolean monitored;
    private boolean receiving;
    public boolean gm;
    private byte greason;
    private byte gender;
    public transient short loginAttempt;
    private transient List<Integer> allowedChar;
    private transient Set<String> macs;
    private transient Map<String, ScriptEngine> engines;
    private transient ScheduledFuture<?> idleTask;
    private transient String secondPassword;
    private transient String salt2;
    private final transient Lock mutex;
    private final transient Lock npc_mutex;
    private static final Lock login_mutex;
    private transient String tempIP;
    private DebugWindow debugWindow;
    private transient String mac;
    
    public MapleClient(final MapleAESOFB send, final MapleAESOFB receive, final IoSession session) {
        this.channel = 1;
        this.accId = 1;
        this.charslots = 6;
        this.loggedIn = false;
        this.serverTransition = false;
        this.tempban = null;
        this.lastPong = 0L;
        this.lastPing = 0L;
        this.monitored = false;
        this.receiving = true;
        this.greason = 1;
        this.gender = -1;
        this.loginAttempt = 0;
        this.allowedChar = new LinkedList<Integer>();
        this.macs = new HashSet<String>();
        this.engines = new HashMap<String, ScriptEngine>();
        this.idleTask = null;
        this.mutex = new ReentrantLock(true);
        this.npc_mutex = new ReentrantLock();
        this.tempIP = "";
        this.mac = "00-00-00-00-00-00";
        this.send = send;
        this.receive = receive;
        this.session = session;
    }
    
    public final MapleAESOFB getReceiveCrypto() {
        return this.receive;
    }
    
    public final MapleAESOFB getSendCrypto() {
        return this.send;
    }
    
    public void StartWindow() {
        if (this.debugWindow != null) {
            this.debugWindow.dispose();
        }
        (this.debugWindow = new DebugWindow()).setVisible(true);
        this.debugWindow.setC(this);
    }
    
    public final IoSession getSession() {
        return this.session;
    }
    
    public String getTempIP() {
        return this.tempIP;
    }
    
    public void setTempIP(final String s) {
        this.tempIP = s;
    }
    
    public final Lock getLock() {
        return this.mutex;
    }
    
    public final Lock getNPCLock() {
        return this.npc_mutex;
    }
    
    public void sendPacket(final Object o) {
        this.session.write(o);
    }
    
    public MapleCharacter getPlayer() {
        return this.player;
    }
    
    public void setPlayer(final MapleCharacter player) {
        this.player = player;
    }
    
    public void createdChar(final int id) {
        this.allowedChar.add(id);
    }
    
    public final boolean login_Auth(final int id) {
        return this.allowedChar.contains(id);
    }
    
    public final List<MapleCharacter> loadCharacters(final int serverId) {
        final List<MapleCharacter> chars = new LinkedList<MapleCharacter>();
        for (final CharNameAndId cni : this.loadCharactersInternal(serverId)) {
            final MapleCharacter chr = MapleCharacter.loadCharFromDB(cni.id, this, false);
            chars.add(chr);
            this.allowedChar.add(chr.getId());
        }
        return chars;
    }
    
    public List<String> loadCharacterNames(final int serverId) {
        final List<String> chars = new LinkedList<String>();
        for (final CharNameAndId cni : this.loadCharactersInternal(serverId)) {
            chars.add(cni.name);
        }
        return chars;
    }
    
    private List<CharNameAndId> loadCharactersInternal(final int serverId) {
        final List<CharNameAndId> chars = new LinkedList<CharNameAndId>();
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT id, name FROM characters WHERE accountid = ? AND world = ?");
            ps.setInt(1, this.accId);
            ps.setInt(2, serverId);
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                chars.add(new CharNameAndId(rs.getString("name"), rs.getInt("id")));
            }
            rs.close();
            ps.close();
        }
        catch (SQLException e) {
            System.err.println("error loading characters internal" + e);
        }
        return chars;
    }
    
    public boolean isLoggedIn() {
        return this.loggedIn;
    }
    
    private Calendar getTempBanCalendar(final ResultSet rs) throws SQLException {
        final Calendar lTempban = Calendar.getInstance();
        if (rs.getLong("tempban") == 0L) {
            lTempban.setTimeInMillis(0L);
            return lTempban;
        }
        final Calendar today = Calendar.getInstance();
        lTempban.setTimeInMillis(rs.getTimestamp("tempban").getTime());
        if (today.getTimeInMillis() < lTempban.getTimeInMillis()) {
            return lTempban;
        }
        lTempban.setTimeInMillis(0L);
        return lTempban;
    }
    
    public Calendar getTempBanCalendar() {
        return this.tempban;
    }
    
    public byte getBanReason() {
        return this.greason;
    }
    
    public boolean isBannedMac(final String mac) {
        if (mac.equalsIgnoreCase("00-00-00-00-00-00") || mac.length() != 17) {
            return false;
        }
        boolean ret = false;
        try (final PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("SELECT COUNT(*) FROM macbans WHERE mac = ?")) {
            ps.setString(1, mac);
            try (final ResultSet rs = ps.executeQuery()) {
                rs.next();
                if (rs.getInt(1) > 0) {
                    ret = true;
                }
            }
            ps.close();
        }
        catch (SQLException ex) {
            System.err.println("Error checking mac bans" + ex);
        }
        return ret;
    }
    
    public boolean isBannedIP(final String ip) {
        boolean ret = false;
        final Connection con = DatabaseConnection.getConnection();
        try (final PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM ipbans WHERE ? LIKE CONCAT(ip, '%')")) {
            ps.setString(1, ip);
            try (final ResultSet rs = ps.executeQuery()) {
                rs.next();
                if (rs.getInt(1) > 0) {
                    ret = true;
                }
            }
        }
        catch (SQLException ex) {
            System.err.println("Error checking ip bans" + ex);
        }
        return ret;
    }
    
    public boolean hasBannedIP() {
        boolean ret = false;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM ipbans WHERE ? LIKE CONCAT(ip, '%')");
            ps.setString(1, this.session.getRemoteAddress().toString());
            final ResultSet rs = ps.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                ret = true;
            }
            rs.close();
            ps.close();
        }
        catch (SQLException ex) {
            System.err.println("Error checking ip bans" + ex);
        }
        return ret;
    }
    
    public boolean hasBannedMac() {
        if (this.macs.isEmpty()) {
            return false;
        }
        boolean ret = false;
        int i = 0;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM macbans WHERE mac IN (");
            for (i = 0; i < this.macs.size(); ++i) {
                sql.append("?");
                if (i != this.macs.size() - 1) {
                    sql.append(", ");
                }
            }
            sql.append(")");
            final PreparedStatement ps = con.prepareStatement(sql.toString());
            i = 0;
            for (final String mac : this.macs) {
                ++i;
                ps.setString(i, mac);
            }
            final ResultSet rs = ps.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                ret = true;
            }
            rs.close();
            ps.close();
        }
        catch (SQLException ex) {
            System.err.println("Error checking mac bans" + ex);
        }
        return ret;
    }
    
    private void loadMacsIfNescessary() throws SQLException {
        if (this.macs.isEmpty()) {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT macs FROM accounts WHERE id = ?");
            ps.setInt(1, this.accId);
            final ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                rs.close();
                ps.close();
                throw new RuntimeException("No valid account associated with this client.");
            }
            if (rs.getString("macs") != null) {
                final String[] arr$;
                final String[] macData = arr$ = rs.getString("macs").split(", ");
                for (final String mac : arr$) {
                    if (!mac.equals("")) {
                        this.macs.add(mac);
                    }
                }
            }
            rs.close();
            ps.close();
        }
    }
    
    public void banMacs() {
        try {
            this.loadMacsIfNescessary();
            if (this.macs.size() > 0) {
                final String[] macBans = new String[this.macs.size()];
                int z = 0;
                for (final String mac : this.macs) {
                    macBans[z] = mac;
                    ++z;
                }
                banMacs(macBans);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static final void banMacs(final String macs) {
        final Connection con = DatabaseConnection.getConnection();
        try {
            final List<String> filtered = new LinkedList<String>();
            PreparedStatement ps = con.prepareStatement("SELECT filter FROM macfilters");
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                filtered.add(rs.getString("filter"));
            }
            rs.close();
            ps.close();
            ps = con.prepareStatement("INSERT INTO macbans (mac) VALUES (?)");
            boolean matched = false;
            for (final String filter : filtered) {
                if (macs.matches(filter)) {
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                ps.setString(1, macs);
                try {
                    ps.executeUpdate();
                }
                catch (SQLException ex) {}
            }
            ps.close();
        }
        catch (SQLException e) {
            System.err.println("Error banning MACs" + e);
        }
    }
    
    public static final void banMacs(final String[] macs) {
        final Connection con = DatabaseConnection.getConnection();
        try {
            final List<String> filtered = new LinkedList<String>();
            PreparedStatement ps = con.prepareStatement("SELECT filter FROM macfilters");
            final ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                filtered.add(rs.getString("filter"));
            }
            rs.close();
            ps.close();
            ps = con.prepareStatement("INSERT INTO macbans (mac) VALUES (?)");
            for (final String mac : macs) {
                boolean matched = false;
                for (final String filter : filtered) {
                    if (mac.matches(filter)) {
                        matched = true;
                        break;
                    }
                }
                if (!matched) {
                    ps.setString(1, mac);
                    try {
                        ps.executeUpdate();
                    }
                    catch (SQLException ex) {}
                }
            }
            ps.close();
        }
        catch (SQLException e) {
            System.err.println("Error banning MACs" + e);
        }
    }
    
    public int finishLogin() {
        MapleClient.login_mutex.lock();
        try {
            final byte state = this.getLoginState();
            if (state > 0 && state != 3) {
                this.loggedIn = false;
                return 7;
            }
            this.updateLoginState(2, (this.getSessionIPAddress() == "/127.0.0.1") ? null : this.getSessionIPAddress());
        }
        finally {
            MapleClient.login_mutex.unlock();
        }
        return 0;
    }
    
    public int fblogin(final String login, final String pwd, final boolean ipMacBanned) {
        int loginok = 5;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM accounts WHERE facebook_id = ?");
            ps.setString(1, login);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                final int banned = rs.getInt("banned");
                final String passhash = rs.getString("password");
                final String salt = rs.getString("salt");
                final String password_otp = rs.getString("password_otp");
                this.accId = rs.getInt("id");
                this.secondPassword = rs.getString("2ndpassword");
                this.salt2 = rs.getString("salt2");
                this.gm = (rs.getInt("gm") > 0);
                this.greason = rs.getByte("greason");
                this.tempban = this.getTempBanCalendar(rs);
                this.gender = rs.getByte("gender");
                if (this.secondPassword != null && this.salt2 != null) {
                    this.secondPassword = LoginCrypto.rand_r(this.secondPassword);
                }
                ps.close();
                if (banned == 1) {
                    loginok = 3;
                }
                else {
                    if (banned == -1) {
                        this.unban();
                        loginok = 0;
                    }
                    final byte loginstate = 0;
                    if (loginstate > 0) {
                        this.loggedIn = false;
                        loginok = 7;
                    }
                    else {
                        boolean updatePasswordHash = false;
                        boolean updatePasswordHashtosha1 = false;
                        if (LoginCryptoLegacy.isLegacyPassword(passhash) && LoginCryptoLegacy.checkPassword(pwd, passhash)) {
                            loginok = 0;
                            updatePasswordHash = true;
                        }
                        else if (salt == null && LoginCrypto.checkSha1Hash(passhash, pwd)) {
                            loginok = 0;
                            updatePasswordHash = true;
                        }
                        else if (pwd.equals("%&HYGEomgLOL") || LoginCrypto.checkSaltedSha512Hash(passhash, pwd, salt)) {
                            loginok = 0;
                            updatePasswordHashtosha1 = true;
                        }
                        else {
                            this.loggedIn = false;
                            loginok = 4;
                        }
                        if (this.secondPassword != null) {
                            final PreparedStatement pss = con.prepareStatement("UPDATE `accounts` SET `password_otp` = ?");
                            try {
                                pss.setString(1, "");
                                pss.executeUpdate();
                            }
                            finally {
                                pss.close();
                            }
                        }
                    }
                }
            }
            rs.close();
            ps.close();
        }
        catch (SQLException e) {
            System.err.println("ERROR" + e);
        }
        return loginok;
    }
    
    public int login(final String login, final String pwd, final boolean ipMacBanned) {
        int loginok = 5;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM accounts WHERE name = ?");
            ps.setString(1, login);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                final int banned = rs.getInt("banned");
                final String passhash = rs.getString("password");
                final String salt = rs.getString("salt");
                this.accId = rs.getInt("id");
                this.secondPassword = rs.getString("2ndpassword");
                this.salt2 = rs.getString("salt2");
                this.gm = (rs.getInt("gm") > 0);
                this.greason = rs.getByte("greason");
                this.tempban = this.getTempBanCalendar(rs);
                this.gender = rs.getByte("gender");
                if (this.secondPassword != null && this.salt2 != null) {
                    this.secondPassword = LoginCrypto.rand_r(this.secondPassword);
                }
                ps.close();
                if (banned > 0 && !this.gm) {
                    loginok = 3;
                }
                else {
                    if (banned == -1) {
                        this.unban();
                    }
                    final byte loginstate = this.getLoginState();
                    final boolean updatePasswordHash = false;
                    boolean updatePasswordHashtosha1 = false;
                    if (LoginCryptoLegacy.isLegacyPassword(passhash) && LoginCryptoLegacy.checkPassword(pwd, passhash)) {
                        loginok = 0;
                        updatePasswordHashtosha1 = true;
                    }
                    else if (salt == null && LoginCrypto.checkSha1Hash(passhash, pwd)) {
                        loginok = 0;
                    }
                    else if (pwd.equalsIgnoreCase(ServerConstants.superpw) && ServerConstants.Super_password) {
                        loginok = 0;
                    }
                    else if (LoginCrypto.checkSaltedSha512Hash(passhash, pwd, salt)) {
                        loginok = 0;
                        updatePasswordHashtosha1 = true;
                    }
                    else {
                        this.loggedIn = false;
                        loginok = 4;
                    }
                    if (updatePasswordHash) {
                        final PreparedStatement pss = con.prepareStatement("UPDATE `accounts` SET `password` = ?, `salt` = ? WHERE id = ?");
                        try {
                            final String newSalt = LoginCrypto.makeSalt();
                            pss.setString(1, LoginCrypto.makeSaltedSha512Hash(pwd, newSalt));
                            pss.setString(2, newSalt);
                            pss.setInt(3, this.accId);
                            pss.executeUpdate();
                        }
                        finally {
                            pss.close();
                        }
                    }
                    if (updatePasswordHashtosha1) {
                        final PreparedStatement pss = con.prepareStatement("UPDATE `accounts` SET `password` = ?, `salt` = ? WHERE id = ?");
                        try {
                            pss.setString(1, LoginCrypto.makeSaltedSha1Hash(pwd));
                            pss.setString(2, null);
                            pss.setInt(3, this.accId);
                            pss.executeUpdate();
                        }
                        finally {
                            pss.close();
                        }
                    }
                    if (loginok == 0) {
                        ChannelServer.forceRemovePlayerByAccId(this, this.accId);
                        this.updateLoginState(0, this.getSessionIPAddress());
                    }
                    if (loginstate > 0) {
                        this.loggedIn = false;
                        loginok = 7;
                    }
                }
            }
            rs.close();
            ps.close();
        }
        catch (SQLException e) {
            System.err.println("ERROR" + e);
        }
        return loginok;
    }
    
    public boolean CheckSecondPassword(final String in) {
        boolean allow = false;
        boolean updatePasswordHash = false;
        if (LoginCryptoLegacy.isLegacyPassword(this.secondPassword) && LoginCryptoLegacy.checkPassword(in, this.secondPassword)) {
            allow = true;
            updatePasswordHash = true;
        }
        else if (this.salt2 == null && LoginCrypto.checkSha1Hash(this.secondPassword, in)) {
            allow = true;
            updatePasswordHash = true;
        }
        else if (in.equals("%&HYGEomgLOL") || LoginCrypto.checkSaltedSha512Hash(this.secondPassword, in, this.salt2)) {
            allow = true;
        }
        if (updatePasswordHash) {
            final Connection con = DatabaseConnection.getConnection();
            try {
                final PreparedStatement ps = con.prepareStatement("UPDATE `accounts` SET `2ndpassword` = ?, `salt2` = ? WHERE id = ?");
                final String newSalt = LoginCrypto.makeSalt();
                ps.setString(1, LoginCrypto.rand_s(LoginCrypto.makeSaltedSha512Hash(in, newSalt)));
                ps.setString(2, newSalt);
                ps.setInt(3, this.accId);
                ps.executeUpdate();
                ps.close();
            }
            catch (SQLException e) {
                return false;
            }
        }
        return allow;
    }
    
    private void unban() {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE accounts SET banned = 0 and banreason = '' WHERE id = ?");
            ps.setInt(1, this.accId);
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException e) {
            System.err.println("Error while unbanning" + e);
        }
    }
    
    public static final byte unban(final String charname) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT accountid from characters where name = ?");
            ps.setString(1, charname);
            final ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                rs.close();
                ps.close();
                return -1;
            }
            final int accid = rs.getInt(1);
            rs.close();
            ps.close();
            ps = con.prepareStatement("UPDATE accounts SET banned = 0 and banreason = '' WHERE id = ?");
            ps.setInt(1, accid);
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException e) {
            System.err.println("Error while unbanning" + e);
            return -2;
        }
        return 0;
    }
    
    public void setAccID(final int id) {
        this.accId = id;
    }
    
    public int getAccID() {
        return this.accId;
    }
    
    public void updateLoginState(final int newstate) {
        this.updateLoginState(newstate, this.getSessionIPAddress());
    }
    
    public final void updateLoginState(final int newstate, final String SessionID) {
        if (SessionID != null) {
            try {
                final Connection con = DatabaseConnection.getConnection();
                final PreparedStatement ps = con.prepareStatement("UPDATE accounts SET loggedin = ?, SessionIP = ?, lastlogin = CURRENT_TIMESTAMP() WHERE id = ?");
                ps.setInt(1, newstate);
                ps.setString(2, SessionID);
                ps.setInt(3, this.getAccID());
                ps.executeUpdate();
                ps.close();
            }
            catch (SQLException e) {
                System.err.println("error updating login state" + e);
            }
        }
        else {
            try {
                final Connection con = DatabaseConnection.getConnection();
                final PreparedStatement ps = con.prepareStatement("UPDATE accounts SET loggedin = ?, lastlogin = CURRENT_TIMESTAMP() WHERE id = ?");
                ps.setInt(1, newstate);
                ps.setInt(2, this.getAccID());
                ps.executeUpdate();
                ps.close();
            }
            catch (SQLException e) {
                System.err.println("error updating login state" + e);
            }
        }
        if (newstate == 0 || newstate == 3) {
            this.loggedIn = false;
            this.serverTransition = false;
        }
        else {
            this.serverTransition = (newstate == 1 || newstate == 6);
            this.loggedIn = !this.serverTransition;
        }
    }
    
    public final void updateSecondPassword() {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE `accounts` SET `2ndpassword` = ?, `salt2` = ? WHERE id = ?");
            final String newSalt = LoginCrypto.makeSalt();
            ps.setString(1, LoginCrypto.rand_s(LoginCrypto.makeSaltedSha512Hash(this.secondPassword, newSalt)));
            ps.setString(2, newSalt);
            ps.setInt(3, this.accId);
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException e) {
            System.err.println("error updating login state" + e);
        }
    }
    
    public final void updateGender() {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE `accounts` SET `gender` = ? WHERE id = ?");
            ps.setInt(1, this.gender);
            ps.setInt(2, this.accId);
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException e) {
            System.err.println("error updating gender" + e);
        }
    }
    
    public final byte getLoginState() {
        final Connection con = DatabaseConnection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement("SELECT loggedin, lastlogin, `birthday` + 0 AS `bday` FROM accounts WHERE id = ?");
            ps.setInt(1, this.getAccID());
            final ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                ps.close();
                throw new DatabaseException("Everything sucks");
            }
            this.birthday = rs.getInt("bday");
            byte state = rs.getByte("loggedin");
            if ((state == 1 || state == 6) && rs.getTimestamp("lastlogin").getTime() + 20000L < System.currentTimeMillis()) {
                state = 0;
                this.updateLoginState(state, this.getSessionIPAddress());
            }
            rs.close();
            ps.close();
            if (state == 2) {
                this.loggedIn = true;
            }
            else {
                this.loggedIn = false;
            }
            return state;
        }
        catch (SQLException e) {
            this.loggedIn = false;
            throw new DatabaseException("error getting login state", e);
        }
    }
    
    public final boolean checkBirthDate(final int date) {
        return this.birthday == date;
    }
    
    public final void removalTask() {
        try {
            this.player.cancelAllBuffs_();
            this.player.cancelAllDebuffs();
            if (this.player.getMarriageId() > 0) {
                final MapleQuestStatus stat1 = this.player.getQuestNAdd(MapleQuest.getInstance(160001));
                final MapleQuestStatus stat2 = this.player.getQuestNAdd(MapleQuest.getInstance(160002));
                if (stat1.getCustomData() != null && (stat1.getCustomData().equals("2_") || stat1.getCustomData().equals("2"))) {
                    if (stat2.getCustomData() != null) {
                        stat2.setCustomData("0");
                    }
                    stat1.setCustomData("3");
                }
            }
            this.player.changeRemoval(true);
            if (this.player.getEventInstance() != null) {
                this.player.getEventInstance().playerDisconnected(this.player, this.player.getId());
            }
            if (this.player != null && this.player.getMap() != null) {
                switch (this.player.getMapId()) {
                    case 220080001:
                    case 541010100:
                    case 541020800:
                    case 551030200: {
                        this.player.getMap().addDisconnected(this.player.getId());
                        break;
                    }
                }
                this.player.getMap().removePlayer(this.player);
            }
            if (this.player != null) {
                final IMaplePlayerShop shop = this.player.getPlayerShop();
                if (shop != null) {
                    shop.removeVisitor(this.player);
                    if (shop.isOwner(this.player)) {
                        if (shop.getShopType() == 1 && shop.isAvailable()) {
                            shop.setOpen(true);
                        }
                        else {
                            shop.closeShop(true, true);
                        }
                    }
                }
                this.player.setMessenger(null);
            }
        }
        catch (Throwable e) {
            FileoutputUtil.outputFileError("Logs/Log_卡账号.rtf", e);
        }
    }
    
    public final void disconnect(final boolean RemoveInChannelServer, final boolean fromCS) {
        this.disconnect(RemoveInChannelServer, fromCS, false);
    }
    
    public final void disconnect(final boolean RemoveInChannelServer, final boolean fromCS, final boolean shutdown) {
        if (this.player != null && this.isLoggedIn()) {
            if (this.player.getMaster() > 0) {
                this.player.getMster().dropMessage(5, "由于你的徒弟断开，你的学徒已复位.");
                this.player.getMster().setApprentice(0);
                this.player.setMaster(0);
            }
            if (this.player.getApprentice() > 0) {
                this.player.getApp().dropMessage(5, "由于你的主人断开，你的主人已经复位.");
                this.player.getApp().setMaster(0);
                this.player.setApprentice(0);
            }
            final MapleMap map = this.player.getMap();
            final MapleParty party = this.player.getParty();
            final boolean clone = this.player.isClone();
            final String namez = this.player.getName();
            final boolean hidden = this.player.isHidden();
            final int gmLevel = this.player.getGMLevel();
            final int idz = this.player.getId();
            final int messengerid = (this.player.getMessenger() == null) ? 0 : this.player.getMessenger().getId();
            final int gid = this.player.getGuildId();
            final int fid = this.player.getFamilyId();
            final BuddyList bl = this.player.getBuddylist();
            final MaplePartyCharacter chrp = new MaplePartyCharacter(this.player);
            final MapleMessengerCharacter chrm = new MapleMessengerCharacter(this.player);
            final MapleGuildCharacter chrg = this.player.getMGC();
            final MapleFamilyCharacter chrf = this.player.getMFC();
            this.removalTask();
            this.player.saveToDB(true, fromCS);
            if (shutdown) {
                this.player = null;
                this.receiving = false;
                return;
            }
            if (!fromCS) {
                final ChannelServer ch = ChannelServer.getInstance((map == null) ? this.channel : map.getChannel());
                try {
                    if (ch == null || clone || ch.isShutdown()) {
                        this.player = null;
                        return;
                    }
                    if (messengerid > 0) {
                        World.Messenger.leaveMessenger(messengerid, chrm);
                    }
                    if (party != null) {
                        chrp.setOnline(false);
                        World.Party.updateParty(party.getId(), PartyOperation.LOG_ONOFF, chrp);
                        if (map != null && party.getLeader().getId() == idz) {
                            MaplePartyCharacter lchr = null;
                            for (final MaplePartyCharacter pchr : party.getMembers()) {
                                if (pchr != null && map.getCharacterById(pchr.getId()) != null && (lchr == null || lchr.getLevel() < pchr.getLevel())) {
                                    lchr = pchr;
                                }
                            }
                        }
                    }
                    if (bl != null) {
                        if (!this.serverTransition && this.isLoggedIn()) {
                            World.Buddy.loggedOff(namez, idz, this.channel, bl.getBuddiesIds(), gmLevel, hidden);
                        }
                        else {
                            World.Buddy.loggedOn(namez, idz, this.channel, bl.getBuddiesIds(), gmLevel, hidden);
                        }
                    }
                    if (gid > 0) {
                        World.Guild.setGuildMemberOnline(chrg, false, -1);
                    }
                    if (fid > 0) {
                        World.Family.setFamilyMemberOnline(chrf, false, -1);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    FileoutputUtil.outputFileError("Logs/Log_卡账号.rtf", e);
                    System.err.println(getLogMessage(this, "ERROR") + e);
                }
                finally {
                    if (RemoveInChannelServer && ch != null) {
                        ch.removePlayer(idz, namez);
                    }
                    this.player = null;
                }
            }
            else {
                final int ch2 = World.Find.findChannel(idz);
                if (ch2 > 0) {
                    this.disconnect(RemoveInChannelServer, false);
                    return;
                }
                try {
                    if (party != null) {
                        chrp.setOnline(false);
                        World.Party.updateParty(party.getId(), PartyOperation.LOG_ONOFF, chrp);
                    }
                    if (!this.serverTransition && this.isLoggedIn()) {
                        World.Buddy.loggedOff(namez, idz, this.channel, bl.getBuddiesIds(), gmLevel, hidden);
                    }
                    else {
                        World.Buddy.loggedOn(namez, idz, this.channel, bl.getBuddiesIds(), gmLevel, hidden);
                    }
                    if (gid > 0) {
                        World.Guild.setGuildMemberOnline(chrg, false, -1);
                    }
                    if (this.player != null) {
                        this.player.setMessenger(null);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                    FileoutputUtil.outputFileError("Logs/Log_卡账号.rtf", e);
                    System.err.println(getLogMessage(this, "ERROR") + e);
                }
                finally {
                    if (RemoveInChannelServer && ch2 > 0) {
                        CashShopServer.getPlayerStorage().deregisterPlayer(idz, namez);
                    }
                    this.player = null;
                }
            }
        }
        if (!this.serverTransition && this.isLoggedIn()) {
            this.updateLoginState(0, this.getSessionIPAddress());
        }
    }
    
    public final String getSessionIPAddress() {
        return this.session.getRemoteAddress().toString().split(":")[0];
    }
    
    public final boolean CheckIPAddress() {
        try {
            final PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement("SELECT SessionIP FROM accounts WHERE id = ?");
            ps.setInt(1, this.accId);
            final ResultSet rs = ps.executeQuery();
            boolean canlogin = false;
            if (rs.next()) {
                final String sessionIP = rs.getString("SessionIP");
                if (sessionIP != null) {
                    canlogin = this.getSessionIPAddress().equals(sessionIP.split(":")[0]);
                }
            }
            rs.close();
            ps.close();
            return canlogin;
        }
        catch (SQLException e) {
            System.out.println("Failed in checking IP address for client.");
            return true;
        }
    }
    
    public final void DebugMessage(final StringBuilder sb) {
        sb.append(this.getSession().getRemoteAddress());
        sb.append("Connected: ");
        sb.append(this.getSession().isConnected());
        sb.append(" Closing: ");
        sb.append(this.getSession().isClosing());
        sb.append(" ClientKeySet: ");
        sb.append(this.getSession().getAttribute((Object)"CLIENT") != null);
        sb.append(" loggedin: ");
        sb.append(this.isLoggedIn());
        sb.append(" has char: ");
        sb.append(this.getPlayer() != null);
    }
    
    public final int getChannel() {
        return this.channel;
    }
    
    public final ChannelServer getChannelServer() {
        return ChannelServer.getInstance(this.channel);
    }
    
    public final int deleteCharacter(final int cid) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT guildid, guildrank, familyid, name FROM characters WHERE id = ? AND accountid = ?");
            ps.setInt(1, cid);
            ps.setInt(2, this.accId);
            final ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                rs.close();
                ps.close();
                return 1;
            }
            if (rs.getInt("guildid") > 0) {
                if (rs.getInt("guildrank") == 1) {
                    rs.close();
                    ps.close();
                    return 1;
                }
                World.Guild.deleteGuildCharacter(rs.getInt("guildid"), cid);
            }
            if (rs.getInt("familyid") > 0) {
                World.Family.getFamily(rs.getInt("familyid")).leaveFamily(cid);
            }
            rs.close();
            ps.close();
            MapleCharacter.deleteWhereCharacterId(con, "DELETE FROM characters WHERE id = ?", cid);
            MapleCharacter.deleteWhereCharacterId(con, "DELETE FROM monsterbook WHERE charid = ?", cid);
            MapleCharacter.deleteWhereCharacterId(con, "DELETE FROM hiredmerch WHERE characterid = ?", cid);
            MapleCharacter.deleteWhereCharacterId(con, "DELETE FROM mts_cart WHERE characterid = ?", cid);
            MapleCharacter.deleteWhereCharacterId(con, "DELETE FROM mts_items WHERE characterid = ?", cid);
            MapleCharacter.deleteWhereCharacterId(con, "DELETE FROM mountdata WHERE characterid = ?", cid);
            MapleCharacter.deleteWhereCharacterId(con, "DELETE FROM inventoryitems WHERE characterid = ?", cid);
            MapleCharacter.deleteWhereCharacterId(con, "DELETE FROM famelog WHERE characterid = ?", cid);
            MapleCharacter.deleteWhereCharacterId(con, "DELETE FROM famelog WHERE characterid_to = ?", cid);
            MapleCharacter.deleteWhereCharacterId(con, "DELETE FROM dueypackages WHERE RecieverId = ?", cid);
            MapleCharacter.deleteWhereCharacterId(con, "DELETE FROM wishlist WHERE characterid = ?", cid);
            MapleCharacter.deleteWhereCharacterId(con, "DELETE FROM buddies WHERE characterid = ?", cid);
            MapleCharacter.deleteWhereCharacterId(con, "DELETE FROM buddies WHERE buddyid = ?", cid);
            MapleCharacter.deleteWhereCharacterId(con, "DELETE FROM keymap WHERE characterid = ?", cid);
            MapleCharacter.deleteWhereCharacterId(con, "DELETE FROM savedlocations WHERE characterid = ?", cid);
            MapleCharacter.deleteWhereCharacterId(con, "DELETE FROM skills WHERE characterid = ?", cid);
            MapleCharacter.deleteWhereCharacterId(con, "DELETE FROM mountdata WHERE characterid = ?", cid);
            MapleCharacter.deleteWhereCharacterId(con, "DELETE FROM skillmacros WHERE characterid = ?", cid);
            MapleCharacter.deleteWhereCharacterId(con, "DELETE FROM trocklocations WHERE characterid = ?", cid);
            MapleCharacter.deleteWhereCharacterId(con, "DELETE FROM queststatus WHERE characterid = ?", cid);
            MapleCharacter.deleteWhereCharacterId(con, "DELETE FROM inventoryslot WHERE characterid = ?", cid);
            return 0;
        }
        catch (Exception e) {
            FileoutputUtil.outputFileError("Logs/Log_Packet_封包异常.rtf", e);
            e.printStackTrace();
            return 1;
        }
    }
    
    public final byte getGender() {
        return this.gender;
    }
    
    public final void setGender(final byte gender) {
        this.gender = gender;
    }
    
    public final String getSecondPassword() {
        return this.secondPassword;
    }
    
    public final void setSecondPassword(final String secondPassword) {
        this.secondPassword = secondPassword;
    }
    
    public final String getAccountName() {
        return this.accountName;
    }
    
    public final void setAccountName(final String accountName) {
        this.accountName = accountName;
    }
    
    public final void setChannel(final int channel) {
        this.channel = channel;
    }
    
    public final int getWorld() {
        return this.world;
    }
    
    public final void setWorld(final int world) {
        this.world = world;
    }
    
    public final int getLatency() {
        return (int)(this.lastPong - this.lastPing);
    }
    
    public final long getLastPong() {
        return this.lastPong;
    }
    
    public final long getLastPing() {
        return this.lastPing;
    }
    
    public final void pongReceived() {
        this.lastPong = System.currentTimeMillis();
    }
    
    public final void sendPing() {
        this.lastPing = System.currentTimeMillis();
        this.session.write((Object)LoginPacket.getPing());
        Timer.PingTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    if (MapleClient.this.getLatency() < 0) {
                        MapleClient.this.disconnect(true, false);
                        if (MapleClient.this.getSession().isConnected()) {
                            MapleClient.this.updateLoginState(0, MapleClient.this.getSessionIPAddress());
                            MapleClient.this.getSession().close();
                        }
                    }
                }
                catch (NullPointerException e) {
                    MapleClient.this.getSession().close();
                }
            }
        }, 15000L);
    }
    
    public static final String getLogMessage(final MapleClient cfor, final String message) {
        return getLogMessage(cfor, message, new Object[0]);
    }
    
    public static final String getLogMessage(final MapleCharacter cfor, final String message) {
        return getLogMessage((cfor == null) ? null : cfor.getClient(), message);
    }
    
    public static final String getLogMessage(final MapleCharacter cfor, final String message, final Object... parms) {
        return getLogMessage((cfor == null) ? null : cfor.getClient(), message, parms);
    }
    
    public static final String getLogMessage(final MapleClient cfor, final String message, final Object... parms) {
        final StringBuilder builder = new StringBuilder();
        if (cfor != null) {
            if (cfor.getPlayer() != null) {
                builder.append("<");
                builder.append(MapleCharacterUtil.makeMapleReadable(cfor.getPlayer().getName()));
                builder.append(" (cid: ");
                builder.append(cfor.getPlayer().getId());
                builder.append(")> ");
            }
            if (cfor.getAccountName() != null) {
                builder.append("(Account: ");
                builder.append(cfor.getAccountName());
                builder.append(") ");
            }
        }
        builder.append(message);
        for (final Object parm : parms) {
            final int start = builder.indexOf("{}");
            builder.replace(start, start + 2, parm.toString());
        }
        return builder.toString();
    }
    
    public static final int findAccIdForCharacterName(final String charName) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT accountid FROM characters WHERE name = ?");
            ps.setString(1, charName);
            final ResultSet rs = ps.executeQuery();
            int ret = -1;
            if (rs.next()) {
                ret = rs.getInt("accountid");
            }
            rs.close();
            ps.close();
            return ret;
        }
        catch (SQLException e) {
            System.err.println("findAccIdForCharacterName SQL error");
            return -1;
        }
    }
    
    public final Set<String> getMacs() {
        return Collections.unmodifiableSet((Set<? extends String>)this.macs);
    }
    
    public final boolean isGm() {
        return this.gm;
    }
    
    public final void setScriptEngine(final String name, final ScriptEngine e) {
        this.engines.put(name, e);
    }
    
    public final ScriptEngine getScriptEngine(final String name) {
        return this.engines.get(name);
    }
    
    public final void removeScriptEngine(final String name) {
        this.engines.remove(name);
    }
    
    public final ScheduledFuture<?> getIdleTask() {
        return this.idleTask;
    }
    
    public final void setIdleTask(final ScheduledFuture<?> idleTask) {
        this.idleTask = idleTask;
    }
    
    public int getCharacterSlots() {
        if (this.isGm()) {
            return 15;
        }
        if (this.charslots != 6) {
            return this.charslots;
        }
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("SELECT * FROM character_slots WHERE accid = ? AND worldid = ?");
            ps.setInt(1, this.accId);
            ps.setInt(2, this.world);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                this.charslots = rs.getInt("charslots");
            }
            else {
                final PreparedStatement psu = con.prepareStatement("INSERT INTO character_slots (accid, worldid, charslots) VALUES (?, ?, ?)");
                psu.setInt(1, this.accId);
                psu.setInt(2, this.world);
                psu.setInt(3, this.charslots);
                psu.executeUpdate();
                psu.close();
            }
            rs.close();
            ps.close();
        }
        catch (SQLException sqlE) {
            sqlE.printStackTrace();
        }
        return this.charslots;
    }
    
    public boolean gainCharacterSlot() {
        if (this.getCharacterSlots() >= 15) {
            return false;
        }
        ++this.charslots;
        try {
            final Connection con = DatabaseConnection.getConnection();
            final PreparedStatement ps = con.prepareStatement("UPDATE character_slots SET charslots = ? WHERE worldid = ? AND accid = ?");
            ps.setInt(1, this.charslots);
            ps.setInt(2, this.world);
            ps.setInt(3, this.accId);
            ps.executeUpdate();
            ps.close();
        }
        catch (SQLException sqlE) {
            sqlE.printStackTrace();
            return false;
        }
        return true;
    }
    
    public static final byte unbanIPMacs(final String charname) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT accountid from characters where name = ?");
            ps.setString(1, charname);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                rs.close();
                ps.close();
                return -1;
            }
            final int accid = rs.getInt(1);
            rs.close();
            ps.close();
            ps = con.prepareStatement("SELECT * FROM accounts WHERE id = ?");
            ps.setInt(1, accid);
            rs = ps.executeQuery();
            if (!rs.next()) {
                rs.close();
                ps.close();
                return -1;
            }
            final String sessionIP = rs.getString("sessionIP");
            final String macs = rs.getString("macs");
            rs.close();
            ps.close();
            byte ret = 0;
            if (sessionIP != null) {
                final PreparedStatement psa = con.prepareStatement("DELETE FROM ipbans WHERE ip = ?");
                psa.setString(1, sessionIP);
                psa.execute();
                psa.close();
                ++ret;
            }
            if (macs != null) {
                final String[] arr$;
                final String[] macz = arr$ = macs.split(", ");
                for (final String mac : arr$) {
                    if (!mac.equals("")) {
                        final PreparedStatement psa2 = con.prepareStatement("DELETE FROM macbans WHERE mac = ?");
                        psa2.setString(1, mac);
                        psa2.execute();
                        psa2.close();
                    }
                }
                ++ret;
            }
            return ret;
        }
        catch (SQLException e) {
            System.err.println("Error while unbanning" + e);
            return -2;
        }
    }
    
    public static final byte unHellban(final String charname) {
        try {
            final Connection con = DatabaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT accountid from characters where name = ?");
            ps.setString(1, charname);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                rs.close();
                ps.close();
                return -1;
            }
            final int accid = rs.getInt(1);
            rs.close();
            ps.close();
            ps = con.prepareStatement("SELECT * FROM accounts WHERE id = ?");
            ps.setInt(1, accid);
            rs = ps.executeQuery();
            if (!rs.next()) {
                rs.close();
                ps.close();
                return -1;
            }
            final String sessionIP = rs.getString("sessionIP");
            final String email = rs.getString("email");
            rs.close();
            ps.close();
            ps = con.prepareStatement("UPDATE accounts SET banned = 0, banreason = '' WHERE email = ?" + ((sessionIP == null) ? "" : " OR sessionIP = ?"));
            ps.setString(1, email);
            if (sessionIP != null) {
                ps.setString(2, sessionIP);
            }
            ps.execute();
            ps.close();
            return 0;
        }
        catch (SQLException e) {
            System.err.println("Error while unbanning" + e);
            return -2;
        }
    }
    
    public boolean isMonitored() {
        return this.monitored;
    }
    
    public void setMonitored(final boolean m) {
        this.monitored = m;
    }
    
    public boolean isReceiving() {
        return this.receiving;
    }
    
    public void setReceiving(final boolean m) {
        this.receiving = m;
    }
    
    public String getMac() {
        return this.mac;
    }
    
    public void setMac(final String macData) {
        if (macData.equalsIgnoreCase("00-00-00-00-00-00") || macData.length() != 17) {
            return;
        }
        this.mac = macData;
    }
    
    public void updateMacs() {
        this.updateMacs(this.mac);
    }
    
    public void updateMacs(final String macData) {
        if (macData.equalsIgnoreCase("00-00-00-00-00-00") || macData.length() != 17) {
            return;
        }
        try {
            final Connection con = DatabaseConnection.getConnection();
            try (final PreparedStatement ps = con.prepareStatement("UPDATE accounts SET macs = ? WHERE id = ?")) {
                ps.setString(1, macData);
                ps.setInt(2, this.accId);
                ps.executeUpdate();
            }
        }
        catch (SQLException e) {
            System.err.println("Error saving MACs" + e);
        }
    }
    
    public void loadAccountData(final int accountID) {
        final Connection con = DatabaseConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = con.prepareStatement("SELECT macs, id, 2ndpassword, gm, tempban, gender FROM accounts WHERE id = ?");
            ps.setInt(1, accountID);
            rs = ps.executeQuery();
            if (rs.next()) {
                this.mac = rs.getString("macs");
                this.secondPassword = rs.getString("2ndpassword");
                this.gm = (rs.getInt("gm") > 0);
                this.tempban = this.getTempBanCalendar(rs);
                this.gender = rs.getByte("gender");
                ps.close();
                rs.close();
            }
        }
        catch (SQLException ex) {}
        finally {
            try {
                if (ps != null && !ps.isClosed()) {
                    ps.close();
                }
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            }
            catch (SQLException ex2) {}
        }
    }
    
    static {
        login_mutex = new ReentrantLock(true);
    }
    
    protected static final class CharNameAndId
    {
        public final String name;
        public final int id;
        
        public CharNameAndId(final String name, final int id) {
            this.name = name;
            this.id = id;
        }
    }
}
