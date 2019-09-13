package server;

import java.util.Set;

import database.DatabaseConnection;
import handling.cashshop.CashShopServer;
import handling.channel.ChannelServer;
import handling.login.LoginServer;
import handling.world.World;
import tools.MaplePacketCreator;

public class ShutdownServer implements Runnable
{
    private static final ShutdownServer instance;
    public static boolean running;
    public int mode;
    
    public ShutdownServer() {
        this.mode = 0;
    }
    
    public static ShutdownServer getInstance() {
        return ShutdownServer.instance;
    }
    
    public void shutdown() {
        this.run();
    }
    
    @Override
    public void run() {
        Timer.WorldTimer.getInstance().stop();
        Timer.MapTimer.getInstance().stop();
        Timer.BuffTimer.getInstance().stop();
        Timer.CloneTimer.getInstance().stop();
        Timer.EventTimer.getInstance().stop();
        Timer.EtcTimer.getInstance().stop();
        for (final ChannelServer cs : ChannelServer.getAllInstances()) {
            cs.closeAllMerchant();
        }
        try {
            World.Guild.save();
            World.Alliance.save();
            World.Family.save();
        }
        catch (Exception ex) {}
        World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(0, " ��Ϸ���������ر�ά��������Ұ�ȫ����..."));
        for (final ChannelServer cs : ChannelServer.getAllInstances()) {
            try {
                cs.setServerMessage("��Ϸ���������ر�ά��������Ұ�ȫ����...");
            }
            catch (Exception ex2) {}
        }
        final Set<Integer> channels = ChannelServer.getAllInstance();
        for (final Integer channel : channels) {
            try {
                final ChannelServer cs2 = ChannelServer.getInstance(channel);
                cs2.saveAll();
                cs2.setFinishShutdown();
                cs2.shutdown();
            }
            catch (Exception e2) {
                System.out.println("Ƶ��" + String.valueOf(channel) + " �رմ���.");
            }
        }
        System.out.println("����˹ر��¼� 1 �����.");
        System.out.println("����˹ر��¼� 2 ��ʼ...");
        try {
            LoginServer.shutdown();
            System.out.println("��¼�ŷ����ر����...");
        }
        catch (Exception ex3) {}
        try {
            CashShopServer.shutdown();
            System.out.println("�̳��ŷ����ر����...");
        }
        catch (Exception ex4) {}
        try {
            DatabaseConnection.closeAll();
        }
        catch (Exception ex5) {}
        System.out.println("����˹ر��¼� 2 �����.");
        try {
            Thread.sleep(1000L);
        }
        catch (Exception e) {
            System.out.println("�رշ���˴��� - 2" + e);
        }
        System.exit(0);
    }
    
    static {
        instance = new ShutdownServer();
        ShutdownServer.running = false;
    }
}
