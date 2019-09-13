package client.messages.commands;

import java.awt.Point;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

import org.apache.mina.core.session.IoSession;

import com.mysql.jdbc.PreparedStatement;

import client.ISkill;
import client.LoginCrypto;
import client.MapleCharacter;
import client.MapleCharacterUtil;
import client.MapleClient;
import client.MapleDisease;
import client.MapleStat;
import client.SkillFactory;
import client.anticheat.CheatingOffense;
import client.inventory.Equip;
import client.inventory.IItem;
import client.inventory.ItemFlag;
import client.inventory.MapleInventoryIdentifier;
import client.inventory.MapleInventoryType;
import client.inventory.MaplePet;
import client.inventory.MapleRing;
import client.messages.CommandProcessorUtil;
import constants.GameConstants;
import constants.ServerConstants;
import database.DatabaseConnection;
import handling.RecvPacketOpcode;
import handling.SendPacketOpcode;
import handling.channel.ChannelServer;
import handling.login.LoginServer;
import handling.login.handler.AutoRegister;
import handling.world.CheaterData;
import handling.world.World;
import handling.world.family.MapleFamily;
import handling.world.guild.MapleGuild;
import scripting.EventManager;
import scripting.NPCScriptManager;
import scripting.PortalScriptManager;
import scripting.ReactorScriptManager;
import server.CashItemFactory;
import server.MapleInventoryManipulator;
import server.MapleItemInformationProvider;
import server.MaplePortal;
import server.MapleShopFactory;
import server.ShutdownServer;
import server.Timer;
import server.events.MapleEvent;
import server.events.MapleEventType;
import server.events.MapleOxQuizFactory;
import server.life.MapleLifeFactory;
import server.life.MapleMonster;
import server.life.MapleMonsterInformationProvider;
import server.life.MapleNPC;
import server.life.MobSkillFactory;
import server.life.OverrideMonsterStats;
import server.life.PlayerNPC;
import server.maps.MapleMap;
import server.maps.MapleMapObject;
import server.maps.MapleMapObjectType;
import server.maps.MapleReactor;
import server.maps.MapleReactorFactory;
import server.maps.MapleReactorStats;
import server.quest.MapleQuest;
import tools.ArrayMap;
import tools.CPUSampler;
import tools.FileoutputUtil;
import tools.HexTool;
import tools.MaplePacketCreator;
import tools.MockIOSession;
import tools.StringUtil;
import tools.data.output.MaplePacketLittleEndianWriter;
import tools.packet.MobPacket;

public class AdminCommand
{
    public static ServerConstants.PlayerGMRank getPlayerLevelRequired() {
        return ServerConstants.PlayerGMRank.ADMIN;
    }
    
    public static class ���ŵ�ͼ extends openmap
    {
    }
    
    public static class �رյ�ͼ extends closemap
    {
    }
    
    public static class ע�� extends register
    {
    }
    
    public static class ������ extends maxstats
    {
    }
    
    public static class ������ extends maxSkills
    {
    }
    
    public static class ��ȫ�� extends WarpAllHere
    {
    }
    
    public static class ����� extends mesoEveryone
    {
    }
    
    public static class ������ extends ExpEveryone
    {
    }
    
    public static class �������˵�� extends CashEveryone
    {
    }
    
    public static class ����� extends GainCash
    {
    }
    
    public static class ˢ�µ�ͼ extends ReloadMap
    {
    }
    
    public static class ף�� extends buff
    {
    }
    
    public static class �������� extends setRate
    {
    }
    
    public static class ��ͼ���� extends WhereAmI
    {
    }
    
    public static class ˢ extends Item
    {
    }
    
    public static class �� extends Drop
    {
    }
    
    public static class ȫ������ extends HealMap
    {
    }
    
    public static class ��� extends KillAll
    {
    }
    
    public static class �������� extends Fame
    {
    }
    
    public static class ���� extends MobVac
    {
    }
    
    public static class ����ذ� extends cleardrops
    {
    }
    
    public static class �ٻ����� extends Spawn
    {
    }
    
    public static class ��ʱ�� extends Clock
    {
    }
    
    public static class �Զ�ע�� extends autoreg
    {
    }
    
    public static class ������� extends mob
    {
    }
    
    public static class �������� extends setUserLimit
    {
    }
    
    public static class ���״̬ extends BanStatus
    {
    }
    
    public static class ���͹��� extends sendNoticeGG
    {
    }
    
    public static class Debug extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().setDebugMessage(!c.getPlayer().getDebugMessage());
            return 1;
        }
    }
    
    public static class sendNoticeGG extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                c.getPlayer().dropMessage("ʹ�÷���: !���͹��� [��������] - �������˷��͹���");
                return 0;
            }
            final String msg = splitted[1];
            for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                for (final MapleCharacter mch : cserv1.getPlayerStorage().getAllCharacters()) {
                    mch.startMapEffect(msg, 5121009);
                }
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!���͹��� [��������] - �������˷��͹���").toString();
        }
    }
    
    public static class BanStatus extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return 0;
            }
            final String name = splitted[1];
            String mac = "";
            String ip = "";
            int acid = 0;
            boolean Systemban = false;
            boolean ACbanned = false;
            boolean IPbanned = false;
            boolean MACbanned = false;
            String reason = null;
            try {
                final Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = (PreparedStatement)con.prepareStatement("select accountid from characters where name = ?");
                ps.setString(1, name);
                try (final ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        acid = rs.getInt("accountid");
                    }
                }
                ps = (PreparedStatement)con.prepareStatement("select banned, banreason, macs, Sessionip from accounts where id = ?");
                ps.setInt(1, acid);
                try (final ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Systemban = (rs.getInt("banned") == 2);
                        ACbanned = (rs.getInt("banned") == 1 || rs.getInt("banned") == 2);
                        reason = rs.getString("banreason");
                        mac = rs.getString("macs");
                        ip = rs.getString("Sessionip");
                    }
                }
                ps.close();
            }
            catch (Exception ex) {}
            if (reason == null || reason == "") {
                reason = "?";
            }
            if (c.isBannedIP(ip)) {
                IPbanned = true;
            }
            if (c.isBannedMac(mac)) {
                MACbanned = true;
            }
            c.getPlayer().dropMessage("���[" + name + "] �ʺ�ID[" + acid + "]�Ƿ񱻷���: " + (ACbanned ? "��" : "��") + (Systemban ? "(ϵͳ�Զ�����)" : "") + ", ԭ��: " + reason);
            c.getPlayer().dropMessage("IP: " + ip + " �Ƿ��ڷ���IP����: " + (IPbanned ? "��" : "��"));
            c.getPlayer().dropMessage("MAC: " + mac + " �Ƿ��ڷ���MAC����: " + (MACbanned ? "��" : "��"));
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!BanStatus <���a����> - �d�ݪ��a�O���Q����έ��").toString();
        }
    }
    
    public static class setUserLimit extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            int UserLimit = LoginServer.getUserLimit();
            try {
                UserLimit = Integer.parseInt(splitted[1]);
            }
            catch (Exception ex) {}
            LoginServer.setUserLimit(UserLimit);
            c.getPlayer().dropMessage("���������������Ѹ���Ϊ" + UserLimit);
            return 1;
        }
    }
    
    public static class SavePlayerShops extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                cserv.closeAllMerchant();
            }
            c.getPlayer().dropMessage(6, "��Ӷ���˴������.");
            return 1;
        }
    }
    
    public static class Shutdown extends CommandExecute
    {
        private static Thread t;
        
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().dropMessage(6, "�رշ�����...");
            if (Shutdown.t == null || !Shutdown.t.isAlive()) {
                (Shutdown.t = new Thread(ShutdownServer.getInstance())).start();
            }
            else {
                c.getPlayer().dropMessage(6, "����ִ����...");
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!shutdown - �رշ�����").toString();
        }
        
        static {
            Shutdown.t = null;
        }
    }
    
    public static class ShutdownTime extends CommandExecute
    {
        private static ScheduledFuture<?> ts;
        private int minutesLeft;
        private static Thread t;
        
        public ShutdownTime() {
            this.minutesLeft = 0;
        }
        
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return 0;
            }
            this.minutesLeft = Integer.parseInt(splitted[1]);
            c.getPlayer().dropMessage(6, "���������� " + this.minutesLeft + "���Ӻ�ر�. �뾡�ٹرվ������� ������.");
            if (ShutdownTime.ts == null && (ShutdownTime.t == null || !ShutdownTime.t.isAlive())) {
                ShutdownTime.t = new Thread(ShutdownServer.getInstance());
                ShutdownTime.ts = Timer.EventTimer.getInstance().register(new Runnable() {
                    @Override
                    public void run() {
                        if (ShutdownTime.this.minutesLeft == 0) {
                            ShutdownServer.getInstance().run();
                            ShutdownTime.t.start();
                            ShutdownTime.ts.cancel(false);
                            return;
                        }
                        final StringBuilder message = new StringBuilder();
                        message.append("[ð�յ�����] ���������� ");
                        message.append(ShutdownTime.this.minutesLeft);
                        message.append("���Ӻ�ر�. �뾡�ٹرվ������� ������.");
                        World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, message.toString()).getBytes());
                        World.Broadcast.broadcastMessage(MaplePacketCreator.serverMessage(message.toString()).getBytes());
                        for (final ChannelServer cs : ChannelServer.getAllInstances()) {
                            cs.setServerMessage("���������� " + ShutdownTime.this.minutesLeft + " ���Ӻ���");
                        }
                        ShutdownTime.this.minutesLeft--;
                    }
                }, 60000L);
            }
            else {
                c.getPlayer().dropMessage(6, "�������ر�ʱ���޸�Ϊ " + this.minutesLeft + "���Ӻ����Եȷ������ر�");
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!shutdowntime <����> - �رշ�����").toString();
        }
        
        static {
            ShutdownTime.ts = null;
            ShutdownTime.t = null;
        }
    }
    
    public static class SaveAll extends CommandExecute
    {
        private int p;
        
        public SaveAll() {
            this.p = 0;
        }
        
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                final List<MapleCharacter> chrs = cserv.getPlayerStorage().getAllCharactersThreadSafe();
                for (final MapleCharacter chr : chrs) {
                    ++this.p;
                    chr.saveToDB(false, false);
                }
            }
            c.getPlayer().dropMessage("[����] " + this.p + "��������ݱ��浽������.");
            this.p = 0;
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!saveall - �������н�ɫ�Y��").toString();
        }
    }
    
    public static class LowHP extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().getStat().setHp(1);
            c.getPlayer().getStat().setMp(1);
            c.getPlayer().updateSingleStat(MapleStat.HP, 1);
            c.getPlayer().updateSingleStat(MapleStat.MP, 1);
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!lowhp - Ѫħ���").toString();
        }
    }
    
    public static class Heal extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().getStat().setHp(c.getPlayer().getStat().getCurrentMaxHp());
            c.getPlayer().getStat().setMp(c.getPlayer().getStat().getCurrentMaxMp());
            c.getPlayer().updateSingleStat(MapleStat.HP, c.getPlayer().getStat().getCurrentMaxHp());
            c.getPlayer().updateSingleStat(MapleStat.MP, c.getPlayer().getStat().getCurrentMaxMp());
            c.getPlayer().dispelDebuffs();
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!heal - ����Ѫħ").toString();
        }
    }
    
    public static class UnbanIP extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return 0;
            }
            final byte ret_ = MapleClient.unbanIPMacs(splitted[1]);
            if (ret_ == -2) {
                c.getPlayer().dropMessage(6, "[unbanip] SQL ����.");
            }
            else if (ret_ == -1) {
                c.getPlayer().dropMessage(6, "[unbanip] ��ɫ������.");
            }
            else if (ret_ == 0) {
                c.getPlayer().dropMessage(6, "[unbanip] No IP or Mac with that character exists!");
            }
            else if (ret_ == 1) {
                c.getPlayer().dropMessage(6, "[unbanip] IP��Mac�ѽ�������һ��.");
            }
            else if (ret_ == 2) {
                c.getPlayer().dropMessage(6, "[unbanip] IP�Լ�Mac�ѳɹ�����.");
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!unbanip <�������> - �������").toString();
        }
    }
    
    public static class TempBan extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            final String name = splitted[1];
            final int ch = World.Find.findChannel(name);
            if (ch <= 0) {
                return 0;
            }
            final MapleCharacter victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            final int reason = Integer.parseInt(splitted[2]);
            final int numDay = Integer.parseInt(splitted[3]);
            final Calendar cal = Calendar.getInstance();
            cal.add(5, numDay);
            final DateFormat df = DateFormat.getInstance();
            if (victim == null) {
                c.getPlayer().dropMessage(6, "[tempban] �Ҳ���Ŀ���ɫ");
            }
            else {
                victim.tempban("��" + c.getPlayer().getName() + "��ʱ������", cal, reason, true);
                c.getPlayer().dropMessage(6, "[tempban] " + splitted[1] + " �ѳɹ�����ʱ������ " + df.format(cal.getTime()));
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!tempban <�������> - ��ʱ�������").toString();
        }
    }
    
    public static class Kill extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            final MapleCharacter player = c.getPlayer();
            if (splitted.length < 2) {
                return 0;
            }
            for (int i = 1; i < splitted.length; ++i) {
                final String name = splitted[1];
                final int ch = World.Find.findChannel(name);
                if (ch <= 0) {
                    return 0;
                }
                final MapleCharacter victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
                if (victim == null) {
                    c.getPlayer().dropMessage(6, "[kill] ��� " + splitted[i] + " ������.");
                }
                else if (player.allowedToTarget(victim)) {
                    victim.getStat().setHp(0);
                    victim.getStat().setMp(0);
                    victim.updateSingleStat(MapleStat.HP, 0);
                    victim.updateSingleStat(MapleStat.MP, 0);
                }
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!kill <�������1> <�������2> ...  - ɱ�����").toString();
        }
    }
    
    public static class Skill extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return 0;
            }
            final ISkill skill = SkillFactory.getSkill(Integer.parseInt(splitted[1]));
            byte level = (byte)CommandProcessorUtil.getOptionalIntArg(splitted, 2, 1);
            final byte masterlevel = (byte)CommandProcessorUtil.getOptionalIntArg(splitted, 3, 1);
            if (level > skill.getMaxLevel()) {
                level = skill.getMaxLevel();
            }
            c.getPlayer().changeSkillLevel(skill, level, masterlevel);
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!skill <����ID> [���ܵȼ�] [�������ȼ�] ...  - ѧϰ����").toString();
        }
    }
    
    public static class Fame extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            final MapleCharacter player = c.getPlayer();
            if (splitted.length < 2) {
                c.getPlayer().dropMessage("!fame <��ɫ����> <����> ...  - ����");
                return 0;
            }
            final String name = splitted[1];
            final int ch = World.Find.findChannel(name);
            if (ch <= 0) {
                return 0;
            }
            final MapleCharacter victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            short fame;
            try {
                fame = Short.parseShort(splitted[2]);
            }
            catch (NumberFormatException nfe) {
                c.getPlayer().dropMessage(6, "���Ϸ�������");
                return 0;
            }
            if (victim != null && player.allowedToTarget(victim)) {
                victim.addFame(fame);
                victim.updateSingleStat(MapleStat.FAME, victim.getFame());
            }
            else {
                c.getPlayer().dropMessage(6, "[fame] ��ɫ������");
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!fame <��ɫ����> <����> ...  - ����").toString();
        }
    }
    
    public static class autoreg extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().dropMessage("Ŀǰ�Զ�ע���Ѿ� " + ServerConstants.ChangeAutoReg());
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!autoreg  - �Զ�ע�Ὺ��").toString();
        }
    }
    
    public static class HealMap extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            final MapleCharacter player = c.getPlayer();
            for (final MapleCharacter mch : player.getMap().getCharacters()) {
                if (mch != null) {
                    mch.getStat().setHp(mch.getStat().getMaxHp());
                    mch.updateSingleStat(MapleStat.HP, mch.getStat().getMaxHp());
                    mch.getStat().setMp(mch.getStat().getMaxMp());
                    mch.updateSingleStat(MapleStat.MP, mch.getStat().getMaxMp());
                    mch.dispelDebuffs();
                }
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!healmap  - ������ͼ�����е���").toString();
        }
    }
    
    public static class GodMode extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            final MapleCharacter player = c.getPlayer();
            if (player.isInvincible()) {
                player.setInvincible(false);
                player.dropMessage(6, "�޵��Ѿ��ر�");
            }
            else {
                player.setInvincible(true);
                player.dropMessage(6, "�޵��Ѿ�����.");
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!godmode  - �޵п���").toString();
        }
    }
    
    public static class GiveSkill extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 3) {
                return 0;
            }
            final String name = splitted[1];
            final int ch = World.Find.findChannel(name);
            if (ch <= 0) {
                return 0;
            }
            final MapleCharacter victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            final ISkill skill = SkillFactory.getSkill(Integer.parseInt(splitted[2]));
            byte level = (byte)CommandProcessorUtil.getOptionalIntArg(splitted, 3, 1);
            final byte masterlevel = (byte)CommandProcessorUtil.getOptionalIntArg(splitted, 4, 1);
            if (level > skill.getMaxLevel()) {
                level = skill.getMaxLevel();
            }
            victim.changeSkillLevel(skill, level, masterlevel);
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!giveskill <�������> <����ID> [���ܵȼ�] [�������ȼ�] - ���輼��").toString();
        }
    }
    
    public static class SP extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().setRemainingSp(CommandProcessorUtil.getOptionalIntArg(splitted, 1, 1));
            c.sendPacket(MaplePacketCreator.updateSp(c.getPlayer(), false));
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!sp [����] - ����SP").toString();
        }
    }
    
    public static class AP extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().setRemainingAp((short)CommandProcessorUtil.getOptionalIntArg(splitted, 1, 1));
            c.getPlayer().updateSingleStat(MapleStat.AVAILABLEAP, CommandProcessorUtil.getOptionalIntArg(splitted, 1, 1));
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!ap [����] - ����AP").toString();
        }
    }
    
    public static class Shop extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            final MapleShopFactory shop = MapleShopFactory.getInstance();
            int shopId;
            try {
                shopId = Integer.parseInt(splitted[1]);
            }
            catch (NumberFormatException ex) {
                return 0;
            }
            if (shop.getShop(shopId) != null) {
                shop.getShop(shopId).sendShop(c);
            }
            else {
                c.getPlayer().dropMessage(5, "���̵�ID������");
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!shop - �����̵�").toString();
        }
    }
    
    public static class �ؼ�ʱ�� extends CommandExecute
    {
        protected static ScheduledFuture<?> ts;
        
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 1) {
                return 0;
            }
            if (�ؼ�ʱ��.ts != null) {
                �ؼ�ʱ��.ts.cancel(false);
                c.getPlayer().dropMessage(0, "ԭ���Ĺؼ�ʱ����ȡ��");
            }
            int minutesLeft;
            try {
                minutesLeft = Integer.parseInt(splitted[1]);
            }
            catch (NumberFormatException ex) {
                return 0;
            }
            if (minutesLeft > 0) {
                �ؼ�ʱ��.ts = Timer.EventTimer.getInstance().schedule(new Runnable() {
                    @Override
                    public void run() {
                        for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                            for (final MapleCharacter mch : cserv.getPlayerStorage().getAllCharacters()) {
                                if (!c.getPlayer().isGM()) {
                                    NPCScriptManager.getInstance().start(mch.getClient(), 9010010);
                                }
                            }
                        }
                        World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "�ؼ�ʱ���Ѿ���ʼ��!!!").getBytes());
                        World.Broadcast.broadcastMessage(MaplePacketCreator.serverMessage("�ؼ�ʱ���Ѿ���ʼ��!!!").getBytes());
                        �ؼ�ʱ��.ts.cancel(false);
                        �ؼ�ʱ��.ts = null;
                    }
                }, minutesLeft * 60 * 1000);
                c.getPlayer().dropMessage(0, "�ؼ�ʱ��Ԥ�������");
            }
            else {
                c.getPlayer().dropMessage(0, "�趨��ʱ����� > 0��");
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!�ؼ�ʱ�� <ʱ��:����> - �ؼ�ʱ��").toString();
        }
        
        static {
            �ؼ�ʱ��.ts = null;
        }
    }
    
    public static class GainCash extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 3) {
                return 0;
            }
            MapleCharacter player = c.getPlayer();
            int amount = 0;
            String name = "";
            try {
                amount = Integer.parseInt(splitted[1]);
                name = splitted[2];
            }
            catch (Exception ex) {
                c.getPlayer().dropMessage("����Ҳ�����");
                return 1;
            }
            final int ch = World.Find.findChannel(name);
            if (ch <= 0) {
                c.getPlayer().dropMessage("����Ҳ�����");
                return 1;
            }
            player = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            if (player == null) {
                c.getPlayer().dropMessage("����Ҳ�����");
                return 1;
            }
            player.modifyCSPoints(1, amount, true);
            player.dropMessage("�Ѿ��յ����" + amount + "��");
            final String msg = "[GM ����] GM " + c.getPlayer().getName() + " �o�� " + player.getName() + " ��� " + amount + "��";
            FileoutputUtil.logToFile("logs/Data/������.txt", "\r\n " + FileoutputUtil.NowTime() + " GM " + c.getPlayer().getName() + " ���� " + player.getName() + " ��� " + amount + "��");
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!gaingash <����> <���> - ȡ��Gash����").toString();
        }
    }
    
    public static class GainMaplePoint extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 3) {
                return 0;
            }
            final int amount = Integer.parseInt(splitted[1]);
            final String name = splitted[2];
            final int ch = World.Find.findChannel(name);
            if (ch <= 0) {
                return 0;
            }
            final MapleCharacter player = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            if (player == null) {
                return 0;
            }
            player.modifyCSPoints(2, amount, true);
            final String msg = "[GM ����] GM " + c.getPlayer().getName() + " ���� " + player.getName() + " ��Ҷ���� " + amount + "��";
            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, msg).getBytes());
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!gainmaplepoint <����> <���> - ȡ�÷�Ҷ����").toString();
        }
    }
    
    public static class GainPoint extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 3) {
                return 0;
            }
            final int amount = Integer.parseInt(splitted[1]);
            final String name = splitted[2];
            final int ch = World.Find.findChannel(name);
            if (ch <= 0) {
                return 0;
            }
            final MapleCharacter player = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            if (player == null) {
                return 0;
            }
            player.setPoints(player.getPoints() + amount);
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!gainpoint <����> <���> - ȡ��Point").toString();
        }
    }
    
    public static class GainVP extends GainPoint
    {
    }
    
    public static class LevelUp extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                c.getPlayer().levelUp();
            }
            else {
                int up = 0;
                try {
                    up = Integer.parseInt(splitted[1]);
                }
                catch (Exception ex) {}
                for (int i = 0; i < up; ++i) {
                    c.getPlayer().levelUp();
                }
            }
            c.getPlayer().setExp(0);
            c.getPlayer().updateSingleStat(MapleStat.EXP, 0);
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!levelup - �ȼ�����").toString();
        }
    }
    
    public static class UnlockInv extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            final java.util.Map<IItem, MapleInventoryType> eqs = new ArrayMap<IItem, MapleInventoryType>();
            boolean add = false;
            if (splitted.length < 2 || splitted[1].equals("ȫ��")) {
                for (final MapleInventoryType type : MapleInventoryType.values()) {
                    for (final IItem item : c.getPlayer().getInventory(type)) {
                        if (ItemFlag.LOCK.check(item.getFlag())) {
                            item.setFlag((byte)(item.getFlag() - ItemFlag.LOCK.getValue()));
                            add = true;
                            c.getPlayer().reloadC();
                            c.getPlayer().dropMessage(5, "�Ѿ�����");
                        }
                        if (ItemFlag.UNTRADEABLE.check(item.getFlag())) {
                            item.setFlag((byte)(item.getFlag() - ItemFlag.UNTRADEABLE.getValue()));
                            add = true;
                            c.getPlayer().reloadC();
                            c.getPlayer().dropMessage(5, "�Ѿ�����");
                        }
                        if (add) {
                            eqs.put(item, type);
                        }
                        add = false;
                    }
                }
            }
            else if (splitted[1].equals("��װ������")) {
                for (final IItem item2 : c.getPlayer().getInventory(MapleInventoryType.EQUIPPED)) {
                    if (ItemFlag.LOCK.check(item2.getFlag())) {
                        item2.setFlag((byte)(item2.getFlag() - ItemFlag.LOCK.getValue()));
                        add = true;
                        c.getPlayer().reloadC();
                        c.getPlayer().dropMessage(5, "�Ѿ�����");
                    }
                    if (ItemFlag.UNTRADEABLE.check(item2.getFlag())) {
                        item2.setFlag((byte)(item2.getFlag() - ItemFlag.UNTRADEABLE.getValue()));
                        add = true;
                        c.getPlayer().reloadC();
                        c.getPlayer().dropMessage(5, "�ѽ����i");
                    }
                    if (add) {
                        eqs.put(item2, MapleInventoryType.EQUIP);
                    }
                    add = false;
                }
            }
            else if (splitted[1].equals("����")) {
                for (final IItem item2 : c.getPlayer().getInventory(MapleInventoryType.EQUIP)) {
                    if (ItemFlag.LOCK.check(item2.getFlag())) {
                        item2.setFlag((byte)(item2.getFlag() - ItemFlag.LOCK.getValue()));
                        add = true;
                        c.getPlayer().reloadC();
                        c.getPlayer().dropMessage(5, "�Ѿ�����");
                    }
                    if (ItemFlag.UNTRADEABLE.check(item2.getFlag())) {
                        item2.setFlag((byte)(item2.getFlag() - ItemFlag.UNTRADEABLE.getValue()));
                        add = true;
                        c.getPlayer().reloadC();
                        c.getPlayer().dropMessage(5, "�Ѿ�����");
                    }
                    if (add) {
                        eqs.put(item2, MapleInventoryType.EQUIP);
                    }
                    add = false;
                }
            }
            else if (splitted[1].equals("����")) {
                for (final IItem item2 : c.getPlayer().getInventory(MapleInventoryType.USE)) {
                    if (ItemFlag.LOCK.check(item2.getFlag())) {
                        item2.setFlag((byte)(item2.getFlag() - ItemFlag.LOCK.getValue()));
                        add = true;
                        c.getPlayer().reloadC();
                        c.getPlayer().dropMessage(5, "�Ѿ�����");
                    }
                    if (ItemFlag.UNTRADEABLE.check(item2.getFlag())) {
                        item2.setFlag((byte)(item2.getFlag() - ItemFlag.UNTRADEABLE.getValue()));
                        add = true;
                        c.getPlayer().reloadC();
                        c.getPlayer().dropMessage(5, "�Ѿ�����");
                    }
                    if (add) {
                        eqs.put(item2, MapleInventoryType.USE);
                    }
                    add = false;
                }
            }
            else if (splitted[1].equals("װ��")) {
                for (final IItem item2 : c.getPlayer().getInventory(MapleInventoryType.SETUP)) {
                    if (ItemFlag.LOCK.check(item2.getFlag())) {
                        item2.setFlag((byte)(item2.getFlag() - ItemFlag.LOCK.getValue()));
                        add = true;
                        c.getPlayer().reloadC();
                        c.getPlayer().dropMessage(5, "�Ѿ�����");
                    }
                    if (ItemFlag.UNTRADEABLE.check(item2.getFlag())) {
                        item2.setFlag((byte)(item2.getFlag() - ItemFlag.UNTRADEABLE.getValue()));
                        add = true;
                        c.getPlayer().reloadC();
                        c.getPlayer().dropMessage(5, "�Ѿ�����");
                    }
                    if (add) {
                        eqs.put(item2, MapleInventoryType.SETUP);
                    }
                    add = false;
                }
            }
            else if (splitted[1].equals("����")) {
                for (final IItem item2 : c.getPlayer().getInventory(MapleInventoryType.ETC)) {
                    if (ItemFlag.LOCK.check(item2.getFlag())) {
                        item2.setFlag((byte)(item2.getFlag() - ItemFlag.LOCK.getValue()));
                        add = true;
                        c.getPlayer().reloadC();
                        c.getPlayer().dropMessage(5, "�Ѿ�����");
                    }
                    if (ItemFlag.UNTRADEABLE.check(item2.getFlag())) {
                        item2.setFlag((byte)(item2.getFlag() - ItemFlag.UNTRADEABLE.getValue()));
                        add = true;
                        c.getPlayer().reloadC();
                        c.getPlayer().dropMessage(5, "�Ѿ�����");
                    }
                    if (add) {
                        eqs.put(item2, MapleInventoryType.ETC);
                    }
                    add = false;
                }
            }
            else {
                if (!splitted[1].equals("����")) {
                    return 0;
                }
                for (final IItem item2 : c.getPlayer().getInventory(MapleInventoryType.CASH)) {
                    if (ItemFlag.LOCK.check(item2.getFlag())) {
                        item2.setFlag((byte)(item2.getFlag() - ItemFlag.LOCK.getValue()));
                        add = true;
                        c.getPlayer().reloadC();
                        c.getPlayer().dropMessage(5, "�Ѿ�����");
                    }
                    if (ItemFlag.UNTRADEABLE.check(item2.getFlag())) {
                        item2.setFlag((byte)(item2.getFlag() - ItemFlag.UNTRADEABLE.getValue()));
                        add = true;
                        c.getPlayer().reloadC();
                        c.getPlayer().dropMessage(5, "�Ѿ�����");
                    }
                    if (add) {
                        eqs.put(item2, MapleInventoryType.CASH);
                    }
                    add = false;
                }
            }
            for (final java.util.Map.Entry<IItem, MapleInventoryType> eq : eqs.entrySet()) {
                c.getPlayer().forceReAddItem_NoUpdate(eq.getKey().copy(), eq.getValue());
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!unlockinv <ȫ��/��װ������/����/����/װ��/����/����> - ��������").toString();
        }
    }
    
    public static class Item extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return 0;
            }
            int itemId = 0;
            try {
                itemId = Integer.parseInt(splitted[1]);
            }
            catch (Exception ex) {}
            final short quantity = (short)CommandProcessorUtil.getOptionalIntArg(splitted, 2, 1);
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            if (GameConstants.isPet(itemId)) {
                final MaplePet pet = MaplePet.createPet(itemId, MapleInventoryIdentifier.getInstance());
                if (pet != null) {
                    MapleInventoryManipulator.addById(c, itemId, (short)1, c.getPlayer().getName(), pet, 90L, (byte)0);
                }
            }
            else if (!ii.itemExists(itemId)) {
                c.getPlayer().dropMessage(5, itemId + " - ��Ʒ������");
            }
            else {
                byte flag = 0;
                flag |= (byte)ItemFlag.LOCK.getValue();
                IItem item;
                if (GameConstants.getInventoryType(itemId) == MapleInventoryType.EQUIP) {
                    item = ii.randomizeStats((Equip)ii.getEquipById(itemId));
                }
                else {
                    item = new client.inventory.Item(itemId, (short)0, quantity, (byte)0);
                    if (GameConstants.getInventoryType(itemId) != MapleInventoryType.USE) {}
                }
                item.setOwner(c.getPlayer().getName());
                item.setGMLog(c.getPlayer().getName());
                MapleInventoryManipulator.addbyItem(c, item);
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!item <����ID> - ȡ�õ���").toString();
        }
    }
    
    public static class serverMsg extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length > 1) {
                final StringBuilder sb = new StringBuilder();
                sb.append(StringUtil.joinStringFrom(splitted, 1));
                for (final ChannelServer ch : ChannelServer.getAllInstances()) {
                    ch.setServerMessage(sb.toString());
                }
                World.Broadcast.broadcastMessage(MaplePacketCreator.serverMessage(sb.toString()).getBytes());
                return 1;
            }
            return 0;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!servermsg ѶϢ - �����Ϸ��Sɫ����").toString();
        }
    }
    
    public static class Say extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length > 1) {
                final StringBuilder sb = new StringBuilder();
                sb.append("[");
                sb.append(c.getPlayer().getName());
                sb.append("] ");
                sb.append(StringUtil.joinStringFrom(splitted, 1));
                World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, sb.toString()).getBytes());
                return 1;
            }
            return 0;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!say ѶϢ - ����������").toString();
        }
    }
    
    public static class Letter extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 3) {
                c.getPlayer().dropMessage(6, "ָ�����: ");
                return 0;
            }
            int start;
            int nstart;
            if (splitted[1].equalsIgnoreCase("green")) {
                start = 3991026;
                nstart = 3990019;
            }
            else {
                if (!splitted[1].equalsIgnoreCase("red")) {
                    c.getPlayer().dropMessage(6, "δ֪���ɫ!");
                    return 1;
                }
                start = 3991000;
                nstart = 3990009;
            }
            String splitString = StringUtil.joinStringFrom(splitted, 2);
            final List<Integer> chars = new ArrayList<Integer>();
            splitString = splitString.toUpperCase();
            for (int i = 0; i < splitString.length(); ++i) {
                final char chr = splitString.charAt(i);
                if (chr == ' ') {
                    chars.add(-1);
                }
                else if (chr >= 'A' && chr <= 'Z') {
                    chars.add((int)chr);
                }
                else if (chr >= '0' && chr <= '9') {
                    chars.add(chr + '?');
                }
            }
            final int w = 32;
            int dStart = c.getPlayer().getPosition().x - splitString.length() / 2 * 32;
            for (final Integer j : chars) {
                if (j == -1) {
                    dStart += 32;
                }
                else if (j < 200) {
                    final int val = start + j - 65;
                    final client.inventory.Item item = new client.inventory.Item(val, (byte)0, (short)1);
                    c.getPlayer().getMap().spawnItemDrop(c.getPlayer(), c.getPlayer(), item, new Point(dStart, c.getPlayer().getPosition().y), false, false);
                    dStart += 32;
                }
                else {
                    if (j < 200 || j > 300) {
                        continue;
                    }
                    final int val = nstart + j - 48 - 200;
                    final client.inventory.Item item = new client.inventory.Item(val, (byte)0, (short)1);
                    c.getPlayer().getMap().spawnItemDrop(c.getPlayer(), c.getPlayer(), item, new Point(dStart, c.getPlayer().getPosition().y), false, false);
                    dStart += 32;
                }
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append(" !letter <color (green/red)> <word> - ����").toString();
        }
    }
    
    public static class Marry extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 3) {
                return 0;
            }
            final int itemId = Integer.parseInt(splitted[2]);
            if (!GameConstants.isEffectRing(itemId)) {
                c.getPlayer().dropMessage(6, "����Ľ�ָID.");
            }
            else {
                final String name = splitted[1];
                final int ch = World.Find.findChannel(name);
                if (ch <= 0) {
                    c.getPlayer().dropMessage(6, "��ұ�������");
                    return 0;
                }
                final MapleCharacter fff = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
                if (fff == null) {
                    c.getPlayer().dropMessage(6, "��ұ�������");
                }
                else {
                    final int[] ringID = { MapleInventoryIdentifier.getInstance(), MapleInventoryIdentifier.getInstance() };
                    try {
                        final MapleCharacter[] chrz = { fff, c.getPlayer() };
                        for (int i = 0; i < chrz.length; ++i) {
                            final Equip eq = (Equip)MapleItemInformationProvider.getInstance().getEquipById(itemId);
                            if (eq == null) {
                                c.getPlayer().dropMessage(6, "����Ľ�ָID.");
                                return 1;
                            }
                            eq.setUniqueId(ringID[i]);
                            MapleInventoryManipulator.addbyItem(chrz[i].getClient(), eq.copy());
                            chrz[i].dropMessage(6, "�ɹ���  " + chrz[i].getName() + " ���");
                        }
                        MapleRing.addToDB(itemId, c.getPlayer(), fff.getName(), fff.getId(), ringID);
                    }
                    catch (SQLException ex) {}
                }
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!marry <�������> <��ָ����> - ���").toString();
        }
    }
    
    public static class ItemCheck extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 3 || splitted[1] == null || splitted[1].equals("") || splitted[2] == null || splitted[2].equals("")) {
                return 0;
            }
            final int item = Integer.parseInt(splitted[2]);
            final String name = splitted[1];
            final int ch = World.Find.findChannel(name);
            if (ch <= 0) {
                c.getPlayer().dropMessage(6, "��ұ�������");
                return 0;
            }
            final MapleCharacter chr = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            final int itemamount = chr.getItemQuantity(item, true);
            if (itemamount > 0) {
                c.getPlayer().dropMessage(6, chr.getName() + " �� " + itemamount + " (" + item + ").");
            }
            else {
                c.getPlayer().dropMessage(6, chr.getName() + " ���]�� (" + item + ")");
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!itemcheck <playername> <itemid> - �����Ʒ").toString();
        }
    }
    
    public static class MobVac extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            for (final MapleMapObject mmo : c.getPlayer().getMap().getAllMonstersThreadsafe()) {
                final MapleMonster monster = (MapleMonster)mmo;
                c.getPlayer().getMap().broadcastMessage(MobPacket.moveMonster(false, -1, 0, 0, 0, 0, monster.getObjectId(), monster.getPosition(), c.getPlayer().getPosition(), c.getPlayer().getLastRes()));
                monster.setPosition(c.getPlayer().getPosition());
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!mobvac - ȫͼ����").toString();
        }
    }
    
    public static class Song extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return 0;
            }
            c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.musicChange(splitted[1]));
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!song - ��������").toString();
        }
    }
    
    public static class �����Զ�� extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            final EventManager em = c.getChannelServer().getEventSM().getEventManager("AutomatedEvent");
            if (em != null) {
                em.scheduleRandomEvent();
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!�����Զ�� - �����Զ��").toString();
        }
    }
    
    public static class ���ʼ extends CommandExecute
    {
        private static ScheduledFuture<?> ts;
        private int min;
        
        public ���ʼ() {
            this.min = 1;
        }
        
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (c.getChannelServer().getEvent() == c.getPlayer().getMapId()) {
                MapleEvent.setEvent(c.getChannelServer(), false);
                c.getPlayer().dropMessage(5, "�Ѿ��رջ��ڣ�����ʹ�� !���ʼ ��������");
                World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "�l��:" + c.getChannel() + "�Ŀǰ�Ѿ��رմ��ſڡ�").getBytes());
                c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.getClock(60));
                ���ʼ.ts = Timer.EventTimer.getInstance().register(new Runnable() {
                    @Override
                    public void run() {
                        if (���ʼ.this.min == 0) {
                            MapleEvent.onStartEvent(c.getPlayer());
                            ���ʼ.ts.cancel(false);
                            return;
                        }
                        ���ʼ.this.min--;
                    }
                }, 60000L);
                return 1;
            }
            c.getPlayer().dropMessage(5, "��������ʹ�� !ѡ�� �趨��ǰ�l���Ļ�����ڵ�ǰ�l�����ͼ��ʹ�á�");
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!���ʼ - ���ʼ").toString();
        }
        
        static {
            ���ʼ.ts = null;
        }
    }
    
    public static class �رջ��� extends CommandExecute
    {
        private static boolean tt;
        
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (c.getChannelServer().getEvent() == c.getPlayer().getMapId()) {
                MapleEvent.setEvent(c.getChannelServer(), false);
                c.getPlayer().dropMessage(5, "�Ѿ��رջ��ڣ�����ʹ�� !���ʼ ��������");
                World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "�l��:" + c.getChannel() + "�Ŀǰ�Ѿ��رմ��ſڡ�").getBytes());
                c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.getClock(60));
                Timer.EventTimer.getInstance().register(new Runnable() {
                    @Override
                    public void run() {
                        �رջ���.tt = true;
                    }
                }, 60000L);
                if (�رջ���.tt) {
                    MapleEvent.onStartEvent(c.getPlayer());
                }
                return 1;
            }
            c.getPlayer().dropMessage(5, "��������ʹ�� !ѡ�� �趨��ǰ�l���Ļ�����ڵ�ǰ�l�����ͼ��ʹ�á�");
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!�رջ��� -�رջ���").toString();
        }
        
        static {
            �رջ���.tt = false;
        }
    }
    
    public static class ѡ�� extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            final MapleEventType type = MapleEventType.getByString(splitted[1]);
            if (type == null) {
                final StringBuilder sb = new StringBuilder("Ŀǰ���ŵĻ��: ");
                for (final MapleEventType t : MapleEventType.values()) {
                    sb.append(t.name()).append(",");
                }
                c.getPlayer().dropMessage(5, sb.toString().substring(0, sb.toString().length() - 1));
            }
            final String msg = MapleEvent.scheduleEvent(type, c.getChannelServer());
            if (msg.length() > 0) {
                c.getPlayer().dropMessage(5, msg);
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!ѡ�� - ѡ��").toString();
        }
    }
    
    public static class CheckGash extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return 0;
            }
            final String name = splitted[1];
            final int ch = World.Find.findChannel(name);
            if (ch <= 0) {
                c.getPlayer().dropMessage(6, "��ұ�������");
                return 0;
            }
            final MapleCharacter chrs = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            if (chrs == null) {
                c.getPlayer().dropMessage(5, "�Ҳ����ý�ɫ");
            }
            else {
                c.getPlayer().dropMessage(6, chrs.getName() + " �� " + chrs.getCSPoints(1) + " ����.");
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!checkgash <�������> - ������").toString();
        }
    }
    
    public static class RemoveItem extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 3) {
                return 0;
            }
            final String name = splitted[1];
            final int id = Integer.parseInt(splitted[2]);
            final int ch = World.Find.findChannel(name);
            if (ch <= 0) {
                c.getPlayer().dropMessage(6, "��ұ�������");
                return 0;
            }
            final MapleCharacter chr = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            if (chr == null) {
                c.getPlayer().dropMessage(6, "����Ҳ�������");
            }
            else {
                chr.removeAll(id);
                c.getPlayer().dropMessage(6, "����IDΪ " + id + " �ĵ����Ѿ��� " + name + " ���ϱ��Ƴ���");
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!removeitem <��ɫ����> <��ƷID> - �Ƴ�������ϵĵ���").toString();
        }
    }
    
    public static class KillMap extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            for (final MapleCharacter map : c.getPlayer().getMap().getCharactersThreadsafe()) {
                if (map != null && !map.isGM()) {
                    map.getStat().setHp(0);
                    map.getStat().setMp(0);
                    map.updateSingleStat(MapleStat.HP, 0);
                    map.updateSingleStat(MapleStat.MP, 0);
                }
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!killmap - ɱ���������").toString();
        }
    }
    
    public static class SpeakMega extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            MapleCharacter victim = null;
            if (splitted.length >= 2) {
                victim = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
            }
            try {
                World.Broadcast.broadcastSmega(MaplePacketCreator.serverNotice(3, (victim == null) ? c.getChannel() : victim.getClient().getChannel(), (victim == null) ? splitted[1] : (victim.getName() + " : " + StringUtil.joinStringFrom(splitted, 2)), true).getBytes());
            }
            catch (Exception e) {
                return 0;
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!speakmega [�������] <ѶϢ> - ��ĳ����ҵ��l�����й㲥").toString();
        }
    }
    
    public static class Speak extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            final String name = splitted[1];
            final int ch = World.Find.findChannel(name);
            if (ch <= 0) {
                c.getPlayer().dropMessage(6, "��ұ�������");
                return 0;
            }
            final MapleCharacter victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            if (victim == null) {
                c.getPlayer().dropMessage(5, "�Ҳ��� '" + splitted[1]);
                return 0;
            }
            victim.getMap().broadcastMessage(MaplePacketCreator.getChatText(victim.getId(), StringUtil.joinStringFrom(splitted, 2), victim.isGM(), 0));
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!speak <�������> <ѶϢ> - ��ĳ����ҷ���Ϣ").toString();
        }
    }
    
    public static class SpeakMap extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            for (final MapleCharacter victim : c.getPlayer().getMap().getCharactersThreadsafe()) {
                if (victim.getId() != c.getPlayer().getId()) {
                    victim.getMap().broadcastMessage(MaplePacketCreator.getChatText(victim.getId(), StringUtil.joinStringFrom(splitted, 1), victim.isGM(), 0));
                }
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!speakmap <ѶϢ> - ��Ŀǰ��ͼ���з�����Ϣ").toString();
        }
    }
    
    public static class SpeakChannel extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            for (final MapleCharacter victim : c.getChannelServer().getPlayerStorage().getAllCharacters()) {
                if (victim.getId() != c.getPlayer().getId()) {
                    victim.getMap().broadcastMessage(MaplePacketCreator.getChatText(victim.getId(), StringUtil.joinStringFrom(splitted, 1), victim.isGM(), 0));
                }
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!speakchannel <ѶϢ> - ��ĿǰƵ�����з�����Ϣ").toString();
        }
    }
    
    public static class SpeakWorld extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                for (final MapleCharacter victim : cserv.getPlayerStorage().getAllCharacters()) {
                    if (victim.getId() != c.getPlayer().getId()) {
                        victim.getMap().broadcastMessage(MaplePacketCreator.getChatText(victim.getId(), StringUtil.joinStringFrom(splitted, 1), victim.isGM(), 0));
                    }
                }
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!speakchannel <ѶϢ> - ��Ŀǰ���������д�����Ϣ").toString();
        }
    }
    
    public static class Disease extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 3) {
                return 0;
            }
            int type;
            if (splitted[1].equalsIgnoreCase("SEAL")) {
                type = 120;
            }
            else if (splitted[1].equalsIgnoreCase("DARKNESS")) {
                type = 121;
            }
            else if (splitted[1].equalsIgnoreCase("WEAKEN")) {
                type = 122;
            }
            else if (splitted[1].equalsIgnoreCase("STUN")) {
                type = 123;
            }
            else if (splitted[1].equalsIgnoreCase("CURSE")) {
                type = 124;
            }
            else if (splitted[1].equalsIgnoreCase("POISON")) {
                type = 125;
            }
            else if (splitted[1].equalsIgnoreCase("SLOW")) {
                type = 126;
            }
            else if (splitted[1].equalsIgnoreCase("SEDUCE")) {
                type = 128;
            }
            else if (splitted[1].equalsIgnoreCase("REVERSE")) {
                type = 132;
            }
            else if (splitted[1].equalsIgnoreCase("ZOMBIFY")) {
                type = 133;
            }
            else if (splitted[1].equalsIgnoreCase("POTION")) {
                type = 134;
            }
            else if (splitted[1].equalsIgnoreCase("SHADOW")) {
                type = 135;
            }
            else if (splitted[1].equalsIgnoreCase("BLIND")) {
                type = 136;
            }
            else {
                if (!splitted[1].equalsIgnoreCase("FREEZE")) {
                    return 0;
                }
                type = 137;
            }
            final MapleDisease dis = MapleDisease.getBySkill(type);
            if (splitted.length == 4) {
                final String name = splitted[2];
                final int ch = World.Find.findChannel(name);
                if (ch <= 0) {
                    c.getPlayer().dropMessage(6, "��ұ�������");
                    return 0;
                }
                final MapleCharacter victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
                if (victim == null) {
                    c.getPlayer().dropMessage(5, "�Ҳ��������");
                }
                else {
                    victim.setChair(0);
                    victim.getClient().sendPacket(MaplePacketCreator.cancelChair(-1));
                    victim.getMap().broadcastMessage(victim, MaplePacketCreator.showChair(c.getPlayer().getId(), 0), false);
                    victim.giveDebuff(dis, MobSkillFactory.getMobSkill(type, CommandProcessorUtil.getOptionalIntArg(splitted, 3, 1)));
                }
            }
            else {
                for (final MapleCharacter victim2 : c.getPlayer().getMap().getCharactersThreadsafe()) {
                    victim2.setChair(0);
                    victim2.getClient().sendPacket(MaplePacketCreator.cancelChair(-1));
                    victim2.getMap().broadcastMessage(victim2, MaplePacketCreator.showChair(c.getPlayer().getId(), 0), false);
                    victim2.giveDebuff(dis, MobSkillFactory.getMobSkill(type, CommandProcessorUtil.getOptionalIntArg(splitted, 2, 1)));
                }
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!disease <SEAL/DARKNESS/WEAKEN/STUN/CURSE/POISON/SLOW/SEDUCE/REVERSE/ZOMBIFY/POTION/SHADOW/BLIND/FREEZE> [��ɫ����] <״̬�ȼ�> - ���˵õ�����״̬").toString();
        }
    }
    
    public static class SendAllNote extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length >= 1) {
                final String text = StringUtil.joinStringFrom(splitted, 1);
                for (final MapleCharacter mch : c.getChannelServer().getPlayerStorage().getAllCharacters()) {
                    c.getPlayer().sendNote(mch.getName(), text);
                }
                return 1;
            }
            return 0;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!sendallnote <����> ����Note�oĿǰ�l����������").toString();
        }
    }
    
    public static class giveMeso extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return 0;
            }
            final String name = splitted[1];
            final int gain = Integer.parseInt(splitted[2]);
            final int ch = World.Find.findChannel(name);
            if (ch <= 0) {
                c.getPlayer().dropMessage(6, "��ұ�������");
                return 0;
            }
            final MapleCharacter victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            if (victim == null) {
                c.getPlayer().dropMessage(5, "�Ҳ��� '" + name);
            }
            else {
                victim.gainMeso(gain, true);
                final String msg = "[GM ����] GM " + c.getPlayer().getName() + " ���� " + victim.getName() + " ��� " + gain + "��";
                World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, msg).getBytes());
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!gainmeso <����> <����> - �o��ҽ��").toString();
        }
    }
    
    public static class CloneMe extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().cloneLook();
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!cloneme - ������¡��").toString();
        }
    }
    
    public static class DisposeClones extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().dropMessage(6, c.getPlayer().getCloneSize() + "����¡����ʧ��.");
            c.getPlayer().disposeClones();
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!disposeclones - �ݻٿ�¡��").toString();
        }
    }
    
    public static class Monitor extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return 0;
            }
            final MapleCharacter target = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
            if (target != null) {
                if (target.getClient().isMonitored()) {
                    target.getClient().setMonitored(false);
                    c.getPlayer().dropMessage(5, "Not monitoring " + target.getName() + " anymore.");
                }
                else {
                    target.getClient().setMonitored(true);
                    c.getPlayer().dropMessage(5, "Monitoring " + target.getName() + ".");
                }
            }
            else {
                c.getPlayer().dropMessage(5, "�Ҳ��������");
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!monitor <���> - ��¼�����Ѷ").toString();
        }
    }
    
    public static class PermWeather extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (c.getPlayer().getMap().getPermanentWeather() > 0) {
                c.getPlayer().getMap().setPermanentWeather(0);
                c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.removeMapEffect());
                c.getPlayer().dropMessage(5, "��ͼ�����ѱ�����.");
            }
            else {
                final int weather = CommandProcessorUtil.getOptionalIntArg(splitted, 1, 5120000);
                if (!MapleItemInformationProvider.getInstance().itemExists(weather) || weather / 10000 != 512) {
                    c.getPlayer().dropMessage(5, "��Ч��ID.");
                }
                else {
                    c.getPlayer().getMap().setPermanentWeather(weather);
                    c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.startMapEffect("", weather, false));
                    c.getPlayer().dropMessage(5, "��ͼ����������.");
                }
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!permweather - �趨����").toString();
        }
    }
    
    public static class CharInfo extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return 0;
            }
            final StringBuilder builder = new StringBuilder();
            final String name = splitted[1];
            final int ch = World.Find.findChannel(name);
            if (ch <= 0) {
                c.getPlayer().dropMessage(6, "��ұ�������");
                return 0;
            }
            final MapleCharacter other = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            if (other == null) {
                builder.append("��ɫ������");
                c.getPlayer().dropMessage(6, builder.toString());
            }
            else {
                if (other.getClient().getLastPing() <= 0L) {
                    other.getClient().sendPing();
                }
                builder.append(MapleClient.getLogMessage(other, ""));
                builder.append(" �� ").append(other.getPosition().x);
                builder.append(" /").append(other.getPosition().y);
                builder.append(" || Ѫ�� : ");
                builder.append(other.getStat().getHp());
                builder.append(" /");
                builder.append(other.getStat().getCurrentMaxHp());
                builder.append(" || ħ�� : ");
                builder.append(other.getStat().getMp());
                builder.append(" /");
                builder.append(other.getStat().getCurrentMaxMp());
                builder.append(" || �������� : ");
                builder.append(other.getStat().getTotalWatk());
                builder.append(" || ħ�������� : ");
                builder.append(other.getStat().getTotalMagic());
                builder.append(" || ��߹��� : ");
                builder.append(other.getStat().getCurrentMaxBaseDamage());
                builder.append(" || ����%�� : ");
                builder.append(other.getStat().dam_r);
                builder.append(" || BOSS����%�� : ");
                builder.append(other.getStat().bossdam_r);
                builder.append(" || ���� : ");
                builder.append(other.getStat().getStr());
                builder.append(" || ���� : ");
                builder.append(other.getStat().getDex());
                builder.append(" || ���� : ");
                builder.append(other.getStat().getInt());
                builder.append(" || ���\ : ");
                builder.append(other.getStat().getLuk());
                builder.append(" || ȫ������ : ");
                builder.append(other.getStat().getTotalStr());
                builder.append(" || ȫ������ : ");
                builder.append(other.getStat().getTotalDex());
                builder.append(" || ȫ������ : ");
                builder.append(other.getStat().getTotalInt());
                builder.append(" || ȫ�����\ : ");
                builder.append(other.getStat().getTotalLuk());
                builder.append(" || ���ֵ : ");
                builder.append(other.getExp());
                builder.append(" || �Mꠠ�B : ");
                builder.append(other.getParty() != null);
                builder.append(" || ���נ�B: ");
                builder.append(other.getTrade() != null);
                builder.append(" || Latency: ");
                builder.append(other.getClient().getLatency());
                builder.append(" || ����PING: ");
                builder.append(other.getClient().getLastPing());
                builder.append(" || ����PONG: ");
                builder.append(other.getClient().getLastPong());
                builder.append(" || IP: ");
                builder.append(other.getClient().getSessionIPAddress());
                other.getClient().DebugMessage(builder);
                c.getPlayer().dropMessage(6, builder.toString());
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!charinfo <��ɫ����> - �鿴��ɫ״̬").toString();
        }
    }
    
    public static class whoishere extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            StringBuilder builder = new StringBuilder("�ڴ˵�ͼ�����: ");
            for (final MapleCharacter chr : c.getPlayer().getMap().getCharactersThreadsafe()) {
                if (builder.length() > 150) {
                    builder.setLength(builder.length() - 2);
                    c.getPlayer().dropMessage(6, builder.toString());
                    builder = new StringBuilder();
                }
                builder.append(MapleCharacterUtil.makeMapleReadable(chr.getName()));
                builder.append(", ");
            }
            builder.setLength(builder.length() - 2);
            c.getPlayer().dropMessage(6, builder.toString());
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!whoishere - �鿴Ŀǰ��ͼ�ϵ����").toString();
        }
    }
    
    public static class Cheaters extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            final List<CheaterData> cheaters = World.getCheaters();
            for (int x = cheaters.size() - 1; x >= 0; --x) {
                final CheaterData cheater = cheaters.get(x);
                c.getPlayer().dropMessage(6, cheater.getInfo());
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!cheaters - �鿴���׽�ɫ").toString();
        }
    }
    
    public static class Connected extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            final java.util.Map<Integer, Integer> connected = World.getConnected();
            final StringBuilder conStr = new StringBuilder("�����ӵĿ͑���: ");
            boolean first = true;
            for (final int i : connected.keySet()) {
                if (!first) {
                    conStr.append(", ");
                }
                else {
                    first = false;
                }
                if (i == 0) {
                    conStr.append("����: ");
                    conStr.append(connected.get(i));
                }
                else {
                    conStr.append("�l�� ");
                    conStr.append(i);
                    conStr.append(": ");
                    conStr.append(connected.get(i));
                }
            }
            c.getPlayer().dropMessage(6, conStr.toString());
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!connected - �鿴�����ߵĿ͑���").toString();
        }
    }
    
    public static class ResetQuest extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return 0;
            }
            MapleQuest.getInstance(Integer.parseInt(splitted[1])).forfeit(c.getPlayer());
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!resetquest <����ID> - ��������").toString();
        }
    }
    
    public static class StartQuest extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return 0;
            }
            MapleQuest.getInstance(Integer.parseInt(splitted[1])).start(c.getPlayer(), Integer.parseInt(splitted[2]));
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!startquest <����ID> - ��ʼ����").toString();
        }
    }
    
    public static class CompleteQuest extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return 0;
            }
            MapleQuest.getInstance(Integer.parseInt(splitted[1])).complete(c.getPlayer(), Integer.parseInt(splitted[2]), Integer.parseInt(splitted[3]));
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!completequest <����ID> - �������").toString();
        }
    }
    
    public static class FStartQuest extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return 0;
            }
            MapleQuest.getInstance(Integer.parseInt(splitted[1])).forceStart(c.getPlayer(), Integer.parseInt(splitted[2]), (splitted.length >= 4) ? splitted[3] : null);
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!fstartquest <����ID> - ǿ�ƿ�ʼ����").toString();
        }
    }
    
    public static class FCompleteQuest extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return 0;
            }
            MapleQuest.getInstance(Integer.parseInt(splitted[1])).forceComplete(c.getPlayer(), Integer.parseInt(splitted[2]));
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!fcompletequest <����ID> - ǿ���������").toString();
        }
    }
    
    public static class FStartOther extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            MapleQuest.getInstance(Integer.parseInt(splitted[2])).forceStart(c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]), Integer.parseInt(splitted[3]), (splitted.length >= 4) ? splitted[4] : null);
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!fstartother - ��֪��ɶ").toString();
        }
    }
    
    public static class FCompleteOther extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            MapleQuest.getInstance(Integer.parseInt(splitted[2])).forceComplete(c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]), Integer.parseInt(splitted[3]));
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!fcompleteother - ��֪��ɶ").toString();
        }
    }
    
    public static class NearestPortal extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            final MaplePortal portal = c.getPlayer().getMap().findClosestSpawnpoint(c.getPlayer().getPosition());
            c.getPlayer().dropMessage(6, portal.getName() + " id: " + portal.getId() + " script: " + portal.getScriptName());
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!nearestportal - ��֪��ɶ").toString();
        }
    }
    
    public static class SpawnDebug extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().dropMessage(6, c.getPlayer().getMap().spawnDebug());
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!spawndebug - debug�������").toString();
        }
    }
    
    public static class Threads extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            final Thread[] threads = new Thread[Thread.activeCount()];
            Thread.enumerate(threads);
            String filter = "";
            if (splitted.length > 1) {
                filter = splitted[1];
            }
            for (int i = 0; i < threads.length; ++i) {
                final String tstring = threads[i].toString();
                if (tstring.toLowerCase().contains(filter.toLowerCase())) {
                    c.getPlayer().dropMessage(6, i + ": " + tstring);
                }
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!threads - �鿴Threads��Ѷ").toString();
        }
    }
    
    public static class ShowTrace extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return 0;
            }
            final Thread[] threads = new Thread[Thread.activeCount()];
            Thread.enumerate(threads);
            final Thread t = threads[Integer.parseInt(splitted[1])];
            c.getPlayer().dropMessage(6, t.toString() + ":");
            for (final StackTraceElement elem : t.getStackTrace()) {
                c.getPlayer().dropMessage(6, elem.toString());
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!showtrace - show trace info").toString();
        }
    }
    
    public static class FakeRelog extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            final MapleCharacter player = c.getPlayer();
            c.sendPacket(MaplePacketCreator.getCharInfo(player));
            player.getMap().removePlayer(player);
            player.getMap().addPlayer(player);
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!fakerelog - �ٵǳ��ٵ���").toString();
        }
    }
    
    public static class ToggleOffense extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return 0;
            }
            try {
                final CheatingOffense co = CheatingOffense.valueOf(splitted[1]);
                co.setEnabled(!co.isEnabled());
            }
            catch (IllegalArgumentException iae) {
                c.getPlayer().dropMessage(6, "Offense " + splitted[1] + " not found");
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!toggleoffense <Offense> - ������ر�CheatOffense").toString();
        }
    }
    
    public static class toggleDrop extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().getMap().toggleDrops();
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!toggledrop - ������رյ���").toString();
        }
    }
    
    public static class ToggleMegaphone extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            World.toggleMegaphoneMuteState();
            c.getPlayer().dropMessage(6, "�㲥�Ƿ���� : " + (c.getChannelServer().getMegaphoneMuteState() ? "��" : "��"));
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!togglemegaphone - �������߹رչ㲥").toString();
        }
    }
    
    public static class SpawnReactor extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return 0;
            }
            int id = 0;
            try {
                id = Integer.parseInt(splitted[1]);
            }
            catch (Exception ex) {}
            final MapleReactorStats reactorSt = MapleReactorFactory.getReactor(id);
            final MapleReactor reactor = new MapleReactor(reactorSt, id);
            reactor.setDelay(-1);
            reactor.setPosition(c.getPlayer().getPosition());
            c.getPlayer().getMap().spawnReactor(reactor);
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!spawnreactor - ����Reactor").toString();
        }
    }
    
    public static class HReactor extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return 0;
            }
            c.getPlayer().getMap().getReactorByOid(Integer.parseInt(splitted[1])).hitReactor(c);
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!hitreactor - ����Reactor").toString();
        }
    }
    
    public static class DestroyReactor extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return 0;
            }
            final MapleMap map = c.getPlayer().getMap();
            final List<MapleMapObject> reactors = map.getMapObjectsInRange(c.getPlayer().getPosition(), Double.POSITIVE_INFINITY, Arrays.asList(MapleMapObjectType.REACTOR));
            if (splitted[1].equals("all")) {
                for (final MapleMapObject reactorL : reactors) {
                    final MapleReactor reactor2l = (MapleReactor)reactorL;
                    c.getPlayer().getMap().destroyReactor(reactor2l.getObjectId());
                }
            }
            else {
                c.getPlayer().getMap().destroyReactor(Integer.parseInt(splitted[1]));
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!drstroyreactor - �Ƴ�Reactor").toString();
        }
    }
    
    public static class ResetReactors extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().getMap().resetReactors();
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!resetreactors - ���ô˵�ͼ���е�Reactor").toString();
        }
    }
    
    public static class SetReactor extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return 0;
            }
            c.getPlayer().getMap().setReactorState(Byte.parseByte(splitted[1]));
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!hitreactor - ����Reactor").toString();
        }
    }
    
    public static class cleardrops extends RemoveDrops
    {
    }
    
    public static class RemoveDrops extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().dropMessage(5, "����� " + c.getPlayer().getMap().getNumItems() + " ��������");
            c.getPlayer().getMap().removeDrops();
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!removedrops - �Ƴ����ϵ���Ʒ").toString();
        }
    }
    
    public static class ExpRate extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length > 1) {
                final int rate = Integer.parseInt(splitted[1]);
                if (splitted.length > 2 && splitted[2].equalsIgnoreCase("all")) {
                    for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                        cserv.setExpRate(rate);
                    }
                }
                else {
                    c.getChannelServer().setExpRate(rate);
                }
                c.getPlayer().dropMessage(6, "���鱶���Ѹı��Ϊ " + rate + "x");
                return 1;
            }
            return 0;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!exprate <����> - ���ľ��鱶��").toString();
        }
    }
    
    public static class DropRate extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length > 1) {
                final int rate = Integer.parseInt(splitted[1]);
                if (splitted.length > 2 && splitted[2].equalsIgnoreCase("all")) {
                    for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                        cserv.setDropRate(rate);
                    }
                }
                else {
                    c.getChannelServer().setDropRate(rate);
                }
                c.getPlayer().dropMessage(6, "���������Ѹı��Ϊ " + rate + "x");
                return 1;
            }
            return 0;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!droprate <����> - ���ĵ��䱶��").toString();
        }
    }
    
    public static class MesoRate extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length > 1) {
                final int rate = Integer.parseInt(splitted[1]);
                if (splitted.length > 2 && splitted[2].equalsIgnoreCase("all")) {
                    for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                        cserv.setMesoRate(rate);
                    }
                }
                else {
                    c.getChannelServer().setMesoRate(rate);
                }
                c.getPlayer().dropMessage(6, "��ұ����Ѹı��Ϊ " + rate + "x");
                return 1;
            }
            return 0;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!mesorate <����> - ���Ľ�Ǯ����").toString();
        }
    }
    
    public static class DCAll extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            int range = -1;
            if (splitted.length < 2) {
                return 0;
            }
            String input = null;
            try {
                input = splitted[1];
            }
            catch (Exception ex) {}
            final String s = splitted[1];
            switch (s) {
                case "m": {
                    range = 0;
                    break;
                }
                case "c": {
                    range = 1;
                    break;
                }
                default: {
                    range = 2;
                    break;
                }
            }
            if (range == -1) {
                range = 1;
            }
            if (range == 0) {
                c.getPlayer().getMap().disconnectAll();
            }
            else if (range == 1) {
                c.getChannelServer().getPlayerStorage().disconnectAll();
            }
            else if (range == 2) {
                for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                    cserv.getPlayerStorage().disconnectAll(true);
                }
            }
            String show = "";
            switch (range) {
                case 0: {
                    show = "��ͼ";
                    break;
                }
                case 1: {
                    show = "�l��";
                    break;
                }
                case 2: {
                    show = "����";
                    break;
                }
            }
            final String msg = "[GM ����] GM " + c.getPlayer().getName() + "  DC �� " + show + "���";
            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, msg).getBytes());
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!dcall [m|c|w] - ������Ҷ���").toString();
        }
    }
    
    public static class GoTo extends CommandExecute
    {
        private static final HashMap<String, Integer> gotomaps;
        
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                c.getPlayer().dropMessage(6, "Syntax: !goto <mapname>");
            }
            else if (GoTo.gotomaps.containsKey(splitted[1])) {
                final MapleMap target = c.getChannelServer().getMapFactory().getMap(GoTo.gotomaps.get(splitted[1]));
                final MaplePortal targetPortal = target.getPortal(0);
                c.getPlayer().changeMap(target, targetPortal);
            }
            else if (splitted[1].equals("locations")) {
                c.getPlayer().dropMessage(6, "Use !goto <location>. Locations are as follows:");
                final StringBuilder sb = new StringBuilder();
                for (final String s : GoTo.gotomaps.keySet()) {
                    sb.append(s).append(", ");
                }
                c.getPlayer().dropMessage(6, sb.substring(0, sb.length() - 2));
            }
            else {
                c.getPlayer().dropMessage(6, "Invalid command ָ��Ҏ�t - Use !goto <location>. For a list of locations, use !goto locations.");
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!goto <����> - ��ĳ����ͼ").toString();
        }
        
        static {
            (gotomaps = new HashMap<String, Integer>()).put("gmmap", 180000000);
            GoTo.gotomaps.put("southperry", 2000000);
            GoTo.gotomaps.put("amherst", 1010000);
            GoTo.gotomaps.put("henesys", 100000000);
            GoTo.gotomaps.put("ellinia", 101000000);
            GoTo.gotomaps.put("perion", 102000000);
            GoTo.gotomaps.put("kerning", 103000000);
            GoTo.gotomaps.put("lithharbour", 104000000);
            GoTo.gotomaps.put("sleepywood", 105040300);
            GoTo.gotomaps.put("florina", 110000000);
            GoTo.gotomaps.put("orbis", 200000000);
            GoTo.gotomaps.put("happyville", 209000000);
            GoTo.gotomaps.put("elnath", 211000000);
            GoTo.gotomaps.put("ludibrium", 220000000);
            GoTo.gotomaps.put("aquaroad", 230000000);
            GoTo.gotomaps.put("leafre", 240000000);
            GoTo.gotomaps.put("mulung", 250000000);
            GoTo.gotomaps.put("herbtown", 251000000);
            GoTo.gotomaps.put("omegasector", 221000000);
            GoTo.gotomaps.put("koreanfolktown", 222000000);
            GoTo.gotomaps.put("newleafcity", 600000000);
            GoTo.gotomaps.put("sharenian", 990000000);
            GoTo.gotomaps.put("pianus", 230040420);
            GoTo.gotomaps.put("horntail", 240060200);
            GoTo.gotomaps.put("chorntail", 240060201);
            GoTo.gotomaps.put("mushmom", 100000005);
            GoTo.gotomaps.put("griffey", 240020101);
            GoTo.gotomaps.put("manon", 240020401);
            GoTo.gotomaps.put("zakum", 280030000);
            GoTo.gotomaps.put("czakum", 280030001);
            GoTo.gotomaps.put("papulatus", 220080001);
            GoTo.gotomaps.put("showatown", 801000000);
            GoTo.gotomaps.put("zipangu", 800000000);
            GoTo.gotomaps.put("ariant", 260000100);
            GoTo.gotomaps.put("nautilus", 120000000);
            GoTo.gotomaps.put("boatquay", 541000000);
            GoTo.gotomaps.put("malaysia", 550000000);
            GoTo.gotomaps.put("taiwan", 740000000);
            GoTo.gotomaps.put("thailand", 500000000);
            GoTo.gotomaps.put("erev", 130000000);
            GoTo.gotomaps.put("ellinforest", 300000000);
            GoTo.gotomaps.put("kampung", 551000000);
            GoTo.gotomaps.put("singapore", 540000000);
            GoTo.gotomaps.put("amoria", 680000000);
            GoTo.gotomaps.put("timetemple", 270000000);
            GoTo.gotomaps.put("pinkbean", 270050100);
            GoTo.gotomaps.put("peachblossom", 700000000);
            GoTo.gotomaps.put("fm", 910000000);
            GoTo.gotomaps.put("freemarket", 910000000);
            GoTo.gotomaps.put("oxquiz", 109020001);
            GoTo.gotomaps.put("ola", 109030101);
            GoTo.gotomaps.put("fitness", 109040000);
            GoTo.gotomaps.put("snowball", 109060000);
            GoTo.gotomaps.put("cashmap", 741010200);
            GoTo.gotomaps.put("golden", 950100000);
            GoTo.gotomaps.put("phantom", 610010000);
            GoTo.gotomaps.put("cwk", 610030000);
            GoTo.gotomaps.put("rien", 140000000);
        }
    }
    
    public static class KillAll extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            MapleMap map = c.getPlayer().getMap();
            double range = Double.POSITIVE_INFINITY;
            boolean drop = false;
            if (splitted.length > 1) {
                final int irange = 9999;
                if (splitted.length < 2) {
                    range = irange * irange;
                }
                else {
                    try {
                        map = c.getChannelServer().getMapFactory().getMap(Integer.parseInt(splitted[1]));
                        range = Integer.parseInt(splitted[2]) * Integer.parseInt(splitted[2]);
                    }
                    catch (Exception ex) {}
                }
                if (splitted.length >= 3) {
                    drop = splitted[3].equalsIgnoreCase("true");
                }
            }
            final List<MapleMapObject> monsters = map.getMapObjectsInRange(c.getPlayer().getPosition(), range, Arrays.asList(MapleMapObjectType.MONSTER));
            for (final MapleMapObject monstermo : map.getMapObjectsInRange(c.getPlayer().getPosition(), range, Arrays.asList(MapleMapObjectType.MONSTER))) {
                final MapleMonster mob = (MapleMonster)monstermo;
                map.killMonster(mob, c.getPlayer(), drop, false, (byte)1);
            }
            c.getPlayer().dropMessage("���ܹ�ɱ�� " + monsters.size() + " ����");
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!killall [range] [mapid] - ɱ�����й���").toString();
        }
    }
    
    public static class ResetMobs extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().getMap().killAllMonsters(false);
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!resetmobs - ���õ�ͼ�����й���").toString();
        }
    }
    
    public static class KillMonster extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return 0;
            }
            final MapleMap map = c.getPlayer().getMap();
            final double range = Double.POSITIVE_INFINITY;
            for (final MapleMapObject monstermo : map.getMapObjectsInRange(c.getPlayer().getPosition(), range, Arrays.asList(MapleMapObjectType.MONSTER))) {
                final MapleMonster mob = (MapleMonster)monstermo;
                if (mob.getId() == Integer.parseInt(splitted[1])) {
                    mob.damage(c.getPlayer(), mob.getHp(), false);
                }
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!killmonster <mobid> - ɱ����ͼ��ĳ������").toString();
        }
    }
    
    public static class KillMonsterByOID extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return 0;
            }
            final MapleMap map = c.getPlayer().getMap();
            final int targetId = Integer.parseInt(splitted[1]);
            final MapleMonster monster = map.getMonsterByOid(targetId);
            if (monster != null) {
                map.killMonster(monster, c.getPlayer(), false, false, (byte)1);
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!killmonsterbyoid <moboid> - ɱ����ͼ��ĳ������").toString();
        }
    }
    
    public static class HitMonsterByOID extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            final MapleMap map = c.getPlayer().getMap();
            final int targetId = Integer.parseInt(splitted[1]);
            final int damage = Integer.parseInt(splitted[2]);
            final MapleMonster monster = map.getMonsterByOid(targetId);
            if (monster != null) {
                map.broadcastMessage(MobPacket.damageMonster(targetId, damage));
                monster.damage(c.getPlayer(), damage, false);
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!hitmonsterbyoid <moboid> <damage> - ��ײ��ͼ��ĳ������").toString();
        }
    }
    
    public static class NPC extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            int npcId = 0;
            try {
                npcId = Integer.parseInt(splitted[1]);
            }
            catch (Exception ex) {}
            final MapleNPC npc = MapleLifeFactory.getNPC(npcId);
            if (npc != null && !npc.getName().equals("MISSINGNO")) {
                npc.setPosition(c.getPlayer().getPosition());
                npc.setCy(c.getPlayer().getPosition().y);
                npc.setRx0(c.getPlayer().getPosition().x + 50);
                npc.setRx1(c.getPlayer().getPosition().x - 50);
                npc.setFh(c.getPlayer().getMap().getFootholds().findBelow(c.getPlayer().getPosition()).getId());
                npc.setCustom(true);
                c.getPlayer().getMap().addMapObject(npc);
                c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.spawnNPC(npc, true));
            }
            else {
                c.getPlayer().dropMessage(6, "�Ҳ����˴���Ϊ" + npcId + "��Npc");
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!npc <npcid> - ���г�NPC").toString();
        }
    }
    
    public static class RemoveNPCs extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().getMap().resetNPCs();
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!removenpcs - �h������NPC").toString();
        }
    }
    
    public static class LookNPCs extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            for (final MapleMapObject reactor1l : c.getPlayer().getMap().getAllNPCsThreadsafe()) {
                final MapleNPC reactor2l = (MapleNPC)reactor1l;
                c.getPlayer().dropMessage(5, "NPC: oID: " + reactor2l.getObjectId() + " npcID: " + reactor2l.getId() + " Position: " + reactor2l.getPosition().toString() + " Name: " + reactor2l.getName());
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!looknpcs - �鿴����NPC").toString();
        }
    }
    
    public static class LookReactors extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            for (final MapleMapObject reactor1l : c.getPlayer().getMap().getAllReactorsThreadsafe()) {
                final MapleReactor reactor2l = (MapleReactor)reactor1l;
                c.getPlayer().dropMessage(5, "Reactor: oID: " + reactor2l.getObjectId() + " reactorID: " + reactor2l.getReactorId() + " Position: " + reactor2l.getPosition().toString() + " State: " + reactor2l.getState() + " Name: " + reactor2l.getName());
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!lookreactors - �鿴���з�Ӧ��").toString();
        }
    }
    
    public static class LookPortals extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            for (final MaplePortal portal : c.getPlayer().getMap().getPortals()) {
                c.getPlayer().dropMessage(5, "Portal: ID: " + portal.getId() + " script: " + portal.getScriptName() + " name: " + portal.getName() + " pos: " + portal.getPosition().x + "," + portal.getPosition().y + " target: " + portal.getTargetMapId() + " / " + portal.getTarget());
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!��Ӧ�� - �鿴���з�Ӧ��").toString();
        }
    }
    
    public static class MakePNPC extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 3) {
                return 0;
            }
            try {
                c.getPlayer().dropMessage(6, "Making playerNPC...");
                final String name = splitted[1];
                final int ch = World.Find.findChannel(name);
                if (ch <= 0) {
                    c.getPlayer().dropMessage(6, "��ұ�������");
                    return 1;
                }
                final MapleCharacter chhr = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
                if (chhr == null) {
                    c.getPlayer().dropMessage(6, splitted[1] + " is not online");
                }
                else {
                    final int npcId = Integer.parseInt(splitted[2]);
                    final MapleNPC npc_c = MapleLifeFactory.getNPC(npcId);
                    if (npc_c == null || npc_c.getName().equals("MISSINGNO")) {
                        c.getPlayer().dropMessage(6, "NPC������");
                        return 1;
                    }
                    final PlayerNPC npc = new PlayerNPC(chhr, npcId, c.getPlayer().getMap(), c.getPlayer());
                    npc.addToServer();
                    c.getPlayer().dropMessage(6, "Done");
                }
            }
            catch (Exception e) {
                c.getPlayer().dropMessage(6, "NPC failed... : " + e.getMessage());
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!���npc <playername> <npcid> - �������NPC").toString();
        }
    }
    
    public static class MakeOfflineP extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            try {
                c.getPlayer().dropMessage(6, "Making playerNPC...");
                final MapleClient cs = new MapleClient(null, null, (IoSession)new MockIOSession());
                final MapleCharacter chhr = MapleCharacter.loadCharFromDB(MapleCharacterUtil.getIdByName(splitted[1]), cs, false);
                if (chhr == null) {
                    c.getPlayer().dropMessage(6, splitted[1] + " does not exist");
                }
                else {
                    final PlayerNPC npc = new PlayerNPC(chhr, Integer.parseInt(splitted[2]), c.getPlayer().getMap(), c.getPlayer());
                    npc.addToServer();
                    c.getPlayer().dropMessage(6, "Done");
                }
            }
            catch (Exception e) {
                c.getPlayer().dropMessage(6, "NPC failed... : " + e.getMessage());
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!����npc <charname> <npcid> - ��������PNPC").toString();
        }
    }
    
    public static class DestroyPNPC extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            try {
                c.getPlayer().dropMessage(6, "Destroying playerNPC...");
                final MapleNPC npc = c.getPlayer().getMap().getNPCByOid(Integer.parseInt(splitted[1]));
                if (npc instanceof PlayerNPC) {
                    ((PlayerNPC)npc).destroy(true);
                    c.getPlayer().dropMessage(6, "Done");
                }
                else {
                    c.getPlayer().dropMessage(6, "!destroypnpc [objectid]");
                }
            }
            catch (Exception e) {
                c.getPlayer().dropMessage(6, "NPC failed... : " + e.getMessage());
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!destroypnpc [objectid] - �h��PNPC").toString();
        }
    }
    
    public static class MyPos extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            final Point pos = c.getPlayer().getPosition();
            c.getPlayer().dropMessage(6, "X: " + pos.x + " | Y: " + pos.y + " | RX0: " + (pos.x + 50) + " | RX1: " + (pos.x - 50) + " | FH: " + c.getPlayer().getFH() + "| CY:" + pos.y);
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!mypos - �ҵ�λ��").toString();
        }
    }
    
    public static class ReloadOps extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            SendPacketOpcode.reloadValues();
            RecvPacketOpcode.reloadValues();
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!reloadops - ��������OpCode").toString();
        }
    }
    
    public static class ReloadDrops extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            MapleMonsterInformationProvider.getInstance().clearDrops();
            ReactorScriptManager.getInstance().clearDrops();
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!����������� - �����d�����").toString();
        }
    }
    
    public static class ReloadPortals extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            PortalScriptManager.getInstance().clearScripts();
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!reloadportals - ������������").toString();
        }
    }
    
    public static class ReloadShops extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            MapleShopFactory.getInstance().clear();
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!���������̵� - ���������̵�").toString();
        }
    }
    
    public static class ReloadEvents extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            for (final ChannelServer instance : ChannelServer.getAllInstances()) {
                instance.reloadEvents();
            }
            return 1;
        }
    }
    
    public static class ReloadQuests extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            MapleQuest.clearQuests();
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!������������ - ������������").toString();
        }
    }
    
    public static class Spawn extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return 0;
            }
            int mid = 0;
            try {
                mid = Integer.parseInt(splitted[1]);
            }
            catch (Exception ex) {}
            int num = Math.min(CommandProcessorUtil.getOptionalIntArg(splitted, 2, 1), 500);
            if (num > 1000) {
                num = 1000;
            }
            final Long hp = CommandProcessorUtil.getNamedLongArg(splitted, 1, "hp");
            final Integer exp = CommandProcessorUtil.getNamedIntArg(splitted, 1, "exp");
            final Double php = CommandProcessorUtil.getNamedDoubleArg(splitted, 1, "php");
            final Double pexp = CommandProcessorUtil.getNamedDoubleArg(splitted, 1, "pexp");
            MapleMonster onemob;
            try {
                onemob = MapleLifeFactory.getMonster(mid);
            }
            catch (RuntimeException e) {
                c.getPlayer().dropMessage(5, "����: " + e.getMessage());
                return 1;
            }
            long newhp;
            if (hp != null) {
                newhp = hp;
            }
            else if (php != null) {
                newhp = (long)(onemob.getMobMaxHp() * (php / 100.0));
            }
            else {
                newhp = onemob.getMobMaxHp();
            }
            int newexp;
            if (exp != null) {
                newexp = exp;
            }
            else if (pexp != null) {
                newexp = (int)(onemob.getMobExp() * (pexp / 100.0));
            }
            else {
                newexp = onemob.getMobExp();
            }
            if (newhp < 1L) {
                newhp = 1L;
            }
            final OverrideMonsterStats overrideStats = new OverrideMonsterStats(newhp, onemob.getMobMaxMp(), newexp, false);
            for (int i = 0; i < num; ++i) {
                final MapleMonster mob = MapleLifeFactory.getMonster(mid);
                mob.setHp(newhp);
                mob.setOverrideStats(overrideStats);
                c.getPlayer().getMap().spawnMonsterOnGroundBelow(mob, c.getPlayer().getPosition());
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!spawn <����ID> <hp|exp|php||pexp = ?> - �ٻ�����").toString();
        }
    }
    
    public static class Clock extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return 0;
            }
            c.getPlayer().getMap().broadcastMessage(MaplePacketCreator.getClock(CommandProcessorUtil.getOptionalIntArg(splitted, 1, 60)));
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!clock <time> ʱ��").toString();
        }
    }
    
    public static class WarpPlayersTo extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            try {
                final MapleMap target = c.getChannelServer().getMapFactory().getMap(Integer.parseInt(splitted[1]));
                final MapleMap from = c.getPlayer().getMap();
                for (final MapleCharacter chr : from.getCharactersThreadsafe()) {
                    chr.changeMap(target, target.getPortal(0));
                }
            }
            catch (Exception e) {
                return 0;
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!WarpPlayersTo <maipid> ��������Ҵ��͵�ĳ����ͼ").toString();
        }
    }
    
    public static class LOLCastle extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length != 2) {
                return 0;
            }
            final MapleMap target = c.getChannelServer().getEventSM().getEventManager("lolcastle").getInstance("lolcastle" + splitted[1]).getMapFactory().getMap(990000300, false, false);
            c.getPlayer().changeMap(target, target.getPortal(0));
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!lolcastle level (level = 1-5) - ��֪����ɶ").toString();
        }
    }
    
    public static class Map extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return 0;
            }
            try {
                final MapleMap target = c.getChannelServer().getMapFactory().getMap(Integer.parseInt(splitted[1]));
                if (target == null) {
                    c.getPlayer().dropMessage(5, "��ͼ������.");
                    return 1;
                }
                MaplePortal targetPortal = null;
                if (splitted.length > 2) {
                    try {
                        targetPortal = target.getPortal(Integer.parseInt(splitted[2]));
                    }
                    catch (IndexOutOfBoundsException e2) {
                        c.getPlayer().dropMessage(5, "���͵����.");
                    }
                    catch (NumberFormatException ex) {}
                }
                if (targetPortal == null) {
                    targetPortal = target.getPortal(0);
                }
                c.getPlayer().changeMap(target, targetPortal);
            }
            catch (Exception e) {
                c.getPlayer().dropMessage(5, "Error: " + e.getMessage());
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!map <mapid|charname> [portal] - ���͵�ĳ��ͼ/��").toString();
        }
    }
    
    public static class StartProfiling extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            final CPUSampler sampler = CPUSampler.getInstance();
            sampler.addIncluded("client");
            sampler.addIncluded("constants");
            sampler.addIncluded("database");
            sampler.addIncluded("handling");
            sampler.addIncluded("provider");
            sampler.addIncluded("scripting");
            sampler.addIncluded("server");
            sampler.addIncluded("tools");
            sampler.start();
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!startprofiling ��ʼ��¼JVM��Ѷ").toString();
        }
    }
    
    public static class StopProfiling extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            final CPUSampler sampler = CPUSampler.getInstance();
            try {
                String filename = "odinprofile.txt";
                if (splitted.length > 1) {
                    filename = splitted[1];
                }
                final File file = new File(filename);
                if (file.exists()) {
                    c.getPlayer().dropMessage(6, "The entered filename already exists, choose a different one");
                    return 1;
                }
                sampler.stop();
                try (final FileWriter fw = new FileWriter(file)) {
                    sampler.save(fw, 1, 10);
                }
            }
            catch (IOException e) {
                System.err.println("Error saving profile" + e);
            }
            sampler.reset();
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!stopprofiling <filename> - ȡ����¼JVM��Ѷ�����浽����").toString();
        }
    }
    
    public static class ReloadMap extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            final MapleCharacter player = c.getPlayer();
            if (splitted.length < 2) {
                return 0;
            }
            final boolean custMap = splitted.length >= 2;
            final int mapid = custMap ? Integer.parseInt(splitted[1]) : player.getMapId();
            final MapleMap map = custMap ? player.getClient().getChannelServer().getMapFactory().getMap(mapid) : player.getMap();
            if (player.getClient().getChannelServer().getMapFactory().destroyMap(mapid)) {
                final MapleMap newMap = player.getClient().getChannelServer().getMapFactory().getMap(mapid);
                final MaplePortal newPor = newMap.getPortal(0);
                final LinkedHashSet<MapleCharacter> mcs = new LinkedHashSet<MapleCharacter>(map.getCharacters());
            Label_0139:
                for (final MapleCharacter m : mcs) {
                    int x = 0;
                    while (x < 5) {
                        try {
                            m.changeMap(newMap, newPor);
                            continue Label_0139;
                        }
                        catch (Throwable t) {
                            ++x;
                            continue;
                        }
                        
                    }
                    player.dropMessage("Failed warping " + m.getName() + " to the new map. Skipping...");
                }
                player.dropMessage("��ͼˢ����ϣ��绹����NPC������ʹ�ô�����.");
                return 1;
            }
            player.dropMessage("Unsuccessful reset!");
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!reloadmap <maipid> - ����ĳ����ͼ").toString();
        }
    }
    
    public static class Respawn extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().getMap().respawn(true);
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!respawn - ���������ͼ").toString();
        }
    }
    
    public static class ResetMap extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().getMap().resetFully();
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!respawn - ���������ͼ").toString();
        }
    }
    
    public static class Reloadall extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            for (final ChannelServer instance : ChannelServer.getAllInstances()) {
                instance.reloadEvents();
            }
            MapleShopFactory.getInstance().clear();
            PortalScriptManager.getInstance().clearScripts();
            MapleItemInformationProvider.getInstance().load();
            CashItemFactory.getInstance().initialize();
            MapleMonsterInformationProvider.getInstance().clearDrops();
            MapleGuild.loadAll();
            MapleFamily.loadAll();
            MapleLifeFactory.loadQuestCounts();
            MapleQuest.initQuests();
            MapleOxQuizFactory.getInstance();
            ReactorScriptManager.getInstance().clearDrops();
            SendPacketOpcode.reloadValues();
            RecvPacketOpcode.reloadValues();
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!Reloadall - ����ȫ������").toString();
        }
    }
    
    public static class PNPC extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            final int npcId = Integer.parseInt(splitted[1]);
            final MapleNPC npc = MapleLifeFactory.getNPC(npcId);
            if (npc != null && !npc.getName().equals("MISSINGNO")) {
                final int xpos = c.getPlayer().getPosition().x;
                final int ypos = c.getPlayer().getPosition().y;
                final int fh = c.getPlayer().getMap().getFootholds().findBelow(c.getPlayer().getPosition()).getId();
                npc.setPosition(c.getPlayer().getPosition());
                npc.setCy(ypos);
                npc.setRx0(xpos);
                npc.setRx1(xpos);
                npc.setFh(fh);
                npc.setCustom(true);
                try {
                    final com.mysql.jdbc.Connection con = (com.mysql.jdbc.Connection)DatabaseConnection.getConnection();
                    try (final PreparedStatement ps = (PreparedStatement)con.prepareStatement("INSERT INTO wz_customlife (dataid, f, hide, fh, cy, rx0, rx1, type, x, y, mid) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                        ps.setInt(1, npcId);
                        ps.setInt(2, 0);
                        ps.setInt(3, 0);
                        ps.setInt(4, fh);
                        ps.setInt(5, ypos);
                        ps.setInt(6, xpos);
                        ps.setInt(7, xpos);
                        ps.setString(8, "n");
                        ps.setInt(9, xpos);
                        ps.setInt(10, ypos);
                        ps.setInt(11, c.getPlayer().getMapId());
                        ps.executeUpdate();
                    }
                }
                catch (SQLException e) {
                    c.getPlayer().dropMessage(6, "Failed to save NPC to the database");
                }
                for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                    cserv.getMapFactory().getMap(c.getPlayer().getMapId()).addMapObject(npc);
                    cserv.getMapFactory().getMap(c.getPlayer().getMapId()).broadcastMessage(MaplePacketCreator.spawnNPC(npc, true));
                }
                c.getPlayer().dropMessage(6, "Please do not reload this map or else the NPC will disappear till the next restart.");
            }
            else {
                c.getPlayer().dropMessage(6, "���޴� Npc ");
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!����npc - ��������NPC").toString();
        }
    }
    
    public static class copyInv extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            final MapleCharacter player = c.getPlayer();
            int type = 1;
            if (splitted.length < 2) {
                return 0;
            }
            final String name = splitted[1];
            final int ch = World.Find.findChannel(name);
            if (ch <= 0) {
                c.getPlayer().dropMessage(6, "��ұ�������");
                return 0;
            }
            final MapleCharacter victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(name);
            if (victim == null) {
                player.dropMessage("�Ҳ��������");
                return 1;
            }
            try {
                type = Integer.parseInt(splitted[2]);
            }
            catch (Exception ex) {}
            if (type == 0) {
                for (final IItem ii : victim.getInventory(MapleInventoryType.EQUIPPED).list()) {
                    final IItem n = ii.copy();
                    player.getInventory(MapleInventoryType.EQUIP).addItem(n);
                }
                player.fakeRelog();
            }
            else {
                MapleInventoryType types;
                if (type == 1) {
                    types = MapleInventoryType.EQUIP;
                }
                else if (type == 2) {
                    types = MapleInventoryType.USE;
                }
                else if (type == 3) {
                    types = MapleInventoryType.ETC;
                }
                else if (type == 4) {
                    types = MapleInventoryType.SETUP;
                }
                else if (type == 5) {
                    types = MapleInventoryType.CASH;
                }
                else {
                    types = null;
                }
                if (types == null) {
                    c.getPlayer().dropMessage("��������");
                    return 1;
                }
                final int[] equip = new int[97];
                for (int i = 1; i < 97; ++i) {
                    if (victim.getInventory(types).getItem((short)i) != null) {
                        equip[i] = i;
                    }
                }
                for (int i = 0; i < equip.length; ++i) {
                    if (equip[i] != 0) {
                        final IItem n2 = victim.getInventory(types).getItem((short)equip[i]).copy();
                        player.getInventory(types).addItem(n2);
                    }
                }
                player.fakeRelog();
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!copyinv ������� װ����λ(0 = װ���� 1=װ���� 2=������ 3=������ 4=װ���� 5=������)(Ԥ��װ����) - ������ҵ���").toString();
        }
    }
    
    public static class RemoveItemOff extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return 0;
            }
            final com.mysql.jdbc.Connection dcon = (com.mysql.jdbc.Connection)DatabaseConnection.getConnection();
            try {
                int id = 0;
                final int quantity = 0;
                final String name = splitted[2];
                final PreparedStatement ps = (PreparedStatement)dcon.prepareStatement("select * from characters where name = ?");
                ps.setString(1, name);
                try (final ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        id = rs.getInt("id");
                    }
                }
                if (id == 0) {
                    c.getPlayer().dropMessage(5, "��ɫ���������Ͽ⡣");
                    return 0;
                }
                final PreparedStatement ps2 = (PreparedStatement)dcon.prepareStatement("delete from inventoryitems WHERE itemid = ? and characterid = ?");
                ps2.setInt(1, Integer.parseInt(splitted[1]));
                ps2.setInt(2, id);
                ps2.executeUpdate();
                c.getPlayer().dropMessage(6, "����IDΪ " + splitted[1] + " �ĵ���" + quantity + "�Ѿ��� " + name + " ���ϱ��Ƴ���");
                ps.close();
                ps2.close();
                return 1;
            }
            catch (SQLException e) {
                return 0;
            }
        }
        
        public String getMessage() {
            return new StringBuilder().append("!removeitem <��ƷID> <��ɫ���Q> - �Ƴ�������ϵĵ���").toString();
        }
    }
    
    public static class ExpEveryone extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                c.getPlayer().dropMessage(splitted[0] + " <������>");
                return 0;
            }
            final int gain = Integer.parseInt(splitted[1]);
            int ret = 0;
            for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                for (final MapleCharacter mch : cserv.getPlayerStorage().getAllCharacters()) {
                    mch.gainExp(gain, true, true, true);
                    ++ret;
                }
            }
            for (final ChannelServer cserv2 : ChannelServer.getAllInstances()) {
                for (final MapleCharacter mch : cserv2.getPlayerStorage().getAllCharacters()) {
                    mch.startMapEffect("����Ա����" + gain + "��������ߵ�������ң�ף����Ŀ�����Ŀ���", 5121009);
                }
            }
            c.getPlayer().dropMessage(6, "����ʹ�óɹ�����ǰ����: " + ret + " ����һ��: " + gain + " ���" + " ���� " + " �ܼ�: " + ret * gain);
            return 1;
        }
    }
    
    public static class CashEveryone extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length > 2) {
                int type = Integer.parseInt(splitted[1]);
                final int quantity = Integer.parseInt(splitted[2]);
                switch (type) {
                    case 1: {
                        type = 1;
                        break;
                    }
                    case 2: {
                        type = 2;
                        break;
                    }
                    default: {
                        c.getPlayer().dropMessage(6, "�÷�: !�������˵�� [�������1-2] [�������][1�ǵ��.2�ǵ��þ�]");
                        return 0;
                    }
                }
                int ret = 0;
                for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter mch : cserv.getPlayerStorage().getAllCharacters()) {
                        mch.modifyCSPoints(type, quantity);
                        ++ret;
                    }
                }
                final String show = (type == 1) ? "���" : "���þ�";
                for (final ChannelServer cserv2 : ChannelServer.getAllInstances()) {
                    for (final MapleCharacter mch2 : cserv2.getPlayerStorage().getAllCharacters()) {
                        mch2.startMapEffect("����Ա����" + quantity + show + "�������ߵ�������ң�ף���Ŀ�����Ŀ���", 5121009);
                    }
                }
                c.getPlayer().dropMessage(6, "����ʹ�óɹ�����ǰ����: " + ret + " ����һ��: " + quantity + " ���" + ((type == 1) ? "��ȯ " : " ����ȯ ") + " �ܼ�: " + ret * quantity);
            }
            else {
                c.getPlayer().dropMessage(6, "�÷�: !�������˵�� [�������1-2] [�������][1�ǵ��.2�ǵ��þ�]");
            }
            return 1;
        }
    }
    
    public static class mesoEveryone extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                c.getPlayer().dropMessage(splitted[0] + " <�����>");
                return 0;
            }
            int ret = 0;
            final int gain = Integer.parseInt(splitted[1]);
            for (final MapleCharacter mch : c.getChannelServer().getPlayerStorage().getAllCharactersThreadSafe()) {
                mch.gainMeso(gain, true);
                ++ret;
            }
            for (final ChannelServer cserv1 : ChannelServer.getAllInstances()) {
                for (final MapleCharacter mch2 : cserv1.getPlayerStorage().getAllCharacters()) {
                    mch2.startMapEffect("����Ա����" + gain + "ð�ձҸ����ߵ�������ң�ף����Ŀ�����Ŀ���", 5121009);
                }
            }
            c.getPlayer().dropMessage(6, "����ʹ�óɹ�����ǰ����: " + ret + " ����һ��: " + gain + " ð�ձ� " + " �ܼ�: " + ret * gain);
            return 1;
        }
    }
    
    public static class setRate extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            final MapleCharacter mc;
            final MapleCharacter player = mc = c.getPlayer();
            if (splitted.length > 2) {
                final int arg = Integer.parseInt(splitted[2]);
                final int seconds = Integer.parseInt(splitted[3]);
                final int mins = Integer.parseInt(splitted[4]);
                final int hours = Integer.parseInt(splitted[5]);
                final int time = seconds + mins * 60 + hours * 60 * 60;
                boolean bOk = true;
                if (splitted[1].equals("����")) {
                    if (arg <= 1000) {
                        for (final ChannelServer cservs : ChannelServer.getAllInstances()) {
                            cservs.setExpRate(arg);
                            cservs.broadcastPacket(MaplePacketCreator.serverNotice(6, "���鱶���Ѿ��ɹ��޸�Ϊ " + arg + "����ף�����Ϸ����.���鱶�ʽ���ʱ�䵽���Զ�������"));
                        }
                    }
                    else {
                        mc.dropMessage("�����ѱ�ϵͳ���ơ�");
                    }
                }
                else if (splitted[1].equals("����")) {
                    if (arg <= 50) {
                        for (final ChannelServer cservs : ChannelServer.getAllInstances()) {
                            cservs.setDropRate(arg);
                            cservs.broadcastPacket(MaplePacketCreator.serverNotice(6, "���ʱ����Ѿ��ɹ��޸�Ϊ " + arg + "����ף�����Ϸ����.���ʱ��ʽ���ʱ�䵽���Զ���������"));
                        }
                    }
                    else {
                        mc.dropMessage("�����ѱ�ϵͳ���ơ�");
                    }
                }
                else if (splitted[1].equals("���")) {
                    if (arg <= 50) {
                        for (final ChannelServer cservs : ChannelServer.getAllInstances()) {
                            cservs.setMesoRate(arg);
                            cservs.broadcastPacket(MaplePacketCreator.serverNotice(6, "��ұ����Ѿ��ɹ��޸�Ϊ " + arg + "����ף�����Ϸ����.��ұ��ʽ���ʱ�䵽���Զ���������"));
                        }
                    }
                    else {
                        mc.dropMessage("�����ѱ�ϵͳ���ơ�");
                    }
                }
                else if (splitted[1].equalsIgnoreCase("boss����")) {
                    if (arg <= 50) {
                        for (final ChannelServer cservs : ChannelServer.getAllInstances()) {
                            cservs.setBossDropRate(arg);
                            cservs.broadcastPacket(MaplePacketCreator.serverNotice(6, "BOSS�����Ѿ��ɹ��޸�Ϊ " + arg + "����ף�����Ϸ����.boos���䱶�ʽ���ʱ�䵽���Զ���������"));
                        }
                    }
                    else {
                        mc.dropMessage("�����ѱ�ϵͳ���ơ�");
                    }
                }
                else if (splitted[1].equals("���ﾭ��")) {
                    if (arg > 5) {
                        mc.dropMessage("�����ѱ�ϵͳ���ơ�");
                    }
                }
                else {
                    bOk = false;
                }
                if (bOk) {
                    final String rate = splitted[1];
                    World.scheduleRateDelay(rate, time);
                }
                else {
                    mc.dropMessage("ʹ�÷���: !�������� <exp����|drop����|meso���|bossboss����|pet> <��> <��> <��> <ʱ>");
                }
            }
            else {
                mc.dropMessage("ʹ�÷���: !�������� <exp����|drop����|meso���|bossboss����|pet> <��> <��> <��> <ʱ>");
            }
            return 1;
        }
    }
    
    public static class WarpAllHere extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            for (final ChannelServer CS : ChannelServer.getAllInstances()) {
                for (final MapleCharacter mch : CS.getPlayerStorage().getAllCharactersThreadSafe()) {
                    if (mch.getMapId() != c.getPlayer().getMapId()) {
                        mch.changeMap(c.getPlayer().getMap(), c.getPlayer().getPosition());
                    }
                    if (mch.getClient().getChannel() != c.getPlayer().getClient().getChannel()) {
                        mch.changeChannel(c.getPlayer().getClient().getChannel());
                    }
                }
            }
            return 1;
        }
        
        public String getMessage() {
            return new StringBuilder().append("!WarpAllHere ��������Ҵ��͵�����").toString();
        }
    }
    
    public static class maxSkills extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            final MapleCharacter player = c.getPlayer();
            player.maxSkills();
            return 1;
        }
    }
    
    public static class Drop extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                return 0;
            }
            int itemId = 0;
            try {
                itemId = Integer.parseInt(splitted[1]);
            }
            catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
            final short quantity = (short)CommandProcessorUtil.getOptionalIntArg(splitted, 2, 1);
            final MapleItemInformationProvider ii = MapleItemInformationProvider.getInstance();
            if (GameConstants.isPet(itemId)) {
                c.getPlayer().dropMessage(5, "�����뵽�����̳ǹ���.");
            }
            else if (!ii.itemExists(itemId)) {
                c.getPlayer().dropMessage(5, itemId + " - ��Ʒ������");
            }
            else {
                IItem toDrop;
                if (GameConstants.getInventoryType(itemId) == MapleInventoryType.EQUIP) {
                    toDrop = ii.randomizeStats((Equip)ii.getEquipById(itemId));
                }
                else {
                    toDrop = new client.inventory.Item(itemId, (short)0, quantity, (byte)0);
                }
                toDrop.setGMLog(c.getPlayer().getName());
                c.getPlayer().getMap().spawnItemDrop(c.getPlayer(), c.getPlayer(), toDrop, c.getPlayer().getPosition(), true, true);
            }
            return 1;
        }
    }
    
    public static class buff extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            final MapleCharacter player = c.getPlayer();
            SkillFactory.getSkill(9001002).getEffect(1).applyTo(player);
            SkillFactory.getSkill(9001003).getEffect(1).applyTo(player);
            SkillFactory.getSkill(9001008).getEffect(1).applyTo(player);
            SkillFactory.getSkill(9001001).getEffect(1).applyTo(player);
            return 1;
        }
    }
    
    public static class maxstats extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            final MapleCharacter player = c.getPlayer();
            player.getStat().setMaxHp((short)30000);
            player.getStat().setMaxMp((short)30000);
            player.getStat().setStr((short)32767);
            player.getStat().setDex((short)32767);
            player.getStat().setInt((short)32767);
            player.getStat().setLuk((short)32767);
            player.updateSingleStat(MapleStat.MAXHP, 30000);
            player.updateSingleStat(MapleStat.MAXMP, 30000);
            player.updateSingleStat(MapleStat.STR, 32767);
            player.updateSingleStat(MapleStat.DEX, 32767);
            player.updateSingleStat(MapleStat.INT, 32767);
            player.updateSingleStat(MapleStat.LUK, 32767);
            return 1;
        }
    }
    
    public static class WhereAmI extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().dropMessage(5, "Ŀǰ��ͼ " + c.getPlayer().getMap().getId() + "���� (" + String.valueOf(c.getPlayer().getPosition().x) + " , " + String.valueOf(c.getPlayer().getPosition().y) + ")");
            return 1;
        }
    }
    
    public static class Packet extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            final MaplePacketLittleEndianWriter mplew = new MaplePacketLittleEndianWriter();
            final int packetheader = Integer.parseInt(splitted[1]);
            String packet_in = " 00 00 00 00 00 00 00 00 00 ";
            if (splitted.length > 2) {
                packet_in = StringUtil.joinStringFrom(splitted, 2);
            }
            mplew.writeShort(packetheader);
            mplew.write(HexTool.getByteArrayFromHexString(packet_in));
            mplew.writeZeroBytes(20);
            c.getSession().write((Object)mplew.getPacket());
            c.getPlayer().dropMessage(packetheader + "�Ѵ��ͷ��[" + mplew.getPacket().getBytes().length + "] : " + mplew.toString());
            return 1;
        }
    }
    
    public static class mob extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            MapleMonster monster = null;
            for (final MapleMapObject monstermo : c.getPlayer().getMap().getMapObjectsInRange(c.getPlayer().getPosition(), 100000.0, Arrays.asList(MapleMapObjectType.MONSTER))) {
                monster = (MapleMonster)monstermo;
                if (monster.isAlive()) {
                    c.getPlayer().dropMessage(6, "���� " + monster.toString());
                }
            }
            if (monster == null) {
                c.getPlayer().dropMessage(6, "�Ҳ�������");
            }
            return 1;
        }
    }
    
    public static class register extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            String acc = null;
            String password = null;
            try {
                acc = splitted[1];
                password = splitted[2];
            }
            catch (Exception ex3) {}
            if (acc == null || password == null) {
                c.getPlayer().dropMessage("�˺Ż������쳣");
                return 0;
            }
            final boolean ACCexist = AutoRegister.getAccountExists(acc);
            if (ACCexist) {
                c.getPlayer().dropMessage("�ʺ��ѱ�ʹ��");
                return 0;
            }
            if (acc.length() >= 12) {
                c.getPlayer().dropMessage("���볤�ȹ���");
                return 0;
            }
            try {
                final Connection con = DatabaseConnection.getConnection();
            }
            catch (Exception ex) {
                System.out.println(ex);
                return 0;
            }
            Connection con = null;
            try (final PreparedStatement ps = (PreparedStatement)con.prepareStatement("INSERT INTO accounts (name, password, md5pass) VALUES (?, ? ,?)")) {
                ps.setString(1, acc);
                ps.setString(2, LoginCrypto.hexSha1(password));
                ps.setString(3, "");
                ps.executeUpdate();
                ps.close();
            }
            catch (SQLException ex2) {
                System.out.println(ex2);
                return 0;
            }
            c.getPlayer().dropMessage("[ע�����]�˺�: " + acc + " ����: " + password);
            return 1;
        }
    }
    
    public static class openmap extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            int mapid = 0;
            String input = null;
            final MapleMap map = null;
            if (splitted.length < 2) {
                c.getPlayer().dropMessage(splitted[0] + " - ���ŵ�ͼ");
                return 0;
            }
            try {
                input = splitted[1];
                mapid = Integer.parseInt(input);
            }
            catch (Exception ex) {}
            for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                cserv.getMapFactory().HealMap(mapid);
            }
            return 1;
        }
    }
    
    public static class closemap extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            int mapid = 0;
            String input = null;
            final MapleMap map = null;
            if (splitted.length < 2) {
                c.getPlayer().dropMessage(splitted[0] + " - �رյ�ͼ");
                return 0;
            }
            try {
                input = splitted[1];
                mapid = Integer.parseInt(input);
            }
            catch (Exception ex) {}
            if (c.getChannelServer().getMapFactory().getMap(mapid) == null) {
                c.getPlayer().dropMessage("��ͼ������");
                return 0;
            }
            for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                cserv.getMapFactory().destroyMap(mapid, true);
            }
            return 1;
        }
    }
    
    public static class reloadcpq extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().getMap().reloadCPQ();
            c.getPlayer().dropMessage("���껪��ͼ���³ɹ�");
            return 1;
        }
    }
}
