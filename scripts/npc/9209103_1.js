var status = -1;
var minLevel = 5; // 35
var maxLevel = 200; // 65

var minPartySize = 1;
var maxPartySize = 6;

function action(mode, type, selection) {
	if (mode == 1) {
		status++;
	} else {
		if (status == 0) {
			cm.dispose();
			return;
		}
		status--;
	}

	if (status == 0) {
		if (cm.getMapId() == 910510000) {
			cm.warp(910000000);
		}
		if (cm.getHour() != 19) {
			cm.sendOk("û�дﵽ�涨���볡ʱ���޷�Ϊ�㿪�����ʹ��ţ�");
			cm.dispose();
			return;
		}

		if (cm.getParty() == null) { // No Party
			cm.sendSimple("��Ը������������һ���������������ᷢ���ϰ������⣬�㽫�޷���������������ΰ����ŶӺ���������������ԣ�������� #b��Ӷӳ�#k ����˵��.\r\n\r\n#rҪ��: " + minPartySize + " ��Ա ���м��� " + minLevel + " ~ " + maxLevel + ".#b#l");
		} else if (!cm.isLeader()) { // Not Party Leader
			cm.sendSimple("������볢�ԣ������ #b��Ӷӳ�#k ����˵��.#b#l");
		} else if (cm.getPlayer().getClient().getChannel() != 1) {
			cm.sendOk("�Բ���ֻ��1�߿���");
			cm.dispose();
		} else {
			// Check if all party members are within PQ levels
			var party = cm.getParty().getMembers();
			var mapId = cm.getMapId();
			var next = true;
			var levelValid = 0;
			var inMap = 0;
			var it = party.iterator();
			while (it.hasNext()) {
				var cPlayer = it.next();
				if ((cPlayer.getLevel() >= minLevel) && (cPlayer.getLevel() <= maxLevel)) {
					levelValid += 1;
				} else {
					next = false;
				}
				if (cPlayer.getMapid() == mapId) {
					inMap += (cPlayer.getJobId() == 900 ? 6 : 1);
				}
			}
			if (party.size() > maxPartySize || inMap < minPartySize) {
				next = false;
			}
			if (next) {
				var em = cm.getEventManager("wugongPQ");
				if (em == null) {
					cm.sendSimple("PQ������һ����������ϵGM�����ͼ.#b#l");
				} else {
					var prop = em.getProperty("state");
					if (prop.equals("0") || prop == null) {
						for (var i = 4001095; i < 4001099; i++) {
							cm.givePartyItems(i, 0, true);
						}
						for (var i = 4001100; i < 4001101; i++) {
							cm.givePartyItems(i, 0, true);
						}

						em.startInstance(cm.getParty(), cm.getMap());
						//   cm.spawnMobOnMap(9600009, 1,260,248, 912010000);
						//cm.����(3,"[" + cm.getName() + "]��������ʱ��򼸱��,��ɱ��������ȡ�ḻ������");
						cm.dispose();
						return;
					} else {
						cm.sendSimple("��һ���ѽ��� #r��򼸱��#k ��ȴ����ǵ��������.#b#");
						cm.dispose();
					}
				}
			} else {
				cm.sendSimple("�������ʧ�ܡ����������¹涨:\r\n\r\n#rҪ��: " + minPartySize + " ��Ա, ���м��� " + minLevel + " ~ " + maxLevel + ".#b#l");
				cm.dispose();
			}
		}
	}
}
