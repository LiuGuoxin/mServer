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
	text += "#d�ϳ�-- #r������ľ���ѫ�¡���Ҫ������Ʒ��\r\n#v4251202##d#z4251202# * 5��\r\n#v1142178##d#z1142178# * 1��\r\n#v4001084##d#z4001084# * 1��\r\n#v4001085##d#z4001085# * 1��\r\n~\r\n"
	text += "\r\n#L1##d���ռ���������Ʒ��ȷ�������ľ���ѫ��";//����
	text += "     \r\n"
        cm.sendSimple(text);
        } else if (selection == 1) {
                      if(!cm.haveItem(1142178,1)){
		cm.sendOk("��������ı��������ٿճ�2��λ�ã�");
            cm.dispose();
        } else if(cm.haveItem(4251202,5) && cm.haveItem(1142178,1) && cm.haveItem(4001084,1) && cm.haveItem(4001085,1)){
				cm.gainItem(4251202, -5);
				cm.gainItem(1142178, -1);
				cm.gainItem(4001084, -1);
				cm.gainItem(4001085, -1);
cm.gainItem(1142788,25,25,25,25,200,200,15,15,10,10,10,0,0,0);
            cm.sendOk("�����ɹ���");
            cm.dispose();
cm.����(3, "��ң�[" + cm.getPlayer().getName() + "]�ɹ����������ľ���ѫ�£���ϲ����");
			}else{
            cm.sendOk("�޷�������������#v4251202#����5��\r\n#v1142178#����1��\r\n#v4001084#����1��\r\n#v4001085#����1��\r\n");
            cm.dispose();
			}
		}
    }
}




