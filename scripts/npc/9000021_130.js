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
	text += "#d�ϳ�-- #r�������ʹѫ�¡���Ҫ������Ʒ��\r\n#v4021000##d#z4021000# * 10��\r\n#v1142109##d#z1142109# * 1��\r\n#v4021002##d#z4021002# * 10��\r\n#v4021004##d#z4021004# * 10��\r\n~\r\n"
	text += "\r\n#L1##d���ռ���������Ʒ��ȷ�������ʹѫ��";//����
	text += "     \r\n"
        cm.sendSimple(text);
        } else if (selection == 1) {
                      if(!cm.haveItem(1142109,1)){
		cm.sendOk("��������ı��������ٿճ�2��λ�ã�");
            cm.dispose();
        } else if(cm.haveItem(4021000,10) && cm.haveItem(1142109,1) && cm.haveItem(4021002,10) && cm.haveItem(4021004,10)){
				cm.gainItem(4021000, -10);
				cm.gainItem(1142109, -1);
				cm.gainItem(4021002, -10);
				cm.gainItem(4021004, -10);
cm.gainItem(1142178,15,15,15,15,100,100,13,13,10,10,10,0,0,0);
            cm.sendOk("�����ɹ���");
            cm.dispose();
cm.����(3, "��ң�[" + cm.getPlayer().getName() + "]�ɹ����������ʹѫ�£���ϲ����");
			}else{
            cm.sendOk("�޷�������������#v4021000#����10��\r\n#v1142109#����1��\r\n#v4021002#����10��\r\n#v4021004#����10��\r\n");
            cm.dispose();
			}
		}
    }
}




