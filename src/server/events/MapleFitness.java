package server.events;

import java.util.concurrent.ScheduledFuture;

import client.MapleCharacter;
import server.Timer;
import tools.MaplePacketCreator;

public class MapleFitness extends MapleEvent
{
    private static final long serialVersionUID = 845748950824L;
    private long time;
    private long timeStarted;
    private ScheduledFuture<?> fitnessSchedule;
    private ScheduledFuture<?> msgSchedule;
    
    public MapleFitness(final int channel, final int[] mapid) {
        super(channel, mapid);
        this.time = 600000L;
        this.timeStarted = 0L;
    }
    
    @Override
    public void finished(final MapleCharacter chr) {
        this.givePrize(chr);
    }
    
    @Override
    public void onMapLoad(final MapleCharacter chr) {
        if (this.isTimerStarted()) {
            chr.getClient().getSession().write((Object)MaplePacketCreator.getClock((int)(this.getTimeLeft() / 1000L)));
        }
    }
    
    @Override
    public void startEvent() {
        this.unreset();
        super.reset();
        this.broadcast(MaplePacketCreator.getClock((int)(this.time / 1000L)));
        this.timeStarted = System.currentTimeMillis();
        this.checkAndMessage();
        this.fitnessSchedule = Timer.EventTimer.getInstance().schedule(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < MapleFitness.this.mapid.length; ++i) {
                    for (final MapleCharacter chr : MapleFitness.this.getMap(i).getCharactersThreadsafe()) {
                        MapleFitness.this.warpBack(chr);
                    }
                }
                MapleFitness.this.unreset();
            }
        }, this.time);
        this.broadcast(MaplePacketCreator.serverNotice(0, "���Ѵ򿪡��ǵ��ڹ����š������롣"));
    }
    
    public boolean isTimerStarted() {
        return this.timeStarted > 0L;
    }
    
    public long getTime() {
        return this.time;
    }
    
    public void resetSchedule() {
        this.timeStarted = 0L;
        if (this.fitnessSchedule != null) {
            this.fitnessSchedule.cancel(false);
        }
        this.fitnessSchedule = null;
        if (this.msgSchedule != null) {
            this.msgSchedule.cancel(false);
        }
        this.msgSchedule = null;
    }
    
    @Override
    public void reset() {
        super.reset();
        this.resetSchedule();
        this.getMap(0).getPortal("join00").setPortalState(false);
    }
    
    @Override
    public void unreset() {
        super.unreset();
        this.resetSchedule();
        this.getMap(0).getPortal("join00").setPortalState(true);
    }
    
    public long getTimeLeft() {
        return this.time - (System.currentTimeMillis() - this.timeStarted);
    }
    
    public void checkAndMessage() {
        this.msgSchedule = Timer.EventTimer.getInstance().register(new Runnable() {
            @Override
            public void run() {
                final long timeLeft = MapleFitness.this.getTimeLeft();
                if (timeLeft > 9000L && timeLeft < 11000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "���������޷�սʤ�ı���������ϣ������һ��սʤ�����´��ټ�~"));
                }
                else if (timeLeft > 11000L && timeLeft < 101000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "�ðɣ���ʣ�µ�ʱ��û�ж����ˡ���ץ��ʱ��!"));
                }
                else if (timeLeft > 101000L && timeLeft < 241000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "��4�׶Σ������һ�� [ð�յ����ܲ���]. �벻Ҫ�����һ�̷�����ƴ��ȫ��. ���Ľ�������߲������Ŷ!"));
                }
                else if (timeLeft > 241000L && timeLeft < 301000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "�����׶Σ��кܶ����壬����ܻῴ�����ǣ����㲻�ܲ�����.������ķ�ʽ������ȥ��."));
                }
                else if (timeLeft > 301000L && timeLeft < 361000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "������������ƶ���С�ĵ�������ȥ��"));
                }
                else if (timeLeft > 361000L && timeLeft < 501000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "���ס��������ڻ�ڼ������ˣ�������Ϸ����̭. ������벹��HP��ʹ��ҩˮ���ƶ�֮ǰ�Ȼָ�HP��"));
                }
                else if (timeLeft > 501000L && timeLeft < 601000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "����Ҫ��������Ҫ֪������Ҫ�������ӵ��㽶���У����ڹ涨��ʱ�����һ�У�"));
                }
                else if (timeLeft > 601000L && timeLeft < 661000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "�ڶ��׶��ϰ��Ǻ������㽶. ��ȷ��������ȷ��·���ƶ���������ǵĹ�����"));
                }
                else if (timeLeft > 661000L && timeLeft < 701000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "���ס��������ڻ�ڼ������ˣ�������Ϸ����̭. ������벹��HP��ʹ��ҩˮ���ƶ�֮ǰ�Ȼָ�HP��"));
                }
                else if (timeLeft > 701000L && timeLeft < 781000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "ÿ���˶��òμ� [ð�յ����ܲ���] �μ������Ŀ��������ɵ�˳�򣬶����ý���������ֻ�����֣��������������4���׶Ρ�"));
                }
                else if (timeLeft > 781000L && timeLeft < 841000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "��Ҫη����ѣ�������ȥ��������Ϸ������������."));
                }
                else if (timeLeft > 841000L) {
                    MapleFitness.this.broadcast(MaplePacketCreator.serverNotice(0, "[ð�յ����ܲ���]��4���׶Σ��������������Ϸ���������������ӱ�������̭��������ע����һ�㡣"));
                }
            }
        }, 90000L);
    }
}
