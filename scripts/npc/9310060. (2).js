//odinms_MS
..importPackage(net.sf.odinms.tools);
importPackage(net.sf.odinms.client);

var status = 0;

function start() {
	status = -1;
	action(1, 0, 0);
}

function action(mode, type, selection) {
	if (mode == -1) {
		cm.dispose();
	} else {
		if (mode == 0 && status == 0) {
			cm.dispose();
			return;
		}
		if (mode == 1)
			status++;
		else
			status--;
		if (status == 0) {
			
			cm.sendSimple("\t\t\t\t\t#e#r独家冒险岛#n\r\n#d====================================================\r\n#d目前账户剩余点卷:"+cm.getPlayer().getCSPoints(1)+"\r\n====================================================\r\n#k你好~欢迎进入#b回归#k服务器~本服务器#r拒绝一切外挂#k，爱戴玩家，拥戴玩家。\r\n你可以通过我了解一些#b服务器信息以及新手礼包领取#k请选择你的操作！#n\r\n\r\n#L1##r#e阅读新手帮助(建议新手先看看)#b#n\r\n\r\n#L2#领取新手礼包，进入游戏 ");
			} else if (status == 1) {
			if (selection == 1) {
				cm.sendOk("\t\t\t#e#b外挂一时爽，全家火葬场#n#d\r\n====================================================#k\r\n本服务器开放现金道具交易，任何商城购买的道具均可以和其他玩家#r交易一次#k！点击玩家右键--即可使用#r现金道具交易#k功能！我们提倡玩家进行交易！汗水应该得到回报！#d\r\n====================================================#k\r\n本服务器按照官方服务器进行维护与修复。功能以仿官方为主！喜欢BT重口味的玩家请绕道！#d\r\n====================================================#k\r\n我们不出售#rBT装备，属性点，攻击力，各种装备礼包！人人平等！为了维护游戏平衡！玩家发现BUG请及时反馈给我们进行维护一个更好的游戏环境！#k#d\r\n====================================================#k\r\n本服务器开放现金道具交易，任何商城购买的道具均可以和其他玩家#r交易一次#k！点击玩家右键--即可使用#r现金道具交易#k功能！我们提倡玩家进行交易！汗水应该得到回报！#d\r\n====================================================#k\r\n本服务器按照官方服务器进行维护与修复。功能以仿官方为主！喜欢BT重口味的玩家请绕道！#d\r\n====================================================#k\r\n#e#b服务器赞助比例：1=300点卷  赞助联系客服QQ：793636201. 网站开放自助充值！也可支付宝！");
				cm.dispose();
			}else if(selection == 2) {
				if(cm.getBossLog('xslb') < 1){
							cm.sendOk("#b亲爱的#r#h ##b你好，欢迎你的到来！！我为你准备了丰富的新手礼包！！\r\n\r\n#r点卷50点、新手勋章一枚、20000抵用卷)、金枫叶5个、特殊药水50个、沙拉50个、白色药水50个、喇叭50个、金币100万");
					cm.gainItem(5530000,-1);
					cm.gainItem(2030000,+50);
					cm.gainItem(5072000,+50);//读取变量
					cm.gainItem(2020000,+50);//读取变量
					cm.gainItem(1142358,,3,3,3,3,18,18,2,2,2,2,2,2,2,2);
					cm.gainItem(2000004,+50);
					cm.gainItem(2000002,+50);
                                        cm.gainItem(4000313,+5);
                                        cm.gainDY(20000);
					cm.gainNX(+50);
                                        cm.gainItem(1002771,+1);
                                        cm.gainItem(1052154,+1);
                                        cm.gainItem(1072324,+1);
                                        cm.gainItem(1082156,+1);
                                        cm.gainMeso(99999);
					cm.serverNotice("欢迎新人！！大家祝贺吧！！！~又是一名新人加入了回归冒险岛~！");
					cm.setBossLog('xslb');
					}else{
					cm.sendOk("你已经领取过一次,或者你没有到达10级");
					cm.dispose();
				}									
			} 
		}
	}
}
