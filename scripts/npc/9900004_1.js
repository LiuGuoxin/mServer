/*
 *
 *  此脚本由乐章网络制作完成
 * 购买商业脚本请加群:1049548
 *
 */


importPackage(net.sf.cherry.client);

var aaa = "#fUI/UIWindow.img/Quest/icon9/0#";
var zzz = "#fUI/UIWindow.img/Quest/icon8/0#";
var sss = "#fUI/UIWindow.img/QuestIcon/3/0#";

//------------------------------------------------------------------------

var chosenMap = -1;
var monsters = 0;
var towns = 0;
var bosses = 0;
var fuben = 0;

//------------------------------------------------------------------------

var bossmaps = Array(
		Array(100000005,0,"蘑菇王"),
		Array(105070002,0,"蘑菇王之墓"), 
		Array(105090900,0,"被诅咒的寺院"), 									Array(240020402,0,"喷火龙栖息地"), 
		Array(240020101,0,"格瑞芬多森林"),
Array(551030200,0,"暴力熊狮子王"), 
	Array(910510001,0,"日本御姐"), 
       Array(541020700,0,"千年树精"), 
		Array(230040420,0,"皮亚奴斯洞穴"), 
		Array(220080000,0,"时间塔的本源"), 
		Array(211042300,0,"扎昆入口"), 						Array(702070400,0,"妖僧入口"),
		Array(240040700,0,"暗黑龙王洞穴"), 						Array(270050100,0,"时间宠儿") 								
		);

//------------------------------------------------------------------------

var monstermaps = Array(
		Array(104040000,0,"射手训练场　　　　　适合 1 ~ 15 级玩家。"), 
		Array(103000101,0,"地铁一号线<第1地区> 适合 20 ~ 30 级玩家。"), 
		Array(103000105,0,"地铁一号线<第4地区> 适合 50 ~ 70 级玩家。"), 
		Array(101030110,0,"第1军营　　　　　　 适合 40 ~ 60 级玩家。"), 
		Array(106000002,0,"危险的峡谷Ⅱ　　　　适合 40 ~ 60 级玩家。"), 
		Array(101030103,0,"遗迹发掘地Ⅲ　　　　适合 40 ~ 60 级玩家。"), 
		Array(101040001,0,"野猪的领土　　　　　适合 20 ~ 35 级玩家。"), 
		Array(101040003,0,"钢之黑怪之地　　　　适合 20 ~ 35 级玩家。"), 
		Array(101030001,0,"野猪的领土Ⅱ　　　　适合 20 ~ 35 级玩家。"), 
		Array(104010001,0,"猪的海岸 　　　　　 适合 10 ~ 20 级玩家。"), 
		Array(105070001,0,"蚂蚁广场 　　　　　 适合 20 ~ 40 级玩家。"), 
		Array(105090300,0,"龙穴　　　　　　　　适合 40 ~ 70 级玩家。"), 
		Array(105040306,0,"巨人之林 　　　　　 适合 60 ~ 80 级玩家。"), 
		Array(230020000,0,"东海叉路　　　　　　适合 30 ~ 40 级玩家。"), 
		Array(230010400,0,"西海叉路　　　　　　适合 40 ~ 50 级玩家。"), 
		Array(211041400,0,"死亡之林Ⅳ　　　　　适合 55 ~ 70 级玩家。"), 
		Array(222010000,0,"乌山入口　　　　　　适合 20 ~ 50 级玩家。"), 
		Array(220010500,0,"露台大厅　　　　　　适合 40 ~ 70 级玩家。"), 
		Array(251010000,0,"十年药草地　　　　　适合 45 ~ 60 级玩家。"), 
		Array(250020000,0,"初级修炼场　　　　　适合 50 ~ 60 级玩家。"),
		Array(800020130,0,"大佛的邂逅　　　　　适合 50 ~ 70 级玩家。"), 
		Array(200040000,0,"云彩公园Ⅲ　　　　　适合 35 ~ 60 级玩家。"),
		Array(541010010,0,"幽灵船２  　　　　　适合 60 ~ 90 级玩家。"),
		Array(200010301,0,"黑暗庭院Ⅰ　　　　　适合 70 ~ 90 级玩家。"),
		Array(600020300,0,"狼蛛洞穴Ⅰ　　　　　适合 80 ~ 120 级玩家。"), 
		Array(240020100,0,"火焰死亡战场　　　　适合 85 ~ 120 级玩家。"),
		Array(220070201,0,"消失的时间　　　　　适合 85 ~ 120 级玩家。"), 
		Array(220070301,0,"时间停止之间　　　　适合 95 ~ 120 级玩家。"),
		Array(240040000,0,"龙的峡谷　　　　    适合 95 ~ 120 级玩家。"),
		Array(551030100,0,"阴森世界入口　　　　适合 95 ~ 120 级玩家。"),  
		Array(541020000,0,"乌鲁城入口　　　　　适合 95 ~ 150 级玩家。"),
		Array(240040500,0,"龙之巢穴入口　　　　适合 100 ~ 150 级玩家。")   
		); 

//------------------------------------------------------------------------

var townmaps = Array(
		Array(910000000,0,"自由市场"), 
		//Array(701000210,0,"大擂台"), 
		Array(1000000,0,"彩虹岛新手村"), 
		Array(104000000,0,"明珠港"), 
		Array(100000000,0,"射手村"), 
		Array(101000000,0,"魔法密林"), 
		Array(102000000,0,"勇士部落"), 
		Array(103000000,0,"废弃都市"), 
		Array(120000000,0,"诺特勒斯号码头"),
		Array(105040300,0,"林中之城"),
		Array(140000000,0,"里恩"),
		Array(200000000,0,"天空之城"),
		Array(211000000,0,"冰峰雪域"), 
		Array(230000000,0,"水下世界"),  
		Array(222000000,0,"童话村"), 
		Array(220000000,0,"玩具城"),
		Array(701000000,0,"东方神州"),
		Array(250000000,0,"武陵"), 
		Array(702000000,0,"少林寺"), 
		Array(500000000,0,"泰国"),
		Array(260000000,0,"阿里安特"),  
		Array(600000000,0,"新叶城"), 
		Array(240000000,0,"神木村"),  
		Array(261000000,0,"马加提亚"), 
		Array(221000000,0,"地球防御本部"), 
		Array(251000000,0,"百草堂"),
		Array(701000200,0,"上海豫园"),
		Array(550000000,0,"马来西亚"),
		Array(130000000,0,"圣地"),
		Array(551000000,0,"甘榜村"),
		Array(740000000,0,"西门町"), 
		Array(801000000,0,"昭和村"), 
		Array(540010000,0,"新加坡机场"),
		Array(541000000,0,"新加坡码头"),
		Array(300000000,0,"艾林森林"), 
		Array(270000100,0,"时间神殿"), 
		Array(702100000,0,"藏经阁"), 
		Array(800000000,0,"古代神社"), 
		Array(130000200,0,"圣地岔路"),
		Array(741000208,0,"钓鱼场"),
		Array(925020000,0,"武陵道场入口"),
		Array(930000000,0,"毒雾森林"),
		Array(930000010,0,"森林入口"),	
		Array(702090101,0,"英语村"),  
		Array(700000000,0,"红鸾宫")
		//Array(749020000,0,"国庆蛋糕地图")
		);

//------------------------------------------------------------------------

var fubenmaps = Array(
		Array(105040316,10,"沉睡森林跳跳"),	
	        Array(103000900,10,"地铁三号线跳跳"), 
		Array(109040001,10,"冒险岛活动跳跳"),     
		Array(280020000,10,"火山跳跳"), 
	        Array(101000100,10,"忍苦跳跳") 											
		);






//------------------------------------------------------------------------

	function start() {
		status = -1;
		action(1, 0, 0);
		}
	function action(mode, type, selection) {
	if (mode == -1) {
		cm.sendOk("#b好的,下次再见.");
		cm.dispose();
		} else {
	if (status >= 0 && mode == 0) {
		cm.sendOk("#b好的,下次再见.");
		cm.dispose();
		return;
		}
	if (mode == 1) {
		status++;
		} else {
		status--;
		}

//------------------------------------------------------------------------

	if (status == 0) { 

   	    var add = "　本服为经典仿官，为了让大家找到最初的回忆只开放个别传送，希望大家能找到当初的感觉　　　　#r#k\r\n\r\n";

//		add += "#r　　　　　　　　　新物品展览#k\r\n";

//		add += "#b座椅#k\r\n";

//		add += "#v3010154# #v3010179# #v3010169# #v3010171# #v3010174# #v3010182# #v3010183# #v3010053##b\r\n\r\n";

//		add += "#b坐骑#k\r\n";

//		add += "#v1902060# #v1912053# #v1902062# #v1912055# #v1902063# #v1912056# #v1902040# #v1912057#\r\n\r\n";

		add += "#L2##r#e副本传送#l";

		add += "#L0##b#e城镇传送#l";

		add += "#L1##b练级传送#l\r\n\r\n";
		
		add += "#L3##rBOSS传送#l";

		add += "#L4##d跳跳地图#l";
 
		cm.sendSimple (add);    

//------------------------------------------------------------------------
				
	} else if (status == 1) {

	if (selection == 0){
		var selStr = "#d　　　　　　　　　选择你的目的地吧.#k#b";
		for (var i = 0; i < townmaps.length; i++) {
		selStr += "\r\n#L" + i + "#" + townmaps[i][2] + "";
		}
		cm.sendSimple(selStr);
		towns = 1;
		}

	if (selection == 1) {
		var selStr = "#d　　　　　　　　　选择你的目的地吧.#k#b";
		for (var i = 0; i < monstermaps.length; i++) {
		selStr += "\r\n#L" + i + "#" + monstermaps[i][2] + "";
		}
		cm.sendSimple(selStr);
		monsters = 1;
		}

	if (selection == 2) {
		cm.warp(701000210, 0);
cm.dispose();
		}

	if (selection == 3) {
		var selStr = "#k\r\n#d　　　　　　　　　选择你的目的地吧.#k#b";
		for (var i = 0; i < bossmaps.length; i++) {
		selStr += "\r\n#L" + i + "#" + bossmaps[i][2] + "";
		}
		cm.sendSimple(selStr);
		bosses = 1;
		}

	if (selection == 4) {
		var selStr = "#d　　　　　　　　　选择你的目的地吧.#k#b";
		for (var i = 0; i < fubenmaps.length; i++) {
		selStr += "\r\n#L" + i + "#" + fubenmaps[i][2] + "";
		}
		cm.sendSimple(selStr);
		fuben = 1;
		}


//------------------------------------------------------------------------

	} else if (status == 2) {

	if (towns == 1) {
		cm.sendYesNo("你确定要去 " + townmaps[selection][2] + "?");
		chosenMap = selection;
		towns = 2;

	} else if (monsters == 1) {
		cm.sendYesNo("你确定要去 " + monstermaps[selection][2] + "?");
		chosenMap = selection;
		monsters = 2;

	} else if (bosses == 1) {
		cm.sendYesNo("你确定要去 " + bossmaps[selection][2] + "?");
		chosenMap = selection;
		bosses = 2;

	} else if (fuben == 1) {
		cm.sendYesNo("你确定要去 " + fubenmaps[selection][2] + "?");
		chosenMap = selection;
		fuben = 2;

		}

//----------------------------------------------------------------------

	} else if (status == 3) {

	if (towns == 2) {
		if(cm.getMeso()>=townmaps[chosenMap][1]){
		cm.warp(townmaps[chosenMap][0], 0);
		cm.gainMeso(-townmaps[chosenMap][1]);
		}else{
		cm.sendOk("你没有足够的金币哦!");
		}
		cm.dispose();

	} else if (monsters == 2) {
		if(cm.getMeso()>=monstermaps[chosenMap][1]){
		cm.warp(monstermaps[chosenMap][0], 0);
		cm.gainMeso(-monstermaps[chosenMap][1]);
		}else{
		cm.sendOk("你没有足够的金币哦!");
		}
		cm.dispose();

	} else if (bosses == 2) {
		if(cm.getMeso()>=bossmaps[chosenMap][1]){
		cm.warp(bossmaps[chosenMap][0], 0);
		cm.gainMeso(-bossmaps[chosenMap][1]);
		}else{
		cm.sendOk("你没有足够的金币哦!");
		}
		cm.dispose();

	} else if (fuben == 2) {
		if(cm.getMeso()>=fubenmaps[chosenMap][1]){
		cm.warp(fubenmaps[chosenMap][0], 0);
		cm.gainMeso(-fubenmaps[chosenMap][1]);
		}else{
		cm.sendOk("你没有足够的金币哦!");
		}
		cm.dispose();

                }

//------------------------------------------------------------------------

		}
		}
		}

