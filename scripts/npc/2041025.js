/*
	Machine Apparatus - Origin of Clocktower(220080001)
*/

var status = -1;

function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode == 1) {
        status++;
    } else {
        status--;
    }
    if (status == 0) {
        cm.sendYesNo("你的要离开这里吗？");
    } else if (status == 1) {	    
	    //cm.getPlayer().getMap().removeDrops();
        cm.warp(220080000);
        if (cm.getPlayerCount(220080001) == 0) {
	    cm.getMap(220080001).removeDrops();
            cm.getMap(220080000).resetReactors();

        }
		cm.getPlayer().setbosslog(0);//BOSS重返
        cm.dispose();
    } else {
        cm.dispose();
    }
}