package handling.channel.handler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import client.MapleCharacter;
import client.MapleClient;
import client.inventory.IItem;
import client.inventory.ItemLoader;
import client.inventory.MapleInventoryType;
import constants.GameConstants;
import database.DatabaseConnection;
import handling.world.World;
import server.MapleInventoryManipulator;
import server.MapleItemInformationProvider;
import server.MerchItemPackage;
import tools.FileoutputUtil;
import tools.MaplePacketCreator;
import tools.Pair;
import tools.data.input.SeekableLittleEndianAccessor;
import tools.packet.PlayerShopPacket;

public class HiredMerchantHandler
{
    public static final void UseHiredMerchant(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        if (c.getPlayer().getMap().allowPersonalShop()) {
            final byte state = checkExistance(c.getPlayer().getAccountID(), c.getPlayer().getId());
            switch (state) {
                case 1: {
                    c.getPlayer().dropMessage(1, "����ȥ�Ҹ���������ȡ��֮ǰ��̯�Ķ���");
                    break;
                }
                case 0: {
                    final boolean merch = World.hasMerchant(c.getPlayer().getAccountID());
                    if (!merch) {
                        c.getSession().write((Object)PlayerShopPacket.sendTitleBox());
                        break;
                    }
                    c.getPlayer().dropMessage(1, "�뻻���ط������������Ѿ��п�����");
                    break;
                }
                default: {
                    c.getPlayer().dropMessage(1, "����δ֪����.");
                    break;
                }
            }
        }
        else {
            c.getSession().close();
        }
    }
    
    private static final byte checkExistance(final int accid, final int charid) {
        final Connection con = DatabaseConnection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement("SELECT * from hiredmerch where accountid = ? OR characterid = ?");
            ps.setInt(1, accid);
            ps.setInt(2, charid);
            final ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ps.close();
                rs.close();
                return 1;
            }
            rs.close();
            ps.close();
            return 0;
        }
        catch (SQLException se) {
            return -1;
        }
    }
    
    public static void MerchantItemStore(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        if (c.getPlayer() == null) {
            return;
        }
        final byte operation = slea.readByte();
        if ((operation == 20 || operation == 26) && c.getPlayer().getLastHM() + 86400000L > System.currentTimeMillis()) {
            c.getPlayer().dropMessage(1, "24Сʱ���޷����в�����\r\n��24Сʱ֮���ٽ��в�����\r\n");
            c.getSession().write((Object)MaplePacketCreator.enableActions());
            c.getPlayer().setConversation(0);
            return;
        }
        switch (operation) {
            case 20: {
                slea.readMapleAsciiString();
                final int conv = c.getPlayer().getConversation();
                final boolean merch = World.hasMerchant(c.getPlayer().getAccountID());
                if (merch) {
                    c.getPlayer().dropMessage(1, "��ر��̵������һ��.");
                    c.getPlayer().setConversation(0);
                    break;
                }
                if (conv == 3) {
                    final MerchItemPackage pack = loadItemFrom_Database(c.getPlayer().getId(), c.getPlayer().getAccountID());
                    if (pack == null) {
                        c.getPlayer().dropMessage(1, "��û����Ʒ������ȡ!");
                        c.getPlayer().setConversation(0);
                    }
                    else if (pack.getItems().size() <= 0) {
                        if (!check(c.getPlayer(), pack)) {
                            c.getSession().write((Object)PlayerShopPacket.merchItem_Message((byte)33));
                            return;
                        }
                        if (deletePackage(c.getPlayer().getId(), c.getPlayer().getAccountID(), pack.getPackageid())) {
                            FileoutputUtil.logToFile_chr(c.getPlayer(), "Logs/Log_��Ӷ�����ȡ��¼.txt", " ��ؽ�� " + pack.getMesos());
                            c.getPlayer().gainMeso(pack.getMesos(), false);
                            c.getPlayer().setConversation(0);
                            c.getPlayer().dropMessage("��ȡ���" + pack.getMesos());
                        }
                        else {
                            c.getPlayer().dropMessage(1, "����δ֪����");
                        }
                        c.getPlayer().setConversation(0);
                        c.getSession().write((Object)MaplePacketCreator.enableActions());
                    }
                    else {
                        c.getSession().write((Object)PlayerShopPacket.merchItemStore_ItemData(pack));
                    }
                    break;
                }
                break;
            }
            case 25: {
                if (c.getPlayer().getConversation() != 3) {
                    return;
                }
                c.getSession().write((Object)PlayerShopPacket.merchItemStore((byte)36));
                break;
            }
            case 26: {
                if (c.getPlayer().getConversation() != 3) {
                    c.getPlayer().dropMessage(1, "����δ֪����1.");
                    c.getSession().write((Object)MaplePacketCreator.enableActions());
                    return;
                }
                final MerchItemPackage pack2 = loadItemFrom_Database(c.getPlayer().getId(), c.getPlayer().getAccountID());
                if (pack2 == null) {
                    c.getPlayer().dropMessage(1, "����δ֪����\r\n��û����Ʒ������ȡ��");
                    return;
                }
                if (!check(c.getPlayer(), pack2)) {
                    c.getSession().write((Object)PlayerShopPacket.merchItem_Message((byte)33));
                    return;
                }
                if (deletePackage(c.getPlayer().getId(), c.getPlayer().getAccountID(), pack2.getPackageid())) {
                    c.getPlayer().gainMeso(pack2.getMesos(), false);
                    for (final IItem item : pack2.getItems()) {
                        MapleInventoryManipulator.addFromDrop(c, item, false);
                    }
                    c.getSession().write((Object)PlayerShopPacket.merchItem_Message((byte)29));
                    String item_id = "";
                    String item_name = "";
                    for (final IItem item2 : pack2.getItems()) {
                        item_id = item_id + item2.getItemId() + "(" + item2.getQuantity() + "), ";
                        item_name = item_name + MapleItemInformationProvider.getInstance().getName(item2.getItemId()) + "(" + item2.getQuantity() + "), ";
                    }
                    FileoutputUtil.logToFile_chr(c.getPlayer(), "Logs/Log_��Ӷ��ȡ��¼.txt", " ��ؽ�� " + pack2.getMesos() + " ��ص������� " + pack2.getItems().size() + " ���� " + item_id);
                    FileoutputUtil.logToFile_chr(c.getPlayer(), "Logs/Log_��Ӷ��ȡ��¼2.txt", " ��ؽ�� " + pack2.getMesos() + " ��ص������� " + pack2.getItems().size() + " ���� " + item_name);
                    break;
                }
                c.getPlayer().dropMessage(1, "����δ֪����.");
                break;
            }
            case 27: {
                c.getPlayer().setConversation(0);
                break;
            }
        }
    }
    
    private static void getShopItem(final MapleClient c) {
        if (c.getPlayer().getConversation() != 3) {
            return;
        }
        final MerchItemPackage pack = loadItemFrom_Database(c.getPlayer().getId(), c.getPlayer().getAccountID());
        if (pack == null) {
            c.getPlayer().dropMessage(1, "����δ֪����");
            return;
        }
        if (!check(c.getPlayer(), pack)) {
            c.getPlayer().dropMessage(1, "�㱳�����Ӳ�����");
            return;
        }
        if (deletePackage(c.getPlayer().getId(), c.getPlayer().getAccountID(), pack.getPackageid())) {
            c.getPlayer().gainMeso(pack.getMesos(), false);
            for (final IItem item : pack.getItems()) {
                MapleInventoryManipulator.addFromDrop(c, item, false);
            }
            c.getPlayer().dropMessage(5, "��ȡ�ɹ���");
        }
        else {
            c.getPlayer().dropMessage(1, "����δ֪����");
        }
    }
    
    private static final boolean check(final MapleCharacter chr, final MerchItemPackage pack) {
        if (chr.getMeso() + pack.getMesos() < 0) {
            return false;
        }
        byte eq = 0;
        byte use = 0;
        byte setup = 0;
        byte etc = 0;
        byte cash = 0;
        for (final IItem item : pack.getItems()) {
            final MapleInventoryType invtype = GameConstants.getInventoryType(item.getItemId());
            if (invtype == MapleInventoryType.EQUIP) {
                ++eq;
            }
            else if (invtype == MapleInventoryType.USE) {
                ++use;
            }
            else if (invtype == MapleInventoryType.SETUP) {
                ++setup;
            }
            else if (invtype == MapleInventoryType.ETC) {
                ++etc;
            }
            else {
                if (invtype != MapleInventoryType.CASH) {
                    continue;
                }
                ++cash;
            }
        }
        return chr.getInventory(MapleInventoryType.EQUIP).getNumFreeSlot() > eq && chr.getInventory(MapleInventoryType.USE).getNumFreeSlot() > use && chr.getInventory(MapleInventoryType.SETUP).getNumFreeSlot() > setup && chr.getInventory(MapleInventoryType.ETC).getNumFreeSlot() > etc && chr.getInventory(MapleInventoryType.CASH).getNumFreeSlot() > cash;
    }
    
    private static final boolean deletePackage(final int charid, final int accid, final int packageid) {
        final Connection con = DatabaseConnection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement("DELETE from hiredmerch where characterid = ? OR accountid = ? OR packageid = ?");
            ps.setInt(1, charid);
            ps.setInt(2, accid);
            ps.setInt(3, packageid);
            ps.execute();
            ps.close();
            ItemLoader.HIRED_MERCHANT.saveItems(null, packageid, accid, charid);
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }
    
    private static final MerchItemPackage loadItemFrom_Database(final int charid, final int accountid) {
        final Connection con = DatabaseConnection.getConnection();
        try {
            final PreparedStatement ps = con.prepareStatement("SELECT * from hiredmerch where characterid = ? OR accountid = ?");
            ps.setInt(1, charid);
            ps.setInt(2, accountid);
            final ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                ps.close();
                rs.close();
                return null;
            }
            final int packageid = rs.getInt("PackageId");
            final MerchItemPackage pack = new MerchItemPackage();
            pack.setPackageid(packageid);
            pack.setMesos(rs.getInt("Mesos"));
            pack.setSentTime(rs.getLong("time"));
            ps.close();
            rs.close();
            final Map<Integer, Pair<IItem, MapleInventoryType>> items = ItemLoader.HIRED_MERCHANT.loadItems_hm(packageid, accountid);
            if (items != null) {
                final List<IItem> iters = new ArrayList<IItem>();
                for (final Pair<IItem, MapleInventoryType> z : items.values()) {
                    iters.add(z.left);
                }
                pack.setItems(iters);
            }
            return pack;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
