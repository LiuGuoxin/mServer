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
            text += "#e#r�ۻ���ֵ�ﵽ2000������ɻ�ã�#n#b\r\n\r\n#r��ȡ�������Ҫ���㹻�Ŀռ�Ŷ������ϵͳ���˶���������Ա������Ŷ\r\n"//3
            text += "#L1##r#v4310025#��ȡ�ۼƳ�ֵ2000���#l\r\n\r\n"//3
            cm.sendSimple(text);
        } else if (selection == 1) {
                      if(!cm.canHold(4310025,1)){
			cm.sendOk("��������ı��������ٿճ�2��λ�ã�");
            cm.dispose();
      }   else if(cm.haveItem(4310025,1)){
				cm.gainItem(4310025, -1);
                               cm.gainItem(4251202, 20);//���ˮ��
				cm.gainItem(2340000, 30);//ף������
				 cm.gainItem(5211060, 1);//����
			cm.gainItem(2049100, 30);//����
				cm.gainItem(1122017, 1);//��׹
       				cm.gainItem(4001226, 15);//����֮��_
                               cm.gainItem(3010070, 1);//����
cm.gainItem(1112405,10,10,10,10,50,50,20,20,0,0,0,0,0,0);//��ָ
				cm.gainItem(1082149,8,8,8,8,100,100,8,8,10,10,10,10,0,0);
//����
cm.gainItem(1102071,30,30,30,30,1000,1000,20,20,10,10,10,10,0,0);//��Ҷ����
				cm.gainItem(1142208,35,35,35,35,100,100,35,35,20,20,20,20,20,20);//ѫ��
 cm.gainItem(1032035,10,10,10,10,0,0,10,10,5,5,5,5,5,5);//��Ҷ����
 cm.gainItem(1132036,10,10,10,10,100,100,8,8,5,5,5,5,5,5);//����
//cm.gainDY(100000);
cm.gainNX(200000);

            cm.sendOk("��ȡ�ɹ�����ѡ���ᡢ��ѡ��������ҹ���Ա��ã�");
//cm.����(2, "��ϲ[" + cm.getPlayer().getName() + "]�ɹ���ȡ�ۻ���ֵ2000�������");
            cm.dispose();
			}else{
            cm.sendOk("��ĳ�ֵ�ﲻ���޶ȣ��������Ѿ���ȡ���ˣ������ظ���ȡ��");
            cm.dispose();
			}
		}
    }
}


