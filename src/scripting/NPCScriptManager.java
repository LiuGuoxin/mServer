package scripting;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.locks.Lock;

import javax.script.Invocable;
import javax.script.ScriptEngine;

import client.MapleClient;
import server.quest.MapleQuest;
import tools.FileoutputUtil;

public class NPCScriptManager extends AbstractScriptManager
{
    private final Map<MapleClient, NPCConversationManager> cms;
    private static final NPCScriptManager instance;
    
    public NPCScriptManager() {
        this.cms = new WeakHashMap<MapleClient, NPCConversationManager>();
    }
    
    public static final NPCScriptManager getInstance() {
        return NPCScriptManager.instance;
    }
    
    public void start(final MapleClient c, final int npc) {
        this.start(c, npc, 0);
    }
    
    public final void start(final MapleClient c, final int npc, final int wh) {
        final Lock lock = c.getNPCLock();
        lock.lock();
        try {
            if (c.getPlayer().isGM()) {
                if (wh == 0) {
                    c.getPlayer().dropMessage("[系统提示]您已经建立与NPC:" + npc + "的对话。");
                }
                else {
                    c.getPlayer().dropMessage("[系统提示]您已经建立与NPC:" + npc + "_" + wh + "的对话。");
                }
            }
            if (!this.cms.containsKey(c)) {
                Invocable iv;
                if (wh == 0) {
                    iv = this.getInvocable("npc/" + npc + ".js", c, true);
                }
                else {
                    iv = this.getInvocable("npc/" + npc + "_" + wh + ".js", c, true);
                }
                final ScriptEngine scriptengine = (ScriptEngine)iv;
                NPCConversationManager cm;
                if (wh == 0) {
                    cm = new NPCConversationManager(c, npc, -1, (byte)(-1), iv, 0);
                }
                else {
                    cm = new NPCConversationManager(c, npc, -1, (byte)(-1), iv, wh);
                }
                this.cms.put(c, cm);
                if (iv == null || getInstance() == null) {
                    if (wh == 0) {
                        cm.sendOk("欢迎来到#b冒险岛#k。对不起暂时无法查询到功能。\r\n我的ID是: #r" + npc + "#k.\r\n ");
                    }
                    else {
                        cm.sendOk("欢迎来到#b冒险岛#k。对不起暂时无法查询到功能。\r\n我的ID是: #r" + npc + "_" + wh + "#k.\r\n ");
                    }
                    cm.dispose();
                    return;
                }
                scriptengine.put("cm", cm);
                scriptengine.put("npcid", npc);
                c.getPlayer().setConversation(1);
                try {
                    iv.invokeFunction("start", new Object[0]);
                }
                catch (NoSuchMethodException nsme) {
                    iv.invokeFunction("action", 1, 0, 0);
                }
            }
            else {
                c.getPlayer().dropMessage(5, "你现在已经假死请使用@ea");
            }
        }
        catch (Exception e) {
            System.err.println("NPC 腳本錯誤, 它ID為 : " + npc + "_" + wh + "." + e);
            if (c.getPlayer().isGM()) {
                c.getPlayer().dropMessage("[系統提示] NPC " + npc + "_" + wh + "腳本錯誤 " + e + "");
            }
            FileoutputUtil.log("Logs/Log_Script_脚本异常.rtf", "Error executing NPC script, NPC ID : " + npc + "_" + wh + "." + e);
            this.dispose(c);
        }
        finally {
            lock.unlock();
        }
    }
    
    public void action(final MapleClient c, final byte mode, final byte type, final int selection) {
        this.action(c, mode, type, selection, 0);
    }
    
    public final void action(final MapleClient c, final byte mode, final byte type, final int selection, final int wh) {
        if (mode != -1) {
            final NPCConversationManager cm = this.cms.get(c);
            if (cm == null || cm.getLastMsg() > -1) {
                return;
            }
            final Lock lock = c.getNPCLock();
            lock.lock();
            try {
                if (cm.pendingDisposal) {
                    this.dispose(c);
                }
                else if (wh == 0) {
                    cm.getIv().invokeFunction("action", mode, type, selection);
                }
                else {
                    cm.getIv().invokeFunction("action", mode, type, selection, wh);
                }
            }
            catch (Exception e) {
                if (c.getPlayer().isGM()) {
                    c.getPlayer().dropMessage("[系統提示] NPC " + cm.getNpc() + "_" + wh + "腳本錯誤 " + e + "");
                }
                System.err.println("NPC 腳本錯誤. 它ID為 : " + cm.getNpc() + "_" + wh + ":" + e);
                this.dispose(c);
                FileoutputUtil.log("Logs/Log_Script_脚本异常.rtf", "Error executing NPC script, NPC ID : " + cm.getNpc() + "_" + wh + "." + e);
            }
            finally {
                lock.unlock();
            }
        }
    }
    
    public final void startQuest(final MapleClient c, final int npc, final int quest) {
        if (!MapleQuest.getInstance(quest).canStart(c.getPlayer(), null)) {
            return;
        }
        final Lock lock = c.getNPCLock();
        lock.lock();
        try {
            if (!this.cms.containsKey(c)) {
                final Invocable iv = this.getInvocable("quest/" + quest + ".js", c, true);
                if (iv == null) {
                    this.dispose(c);
                    return;
                }
                final ScriptEngine scriptengine = (ScriptEngine)iv;
                final NPCConversationManager cm = new NPCConversationManager(c, npc, quest, (byte)0, iv, 0);
                this.cms.put(c, cm);
                scriptengine.put("qm", cm);
                c.getPlayer().setConversation(1);
                if (c.getPlayer().isGM()) {
                    c.getPlayer().dropMessage("[系統提示]您已經建立與任務腳本:" + quest + "的往來。");
                }
                iv.invokeFunction("start", 1, 0, 0);
            }
            else {
                this.dispose(c);
            }
        }
        catch (Exception e) {
            System.err.println("Error executing Quest script. (" + quest + ")..NPCID: " + npc + ":" + e);
            FileoutputUtil.log("Logs/Log_Script_脚本异常.rtf", "Error executing Quest script. (" + quest + ")..NPCID: " + npc + ":" + e);
            this.dispose(c);
        }
        finally {
            lock.unlock();
        }
    }
    
    public final void startQuest(final MapleClient c, final byte mode, final byte type, final int selection) {
        final Lock lock = c.getNPCLock();
        final NPCConversationManager cm = this.cms.get(c);
        if (cm == null || cm.getLastMsg() > -1) {
            return;
        }
        lock.lock();
        try {
            if (cm.pendingDisposal) {
                this.dispose(c);
            }
            else {
                cm.getIv().invokeFunction("start", mode, type, selection);
            }
        }
        catch (Exception e) {
            if (c.getPlayer().isGM()) {
                c.getPlayer().dropMessage("[系統提示]任務腳本:" + cm.getQuest() + "錯誤...NPC: " + cm.getNpc() + ":" + e);
            }
            System.err.println("Error executing Quest script. (" + cm.getQuest() + ")...NPC: " + cm.getNpc() + ":" + e);
            FileoutputUtil.log("Logs/Log_Script_脚本异常.rtf", "Error executing Quest script. (" + cm.getQuest() + ")..NPCID: " + cm.getNpc() + ":" + e);
            this.dispose(c);
        }
        finally {
            lock.unlock();
        }
    }
    
    public final void endQuest(final MapleClient c, final int npc, final int quest, final boolean customEnd) {
        if (!customEnd && !MapleQuest.getInstance(quest).canComplete(c.getPlayer(), null)) {
            return;
        }
        final Lock lock = c.getNPCLock();
        lock.lock();
        try {
            if (!this.cms.containsKey(c)) {
                final Invocable iv = this.getInvocable("quest/" + quest + ".js", c, true);
                if (iv == null) {
                    this.dispose(c);
                    return;
                }
                final ScriptEngine scriptengine = (ScriptEngine)iv;
                final NPCConversationManager cm = new NPCConversationManager(c, npc, quest, (byte)1, iv, 0);
                this.cms.put(c, cm);
                scriptengine.put("qm", cm);
                c.getPlayer().setConversation(1);
                iv.invokeFunction("end", 1, 0, 0);
            }
        }
        catch (Exception e) {
            if (c.getPlayer().isGM()) {
                c.getPlayer().dropMessage("[系統提示]任務腳本:" + quest + "錯誤...NPC: " + quest + ":" + e);
            }
            System.err.println("Error executing Quest script. (" + quest + ")..NPCID: " + npc + ":" + e);
            FileoutputUtil.log("Logs/Log_Script_脚本异常.rtf", "Error executing Quest script. (" + quest + ")..NPCID: " + npc + ":" + e);
            this.dispose(c);
        }
        finally {
            lock.unlock();
        }
    }
    
    public final void endQuest(final MapleClient c, final byte mode, final byte type, final int selection) {
        final Lock lock = c.getNPCLock();
        final NPCConversationManager cm = this.cms.get(c);
        if (cm == null || cm.getLastMsg() > -1) {
            return;
        }
        lock.lock();
        try {
            if (cm.pendingDisposal) {
                this.dispose(c);
            }
            else {
                cm.getIv().invokeFunction("end", mode, type, selection);
            }
        }
        catch (Exception e) {
            if (c.getPlayer().isGM()) {
                c.getPlayer().dropMessage("[系統提示]任務腳本:" + cm.getQuest() + "錯誤...NPC: " + cm.getNpc() + ":" + e);
            }
            System.err.println("Error executing Quest script. (" + cm.getQuest() + ")...NPC: " + cm.getNpc() + ":" + e);
            FileoutputUtil.log("Logs/Log_Script_脚本异常.rtf", "Error executing Quest script. (" + cm.getQuest() + ")..NPCID: " + cm.getNpc() + ":" + e);
            this.dispose(c);
        }
        finally {
            lock.unlock();
        }
    }
    
    public final void dispose(final MapleClient c) {
        final NPCConversationManager npccm = this.cms.get(c);
        if (npccm != null) {
            this.cms.remove(c);
            if (npccm.getType() == -1) {
                if (npccm.getwh() == 0) {
                    c.removeScriptEngine("scripts/npc/" + npccm.getNpc() + ".js");
                }
                else {
                    c.removeScriptEngine("scripts/npc/" + npccm.getNpc() + "_" + npccm.getwh() + ".js");
                }
                c.removeScriptEngine("scripts/npc/notcoded.js");
            }
            else {
                c.removeScriptEngine("scripts/quest/" + npccm.getQuest() + ".js");
            }
        }
        if (c.getPlayer() != null && c.getPlayer().getConversation() == 1) {
            c.getPlayer().setConversation(0);
        }
    }
    
    public final NPCConversationManager getCM(final MapleClient c) {
        return this.cms.get(c);
    }
    
    static {
        instance = new NPCScriptManager();
    }
}
