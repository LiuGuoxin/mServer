package client.messages.commands;

import client.MapleCharacter;
import client.MapleClient;
import client.SkillFactory;
import constants.ServerConstants;
import handling.channel.ChannelServer;
import handling.world.World;
import server.maps.MapleMap;
import tools.FileoutputUtil;
import tools.MaplePacketCreator;
import tools.StringUtil;

public class InternCommand
{
    public static ServerConstants.PlayerGMRank getPlayerLevelRequired() {
        return ServerConstants.PlayerGMRank.INTERN;
    }
    
    public static class ���� extends Warp
    {
    }
    
    public static class ��� extends Ban
    {
    }
    
    public static class ���� extends Hide
    {
    }
    
    public static class ������� extends UnHide
    {
    }
    
    public static class Ban extends CommandExecute
    {
        protected boolean hellban;
        
        public Ban() {
            this.hellban = false;
        }
        
        private String getCommand() {
            return "Ban";
        }
        
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 3) {
                c.getPlayer().dropMessage(5, "[Syntax] !" + this.getCommand() + " <���> <ԭ��>");
                return 0;
            }
            final int ch = World.Find.findChannel(splitted[1]);
            final StringBuilder sb = new StringBuilder(c.getPlayer().getName());
            sb.append(" banned ").append(splitted[1]).append(": ").append(StringUtil.joinStringFrom(splitted, 2));
            final MapleCharacter target = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(splitted[1]);
            if (target == null || ch < 1) {
                if (MapleCharacter.ban(splitted[1], sb.toString(), false, c.getPlayer().isAdmin() ? 250 : c.getPlayer().getGMLevel(), splitted[0].equals("!hellban"))) {
                    c.getPlayer().dropMessage(6, "[" + this.getCommand() + "] �ɹ����߷��� " + splitted[1] + ".");
                    return 1;
                }
                c.getPlayer().dropMessage(6, "[" + this.getCommand() + "] ����ʧ�� " + splitted[1]);
                return 0;
            }
            else {
                if (c.getPlayer().getGMLevel() <= target.getGMLevel()) {
                    c.getPlayer().dropMessage(6, "[" + this.getCommand() + "] ���ܷ���GM...");
                    return 1;
                }
                sb.append(" (IP: ").append(target.getClient().getSessionIPAddress()).append(")");
                if (target.ban(sb.toString(), c.getPlayer().isAdmin(), false, this.hellban)) {
                    c.getPlayer().dropMessage(6, "[" + this.getCommand() + "] �ɹ����� " + splitted[1] + ".");
                    FileoutputUtil.logToFile_chr(c.getPlayer(), "Logs/Log_���.rtf", sb.toString());
                    World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(6, "[���ϵͳ]" + target.getName() + " ��Ϊʹ�÷Ƿ�����������÷�š�").getBytes());
                    return 1;
                }
                c.getPlayer().dropMessage(6, "[" + this.getCommand() + "] ����ʧ��.");
                return 0;
            }
        }
    }
    
    public static class online1 extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().dropMessage(6, "���ߵĽ�ɫ �l��-" + c.getChannel() + ":");
            c.getPlayer().dropMessage(6, c.getChannelServer().getPlayerStorage().getOnlinePlayers(true));
            return 1;
        }
    }
    
    public static class CnGM extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            World.Broadcast.broadcastGMMessage(MaplePacketCreator.serverNotice(5, "<GM�����Ӵ�>�l��" + c.getPlayer().getClient().getChannel() + " [" + c.getPlayer().getName() + "] : " + StringUtil.joinStringFrom(splitted, 1)).getBytes());
            return 1;
        }
    }
    
    public static class Hide extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            SkillFactory.getSkill(9001004).getEffect(1).applyTo(c.getPlayer());
            c.getPlayer().dropMessage(6, "����Ա���� = ���� \r\n ���������!unhide");
            return 0;
        }
    }
    
    public static class UnHide extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().dispelBuff(9001004);
            c.getPlayer().dropMessage(6, "����Ա���� = �ر� \r\n ����������!hide");
            return 1;
        }
    }
    
    public static class Warp extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            MapleCharacter victim = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
            if (victim != null) {
                if (splitted.length == 2) {
                    c.getPlayer().changeMap(victim.getMap(), victim.getMap().findClosestSpawnpoint(victim.getPosition()));
                }
                else {
                    final MapleMap target = ChannelServer.getInstance(c.getChannel()).getMapFactory().getMap(Integer.parseInt(splitted[2]));
                    victim.changeMap(target, target.getPortal(0));
                }
            }
            else {
                try {
                    victim = c.getPlayer();
                    final int ch = World.Find.findChannel(splitted[1]);
                    if (ch < 0) {
                        final MapleMap target2 = c.getChannelServer().getMapFactory().getMap(Integer.parseInt(splitted[1]));
                        c.getPlayer().changeMap(target2, target2.getPortal(0));
                    }
                    else {
                        victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(splitted[1]);
                        c.getPlayer().dropMessage(6, "���ڻ�Ƶ��,��ȴ�.");
                        if (victim.getMapId() != c.getPlayer().getMapId()) {
                            final MapleMap mapp = c.getChannelServer().getMapFactory().getMap(victim.getMapId());
                            c.getPlayer().changeMap(mapp, mapp.getPortal(0));
                        }
                        c.getPlayer().changeChannel(ch);
                    }
                }
                catch (Exception e) {
                    c.getPlayer().dropMessage(6, "����Ҳ����� " + e.getMessage());
                    return 0;
                }
            }
            return 1;
        }
    }
}
