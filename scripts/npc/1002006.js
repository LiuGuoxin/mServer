/*
 * 
 * @wnms
 * @����̨���͸���npc
 */
var ����ţ = "#fMob/0130100.img/move/0#";
function start() {
    status = -1;
    action(1, 0, 0);
}
var ð�ձ� = 5000;
function action(mode, type, selection) {
    if (mode == -1) {
        cm.dispose();
    }
    else {
        if (status >= 0 && mode == 0) {
            cm.sendOk("��л��Ĺ��٣�");
            cm.dispose();
            return;
        }
        if (mode == 1) {
            status++;
        }
        else {
            status--;
        }
        if (status == 0) {
            cm.sendSimple("#r��ѡ����������Ѫ��\r\n\r\n#d#L0#1000HPѪ������#l\r\n\r\n#L1##g2000ѪѪ������#n#l\r\n\r\n#b#L2##r3000ѪѪ������\r\n\r\n");
        } else if (status == 1) {
            if (selection == 0) {//��������
             cm.openNpc(1002006,100);
            } else if (selection == 1) {//�����һ�����
              cm.openNpc(1002006,110);
            }else if(selection == 2){
                cm.openNpc(1002006,120);
        }
        }
    }
}


