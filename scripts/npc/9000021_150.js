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
                
   cm.sendOk("��лʹ��.");
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
	for(i = 0; i < 10; i++){
		text += "";
	}				
	text += "#d�ϳ�-- #r������ͳ����ѫ�¡���Ҫ������Ʒ��\r\n#v4251202##d#z4251202# * 10��\r\n#v1142788##d#z1142788# * 1��\r\n#v4001083##d#z4001083# * 1��\r\n#v4030002##d#z4030002# * 30��\r\n~\r\n"
	text += "\r\n#L1##d���ռ���������Ʒ��ȷ������ͳ����ѫ��";//����
	text += "     \r\n"
        cm.sendSimple(text);
        } else if (selection == 1) {
                      if(!cm.haveItem(1142788,1)){
		cm.sendOk("��������ı��������ٿճ�2��λ�ã�");
            cm.dispose();
        } else if(cm.haveItem(4251202,10) && cm.haveItem(1142788,1) && cm.haveItem(4001083,1) && cm.haveItem(4030002,30)){
				cm.gainItem(4251202, -10);
				cm.gainItem(1142788, -1);
				cm.gainItem(4001083, -1);
				cm.gainItem(4030002, -30);
cm.gainItem(1142569,30,30,30,30,500,500,20,20,30,30,30,30,30,30);
            cm.sendOk("�����ɹ���");
            cm.dispose();
cm.����(3, "��ң�[" + cm.getPlayer().getName() + "]�ɹ���������ͳ����ѫ�£���ϲ����");
			}else{
            cm.sendOk("�޷�������������#v4251202#����10��\r\n#v1142788#����1��\r\n#v4001083#����1��\r\n#v4030002#����30��\r\n");
            cm.dispose();
			}
		}
    }
}




