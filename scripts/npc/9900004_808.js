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
            text += "#e#r�ۻ���ֵ�ﵽ3000������ɻ�ã�#n#b\r\n\r\n#r��ȡ�������Ҫ���㹻�Ŀռ�Ŷ������ϵͳ���˶���������Ա������Ŷ\r\n"//3
            text += "#L1##r#v4310025#��ȡ�ۼƳ�ֵ3000���#l\r\n\r\n"//3
            cm.sendSimple(text);
        } else if (selection == 1) {
                      if(!cm.canHold(4310025,1)){
			cm.sendOk("��������ı��������ٿճ�2��λ�ã�");
            cm.dispose();
      }   else if(cm.haveItem(4310010,1)){
				cm.gainItem(4310010, -1);
                                cm.gainItem(1122017, 1);//diaozhui
                                cm.gainItem(2049100, 40);
                                cm.gainItem(5211060, 1)
                                cm.gainItem(5150040, 30);
                                cm.gainItem(2340000, 40);;
                                cm.gainItem(4001226, 40)
                                cm.gainItem(1012070,10,10,10,10,0,0,10,10,0,0,0,0,0,0);//xuugao
  
  
cm.gainItem(1112661,20,20,20,20,3500,3500,30,30,20,20,20,20,0,0);//jiezhi
				cm.gainItem(1102041,30,30,30,30,1000,1000,15,15,10,10,10,10,0,0);
//��Ҷ����
cm.gainItem(1082149,40,40,40,40,5000,5000,20,20,10,10,10,10,0,0);
				cm.gainItem(1142742,40,40,40,40,2000,2000,40,40,100,100,100,100,20,20);//����ð�յ�-����
				//cm.gainMeso(20000000);
 //cm.gainItem(1102039,10,10,10,10,0,0,10,10,5,5,5,5,5,5);
//cm.gainDY(200000);
cm.gainNX(200000);

            cm.sendOk("��ȡ�ɹ�����ѡ���ᡢ��ѡ��������ҹ���Ա��ã�");
//cm.����(2, "��ϲ[" + cm.getPlayer().getName() + "]�ɹ���ȡ�ۻ���ֵ3000�������");
            cm.dispose();
			}else{
            cm.sendOk("��ĳ�ֵ�ﲻ���޶ȣ��������Ѿ���ȡ���ˣ������ظ���ȡ��");
            cm.dispose();
			}
		}
    }
}


