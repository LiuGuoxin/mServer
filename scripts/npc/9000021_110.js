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
	text += "#d�ϳ�-- #r���м�ð�ռ�ѫ�¡���Ҫ������Ʒ��\r\n#v4000017##d#z4000017# * 50��\r\n#v1142101##d#z1142101# * 1��\r\n#v4000040##d#z4000040# * 10��\r\n#v4000176##d#z4000176# * 10��\r\n~\r\n"
	text += "\r\n#L1##d���ռ���������Ʒ��ȷ���м�ð�ռ�ѫ��";//����
	text += "     \r\n"
        cm.sendSimple(text);
        } else if (selection == 1) {
                      if(!cm.haveItem(1142101,1)){
		cm.sendOk("��������ı��������ٿճ�2��λ�ã�");
            cm.dispose();
        } else if(cm.haveItem(4000017,50) && cm.haveItem(1142101,1) && cm.haveItem(4000040,10) && cm.haveItem(4000176,10)){
				cm.gainItem(4000017, -50);
				cm.gainItem(1142101, -1);
				cm.gainItem(4000040, -10);
				cm.gainItem(4000176, -10);
cm.gainItem(1142108,8,8,8,8,50,50,7,7,10,10,10,0,0,0);
            cm.sendOk("�����ɹ���");
            cm.dispose();
cm.����(3, "��ң�[" + cm.getPlayer().getName() + "]�ɹ������м�ð�ռ�ѫ�£���ϲ����");
			}else{
            cm.sendOk("�޷�������������#v4000017#����50��\r\n#v1142101#����1��\r\n#v4000040#����10��\r\n#v4000176#����10��\r\n");
            cm.dispose();
			}
		}
    }
}




