/*
 * 
 * @wnms
 * @����̨���͸���npc
 */
var ����ţ = "#fMob/0130101.img/move/0#";
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
            cm.sendSimple("#r" + ����ţ + ����ţ + "��ѡ����Ҫ�������Ʒ" + ����ţ + ����ţ + ����ţ + "\r\n#b#L2##g͸��װ��#n#l\r\n#L4##g��ֵ���#n#l\r\n#L1##g��������#n#l\r\n#L5##g���ֶᱦ��#n#l\r\n#L3##g����ר��#n#l\r\n");
        } else if (status == 1) {
            if (selection == 0) {//�س�
             cm.openNpc(9040004,1);
            } else if (selection == 1) {//160����
              cm.openNpc(9040004,2);
            }else if(selection == 2){//��ָ
                cm.openNpc(9040004,3);
            }else if(selection == 3){//
                cm.openNpc(9040004,4);
            }else if(selection == 4){
                cm.openNpc(9040004,5);
            }else if(selection == 5){
                cm.openNpc(9040004,8);
            }else if(selection == 6){
                cm.openNpc(9000021,160);
        }
        }
    }
}


