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
            text += "#e#r�ۻ���ֵ�ﵽ300������ɻ�ã�#n#b\r\n\r\n#r��ȡ�������Ҫ���㹻�Ŀռ�Ŷ������ϵͳ���˶���������Ա������Ŷ\r\n"//3
            text += "#L1##r#v4310049#��ȡ�ۼƳ�ֵ300���#l\r\n\r\n"//3
            cm.sendSimple(text);
        } else if (selection == 1) {
                      if(!cm.canHold(1112793,1)){
			cm.sendOk("��������ı��������ٿճ�2��λ�ã�");
            cm.dispose();
        } else if(cm.haveItem(4310049,1)){
				cm.gainItem(4310049, -1);
                                
				cm.gainItem(1082252,1,1,1,1,1,1,10,10,1,1,1,1,1,1);
				
				cm.gainItem(1142218,10,10,10,10,50,50,10,10,5,5,5,5,10,10);//xunzhang
				cm.gainItem(1112405,7,7,7,7,100,100,7,7,7,7,0,0,0,0);
                                cm.gainItem(2049100, 10);
                                cm.gainItem(5150040, 8);
                                cm.gainItem(2340000, 10);
                                       cm.gainItem(4001129, 8);
                                 cm.gainItem(4001226, 3);
                                    cm.gainItem(4251202, 5);
//cm.gainNx(+20000);

            cm.sendOk("��ȡ�ɹ�����ѡ��������ҹ���Ա��ã�");

            cm.dispose();
			}else{
            cm.sendOk("��ĳ�ֵ�ﲻ���޶ȣ��������Ѿ���ȡ���ˣ������ظ���ȡ��");
            cm.dispose();
			}
		}
    }
}


