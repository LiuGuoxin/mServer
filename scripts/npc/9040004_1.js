..var status = 0;
var choice;
var scrolls = Array(5570000,2044908,2040303,2044815,2040403,2040506,2040508,2040507,2040603,2040709,2040710,2040711,2040806,2040807,2040903,2041024,2041025,2043003,2043103,2043203,2043303,2043703,2043803,2044003,2044103,2044203,2044303,2044403,2044503,2044603,2044703);
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
            cm.sendSimple("��ӭ����#r�ع�ð�յ������̵�#k ,�����������̵����Ʒô������ѡ��ɣ�ÿ����Ҫ2400��ȯ��." + choices);
        } else if (status == 1) {
            cm.sendYesNo("��ȷ����Ҫ���������Ʒô���⽫������3000��ȯ����" +"\r\n#v" + scrolls[selection] + "##t" + scrolls[selection] + "#");
           choice = selection;
        } else if (status == 2) {
            if (cm.getPlayer().getCSPoints(1) >= 3000) {
                cm.gainNX(-3000);
                cm.gainItem(scrolls[choice], 1);
                cm.sendOk("лл��Ĺ�ˣ��㹺�����Ʒ�Ѿ�������ı�����.");
                cm.dispose();
            } else {
                    cm.sendOk("��Ǹ����û�㹻��Ǯ��.");
                    cm.dispose();
            }
        }
    }
}