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
            text += "#e#r�ۻ���ֵ�ﵽ100������ɻ�ã�#n#b\r\n��ȡ�������Ҫ���㹻�Ŀռ�Ŷ������ϵͳ���˶���������Ա������Ŷ\r\n"//3
            text += "#L1##r#v4310014#��ȡ�ۼƳ�ֵ100���#l\r\n\r\n"//3
            cm.sendSimple(text);
        } else if (selection == 1) {
                      if(!cm.canHold(4310014,1)){
			cm.sendOk("��������ı��������ٿճ�2��λ�ã�");
            cm.dispose();
        } else if(cm.haveItem(4310014,1)){
				cm.gainItem(4310014, -1);
		cm.gainItem(1142716,5,5,5,5,50,50,5,5,5,5,5,0,0,0);
				cm.gainItem(1112405,5,5,5,5,100,100,5,5,5,5,0,0,0,0);
                                cm.gainItem(4251202, 2);
                                cm.gainItem(2340000, 5);
                                cm.gainItem(4001129, 5);
                                cm.gainItem(4001226, 1);
                                 cm.gainItem(2049100, 5);
			         cm.gainItem(5150040, 5);
                                      cm.gainNx(20000);��

            cm.sendOk("��ȡ�ɹ��������׹�����ҹ���Ա��ã�");
cm.����(2, "��ϲ[" + cm.getPlayer().getName() + "]�ɹ���ȡ�ۻ���ֵ100�������");
            cm.dispose();
			}else{
            cm.sendOk("��ĳ�ֵ�ﲻ���޶ȣ��������Ѿ���ȡ���ˣ������ظ���ȡ��");
            cm.dispose();
			}
		}
    }
}


