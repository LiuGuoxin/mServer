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
	text += "#d�ϳ�-- #r��߼�ð�ռ�ѫ�¡���Ҫ������Ʒ��\r\n#v4011002##d#z4011002# * 20��\r\n#v1142108##d#z1142108# * 1��\r\n#v4011003##d#z4011003# * 10��\r\n#v4000176##d#z4000176# * 20��\r\n~\r\n"
	text += "\r\n#L1##d���ռ���������Ʒ��ȷ���߼�ð�ռ�ѫ��";//����
	text += "     \r\n"
        cm.sendSimple(text);
        } else if (selection == 1) {
                      if(!cm.haveItem(1142108,1)){
		cm.sendOk("��������ı��������ٿճ�2��λ�ã�");
            cm.dispose();
        } else if(cm.haveItem(4011002,20) && cm.haveItem(1142108,1) && cm.haveItem(4011003,10) && cm.haveItem(4000176,20)){
				cm.gainItem(4011002, -20);
				cm.gainItem(1142108, -1);
				cm.gainItem(4011003, -10);
				cm.gainItem(4000176, -20);
cm.gainItem(1142109,12,12,12,12,80,80,10,10,10,10,10,0,0,0);
            cm.sendOk("�����ɹ���");
            cm.dispose();
cm.����(3, "��ң�[" + cm.getPlayer().getName() + "]�ɹ������߼�ð�ռ�ѫ�£���ϲ����");
			}else{
            cm.sendOk("�޷�������������#v4011002#����20��\r\n#v1142108#����1��\r\n#v4011003#����10��\r\n#v4000176#����20��\r\n");
            cm.dispose();
			}
		}
    }
}




