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
                
   cm.sendOk("感谢使用.");
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
	text += "#d合成-- #r★中级冒险家勋章★需要以下物品：\r\n#v4000017##d#z4000017# * 50个\r\n#v1142101##d#z1142101# * 1个\r\n#v4000040##d#z4000040# * 10个\r\n#v4000176##d#z4000176# * 10个\r\n~\r\n"
	text += "\r\n#L1##d我收集了以上物品。确定中级冒险家勋章";//七天
	text += "     \r\n"
        cm.sendSimple(text);
        } else if (selection == 1) {
                      if(!cm.haveItem(1142101,1)){
		cm.sendOk("请清理你的背包，至少空出2个位置！");
            cm.dispose();
        } else if(cm.haveItem(4000017,50) && cm.haveItem(1142101,1) && cm.haveItem(4000040,10) && cm.haveItem(4000176,10)){
				cm.gainItem(4000017, -50);
				cm.gainItem(1142101, -1);
				cm.gainItem(4000040, -10);
				cm.gainItem(4000176, -10);
cm.gainItem(1142108,8,8,8,8,50,50,7,7,10,10,10,0,0,0);
            cm.sendOk("换购成功！");
            cm.dispose();
cm.喇叭(3, "玩家：[" + cm.getPlayer().getName() + "]成功制作中级冒险家勋章，恭喜！！");
			}else{
            cm.sendOk("无法制作，或许你#v4000017#不足50个\r\n#v1142101#不足1个\r\n#v4000040#不足10个\r\n#v4000176#不足10个\r\n");
            cm.dispose();
			}
		}
    }
}




