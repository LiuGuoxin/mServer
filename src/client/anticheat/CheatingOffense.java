package client.anticheat;

public enum CheatingOffense
{
    �ٻ��޿��ٹ���((byte)5, 6000L, 10, (byte)1), 
    ���ٹ���((byte)5, 6000L, 50, (byte)2), 
    ���ٹ���2((byte)5, 9000L, 20, (byte)2), 
    �����ƶ�((byte)1, 30000L, 20, (byte)2), 
    �˺���ͬ((byte)2, 30000L, 150, (byte)2), 
    �����޵�((byte)1, 30000L, 1200, (byte)0), 
    ħ���˺�����((byte)5, 30000L, -1, (byte)0), 
    ħ���˺�����2((byte)10, 30000L, -1, (byte)0), 
    ����������((byte)5, 30000L, -1, (byte)0), 
    ������ײ����((byte)1, 60000L, 100, (byte)2), 
    ��������2((byte)10, 30000L, -1, (byte)0), 
    ������Χ����((byte)5, 60000L, 1500), 
    �ٻ��޹�����Χ����((byte)5, 60000L, 200), 
    �ظ�����HP((byte)1, 30000L, 1000, (byte)0), 
    �ظ�����MP((byte)1, 30000L, 1000, (byte)0), 
    ȫͼ����_�ͻ���((byte)5, 5000L, 10), 
    ȫͼ����_�����((byte)3, 5000L, 100), 
    ����ȫͼ����_�ͻ���((byte)5, 10000L, 20), 
    ����ȫͼ����_�����((byte)3, 10000L, 100, (byte)0), 
    ʹ�ù�Զ���͵�((byte)1, 60000L, 100, (byte)0), 
    �ر��ʹ���((byte)20, 180000L, 100, (byte)2), 
    �����쳣((byte)1, 300000L), 
    ������������((byte)1, 300000L, -1, (byte)0), 
    ʹ�ò����ڵ���((byte)1, 300000L), 
    ����Լ�����((byte)1, 1000L, 1), 
    ����ʮ�弶�������((byte)1, 1000L, 1), 
    ��Ǯը��_�����ڵ���((byte)1, 300000L), 
    �ٻ��޹������������쳣((byte)1, 10000L, 3), 
    �����������ǲ���ϵ����((byte)20, 10000L, 3), 
    HEAL_ATTACKING_UNDEAD((byte)20, 30000L, 100), 
    ����((byte)1, 7000L, 5);
    
    private final byte points;
    private final long validityDuration;
    private final int autobancount;
    private byte bantype;
    
    public final byte getPoints() {
        return this.points;
    }
    
    public final long getValidityDuration() {
        return this.validityDuration;
    }
    
    public final boolean shouldAutoban(final int count) {
        return this.autobancount != -1 && count >= this.autobancount;
    }
    
    public final byte getBanType() {
        return this.bantype;
    }
    
    public final void setEnabled(final boolean enabled) {
        this.bantype = (byte)(enabled ? 1 : 0);
    }
    
    public final boolean isEnabled() {
        return this.bantype >= 1;
    }
    
    private CheatingOffense(final byte points, final long validityDuration) {
        this(points, validityDuration, -1, (byte)1);
    }
    
    private CheatingOffense(final byte points, final long validityDuration, final int autobancount) {
        this(points, validityDuration, autobancount, (byte)1);
    }
    
    private CheatingOffense(final byte points, final long validityDuration, final int autobancount, final byte bantype) {
        this.bantype = 0;
        this.points = points;
        this.validityDuration = validityDuration;
        this.autobancount = autobancount;
        this.bantype = bantype;
    }
}
