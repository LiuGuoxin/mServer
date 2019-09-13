package handling.channel.handler;

import java.util.ArrayList;
import java.util.List;

import client.MapleCharacter;
import client.MapleClient;
import tools.MaplePacketCreator;
import tools.data.input.SeekableLittleEndianAccessor;

public class BeanGame
{
    public static void BeanGame1(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        final MapleCharacter chr = c.getPlayer();
        final List<Beans> beansInfo = new ArrayList<Beans>();
        final int type = slea.readByte();
        int ���� = 0;
        int ������̖ = 0;
        if (type == 1) {
            ���� = slea.readShort();
            chr.setBeansRange(����);
            c.getSession().write((Object)MaplePacketCreator.enableActions());
        }
        else if (type == 0) {
            ���� = slea.readShort();
            ������̖ = slea.readInt() + 1;
            chr.setBeansRange(����);
            chr.setBeansNum(������̖);
            if (������̖ == 1) {
                chr.setCanSetBeansNum(false);
            }
        }
        else if (type == 2) {
            if (type == 11 || type == 0) {
                ���� = slea.readShort();
                ������̖ = slea.readInt() + 1;
                chr.setBeansRange(����);
                chr.setBeansNum(������̖);
                if (������̖ == 1) {
                    chr.setCanSetBeansNum(false);
                }
            }
        }
        else if (type == 6) {
            slea.skip(1);
            final int ѭ�h�Δ� = slea.readByte();
            if (ѭ�h�Δ� == 0) {
                return;
            }
            if (ѭ�h�Δ� != 1) {
                slea.skip((ѭ�h�Δ� - 1) * 8);
            }
            if (chr.isCanSetBeansNum()) {
                chr.setBeansNum(chr.getBeansNum() + ѭ�h�Δ�);
            }
            chr.gainBeans(-ѭ�h�Δ�);
            chr.setCanSetBeansNum(true);
        }
        else if (type == 11 || type == 6) {
            ���� = slea.readShort();
            chr.setBeansRange(����);
            final byte size = (byte)(slea.readByte() + 1);
            final short Pos = slea.readShort();
            final byte Type = (byte)(slea.readByte() + 1);
            c.getSession().write((Object)MaplePacketCreator.showBeans(����, size, Pos, Type));
        }
        else {
            System.out.println("δ̎�����͡�" + type + "��\n��" + slea.toString());
        }
    }
    
    private static int getBeanType() {
        final int random = rand(1, 100);
        int beanType = 0;
        switch (random) {
            case 2: {
                beanType = 1;
                break;
            }
            case 49: {
                beanType = 2;
                break;
            }
            case 99: {
                beanType = 3;
                break;
            }
        }
        return beanType;
    }
    
    private static int rand(final int lbound, final int ubound) {
        return (int)(Math.random() * (ubound - lbound + 1) + lbound);
    }
    
    public static final void BeanGame2(final SeekableLittleEndianAccessor slea, final MapleClient c) {
        c.getSession().write((Object)MaplePacketCreator.updateBeans(c.getPlayer().getId(), c.getPlayer().getBeans()));
        c.getSession().write((Object)MaplePacketCreator.enableActions());
    }
    
    public class Beans
    {
        private int number;
        private int type;
        private int pos;
        
        public Beans(final int pos, final int type, final int number) {
            this.pos = pos;
            this.number = number;
            this.type = type;
        }
        
        public int getType() {
            return this.type;
        }
        
        public int getNumber() {
            return this.number;
        }
        
        public int getPos() {
            return this.pos;
        }
    }
}
