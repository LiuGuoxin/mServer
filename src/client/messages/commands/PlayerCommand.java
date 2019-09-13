package client.messages.commands;

import java.util.Map;

import client.MapleClient;
import constants.ServerConstants;
import handling.world.World;
import scripting.NPCScriptManager;
import tools.FileoutputUtil;
import tools.MaplePacketCreator;
import tools.StringUtil;

public class PlayerCommand
{
    public static ServerConstants.PlayerGMRank getPlayerLevelRequired() {
        return ServerConstants.PlayerGMRank.NORMAL;
    }
    
    public static class �浵 extends save
    {
    }
    
    public static class ���� extends help
    {
    }
    
    public static class ��ȡ��ȯ extends gainPoint
    {
    }
    
    public static class ���� extends Mobdrop
    {
    }
    
    public static class ea extends �鿴
    {
    }
    
    public static class �⿨ extends �鿴
    {
    }
    
    public static class ���� extends ziyou
    {
    }
    
    public static class �ط� extends backBoss
    {
    }
    
    public static class �鿴 extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            NPCScriptManager.getInstance().dispose(c);
            c.getSession().write((Object)MaplePacketCreator.enableActions());
            c.getPlayer().dropMessage(1, "�����Ѵ������.");
            c.getPlayer().dropMessage(6, "��ǰʱ����" + FileoutputUtil.CurrentReadable_Time() + " GMT+8 | ����ֵ���� " + Math.round((float)c.getPlayer().getEXPMod()) * 100 * Math.round(c.getPlayer().getStat().expBuff / 100.0) + "%, ���ﱶ�� " + Math.round((float)c.getPlayer().getDropMod()) * 100 * Math.round(c.getPlayer().getStat().dropBuff / 100.0) + "%, ��ұ��� " + Math.round(c.getPlayer().getStat().mesoBuff / 100.0) * 100L + "%");
            c.getPlayer().dropMessage(6, "��ǰ�ӳ� " + c.getPlayer().getClient().getLatency() + " ����");
            if (c.getPlayer().isAdmin()) {
                c.sendPacket(MaplePacketCreator.sendPyramidEnergy("massacre_hit", String.valueOf(50)));
            }
            return 1;
        }
    }
    
    public static class save extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().saveToDB(false, false);
            c.getPlayer().dropMessage("�浵�ɹ�");
            return 1;
        }
    }
    
    public static class ziyou extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            switch (c.getPlayer().getMapId()) {
                case 925100500: {
                    c.getPlayer().dropMessage(1, "�Ҷ���¥ĳ�����㰡,���벻��boss��ͨ��.");
                    break;
                }
                case 220080001: {
                    c.getPlayer().dropMessage(1, "������,���˼���Σ�հ�...");
                    break;
                }
                case 240060200: {
                    c.getPlayer().dropMessage(1, "��ǰ��ͼ�޷�ʹ�ô���!");
                    break;
                }
                case 280030000: {
                    c.getPlayer().dropMessage(1, "��ǰ��ͼ�޷�ʹ�ô���!");
                    break;
                }
                case 270050100: {
                    c.getPlayer().dropMessage(1, "��ǰ��ͼ�޷�ʹ�ô���!");
                    break;
                }
                case 910000000: {
                    c.getPlayer().dropMessage(1, "���Ѿ���������,��������?");
                    break;
                }
                case 922010100: {
                    c.getPlayer().dropMessage(1, "��ǰ��ͼ�޷�ʹ�ô���!");
                    break;
                }
                case 922010200: {
                    c.getPlayer().dropMessage(1, "��ǰ��ͼ�޷�ʹ�ô���!");
                    break;
                }
                case 922010300: {
                    c.getPlayer().dropMessage(1, "��ǰ��ͼ�޷�ʹ�ô���!");
                    break;
                }
                case 922010400: {
                    c.getPlayer().dropMessage(1, "��ǰ��ͼ�޷�ʹ�ô���!");
                    break;
                }
                case 922010500: {
                    c.getPlayer().dropMessage(1, "��ǰ��ͼ�޷�ʹ�ô���!");
                    break;
                }
                case 922010600: {
                    c.getPlayer().dropMessage(1, "��ǰ��ͼ�޷�ʹ�ô���!");
                    break;
                }
                case 922010700: {
                    c.getPlayer().dropMessage(1, "��ǰ��ͼ�޷�ʹ�ô���!");
                    break;
                }
                case 922010800: {
                    c.getPlayer().dropMessage(1, "��ǰ��ͼ�޷�ʹ�ô���!");
                    break;
                }
                case 922010900: {
                    c.getPlayer().dropMessage(1, "��ǰ��ͼ�޷�ʹ�ô���!");
                    break;
                }
                case 922011000: {
                    c.getPlayer().dropMessage(1, "��ǰ��ͼ�޷�ʹ�ô���!");
                    break;
                }
                case 922011100: {
                    c.getPlayer().dropMessage(1, "��ǰ��ͼ�޷�ʹ�ô���!");
                    break;
                }
                case 922010000: {
                    c.getPlayer().dropMessage(1, "��ǰ��ͼ�޷�ʹ�ô���!");
                    break;
                }
                default: {
                    c.getPlayer().changeMap(910000000);
                    c.getPlayer().dropMessage("���ͳɹ�");
                    break;
                }
            }
            return 1;
        }
    }
    
    public static class gainPoint extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            NPCScriptManager.getInstance().dispose(c);
            c.getSession().write((Object)MaplePacketCreator.enableActions());
            final NPCScriptManager npc = NPCScriptManager.getInstance();
            npc.start(c, 9270034);
            return 1;
        }
    }
    
    public static class Mobdrop extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            NPCScriptManager.getInstance().dispose(c);
            c.getSession().write((Object)MaplePacketCreator.enableActions());
            final NPCScriptManager npc = NPCScriptManager.getInstance();
            npc.start(c, 2000);
            return 1;
        }
    }
    
    public static class CGM extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted[1] == null) {
                c.getPlayer().dropMessage(6, "�����лл.");
                return 1;
            }
            if (c.getPlayer().isGM()) {
                c.getPlayer().dropMessage(6, "��Ϊ���Լ���GM�޷�ʹ�ô�����,���Գ���!cngm <ѶϢ> ����GM�����l��~");
                return 1;
            }
            if (!c.getPlayer().getCheatTracker().GMSpam(100000, 1)) {
                World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(6, "�l�� " + c.getPlayer().getClient().getChannel() + " ��� [" + c.getPlayer().getName() + "] : " + StringUtil.joinStringFrom(splitted, 1)).getBytes());
                c.getPlayer().dropMessage(6, "ѶϢ�Ѿ�����GM��!");
            }
            else {
                c.getPlayer().dropMessage(6, "Ϊ�˷�ֹ��GMˢ������ÿ1���ֻ�ܷ�һ��.");
            }
            return 1;
        }
    }
    
    public static class backBoss extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (c.getPlayer().checkBossBack() <= 0) {
                c.getPlayer().dropMessage("��ѯ�����ط���¼!");
                return 0;
            }
            final Map<String, Object> backBoss = c.getPlayer().getBackBoss();
            final int ch = World.Find.findChannel(c.getPlayer().getName());
            if (ch == Integer.parseInt(backBoss.get("channel").toString())) {
                final String string = backBoss.get("boss_name").toString();
                switch (string) {
                    case "����": {
                        c.getPlayer().changeMap(240060200);
                        break;
                    }
                    case "����": {
                        c.getPlayer().changeMap(280030000);
                        break;
                    }
                    case "��ɮ": {
                        c.getPlayer().changeMap(702060000);
                        break;
                    }
                    case "PB": {
                        c.getPlayer().changeMap(270050100);
                        break;
                    }
                }
                c.getPlayer().dropMessage("�ط�BOSS�ɹ�!");
                return 1;
            }
            c.getPlayer().dropMessage("��ǰƵ��û���ط�BOSS��Ϣ,�����л�" + Integer.parseInt(backBoss.get("channel").toString()) + "Ƶ��,�л��ɹ������ڴ�����@�ط�!");
            c.getPlayer().changeChannel(Integer.parseInt(backBoss.get("channel").toString()));
            return 0;
        }
    }
    
    public static class help extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().dropMessage(5, "ָ���б� :");
            c.getPlayer().dropMessage(5, "@�⿨/@�鿴/@ea  < ����쳣+�鿴��ǰ״̬ >");
            c.getPlayer().dropMessage(5, "@CGM ѶϢ        < ����ѶϢ�oGM >");
            c.getPlayer().dropMessage(5, "@����            < ��ѯ��ǰ��ͼ���ﱬ�� >");
            c.getPlayer().dropMessage(5, "@��ȡ��ȯ        < ��ֵ��ȡ��ȯ >");
            c.getPlayer().dropMessage(5, "@�浵            < ���浱ǰ������Ϣ >");
            c.getPlayer().dropMessage(5, "@����            < ���͵������г� >");
            c.getPlayer().dropMessage(5, "@�ط�            < ��BOSS�����ط���ͼ >");
            return 1;
        }
    }
}
