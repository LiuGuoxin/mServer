package scripting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.script.Invocable;
import javax.script.ScriptEngine;

import client.MapleClient;
import database.DatabaseConnection;
import server.maps.MapleReactor;
import server.maps.ReactorDropEntry;
import tools.FileoutputUtil;

public class ReactorScriptManager extends AbstractScriptManager
{
    private static final ReactorScriptManager instance;
    private final Map<Integer, List<ReactorDropEntry>> drops;
    
    public ReactorScriptManager() {
        this.drops = new HashMap<Integer, List<ReactorDropEntry>>();
    }
    
    public static final ReactorScriptManager getInstance() {
        return ReactorScriptManager.instance;
    }
    
    public final void act(final MapleClient c, final MapleReactor reactor) {
        try {
            if (c.getPlayer().isGM()) {
                c.getPlayer().dropMessage("[ϵͳ��ʾ]���Ѿ�������reactor:" + reactor.getReactorId() + "�ĶԻ���");
            }
            final Invocable iv = this.getInvocable("reactor/" + reactor.getReactorId() + ".js", c);
            if (iv == null) {
                return;
            }
            final ScriptEngine scriptengine = (ScriptEngine)iv;
            final ReactorActionManager rm = new ReactorActionManager(c, reactor);
            scriptengine.put("rm", rm);
            iv.invokeFunction("act", new Object[0]);
        }
        catch (Exception e) {
            System.err.println("Error executing reactor script. ReactorID: " + reactor.getReactorId() + ", ReactorName: " + reactor.getName() + ":" + e);
            FileoutputUtil.log("Logs/Log_Script_�ű��쳣.rtf", "Error executing reactor script. ReactorID: " + reactor.getReactorId() + ", ReactorName: " + reactor.getName() + ":" + e);
        }
    }
    
    public final List<ReactorDropEntry> getDrops(final int rid) {
        List<ReactorDropEntry> ret = this.drops.get(rid);
        if (ret != null) {
            return ret;
        }
        ret = new LinkedList<ReactorDropEntry>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            final Connection con = DatabaseConnection.getConnection();
            ps = con.prepareStatement("SELECT * FROM reactordrops WHERE reactorid = ?");
            ps.setInt(1, rid);
            rs = ps.executeQuery();
            while (rs.next()) {
                ret.add(new ReactorDropEntry(rs.getInt("itemid"), rs.getInt("chance"), rs.getInt("questid")));
            }
            rs.close();
            ps.close();
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
            catch (SQLException ignore) {
                return ret;
            }
        }
        catch (SQLException e) {
            System.err.println("Could not retrieve drops for reactor " + rid + e);
            return ret;
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
            catch (SQLException ignore2) {
                return ret;
            }
        }
        this.drops.put(rid, ret);
        return ret;
    }
    
    public final void clearDrops() {
        this.drops.clear();
    }
    
    static {
        instance = new ReactorScriptManager();
    }
}
