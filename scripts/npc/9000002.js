var status = 0;

function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if(mode == -1){
		cm.dispose();
	}else{
		if(mode == 0 && status == 0){
			cm.dispose();
			return;
		}
		if(mode == 1){
			status++;
		}else{
			status--;
		}
		if(status == 0){
			//cm.sendYesNo("��ϲ����սͨ��,��ȡ��Ľ�����!")
			cm.sendYesNo("���δ��ʼ!~!")
		}else if(status == 1){
			if(cm.getBossLog('2017-1-4�') < 1){
				var map = cm.getSavedLocation("EVENT");
				if (map > -1 && map != cm.getMapId()) {
					cm.warp(map, 0);
				} else {
					cm.warp(910000000, 0);
				}
				//cm.gainItem(2049100, 5);//����
				//cm.gainItem(2340000, 5);//ף��
				//cm.gainItem(4001226, 1);//������
				//cm.gainNX(1000)//��ȯ
				//cm.setBossLog('2017-1-4�');
				cm.dispose();
			}else{
				cm.sendOk("���Ѿ��μӹ�һ����,�����ظ���ȡ.");
				cm.dispose();
			}
			
		}else{
			cm.dispose();
		}
	}
}
