function start() {
    status = -1;

    action(1, 0, 0);
}
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
            var tex2 = "";
            var text = "";
            for (i = 0; i < 10; i++) {
                text += "#k��ӭ����#r����ð�յ�#k����������ȡ��ֵ��ƷŶ!˲���Ϊ����\r\n\r\n";
            text += "#L1##d#v4310014##z4310014#\t��ȡ�ۼƳ�ֵ100���#l\r\n\r\n"//
            //text += "#L2##d#v4310016##z4310016#\t��ȡ�ۼƳ�ֵ200���#l\r\n\r\n"//
            text += "#L3##d#v4310049##z4310049#\t��ȡ�ۼƳ�ֵ300���#l\r\n\r\n"
            text += "#L4##d#v4310011##z4310011#\t��ȡ�ۼƳ�ֵ500���#l\r\n\r\n"
            //text += "#L5##d#v4310018##z4310018#\t��ȡ�ۼƳ�ֵ800���#l\r\n\r\n"
            text += "#L6##d#v4310028##z4310028#\t��ȡ�ۼƳ�ֵ1000���#l\r\n\r\n"
            text += "#L7##d#v4310025##z4310025#\t��ȡ�ۼƳ�ֵ2000���#l\r\n\r\n"
            text += "#L8##d#v4310010##z4310010#\t��ȡ�ۼƳ�ֵ3000���#l\r\n\r\n"
            cm.sendSimple(text);
            }
        } else if (selection == 1) {
		cm.openNpc(9900004, 801);
        } else if (selection == 2) {
		cm.openNpc(9900004, 802);
        } else if (selection == 3) {
		cm.openNpc(9900004, 803);
        } else if (selection == 4) {
		cm.openNpc(9900004, 804);
        } else if (selection == 5) {
		cm.openNpc(9900004, 805);
        } else if (selection == 6) {
		cm.openNpc(9900004, 806);
        } else if (selection == 7) {
		cm.openNpc(9900004, 807);
        } else if (selection == 8) {
		cm.openNpc(9900004, 808);
	}
    }
}


