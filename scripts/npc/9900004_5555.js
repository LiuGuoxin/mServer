importPackage(net.sf.odinms.client);
var status = 0;

var ttt ="#fUI/UIWindow.img/Quest/icon9/0#";
var xxx ="#fUI/UIWindow.img/Quest/icon8/0#";
var sss ="#fUI/UIWindow.img/QuestIcon/3/0#";


	function start() {
		status = -1;
		action(1, 0, 0);
		}
	function action(mode, type, selection) {
		if (mode == -1) {
		cm.dispose();
		} else {
		if (status >= 0 && mode == 0) {
		cm.dispose();
		return;
		}
		if (mode == 1)
		status++;
		else
		status--;


	if (status == 0) {

	    var textz = "\r\n#e��ӭ����#r����ð�յ�ÿ������ר��.ÿ�����񶼿������.��ȡ���Ӧ�Ľ�������������ÿ�ն���#l\r\n#bע�����ϲ����ᱻϵͳ�̵����̵�һ�ɲ��⣬��ע��#l\r\n";

		textz += "#d#L0#�ռ�#v4000015##b#z4000015##r 100 #d���ɶһ�#r#v4251201#�е����\r\n";

		textz += "#d#L1#�ռ�#v4000010##b#z4000010##r 100 #d���ɶһ�#r#v4030002#����˹����2��\r\n";

		textz += "#d#L2#�ռ�#v4000053##b#z4000053##r 22 #d���ɶһ�#r#v4001322#��������ʯ10\r\n";

		textz += "#d#L3#�ռ�#v4000180##b#z4000180##r 66 #d���ɶ�#r#v4251201#�е����\r\n";

		textz += "#d#L4#�ռ�#v4000273##b#z4000273##r 88 #d���ɶһ�#r#v2340000#ף������#l\r\n";

		textz += "#d#L5#�ռ�#v4001084##b#z4001084##r 1 #d���ɶһ�#r200����#l\r\n";

		textz += "#d#L6#�ռ�#v4001085##b#z4001085##r 1 #d���ɶһ�#r#v4251201#�е����#l\r\n";

		textz += "#d#L7#�ռ�#v4001083##b#z4001083##r 1 #d���ɶһ�#r#v4001226#����֮��#l\r\n";

		//textz += "#d#L8#�ռ�#v4001085##b#z4001085##r 1 #d��\r\n  �ɶһ�#r1000��#d��#l\r\n";

		//textz += "#d#L9#�ռ�#v4001084##b#z4001084##r 1 #d��\r\n  �ɶһ�#r1000��#d��#l\r\n";

		//textz += "#d#L10#�ռ�#v4001083##b#z4001083##r 1 #d��\r\n  �ɶһ�#r2000��#d��#l\r\n";
                cm.sendSimple (textz);  

			
	}else if (status == 1) {

	if (selection == 0) {
if (cm.haveItem(4000015,100) && cm.getBossLog('PlayQuest1') < 1) {
cm.dispose();

 			cm.sendOk("�����#r100W#k���#k");
      			cm.dispose();
		cm.setBossLog('PlayQuest1');
                cm.gainItem(4251201,1);
                //cm.gainExp( + 50000);
		cm.gainItem(4000015,-100);
		cm.sendOk("�������,������½���:\r\n");
		cm.dispose();
	} else 
		cm.sendOk("�����Ƿ����㹻����Ʒ��\r\n#r(ע:������ÿ��ֻ�����һ��)#k");
		cm.dispose();
	    


}else if (selection == 1) {
	if (cm.haveItem(4000010,100) && cm.getBossLog('PlayQuest2') < 1) {
		cm.gainItem(4000010,-100);
		cm.setBossLog('PlayQuest2');
		cm.gainItem(4030002,2);
		cm.sendOk("�������,������½���:\r\n#20����!");
		cm.dispose();
	} else 
		cm.sendOk("�����Ƿ����㹻����Ʒ��\r\n#r(ע:������ÿ��ֻ�����һ��)#k");
		cm.dispose();
	    

}else if (selection == 2) {
	if (cm.haveItem(4000053,22) && cm.getBossLog('PlayQuest3') < 1) {
		cm.gainItem(4000053,-22);
		cm.setBossLog('PlayQuest3');
		cm.gainItem(4001322,10);
		cm.sendOk("�������,������½���:\r\n#40����!");
		cm.dispose();
	} else 
		cm.sendOk("�����Ƿ����㹻����Ʒ��\r\n#r(ע:������ÿ��ֻ�����һ��)#k");
		cm.dispose();

}else if (selection == 3){
	if (cm.haveItem(4000180,66) && cm.getBossLog('PlayQuest4') < 1) {
		cm.gainItem(4000180,-66);
		cm.setBossLog('PlayQuest4');
		cm.gainItem(4251201,1);
		cm.sendOk("�������,������½���:\r\n#150����!");
		cm.dispose();
	} else 
		cm.sendOk("�����Ƿ����㹻����Ʒ��\r\n#r(ע:������ÿ��ֻ�����һ��)#k");
		cm.dispose();

}else if (selection == 4){
	if (cm.haveItem(4000273,88) && cm.getBossLog('PlayQuest5') < 1) {
		cm.gainItem(4000273,-88);
		cm.setBossLog('PlayQuest5');
		cm.gainItem(2340000,1);
		cm.sendOk("�������,������½���:\r\n#ף������!");
		cm.dispose();
	} else 
		cm.sendOk("�����Ƿ����㹻����Ʒ��\r\n#r(ע:������ÿ��ֻ�����һ��)#k");
		cm.dispose();

}else if (selection == 5){
	if (cm.haveItem(4001084,1) && cm.getBossLog('PlayQuest6') < 1) {
		cm.gainItem(4001084,-1);
		cm.setBossLog('PlayQuest6');
		cm.gainExp( + 2000000);
		cm.sendOk("�������,������½���:\r\n#200����!");
		cm.dispose();
	} else 
		cm.sendOk("�����Ƿ����㹻����Ʒ��\r\n#r(ע:������ÿ��ֻ�����һ��)#k");
		cm.dispose();

}else if (selection == 6){
	if (cm.haveItem(4001085,1) && cm.getBossLog('PlayQuest7') < 1) {
		cm.gainItem(4001085,-1);
		cm.setBossLog('PlayQuest7');
		cm.gainItem(4251201,1);
		cm.sendOk("�������,������½���:\r\n#�е����!");
		cm.dispose();
	} else 
		cm.sendOk("�����Ƿ����㹻����Ʒ��\r\n#r(ע:������ÿ��ֻ�����һ��)#k");
		cm.dispose();

}else if (selection == 7){
	if (cm.haveItem(4001083,1) && cm.getBossLog('PlayQuest8') < 1) {
		cm.gainItem(4001083,-1);
		cm.setBossLog('PlayQuest8');
		cm.gainItem(4001226,1);
		cm.sendOk("�������,������½���:\r\n����֮��");
		cm.dispose();
	} else 
		cm.sendOk("�����Ƿ����㹻����Ʒ��\r\n#r(ע:������ÿ��ֻ�����һ��)#k");
		cm.dispose();

}else if (selection == 8){
	if (cm.haveItem(4001085,1) && cm.getBossLog('PlayQuest9') < 1) {
		cm.gainItem(4001085,-1);
		cm.setBossLog('PlayQuest9');
		cm.gainNX(1000);
		cm.sendOk("�������,������½���:\r\n1000���");
		cm.dispose();
	} else 
		cm.sendOk("�����Ƿ����㹻����Ʒ��\r\n#r(ע:������ÿ��ֻ�����һ��)#k");
		cm.dispose();

}else if (selection == 9){
	if (cm.haveItem(4001084,1) && cm.getBossLog('PlayQuest10') < 1) {
		cm.gainItem(4001084,-1);
		cm.setBossLog('PlayQuest10');
		cm.gainNX(1000);
		cm.sendOk("�������,������½���:\r\n1000���");
		cm.dispose();
	} else 
		cm.sendOk("�����Ƿ����㹻����Ʒ��\r\n#r(ע:������ÿ��ֻ�����һ��)#k");
		cm.dispose();

}else if (selection == 10){
	if (cm.haveItem(4001083,1) && cm.getBossLog('PlayQuest11') < 1) {
		cm.gainItem(4001083,-1);
		cm.setBossLog('PlayQuest11');
		cm.gainNX(2000);
		cm.sendOk("�������,������½���:\r\n2000���");
		cm.dispose();
	} else 
		cm.sendOk("�����Ƿ����㹻����Ʒ��\r\n#r(ע:������ÿ��ֻ�����һ��)#k");
		cm.dispose();



}else if (selection == 11){
	if (cm.haveItem(4001126,1000) && cm.getBossLog('PlayQuest14') < 1) {
		cm.gainItem(4001126,-1000);
		cm.setBossLog('PlayQuest14');
		cm.gainNX(1000);
		cm.sendOk("�������,������½���:\r\n1000���");
		cm.dispose();
	} else 
		cm.sendOk("�����Ƿ����㹻����Ʒ��\r\n#r(ע:������ÿ��ֻ�����һ��)#k");
		cm.dispose();


}
}
}
}
