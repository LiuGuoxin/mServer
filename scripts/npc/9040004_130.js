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
                text += "";
            }
			//��ʾ��ƷIDͼƬ�õĴ�����  #v����д��ID#
            //text += "#e#r��ã�����͸��װ��,��������.\r\n\r\n"//3
            //text += "#L1##e#d #v1022048#����͸���۾�\r\n"//3
               //text += "#L2##e#d #v1102039#����͸������\r\n"//3
               //text += "#L3##e#d #v1002186#����͸��ñ\r\n"//3
               //text += "#L4##e#d #v1072153#����͸��Ь��\r\n"//3
               //text += "#L5##e#d #v1702224#����͸������\r\n"//3
            //text += "#L6##e#d #v1032024#����͸������\r\n"//3
            text += "#L7##e#d #v1112226#���������ָ\r\n"//3
            text += "#L8##e#d #v1112122#������Ƭ��ָ\r\n"//3
            //text += "#L9##e#d #v1112952#����ϡ���ķ�ŭ��ָ\r\n"//3
            text += "#L10##e#d #v1112404#���򼫹��ָ\r\n"//3
            cm.sendSimple(text);
        } else if (selection == 1) {
		cm.openNpc(9040004, 31);
        } else if (selection == 2) {
		cm.openNpc(9040004, 32);
        } else if (selection == 3) {
		cm.openNpc(9040004, 33);
        } else if (selection == 4) {
		cm.openNpc(9040004, 34);
        } else if (selection == 5) {
		cm.openNpc(9040004, 35);
        } else if (selection == 6) {
		cm.openNpc(9040004, 36);
        } else if (selection == 7) {
		cm.openNpc(9040004, 37);
        } else if (selection == 8) {
		cm.openNpc(9040004, 38);
        } else if (selection == 9) {
		cm.openNpc(9040004, 39);
        } else if (selection == 10) {
		cm.openNpc(9040004, 310);
	}
    }
}


