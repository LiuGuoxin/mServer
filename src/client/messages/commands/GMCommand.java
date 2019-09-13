package client.messages.commands;

import java.util.Map;

import client.MapleCharacter;
import client.MapleClient;
import client.inventory.IItem;
import client.inventory.MapleInventoryType;
import constants.ServerConstants;
import handling.channel.ChannelServer;
import handling.world.World;
import server.MapleInventoryManipulator;
import server.maps.MapleMap;
import tools.ArrayMap;
import tools.Pair;
import tools.StringUtil;

public class GMCommand
{
    public static ServerConstants.PlayerGMRank getPlayerLevelRequired() {
        return ServerConstants.PlayerGMRank.GM;
    }
    
    public static class �� extends WarpHere
    {
    }
    
    public static class �ȼ� extends Level
    {
    }
    
    public static class תְ extends Job
    {
    }
    
    public static class ��� extends ClearInv
    {
    }
    
    public static class ���� extends DC
    {
    }
    
    public static class ��ȡ��� extends spy
    {
    }
    
    public static class �������� extends online
    {
    }
    
    public static class ������ extends UnBan
    {
    }
    
    public static class ˢǮ extends GainMeso
    {
    }
    
    public static class WarpHere extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            MapleCharacter victim = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
            if (victim != null) {
                victim.changeMap(c.getPlayer().getMap(), c.getPlayer().getMap().findClosestSpawnpoint(c.getPlayer().getPosition()));
            }
            else {
                final int ch = World.Find.findChannel(splitted[1]);
                if (ch < 0) {
                    c.getPlayer().dropMessage(5, "��ɫ������");
                    return 1;
                }
                victim = ChannelServer.getInstance(ch).getPlayerStorage().getCharacterByName(splitted[1]);
                c.getPlayer().dropMessage(5, "���ڴ�����ҵ����");
                victim.dropMessage(5, "GM���ڴ�����");
                if (victim.getMapId() != c.getPlayer().getMapId()) {
                    final MapleMap mapp = victim.getClient().getChannelServer().getMapFactory().getMap(c.getPlayer().getMapId());
                    victim.changeMap(mapp, mapp.getPortal(0));
                }
                victim.changeChannel(c.getChannel());
            }
            return 1;
        }
    }
    
    public static class UnBan extends CommandExecute
    {
        protected boolean hellban;
        
        public UnBan() {
            this.hellban = false;
        }
        
        private String getCommand() {
            return "UnBan";
        }
        
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                c.getPlayer().dropMessage(6, "[Syntax] !" + this.getCommand() + " <ԭ��>");
                return 0;
            }
            byte ret;
            if (this.hellban) {
                ret = MapleClient.unHellban(splitted[1]);
            }
            else {
                ret = MapleClient.unban(splitted[1]);
            }
            if (ret == -2) {
                c.getPlayer().dropMessage(6, "[" + this.getCommand() + "] SQL error.");
                return 0;
            }
            if (ret == -1) {
                c.getPlayer().dropMessage(6, "[" + this.getCommand() + "] The character does not exist.");
                return 0;
            }
            c.getPlayer().dropMessage(6, "[" + this.getCommand() + "] Successfully unbanned!");
            final byte ret_ = MapleClient.unbanIPMacs(splitted[1]);
            if (ret_ == -2) {
                c.getPlayer().dropMessage(6, "[UnbanIP] SQL error.");
            }
            else if (ret_ == -1) {
                c.getPlayer().dropMessage(6, "[UnbanIP] The character does not exist.");
            }
            else if (ret_ == 0) {
                c.getPlayer().dropMessage(6, "[UnbanIP] No IP or Mac with that character exists!");
            }
            else if (ret_ == 1) {
                c.getPlayer().dropMessage(6, "[UnbanIP] IP/Mac -- one of them was found and unbanned.");
            }
            else if (ret_ == 2) {
                c.getPlayer().dropMessage(6, "[UnbanIP] Both IP and Macs were unbanned.");
            }
            return (ret_ > 0) ? 1 : 0;
        }
    }
    
    public static class DC extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            ChannelServer.forceRemovePlayerByCharName(splitted[1]);
            c.getPlayer().dropMessage("������ſ��ǳɹ�");
            return 1;
        }
    }
    
    public static class Job extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().changeJob(Integer.parseInt(splitted[1]));
            return 1;
        }
    }
    
    public static class GainMeso extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().gainMeso(Integer.MAX_VALUE - c.getPlayer().getMeso(), true);
            return 1;
        }
    }
    
    public static class Level extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            c.getPlayer().setLevel(Short.parseShort(splitted[1]));
            c.getPlayer().levelUp();
            if (c.getPlayer().getExp() < 0) {
                c.getPlayer().gainExp(-c.getPlayer().getExp(), false, false, true);
            }
            return 1;
        }
    }
    
    public static class spy extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            if (splitted.length < 2) {
                c.getPlayer().dropMessage(6, "ʹ�ù���: !spy <�������>");
            }
            else {
                final MapleCharacter victim = c.getChannelServer().getPlayerStorage().getCharacterByName(splitted[1]);
                if (victim.getGMLevel() > c.getPlayer().getGMLevel() && c.getPlayer().getId() != victim.getId()) {
                    c.getPlayer().dropMessage(5, "�㲻�ܲ鿴�����Ȩ�޵���!");
                    return 0;
                }
                if (victim != null) {
                    c.getPlayer().dropMessage(5, "�����(" + victim.getId() + ")״̬:");
                    c.getPlayer().dropMessage(5, "�ȼ�: " + victim.getLevel() + "ְҵ: " + victim.getJob() + "����: " + victim.getFame());
                    c.getPlayer().dropMessage(5, "��ͼ: " + victim.getMapId() + " - " + victim.getMap().getMapName().toString());
                    c.getPlayer().dropMessage(5, "����: " + victim.getStat().getStr() + "  ||  ����: " + victim.getStat().getDex() + "  ||  ����: " + victim.getStat().getInt() + "  ||  ����: " + victim.getStat().getLuk());
                    c.getPlayer().dropMessage(5, "ӵ�� " + victim.getMeso() + " ���.");
                }
                else {
                    c.getPlayer().dropMessage(5, "�Ҳ��������.");
                }
            }
            return 1;
        }
    }
    
    public static class online extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            int total = 0;
            final int curConnected = c.getChannelServer().getConnectedClients();
            c.getPlayer().dropMessage(6, "-------------------------------------------------------------------------------------");
            c.getPlayer().dropMessage(6, "�l��: " + c.getChannelServer().getChannel() + " ��������: " + curConnected);
            total += curConnected;
            for (final MapleCharacter chr : c.getChannelServer().getPlayerStorage().getAllCharacters()) {
                if (chr != null && c.getPlayer().getGMLevel() >= chr.getGMLevel()) {
                    final StringBuilder ret = new StringBuilder();
                    ret.append(" ��ɫ���� ");
                    ret.append(StringUtil.getRightPaddedStr(chr.getName(), ' ', 15));
                    ret.append(" ID: ");
                    ret.append(StringUtil.getRightPaddedStr(chr.getId() + "", ' ', 4));
                    ret.append(" �ȼ�: ");
                    ret.append(StringUtil.getRightPaddedStr(String.valueOf(chr.getLevel()), ' ', 4));
                    ret.append(" ְҵ: ");
                    ret.append(chr.getJob());
                    if (chr.getMap() == null) {
                        continue;
                    }
                    ret.append(" ��ͼ: ");
                    ret.append(chr.getMapId());
                    ret.append("(").append(chr.getMap().getMapName()).append(")");
                    c.getPlayer().dropMessage(6, ret.toString());
                }
            }
            c.getPlayer().dropMessage(6, "��ǰƵ���ܼ���������: " + total);
            c.getPlayer().dropMessage(6, "-------------------------------------------------------------------------------------");
            final int channelOnline = c.getChannelServer().getConnectedClients();
            int totalOnline = 0;
            for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                totalOnline += cserv.getConnectedClients();
            }
            c.getPlayer().dropMessage(6, "��ǰ�������ܼ���������: " + totalOnline + "��");
            c.getPlayer().dropMessage(6, "-------------------------------------------------------------------------------------");
            return 1;
        }
    }
    
    public static class ClearInv extends CommandExecute
    {
        @Override
        public int execute(final MapleClient c, final String[] splitted) {
            final Map<Pair<Short, Short>, MapleInventoryType> eqs = new ArrayMap<Pair<Short, Short>, MapleInventoryType>();
            if (splitted[1].equals("ȫ��")) {
                for (final MapleInventoryType type : MapleInventoryType.values()) {
                    for (final IItem item : c.getPlayer().getInventory(type)) {
                        eqs.put(new Pair<Short, Short>(item.getPosition(), item.getQuantity()), type);
                    }
                }
            }
            else if (splitted[1].equals("��װ������")) {
                for (final IItem item2 : c.getPlayer().getInventory(MapleInventoryType.EQUIPPED)) {
                    eqs.put(new Pair<Short, Short>(item2.getPosition(), item2.getQuantity()), MapleInventoryType.EQUIPPED);
                }
            }
            else if (splitted[1].equals("����")) {
                for (final IItem item2 : c.getPlayer().getInventory(MapleInventoryType.EQUIP)) {
                    eqs.put(new Pair<Short, Short>(item2.getPosition(), item2.getQuantity()), MapleInventoryType.EQUIP);
                }
            }
            else if (splitted[1].equals("����")) {
                for (final IItem item2 : c.getPlayer().getInventory(MapleInventoryType.USE)) {
                    eqs.put(new Pair<Short, Short>(item2.getPosition(), item2.getQuantity()), MapleInventoryType.USE);
                }
            }
            else if (splitted[1].equals("װ��")) {
                for (final IItem item2 : c.getPlayer().getInventory(MapleInventoryType.SETUP)) {
                    eqs.put(new Pair<Short, Short>(item2.getPosition(), item2.getQuantity()), MapleInventoryType.SETUP);
                }
            }
            else if (splitted[1].equals("����")) {
                for (final IItem item2 : c.getPlayer().getInventory(MapleInventoryType.ETC)) {
                    eqs.put(new Pair<Short, Short>(item2.getPosition(), item2.getQuantity()), MapleInventoryType.ETC);
                }
            }
            else if (splitted[1].equals("����")) {
                for (final IItem item2 : c.getPlayer().getInventory(MapleInventoryType.CASH)) {
                    eqs.put(new Pair<Short, Short>(item2.getPosition(), item2.getQuantity()), MapleInventoryType.CASH);
                }
            }
            else {
                c.getPlayer().dropMessage(6, "[ȫ��/��װ������/����/����/װ��/����/����]");
            }
            for (final Map.Entry<Pair<Short, Short>, MapleInventoryType> eq : eqs.entrySet()) {
                MapleInventoryManipulator.removeFromSlot(c, eq.getValue(), eq.getKey().left, eq.getKey().right, false, false);
            }
            return 1;
        }
    }
}
