/*
 * 
 * @wnms
 * @����̨���͸���npc
 */
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
            cm.sendSimple("#r����������ð�յ�ѫ��������,��ѡ����������ѫ��\r\n#d#L0##v1142101#�Ȱ�ð�յ�ѫ������#l\r\n#L1##g#v1142108#�м�ð�ռ�ѫ������#n#l\r\n#b#L2##r#v1142109#�߼�ð�ռ�ѫ������\r\n#L3##g#v1142178#ð�������ʹѫ������\n\r\n#b#L4##r#v1142788#�����ľ���ѫ��ѫ������\r\n#b#L5##r#v1142569#����ͳ����ѫ������\r\n#L6##g#v1142574#������֤ѫ��#n#l\r\n");
        } else if (status == 1) {
            if (selection == 0) {//��������
             cm.openNpc(9000021,100);
            } else if (selection == 1) {//�����һ�����
              cm.openNpc(9000021,110);
            }else if(selection == 2){
                cm.openNpc(9000021,120);
            }else if(selection == 3){
                cm.openNpc(9000021,130);
            }else if(selection == 4){
                cm.openNpc(9000021,140);
            }else if(selection == 5){
                cm.openNpc(9000021,150);
            }else if(selection == 6){
                cm.openNpc(9000021,160);
        }
        }
    }
}


