/*
    WNMS - T079
*/

var status = -1;
var rank = "D";
var exp = 0;

function start() {
    if (cm.getCarnivalParty() != null) {
        status = 99;
    }
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        status--;
    }
    if (mode == -1) {
        cm.dispose();
        return;
    }
    if (status == 0) {
        selStr = cm.getChar().getName() + "���,��ǰʱ����#b" + cm.getHour() + "ʱ:" + cm.getMin() + "��:" + cm.getSec() + "��\r\n#k��ȯ:#r" + cm.getNX(1) + ". \r\n#k��ʱ��ﵽ#r20:00-20:05#k֮��,������������������~\r\n";
        selStr += "�����ѡ��Ҫ����Ǿ��顣���ÿ�οɻ��1-5���������ݵ�ǰ�ȼ����ģ�\r\n";
        selStr += "#L0#��Ҫ���#l\r\n\r\n";
        selStr += "#L1#��Ҫ����#l";
        cm.sendSimple(selStr);
    } else if (status == 1) {
        switch (selection) {
            case 0://nx
                if (cm.getHour() == 20 && cm.getMin() <= 5) {
                    sl = Math.random() * 5 + 1;
                    cm.gainNX(+sl);
                    cm.sendOk("�����:" + sl + "���");
                    cm.dispose();
                } else {

                    cm.sendOk("�ʱ�仹û�е�");
                    cm.dispose();
                }
                break;
            case 1://exp
                 if (cm.getHour() == 20 && cm.getMin() <= 5) {
                    sl = (cm.getLevel()*5)+Math.random() * cm.getLevel() + 1;
                    cm.gainExp(+sl);
                    cm.sendOk("�����:" + sl + "����");
                    cm.dispose();
                } else {

                    cm.sendOk("�ʱ�仹û�е�");
                    cm.dispose();
                }
                break;
           
        }
    }
}
