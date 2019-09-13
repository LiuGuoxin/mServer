package server.events;

import client.MapleCharacter;
import handling.MaplePacket;
import handling.channel.ChannelServer;
import handling.world.World;
import server.MapleInventoryManipulator;
import server.RandomRewards;
import server.Timer;
import server.maps.MapleMap;
import server.maps.SavedLocationType;
import tools.MaplePacketCreator;

public abstract class MapleEvent
{
    protected int[] mapid;
    protected int channel;
    protected boolean isRunning;
    
    public MapleEvent(final int channel, final int[] mapid) {
        this.isRunning = false;
        this.channel = channel;
        this.mapid = mapid;
    }
    
    public boolean isRunning() {
        return this.isRunning;
    }
    
    public MapleMap getMap(final int i) {
        return this.getChannelServer().getMapFactory().getMap(this.mapid[i]);
    }
    
    public ChannelServer getChannelServer() {
        return ChannelServer.getInstance(this.channel);
    }
    
    public void broadcast(final MaplePacket packet) {
        for (int i = 0; i < this.mapid.length; ++i) {
            this.getMap(i).broadcastMessage(packet);
        }
    }
    
    public void givePrize(final MapleCharacter chr) {
        final int reward = RandomRewards.getInstance().getEventReward();
        if (reward == 0) {
            chr.gainMeso(66666, true, false, false);
            chr.dropMessage(5, "���� 166666 ð�ձ�");
        }
        else if (reward == 1) {
            chr.gainMeso(399999, true, false, false);
            chr.dropMessage(5, "���� 399999 ð�ձ�");
        }
        else if (reward == 2) {
            chr.modifyCSPoints(0, 200, false);
            chr.dropMessage(5, "���� 200 ���");
        }
        else if (reward == 3) {
            chr.addFame(2);
            chr.dropMessage(5, "���� 2 ����");
        }
        if (MapleInventoryManipulator.checkSpace(chr.getClient(), 4032226, 1, "")) {
            MapleInventoryManipulator.addById(chr.getClient(), 4032226, (short)1, (byte)0);
            chr.dropMessage(5, "���� 1 ���ƽ�����");
        }
        else {
            chr.gainMeso(100000, true, false, false);
            chr.dropMessage(5, "�����㱳�����ˡ�����ֻ�ܸ�����ð�ձң�");
        }
    }
    
    public void finished(final MapleCharacter chr) {
    }
    
    public void onMapLoad(final MapleCharacter chr) {
    }
    
    public void startEvent() {
    }
    
    public void warpBack(final MapleCharacter chr) {
        int map = chr.getSavedLocation(SavedLocationType.EVENT);
        if (map <= -1) {
            map = 104000000;
        }
        final MapleMap mapp = chr.getClient().getChannelServer().getMapFactory().getMap(map);
        chr.changeMap(mapp, mapp.getPortal(0));
    }
    
    public void reset() {
        this.isRunning = true;
    }
    
    public void unreset() {
        this.isRunning = false;
    }
    
    public static final void setEvent(final ChannelServer cserv, final boolean auto) {
        if (auto) {
            for (final MapleEventType t : MapleEventType.values()) {
                final MapleEvent e = cserv.getEvent(t);
                if (e.isRunning) {
                    for (final int i : e.mapid) {
                        if (cserv.getEvent() == i) {
                            e.broadcast(MaplePacketCreator.serverNotice(0, "������ʼֻʣ��һ����!"));
                            e.broadcast(MaplePacketCreator.getClock(60));
                            Timer.EventTimer.getInstance().schedule(new Runnable() {
                                @Override
                                public void run() {
                                    e.startEvent();
                                }
                            }, 60000L);
                            break;
                        }
                    }
                }
            }
        }
        cserv.setEvent(-1);
    }
    
    public static final void mapLoad(final MapleCharacter chr, final int channel) {
        if (chr == null) {
            return;
        }
        for (final MapleEventType t : MapleEventType.values()) {
            final MapleEvent e = ChannelServer.getInstance(channel).getEvent(t);
            if (e.isRunning) {
                if (chr.getMapId() == 109050000) {
                    e.finished(chr);
                }
                for (final int i : e.mapid) {
                    if (chr.getMapId() == i) {
                        e.onMapLoad(chr);
                    }
                }
            }
        }
    }
    
    public static final void onStartEvent(final MapleCharacter chr) {
        for (final MapleEventType t : MapleEventType.values()) {
            final MapleEvent e = chr.getClient().getChannelServer().getEvent(t);
            if (e.isRunning) {
                for (final int i : e.mapid) {
                    if (chr.getMapId() == i) {
                        e.startEvent();
                        chr.dropMessage(5, String.valueOf(t) + " ���ʼ");
                    }
                }
            }
        }
    }
    
    public static final String scheduleEvent(final MapleEventType event, final ChannelServer cserv) {
        if (cserv.getEvent() != -1 || cserv.getEvent(event) == null) {
            return "�Ļ�Ѿ�����ֹ������.";
        }
        for (final int i : cserv.getEvent(event).mapid) {
            if (cserv.getMapFactory().getMap(i).getCharactersSize() > 0) {
                return "�û�Ѿ���ִ����.";
            }
        }
        cserv.setEvent(cserv.getEvent(event).mapid[0]);
        cserv.getEvent(event).reset();
        World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(0, "� " + String.valueOf(event) + " ������Ƶ�� " + cserv.getChannel() + " ���� , Ҫ�μӵ�����뵽Ƶ�� " + cserv.getChannel() + ".���ҵ������г����npc�����룡").getBytes());
        World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(0, "� " + String.valueOf(event) + " ������Ƶ�� " + cserv.getChannel() + " ���� , Ҫ�μӵ�����뵽Ƶ�� " + cserv.getChannel() + ".���ҵ������г����npc�����룡").getBytes());
        World.Broadcast.broadcastMessage(MaplePacketCreator.serverNotice(0, "� " + String.valueOf(event) + " ������Ƶ�� " + cserv.getChannel() + " ���� , Ҫ�μӵ�����뵽Ƶ�� " + cserv.getChannel() + ".���ҵ������г����npc�����룡").getBytes());
        return "";
    }
}
