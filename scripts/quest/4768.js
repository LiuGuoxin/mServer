var status = -1;

function start(mode, type, selection) {
	if (mode == -1) {
		qm.dispose();
	} else {
		if (mode == 1)
			status++;
		else
			status--;
		if (status == 0) {
			if(qm.getQuestStatus(4768)==2){
			qm.sendOk("你已经领取过奖励，继续努力到70级可以获得更多奖励喔");
			qm.forceCompleteQuest(4768);
			qm.dispose();
			}else{
			qm.sendNext("恭喜你当前等级已经到达#b60#k级。");
			}
		} else if (status == 1) {
			qm.sendOk("恭喜你获得系统奖励！\r\n\r\n#fUI/UIWindow.img/QuestIcon/4/0#\r\n#一千抵用卷");
			qm.gainDY(1000);
                        qm.gainItem(4031691, 1);
			qm.forceCompleteQuest(4768);
			qm.dispose();
		} 
	}
}
