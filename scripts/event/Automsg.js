importPackage(Packages.tools);
var setupTask;

function init() {
	scheduleNew();
}

function scheduleNew() {
	setupTask = em.schedule("start", 1000 * 60 * 3);
}

function cancelSchedule() {
	setupTask.cancel(true);
}

function start() {
	scheduleNew();
var Message = new Array("����ð�յ���ӭ��",
"��ܰ���ѣ�����ð�յ����̳��ǽ�ֹת��Ʒ�ģ����򱳰���Ʒ����ʧЧ",
"ÿ�������г���������ϻ��ΨһQQȺ594278543,ΨһGMQQ793636201",
"��Ϸ�ڴ���������ʲô���⣬����˽�����ǵ�Ⱥ������ֵ��������׼Ⱥ��",
"����ð�յ����ո���������Ҫ��ֱ��1Сʱ�����������˵,����ȫ��¼��",
"����ð�յ���ӭ��,120����130��������FFN�����ֱ�Ӳ�ͬ��BOSS���ϳ���",
"��ܰ���ѣ�����ð�յ����̳��ǽ�ֹת��Ʒ�ģ����򱳰���Ʒ����ʧЧ");
em.getChannelServer().broadcastPacket(MaplePacketCreator.serverNotice(0,Message[Math.floor(Math.random() * Message.length)]));
}
