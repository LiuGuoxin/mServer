var status = 0;
var choice;
var scrolls = Array(1402251,1412177,1422184,1432214,1482216,1442268,1452252,1462239,1472261,1382259,1492231);
/*
*  �σ��޸�
*/
function start() {
    status = -1;
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == -1)
        cm.dispose();
    else {
        if (status == 0 && mode == 0) {
            cm.dispose();
            return;
        } else if (status >= 1 && mode == 0) {
            cm.sendOk("�ðɣ���ӭ�´μ������٣�.");
            cm.dispose();
            return;
        }
        if (mode == 1)
            status++;
        else
            status--;

        if (status == 0) {
            var choices = "\r\n�����������ѡ��Ĺ�����Ʒ: ";
            for (var i = 0; i < scrolls.length; i++) {
                    choices += "\r\n#L" + i + "##v" + scrolls[i] + "##t" + scrolls[i] + "##l";
            }
            cm.sendSimple("��ӭ����#r����ð�յ�160�������̵�#k ,�����������̵����Ʒô������ѡ��ɣ�ÿ����Ҫ100000��ȯ��." + choices);
        } else if (status == 1) {
            cm.sendYesNo("��ȷ����Ҫ���������Ʒô���⽫������100000��ȯ����" +"\r\n#v" + scrolls[selection] + "##t" + scrolls[selection] + "#");
           choice = selection;
        } else if (status == 2) {
            if (cm.getPlayer().getCSPoints(1) >= 100000) {
                cm.gainNX(-100000);
                //cm.gainItem(scrolls[choice], 1);
		cm.gainGachaponItem(scrolls[choice], 1,"��������");
                cm.sendOk("лл��Ĺ�ˣ��㹺�����Ʒ�Ѿ�������ı�����.");
                cm.dispose();
            } else {
                    cm.sendOk("��Ǹ����û�㹻��Ǯ��.");
                    cm.dispose();
            }
        }
    }
}