package server.events;

public enum MapleEventType
{
    ��Ҭ�ӱ���("Ҭ�ӱ���", new int[] { 109080000 }), 
    ��ƿ�Ǳ���("��ƿ��", new int[] { 109080010 }), 
    ��ߵ�("��ߵ�", new int[] { 109040000, 109040001, 109040002, 109040003, 109040004 }), 
    ��¥��¥("��¥~��¥~", new int[] { 109030001, 109030002, 109030003 }), 
    ����0X����("����OX����", new int[] { 109020001 }), 
    ѩ����("ѩ����", new int[] { 109060000 });
    
    public String command;
    public int[] mapids;
    
    private MapleEventType(final String comm, final int[] mapids) {
        this.command = comm;
        this.mapids = mapids;
    }
    
    public static final MapleEventType getByString(final String splitted) {
        for (final MapleEventType t : values()) {
            if (t.command.equalsIgnoreCase(splitted)) {
                return t;
            }
        }
        return null;
    }
}
