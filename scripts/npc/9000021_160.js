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
	text += "#d�ϳ�-- #r��������֤ѫ�¡���Ҫ������Ʒ��\r\n#v4000017##d#z4000017# * 2��\r\n#v4001230##d#z4001230# * 1��\r\n#v4000040##d#z4000040# * 2��\r\n#v4000176##d#z4000176# * 2��\r\n~\r\n"
	text += "\r\n#L1##d���ռ���������Ʒ��ȷ���Ȱ�ð�յ�ѫ��";//����
	text += "     \r\n"
        cm.sendSimple(text);
        } else if (selection == 1) {
                      if(!cm.canHold(4001230,1)){
			cm.sendOk("��������ı��������ٿճ�2��λ�ã�");
            cm.dispose();
        } else if(cm.haveItem(4000017,2) && cm.haveItem(4001230,1) && cm.haveItem(4000040,2) && cm.haveItem(4000176,2)){
				cm.gainItem(4000017, -2);
				cm.gainItem(4001230, -1);
				cm.gainItem(4000040, -2);
				cm.gainItem(4000176, -2);
cm.gainItem(1142574,10,10,10,10,100,100,8,8,10,10,10,10,10,0);
cm.gainNX(+10000);
            cm.sendOk("�����ɹ���");
            cm.dispose();
cm.����(3, "��ң�[" + cm.getPlayer().getName() + "]�ɹ�����������֤ѫ�£���ϲ����");
			}else{
            cm.sendOk("�޷�������������#v4000017#����2��\r\n#v4001230#����1��\r\n#v4000040#����2��\r\n#v4000176#����2��\r\n");
            cm.dispose();
			}
		}
    }
}




