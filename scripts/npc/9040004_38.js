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
	text += "#d����-- #r��������Ƭ��ָ����Ҫ������Ʒ��\r\n#30000��ȯ# * \r\n~\r\n"
	text += "\r\n#L1##d��Ҫ����������Ƭ��ָ";//����
	text += "     \r\n"
        cm.sendSimple(text);
        } else if (selection == 1) {
                      if(!cm.canHold(4001230,1)){
			cm.sendOk("��������ı��������ٿճ�2��λ�ã�");
            cm.dispose();
        } else if (cm.getPlayer().getCSPoints(1) >= 30000) {
                cm.gainNX(-30000);
               
cm.gainItem(1112122,10,10,10,10,10,10,10,10,8,8,8,0,0,0);
            cm.sendOk("�����ɹ���");
            cm.dispose();
cm.����(3, "��ң�[" + cm.getPlayer().getName() + "]�ɹ�����������Ƭ��ָ����ϲ����");
			}else{
            cm.sendOk("�޷����򣬵����\r\n");
            cm.dispose();
			}
		}
    }
}




