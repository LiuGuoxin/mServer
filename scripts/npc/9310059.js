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

            cm.sendOk("感谢你的光临！");
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
			//显示物品ID图片用的代码是  #v这里写入ID#
            text += "#e#r你好！在我这里可以帮你制作你所需要的武器，以下是我可以为您制作的武器列表.\r\n"//3
            //text += "#L2##e#d#v1302030#Lv43  枫叶武器制造(每个职业武器都可制作)#l\r\n"//3
            //text += "#L3##e#d#v1332056#Lv64  枫叶武器制造(每个职业武器都可制作)#l\r\n"//3
            //text += "#L4##e#d#v1332114#Lv77  黄金枫叶制造(每个职业武器都可制作)#l\r\n"//3
            text += "#L5##e#d#v1412135#Lv150 法弗纳兑换#l\r\n"//3
            //text += "#L7##e#d#v4031975#Lv120 永恒武器制造#l\r\n"//3
            //text += "#L8##e#d#v4031975#Lv130 伽耶武器制造(天然金色品质武器)#l\r\n"//3
           text += "#L9##e#d#v1002357#Lv100 进阶扎昆头盔合成#l\r\n"//3

            text += "#L10##e#d#v1122266#Lv140 高级贝勒德合成#l\r\n"//3
            cm.sendSimple(text);
        } else if (selection == 1) {
		cm.openNpc(9000018, 1);
        } else if (selection == 2) {
		cm.openNpc(9000018, 1);
        } else if (selection == 3) {
		cm.openNpc(9000018, 2);
        } else if (selection == 4) {
		cm.openNpc(9000018, 3);
        } else if (selection == 5) {
		cm.openNpc(9310059, 777);
        } else if (selection == 6) {
		cm.openNpc(9000018, 5);
        } else if (selection == 7) {
		cm.openNpc(9000018, 6);
        } else if (selection == 8) {
		cm.openNpc(9000018, 9);
	}
    }
}


