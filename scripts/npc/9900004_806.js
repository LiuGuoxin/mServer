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
            text += "#e#r�ۻ���ֵ�ﵽ1000������ɻ�ã�#n#b\r\n\r\n#r��ȡ�������Ҫ���㹻�Ŀռ�Ŷ������ϵͳ���˶���������Ա������Ŷ\r\n"//3
            text += "#L1##r#v4310028#��ȡ�ۼƳ�ֵ1000���#l\r\n\r\n"//3
            cm.sendSimple(text);
        } else if (selection == 1) {
                      if(!cm.canHold(1112793,1)){
			cm.sendOk("��������ı��������ٿճ�2��λ�ã�");
            cm.dispose();
        } else if(cm.haveItem(4310028,1)){
				cm.gainItem(4310028, -1);
                                //cm.gainItem(1122017, 1);
                                //cm.gainItem(2049100, 20);
                                //cm.gainItem(5150040, 15);
                                //cm.gainItem(2340000, 20);
                                //cm.gainItem(5211060, 1);
				//cm.gainItem(1122017, 1);//��׹
                //cm.gainItem(3010070, 1);//����
//cm.gainItem(1112405,10,10,10,10,300,300,10,10,0,0,0,0,0,0);//jiezhi
//cm.gainItem(1112661,20,20,20,20,100,100,10,10,10,10,10,10,0,0);//jiezhi
				cm.gainItem(1142207,25,25,25,25,1000,1000,25,25,20,20,0,0,0,0);//ѫ��
				
//cm.gainItem(1032035,5,5,5,5,300,300,5,5,0,0,0,0,0,0);//��Ҷ����
                                //cm.gainDY(100000);
                                //cm.gainNX(250000);
//cm.gainItem(1102071,10,10,10,10,300,300,14,14,0,0,0,0,0,0);//��Ҷ����
  //cm.gainItem(4001226, 10);
  //cm.gainItem(4251202, 8);

                                //cm.gainItem(1032035,5,5,5,5,0,0,5,5,5,5,5,5,5,5);//��Ҷ����
            cm.sendOk("��ȡ�ɹ�����ѡ���ᡢ��ѡ��������ҹ���Ա��ã�");
//cm.����(2, "��ϲ[" + cm.getPlayer().getName() + "]�ɹ���ȡ�ۻ���ֵ1000�������");
            cm.dispose();
			}else{
            cm.sendOk("��ĳ�ֵ�ﲻ���޶ȣ��������Ѿ���ȡ���ˣ������ظ���ȡ��");
            cm.dispose();
			}
		}
    }
}


