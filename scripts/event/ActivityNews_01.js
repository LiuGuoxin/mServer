importPackage(Packages.tools);
var setupTask;

function init() {
	scheduleNew();
}

function scheduleNew() {
	setupTask = em.schedule("start", 1000);
}

function cancelSchedule() {
	setupTask.cancel(true);
}

function start() {
	scheduleNew();
	var date = new Date();
	var hours = date.getHours(); //ʱ
	var minute = date.getMinutes(); //��
	var second = date.getSeconds(); //��
	if (minute == 0 && second == 0) {
		em.getChannelServer().����("1�����ɸ���ȫ��ᱦ��ʼ��,ȫ��ÿ�����㿪��");
	}
}
