
importPackage(net.sf.cherry.server);
importPackage(java.util);
importPackage(net.sf.cherry.client);

var needap = 0; //��ҪAP������

var needsj = 5000;//����������Ҫ��������

var lglp = 500;//��������ħ��������Ҫ��Ԫ��

var mk1 = 4251200;//��Ҫ����Ʒ ����ĸ��
var mk2 = 4004001;
var mk3 = 4004002;
var mk4 = 4004003;
var item2 = 4001126;//��Ҫ����Ʒ ﮿�ʯĸ��
var item1sl = 1;//��Ҫ��Ʒ1������
var item2sl = 5;//��Ҫ��Ʒ2������
var item3sl = 5;//��Ҫ��Ʒ2������
var item4sl = 5;//��Ҫ��Ʒ2������
var item5sl = 100;//��Ҫ��Ʒ2������
var x = 5;//ǿ��������ֵ
var slot;
var item;
var qty;
var display;

function start() {
    status = -1;
    action(1, 0, -1);
}

function action(mode, type, selection) {
    if (mode == 1)
        status++;
    else if (mode == 0 && type == 0) {
        status--;
        
    } else {
        cm.sendOk("�õ�,����������Ҫ��ʲô,�һ�������Ϊ������..");
        cm.dispose();
        return;
    }
if (status == 0) {
var text = "#fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1#\r\n���,�ҵ�����#h #.�ҵ�ID:"+cm.getNpc()+"\r\n";
text += "#r���ˮ��ָ����Ұ��BOSS����.��ȥ������ѯBOSS����.#k\r\n";
//text += "#b#L1#������װ�� �������� ��#l#l\r\n#L2#������װ�� ħ�������� ��#l\r\n";
//text += "#b#L3#������װ�� ��������ֵ ��#l  \r\n#L4#������װ�� ��������ֵ ��#l\r\n";
//text += "#b#L5#������װ�� ��������ֵ ��#l \r\n"; 
//text += "#b#L6#������װ�� ��������ֵ ��#l \r\n\r\n";
text += "#b#L7#������װ�� ���������� ��#l\r\n\r\n";
//text += "#k#n��Ŀǰɱ��#r" + cm.getChar().getPvpKills() +"#k��  ��ɱ����:#r" + cm.getChar().getPvpDeaths() +"#k�� ���:#r" + cm.getNX() + "#k #fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1##fEffect/CharacterEff/1112905/0/1#";
cm.sendSimple(text);

//==========================
} else if (status == 1) {
if (selection == 1) {
if(cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1) ==null) {
cm.sendOk("��һ��û��װ��,�޷�ʹ��.");
cm.dispose();
}else if(cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).getExpiration() !=null) {
cm.sendOk("��ʱװ������ʹ�øù���.");cm.dispose();
cm.dispose();
}else if(cm.getChar().getRemainingAp() < "+needap+")  {
cm.sendOk("#b#b����ǿ����Ҫ#r "+needap+" ������ֵ#k#b,��ʣ�������ֵ����!#k");
cm.dispose();
} else{
var text = "";
text += "��Ҫ������װ��Ϊ:#v"+cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).getItemId()+"#,����������Ϊ��#r"+cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).getUpgradeSlots()+"��#k\r\n����#r����װ����������#kǿ����Ҫ����Ҫ��\r\n�����ġ"+item1sl+"��#v" + mk1 + "# + "+item2sl+"��#v" + item2 + "# + "+item3sl+"��#v" + item3 + "# + "+item4sl+"��#v" + item4 + "# + #r"+needap+"#k ����ֵ\r\n\r\n��Ч���װ��#r������������:#b"+x+"#r.\r\n\r\n#k��ע���ٸ�ǿ���豣֤����������>0,���ɽ���.\r\n";
text += "       ��#b#eװ������һ����ƷΪǿ����Ʒ#n#K.\r\n#k�������������ע������,�����ʧ��������и���.\r\n";
text += "#L0#�����￪ʼǿ��װ��������#l";
text += "\r\n\r\n";
text += "";
cm.sendSimple(text);  
}
} else if (status == 1) {
if (selection == 2) {
if(cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1) ==null) {
cm.sendOk("��һ��û��װ��,�޷�ʹ��.");cm.dispose();
cm.dispose();
}else if(cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).getExpiration() !=null) {
cm.sendOk("��ʱװ������ʹ�øù���.");cm.dispose();
cm.dispose();
} else{
var text = "";
text += "��Ҫ������װ��Ϊ:#v"+cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).getItemId()+"#,����������Ϊ��#r"+cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).getUpgradeSlots()+"��#k\r\n����#r����װ��ħ��������#kǿ����Ҫ����Ҫ��\r\n�����ġ"+item1sl+"��#v" + mk2 + "# + "+item2sl+"��#v" + item2 + "# + "+item3sl+"��#v" + item3 + "# + "+item4sl+"��#v" + item4 + "# + #r"+needap+"#k ����ֵ\r\n\r\n��Ч���װ��#rħ������������:#b"+x+"#r.\r\n\r\n#k��ע���ٸ�ǿ���豣֤����������>0,���ɽ���.\r\n";
text += "       ��#b#eװ������һ����ƷΪǿ����Ʒ#n#K.\r\n#k�������������ע������,�����ʧ��������и���.\r\n";
text += "#r#L1#�����￪ʼǿ��װ��������#l";
text += "\r\n\r\n";
text += "";
cm.sendSimple(text); }
} else if (status == 1) {
if (selection == 3) {
if(cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1) ==null) {
cm.sendOk("��һ��û��װ��,�޷�ʹ��.");cm.dispose();
cm.dispose();
}else if(cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).getExpiration() !=null) {
cm.sendOk("��ʱװ������ʹ�øù���.");cm.dispose();
cm.dispose();
}else if(cm.getChar().getRemainingAp() < "+needap+")  {
cm.sendOk("#b#b����ǿ����Ҫ#r "+needap+" ������ֵ#k#b,��ʣ�������ֵ����!#k");
cm.dispose();
} else{
var text = "";
text += "��Ҫ������װ��Ϊ:#v"+cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).getItemId()+"#,����������Ϊ��#r"+cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).getUpgradeSlots()+"��#k\r\n����#r����װ����������ֵ#kǿ����Ҫ����Ҫ��\r\n�����ġ"+item1sl+"��#v" + mk1 + "# + "+item5sl+"��#v" + item2 + "\r\n\r\n��Ч���װ��#r��������ֵ����:#b"+x+"#r.�Ӿ����:#b-1#r.\r\n\r\n#k��ע���ٸ�ǿ���豣֤����������>0,���ɽ���.\r\n";
text += "       ��#b#eװ������һ����ƷΪǿ����Ʒ#n#K.\r\n#k�������������ע������,�����ʧ��������и���.\r\n";
text += "#r#L2#�����￪ʼǿ��װ��������#l";
text += "\r\n\r\n";
text += "";
cm.sendSimple(text);  }
} else if (status == 1) {
if (selection == 4) {
if(cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1) ==null) {
cm.sendOk("��һ��û��װ��,�޷�ʹ��.");cm.dispose();
cm.dispose();
}else if(cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).getExpiration() !=null) {
cm.sendOk("��ʱװ������ʹ�øù���.");cm.dispose();
cm.dispose();
}else if(cm.getChar().getRemainingAp() < "+needap+")  {
cm.sendOk("#b#b����ǿ����Ҫ#r "+needap+" ������ֵ#k#b,��ʣ�������ֵ����!#k");
cm.dispose();
} else{
var text = "";
text += "��Ҫ������װ��Ϊ:#v"+cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).getItemId()+"#,����������Ϊ��#r"+cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).getUpgradeSlots()+"��#k\r\n����#r����װ����������ֵ#kǿ����Ҫ����Ҫ��\r\n�����ġ"+item1sl+"��#v" + mk3 + "# + "+item5sl+"��#v" + item2 + "\r\n\r\n��Ч���װ��#r��������ֵ����:#b"+x+"#r.\r\n\r\n#k��ע���ٸ�ǿ���豣֤����������>0,���ɽ���.\r\n";
text += "       ��#b#eװ������һ����ƷΪǿ����Ʒ#n#K.\r\n#k�������������ע������,�����ʧ��������и���.\r\n";
text += "#r#L3#�����￪ʼǿ��װ��������#l";
text += "\r\n\r\n";
text += "";
cm.sendSimple(text);  }
} else if (status == 1) {
if (selection == 5) {
if(cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1) ==null) {
cm.sendOk("��һ��û��װ��,�޷�ʹ��.");cm.dispose();
cm.dispose();
}else if(cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).getExpiration() !=null) {
cm.sendOk("��ʱװ������ʹ�øù���.");cm.dispose();
cm.dispose();
}else if(cm.getChar().getRemainingAp() < "+needap+")  {
cm.sendOk("#b#b����ǿ����Ҫ#r "+needap+" ������ֵ#k#b,��ʣ�������ֵ����!#k");
cm.dispose();
} else{
var text = "";
text += "��Ҫ������װ��Ϊ:#v"+cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).getItemId()+"#,����������Ϊ��#r"+cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).getUpgradeSlots()+"��#k\r\n����#r����װ����������ֵ#kǿ����Ҫ����Ҫ��\r\n�����ġ"+item1sl+"��#v" + mk2 + "# + "+item5sl+"��#v" + item2 + "\r\n\r\n��Ч���װ��#r��������ֵ����:#b"+x+"#r.\r\n\r\n#k��ע���ٸ�ǿ���豣֤����������>0,���ɽ���.\r\n";
text += "       ��#b#eװ������һ����ƷΪǿ����Ʒ#n#K.\r\n#k�������������ע������,�����ʧ��������и���.\r\n";
text += "#r#L4#�����￪ʼǿ��װ��������#l";
text += "\r\n\r\n";
text += "";
cm.sendSimple(text);  }
} else if (status == 1) {
if (selection == 6) {
if(cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1) ==null) {
cm.sendOk("��һ��û��װ��,�޷�ʹ��.");
cm.dispose();
}else if(cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).getExpiration() !=null) {
cm.sendOk("��ʱװ������ʹ�øù���.");cm.dispose();
cm.dispose();
}else if(cm.getChar().getRemainingAp() < "+needap+")  {
cm.sendOk("#b#b����ǿ����Ҫ#r "+needap+" ������ֵ#k#b,��ʣ�������ֵ����!#k");
cm.dispose();
} else{
var text = "";
text += "��Ҫ������װ��Ϊ:#v"+cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).getItemId()+"#,����������Ϊ��#r"+cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).getUpgradeSlots()+"��#k\r\n����#r����װ����������ֵ#kǿ����Ҫ����Ҫ��\r\n�����ġ"+item1sl+"��#v" + mk4 + "# + "+item5sl+"��#v" + item2 + "\r\n\r\n��Ч���װ��#r��������ֵ����:#b"+x+"#r.\r\n\r\n#k��ע���ٸ�ǿ���豣֤����������>0,���ɽ���.\r\n";
text += "       ��#b#eװ������һ����ƷΪǿ����Ʒ#n#K.\r\n#k�������������ע������,�����ʧ��������и���.\r\n";
text += "#r#L5#�����￪ʼǿ��װ��������#l";
text += "\r\n\r\n";
text += "";
cm.sendSimple(text);  }
} else if (status == 1) {
if (selection == 7) {
if(cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1) ==null) {
cm.sendOk("��һ��û��װ��,�޷�ʹ��.");
}else if(cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).getExpiration() !=null) {
cm.sendOk("��ʱװ������ʹ�øù���.");cm.dispose();
cm.dispose();
}else if(cm.getChar().getRemainingAp() < "+needap+")  {
cm.sendOk("#b#b����ǿ����Ҫ#r "+needap+" ������ֵ#k#b,��ʣ�������ֵ����!#k");
cm.dispose();
} else{
var text = "";
text += "��Ҫ������װ��Ϊ:#v"+cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).getItemId()+"#\r\n����#r����װ������������#kǿ����Ҫ����Ҫ��\r\n�����ġ"+item1sl+"��#v" + mk1 + "\r\n\r\n��Ч���װ��#r��������������:#b1#r..\r\n\r\n#k��ע��";
text += "��#b#eװ������һ����ƷΪǿ����Ʒ#n#K.\r\n#k�������������ע������,�����ʧ��������и���.\r\n";
text += "#r#L6#�����￪ʼǿ��װ��������#l";
text += "\r\n\r\n";
text += "";
cm.sendSimple(text);  }






} else if (status == 1) {
if (selection == 11) {
if(cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1) ==null) {
cm.sendOk("��һ��û��װ��,�޷�ʹ��.");
}else if(cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).getExpiration() !=null) {
cm.sendOk("��ʱװ������ʹ�øù���.");cm.dispose();
cm.dispose();
}else if(cm.getChar().getRemainingAp() < "+needap+")  {
cm.sendOk("#b#b����ǿ����Ҫ#r "+needap+" ������ֵ#k#b,��ʣ�������ֵ����!#k");
cm.dispose();
} else{
var text = "";
text += "��Ҫ������װ��Ϊ:#v"+cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).getItemId()+"#,����������Ϊ��#r"+cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).getUpgradeSlots()+"��#k\r\n����#r����װ������������#kǿ����Ҫ����Ҫ��\r\n�����ġ"+lglp+" �� Ԫ��\r\n\r\n��Ч�����ָ#rħ������������������:#b5#r..\r\n\r\n#k��ע��";
text += "��#b#eװ������һ����ƷΪǿ����Ʒ#n#K.\r\n#k�������������ע������,�����ʧ��������и���.\r\n";
text += "#r#L7#�����￪ʼǿ��װ��������#l";
text += "\r\n\r\n";
text += "";
cm.sendSimple(text);  }






} else if (status == 1) {
if (selection == 8) {
cm.openNpc( 1063004);
} else if (status == 1) {
if (selection == 10) {
cm.openNpc( 2131006);
} else if (status == 1) {
if (selection == 9) {
var a ="#r��ע��:�˹�����������Ϊ�����������,������������ǰ����Ҫ�Ķ��������ڲֿ���:\r\n#k���ǰ���Ҫ��������ֿ���.���ҵ�.��������~\r\n#b";
a+= "\r\n#L7#�I(^��^)�Jװ����"; 
a+= "\r\n#L8#�I(^��^)�J������"; 
a+= "\r\n#L9#�I(^��^)�J������"; 
a+= "\r\n#L10#�I(^��^)�J������"; 
a+= "\r\n#L11#�I(^��^)�J������"; 
} 
cm.sendSimple(a);
} else if (status == 1) {

//����֮ǰ����
}}}}
}
}
}
}
}
}
//����status == 1
} else if (status == 2) {
if (selection == 0) {
var item = cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy();
if (cm.getChar().getRemainingAp() < needap) {
cm.sendOk("#b����װ������������Ҫ#r"+needap+"#b������ֵ,��ʣ�������ֵ����!");
cm.dispose();
}
else if (!cm.haveItem(mk1,item1sl)) {
cm.sendOk("��ı�����û��"+item1sl+"��#v"+  mk1 +"#");
cm.dispose();
}
else if (!cm.haveItem(item2,item2sl)) {
cm.sendOk("��ı�����û��"+item2sl+"��#v"+  item2 +"#");
cm.dispose();
}
else if (!cm.haveItem(item3,item3sl)) {
cm.sendOk("��ı�����û��"+item3sl+"��#v"+  item3 +"#");
cm.dispose();
}
else if (!cm.haveItem(item4,item4sl)) {
cm.sendOk("��ı�����û��"+item4sl+"��#v"+  item4 +"#");
cm.dispose();

}
else if (cm.haveItem(mk1,item1sl) && cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy().getUpgradeSlots() >=1) {

var statup = new java.util.ArrayList();
cm.getChar().setRemainingAp (cm.getChar().getRemainingAp() - needap);
                    statup.add (new net.sf.cherry.tools.Pair(net.sf.cherry.client.MapleStat.AVAILABLEAP, java.lang.Integer.valueOf(cm.getChar().getRemainingAp())));
                    cm.getChar().getClient().getSession().write(net.sf.cherry.tools.MaplePacketCreator.updatePlayerStats(statup));
cm.gainItem(mk1,-item1sl);
cm.gainItem(item2,-item2sl);
cm.gainItem(item3,-item3sl);
cm.gainItem(item4,-item4sl);
var item = cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy();
item.setWatk(item.getWatk() + 5);
MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1,1, true);
MapleInventoryManipulator.addFromDrop(cm.getChar().getClient(), item, "Edit by Kevin");
cm.sendOk("��ϲ��,�����������ɹ�.\r\n��������"+x+"!");
cm.dispose();
}
else{
cm.sendOk("ǿ��Ҫ����.");
cm.dispose();
}


} else if (status == 2) {
if (selection == 1) {
var item = cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy();
if (cm.getChar().getRemainingAp() < needap) {
cm.sendOk("#b����װ��ħ����������Ҫ#r"+needap+"#b������ֵ,��ʣ�������ֵ����!");
cm.dispose();
}
else if (!cm.haveItem(mk2,item1sl)) {
cm.sendOk("��ı�����û��"+item1sl+"��#v"+  mk2 +"#");
cm.dispose();
}
else if (!cm.haveItem(item2,item2sl)) {
cm.sendOk("��ı�����û��"+item2sl+"��#v"+  item2 +"#");
cm.dispose();
}
else if (!cm.haveItem(item3,item3sl)) {
cm.sendOk("��ı�����û��"+item3sl+"��#v"+  item3 +"#");
cm.dispose();
}
else if (!cm.haveItem(item4,item4sl)) {
cm.sendOk("��ı�����û��"+item4sl+"��#v"+  item4 +"#");
cm.dispose();

}else if (cm.haveItem(mk2,item1sl) && cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy().getUpgradeSlots() >=1) {

var statup = new java.util.ArrayList();
cm.getChar().setRemainingAp (cm.getChar().getRemainingAp() - needap);
                    statup.add (new net.sf.cherry.tools.Pair(net.sf.cherry.client.MapleStat.AVAILABLEAP, java.lang.Integer.valueOf(cm.getChar().getRemainingAp())));
                    
                   
                    cm.getChar().getClient().getSession().write(net.sf.cherry.tools.MaplePacketCreator.updatePlayerStats(statup));
cm.gainItem(mk2,-item1sl);
cm.gainItem(item2,-item2sl);
cm.gainItem(item3,-item3sl);
cm.gainItem(item4,-item4sl);
var item = cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy();
item.setMatk(item.getMatk() + x);
MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1,1, true);
MapleInventoryManipulator.addFromDrop(cm.getChar().getClient(), item, "Edit by Kevin");
cm.sendOk("��ϲ��,����ħ�������ɹ�.\r\nħ��������"+x+"!");
cm.dispose();
}
else{
cm.sendOk("ǿ��Ҫ����.");
cm.dispose();
}

} else if (status == 2) {
if (selection == 2) {
var item = cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy();
if (cm.getChar().getRemainingAp() < needap) {
cm.sendOk("#b����װ����������ֵ��Ҫ#r"+needap+"#b������ֵ,��ʣ�������ֵ����!");
cm.dispose();
}
else if (!cm.haveItem(mk1,item1sl)) {
cm.sendOk("��ı�����û��"+item1sl+"��#v"+  mk1 +"#");
cm.dispose();
}
else if (!cm.haveItem(item2,item5sl)) {
cm.sendOk("��ı�����û��"+item5sl+"��#v"+  item2 +"#");
cm.dispose();

}else if (cm.haveItem(mk1,item1sl) && cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy().getUpgradeSlots() >=1) {

var statup = new java.util.ArrayList();
cm.getChar().setRemainingAp (cm.getChar().getRemainingAp() - needap);
                    statup.add (new net.sf.cherry.tools.Pair(net.sf.cherry.client.MapleStat.AVAILABLEAP, java.lang.Integer.valueOf(cm.getChar().getRemainingAp())));
                    
                   
                    cm.getChar().getClient().getSession().write(net.sf.cherry.tools.MaplePacketCreator.updatePlayerStats(statup));
cm.gainItem(mk1,-item1sl);
cm.gainItem(item2,-item5sl);
var item = cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy();
item.setUpgradeSlots(item.getUpgradeSlots()-1);
item.setStr(item.getStr()+5);
MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1,1, true);
MapleInventoryManipulator.addFromDrop(cm.getChar().getClient(), item, "Edit by Kevin");
cm.sendOk("��ϲ��,���������ɹ�.r\n�Ҿ����-1.\r\n������"+x+"!");
cm.dispose();
}
else{
cm.sendOk("ǿ��Ҫ����.");
cm.dispose();
}

} else if (status == 2) {
if (selection == 3) {
var item = cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy();
if (cm.getChar().getRemainingAp() < needap) {
cm.sendOk("#b����װ����������ֵ��Ҫ#r"+needap+"#b������ֵ,��ʣ�������ֵ����!");
cm.dispose();
}
else if (!cm.haveItem(mk3,item1sl)) {
cm.sendOk("��ı�����û��"+item1sl+"��#v"+  mk3 +"#");
cm.dispose();
}
else if (!cm.haveItem(item2,item5sl)) {
cm.sendOk("��ı�����û��"+item5sl+"��#v"+  item2 +"#");
cm.dispose();

}else if (cm.haveItem(mk3,item1sl) && cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy().getUpgradeSlots() >=1) {

var statup = new java.util.ArrayList();
cm.getChar().setRemainingAp (cm.getChar().getRemainingAp() - needap);
                    statup.add (new net.sf.cherry.tools.Pair(net.sf.cherry.client.MapleStat.AVAILABLEAP, java.lang.Integer.valueOf(cm.getChar().getRemainingAp())));
                    
                   
                    cm.getChar().getClient().getSession().write(net.sf.cherry.tools.MaplePacketCreator.updatePlayerStats(statup));
cm.gainItem(mk3,-item1sl);
cm.gainItem(item2,-item5sl);
var item = cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy();
item.setUpgradeSlots(item.getUpgradeSlots()-1);
item.setDex(item.getDex() + 5);
MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1,1, true);
MapleInventoryManipulator.addFromDrop(cm.getChar().getClient(), item, "Edit by Kevin");
cm.sendOk("��ϲ��,������������ֵ�ɹ�.r\n�Ҿ����-1.\r\n��������ֵ��"+x+"!");
cm.dispose();
}
else{
cm.sendOk("ǿ��Ҫ����.");
cm.dispose();
}
} else if (status == 2) {
if (selection == 4) {
var item = cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy();
if (cm.getChar().getRemainingAp() < needap) {
cm.sendOk("#b����װ����������ֵ��Ҫ#r"+needap+"#b������ֵ,��ʣ�������ֵ����!");
cm.dispose();
}
else if (!cm.haveItem(mk2,item1sl)) {
cm.sendOk("��ı�����û��"+item1sl+"��#v"+  mk2 +"#");
cm.dispose();
}
else if (!cm.haveItem(item2,item5sl)) {
cm.sendOk("��ı�����û��"+item5sl+"��#v"+  item2 +"#");
cm.dispose();

}else if (cm.haveItem(mk2,item1sl) && cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy().getUpgradeSlots() >=1) {

var statup = new java.util.ArrayList();
cm.getChar().setRemainingAp (cm.getChar().getRemainingAp() - needap);
                    statup.add (new net.sf.cherry.tools.Pair(net.sf.cherry.client.MapleStat.AVAILABLEAP, java.lang.Integer.valueOf(cm.getChar().getRemainingAp())));
                    
                   
                    cm.getChar().getClient().getSession().write(net.sf.cherry.tools.MaplePacketCreator.updatePlayerStats(statup));
cm.gainItem(mk2,-item1sl);
cm.gainItem(item2,-item5sl);
var item = cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy();
item.setUpgradeSlots(item.getUpgradeSlots()-1);
item.setInt(item.getInt() + 5);
MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1,1, true);
MapleInventoryManipulator.addFromDrop(cm.getChar().getClient(), item, "Edit by Kevin");
cm.sendOk("��ϲ��,������������ֵ�ɹ�.r\n�Ҿ����-1.\r\n��������ֵ��"+x+"!");
cm.dispose();
}
else{
cm.sendOk("ǿ��Ҫ����.");
cm.dispose();
}
} else if (status == 2) {
if (selection == 5) {
var item = cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy();
if (cm.getChar().getRemainingAp() < needap) {
cm.sendOk("#b����װ����������ֵ��Ҫ#r"+needap+"#b������ֵ,��ʣ�������ֵ����!");
cm.dispose();
}
else if (!cm.haveItem(mk4,item1sl)) {
cm.sendOk("��ı�����û��"+item1sl+"��#v"+  mk4 +"#");
cm.dispose();
}
else if (!cm.haveItem(item2,item5sl)) {
cm.sendOk("��ı�����û��"+item5sl+"��#v"+  item2 +"#");
cm.dispose();

}else if (cm.haveItem(mk4,item1sl) && cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy().getUpgradeSlots() >=1) {

var statup = new java.util.ArrayList();
cm.getChar().setRemainingAp (cm.getChar().getRemainingAp() - needap);
                    statup.add (new net.sf.cherry.tools.Pair(net.sf.cherry.client.MapleStat.AVAILABLEAP, java.lang.Integer.valueOf(cm.getChar().getRemainingAp())));
                    
                   
                    cm.getChar().getClient().getSession().write(net.sf.cherry.tools.MaplePacketCreator.updatePlayerStats(statup));
cm.gainItem(mk4,-item1sl);
cm.gainItem(item2,-item5sl);
var item = cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy();
item.setUpgradeSlots(item.getUpgradeSlots()-1);
item.setLuk(item.getLuk() + 5);
MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1,1, true);
MapleInventoryManipulator.addFromDrop(cm.getChar().getClient(), item, "Edit by Kevin");
cm.sendOk("��ϲ��,������������ֵ�ɹ�.r\n�Ҿ����-1.\r\n��������ֵ��"+x+"!");
cm.dispose();
}
else{
cm.sendOk("ǿ��Ҫ����.");
cm.dispose();
}

} else if (status == 2) {
if (selection == 6) {
if (cm.getChar().getRemainingAp() < needap) {
cm.sendOk("#b����װ����������ֵ��Ҫ#r"+needap+"#b������ֵ,��ʣ�������ֵ����!");
cm.dispose();
}
else if (!cm.haveItem(mk1,item1sl)) {
cm.sendOk("��ı�����û��"+item1sl+"��#v"+  mk1 +"#���Ե�����̳ǹ���.Ҳ���Ի�ɱҰ��BOSS����");
cm.dispose();
}else {
var item = cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy();
var statup = new java.util.ArrayList();
cm.gainItem(mk1,-item1sl);
cm.getChar().setRemainingAp (cm.getChar().getRemainingAp() - needap);
                statup.add (new net.sf.cherry.tools.Pair(net.sf.cherry.client.MapleStat.AVAILABLEAP, java.lang.Integer.valueOf(cm.getChar().getRemainingAp())));
                cm.getChar().getClient().getSession().write(net.sf.cherry.tools.MaplePacketCreator.updatePlayerStats(statup));
item.setUpgradeSlots((item.getUpgradeSlots() + 1));
MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1,1, true);
MapleInventoryManipulator.addFromDrop(cm.getChar().getClient(), item, "Edit by Kevin");
cm.sendOk("��ϲ��,���ӿ����������ɹ�.\r\n����������+1!");
cm.dispose();
}



} else if (status == 2) {
if (selection == 7) {
if (cm.getzb() < lglp ) {
cm.sendOk("#b����װ���ħ��Ҫ#r"+lglp+"#b��Ԫ��,��ʣ���Ԫ������!");
cm.dispose();
}
else if (!cm.haveItem(4001430)) {
cm.sendOk("��ı�����û�к���֮��#v4001430#");
cm.dispose();
}
else if (cm.getChar().getRemainingAp() < needap) {
cm.sendOk("#b����װ����������ֵ��Ҫ#r"+needap+"#b������ֵ,��ʣ�������ֵ����!");
cm.dispose();
}else {
var item = cm.getChar().getInventory(MapleInventoryType.EQUIP).getItem(1).copy();
var statup = new java.util.ArrayList();
cm.setzb(-lglp);
cm.gainItem(4001430,-1);
cm.getChar().setRemainingAp (cm.getChar().getRemainingAp() - needap);
                statup.add (new net.sf.cherry.tools.Pair(net.sf.cherry.client.MapleStat.AVAILABLEAP, java.lang.Integer.valueOf(cm.getChar().getRemainingAp())));
                cm.getChar().getClient().getSession().write(net.sf.cherry.tools.MaplePacketCreator.updatePlayerStats(statup));
item.setWatk(item.getWatk() + 5);
item.setMatk(item.getMatk() + 5);
MapleInventoryManipulator.removeFromSlot(cm.getC(), MapleInventoryType.EQUIP, 1,1, true);
MapleInventoryManipulator.addFromDrop(cm.getChar().getClient(), item, "Edit by Kevin");
cm.sendOk("��ϲ��,�����ħ�����ɹ�.\r\n����+5!");
cm.dispose();
}

} else if (status == 2) {
if (selection == 7) {
cm.deleteItem(1);
cm.sendOk("��ϲ,�Ѿ�Ϊ���������!");  
cm.dispose();
} else if (status == 2) {
if (selection == 8) {
cm.deleteItem(2);
cm.sendOk("��ϲ,�Ѿ�Ϊ���������!");  
cm.dispose();
} else if (status == 2) {
if (selection == 9) {
cm.deleteItem(3);
cm.sendOk("��ϲ,�Ѿ�Ϊ���������!");  
cm.dispose();
} else if (status == 2) {
if (selection == 10) {
cm.deleteItem(4);
cm.sendOk("��ϲ,�Ѿ�Ϊ���������!");  
cm.dispose();
} else if (status == 2) {
if (selection == 11) {
cm.deleteItem(5);
cm.sendOk("��ϲ,�Ѿ�Ϊ���������!");  
cm.dispose();
}}}}}}}}}}}}}

//����status == 2
}
}