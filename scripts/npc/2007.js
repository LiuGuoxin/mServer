/* 
 * NPC   : Dev Doll
 * Map   : GMMAP
 */
var ��ɫ��ͷ = "#fUI/UIWindow/Quest/icon2/7#";
var status = 0;
var invs = Array(1, 5);
var invv;
var selected;
var slot_1 = Array();
var slot_2 = Array();
var statsSel;
var totalLevel = 0;
function start() {
    action(1, 0, 0);
}

function action(mode, type, selection) {
    if (mode != 1) {
        cm.dispose();
        return;
    }
    status++;
    if (status == 1) {
        var bbb = false;
	var itemid = 0;
        var selStr = "��ѡ����Ҫ���������Ҿ������װ��#n\r\n#b";
        selStr += "#dװ�����о��Ѻ󣬻�ӷ���Ǳ�����ԣ���ÿ����Ҫ����һö#b����֮��#d  ����֮�Ŀ���ͨ��2���ߵ����ˮ���һ��ޣ�����������аٷ�֮30�ļ��ʻ�ʧ�ܵ�\r\n#e����װ���ǵ�һ���!#n\r\n"
        for (var x = 0; x < invs.length; x++) {
            var inv = cm.getInventory(invs[x]);
            for (var i = 0; i <= 1; i++) {
                if (x == 0) {
                    slot_1.push(i);
                } else {
                    slot_2.push(i);
                }
                var it = inv.getItem(i);
                if (it == null) {
                    continue;
                }
                itemid = it.getItemId();
		//totalLevel = (it.getUpgradeSlots() + it.getLevel())
                if (cm.isCash(itemid)) {
                    continue;
                }
                bbb = true;
				if(itemid == 1122000){
					cm.sendOk("�Բ��𣬸�װ���޷����о��ѣ�");
					cm.dispose();
					return;
				}
                selStr += "#L" + ((invs[x] * 1000) + i) + "#" + ��ɫ��ͷ + "��Ҫ����:#z" + itemid + "# #v" + itemid + "#���Ҿ����#l\r\n";
            }
        }
        if (!bbb) {
            cm.sendOk("��һ��û��װ������ȷ�����Ƿ��һ��û��װ������Ϊ�ֽ�װ����");
            cm.dispose();
            return;
        }
         if (itemid >= 1112000 && itemid <= 1113000) {
            cm.sendOk("�Բ��� ��ָ�޷����о���!");
            cm.dispose();
            return;
        }
        if (itemid >= 1112000 && itemid <= 1113000) {
            cm.sendOk("�Բ��� ��ָ�޷����о���!");
            cm.dispose();
            return;
        }
        if (itemid >= 1142000 && itemid <= 1142999) {
            cm.sendOk("�Բ���~ѫ��Ҳ���޷����ѵģ�");
            cm.dispose();
            return;
        }
	//if(totalLevel > 20){
	//    cm.sendOk("�Ѵﵽ���ǿ���ȼ���");
        //    cm.dispose();
        //    return;
	//}
        cm.sendSimple(selStr + "#k");
    } else if (status == 2) {
        invv = (selection / 1000) | 0;
        selected = (selection % 1000) | 0;
        var inzz = cm.getInventory(invv);
        if (selected >= inzz.getSlotLimit()) {
            cm.sendOk("����������һ��.");
            cm.dispose();
            return;
        }
        if (invv == invs[0]) {
            statsSel = inzz.getItem(slot_1[selected]);
        } else if (invv == invs[1]) {
            statsSel = inzz.getItem(slot_2[selected]);
        }
        if (statsSel == null) {
            cm.sendOk("����������һ��.");
            cm.dispose();
            return;
        }
        if ((inzz.getItem(1) == null)) {
            cm.sendOk("��ȷ����װ����");
            cm.dispose();
            return;
        }
        // str, dex,luk,Int,hp,mp,watk,matk,wdef,mdef,hb,mz,ty, yd
        Str = statsSel.getStr();//����
        Dex = statsSel.getDex();//����
        Luk = statsSel.getLuk();//����
        Int = statsSel.getInt();//����
        Hp = statsSel.getHp();//����ֵ
        Mp = statsSel.getMp();//ħ��ֵ
        Watk = statsSel.getWatk();//������
        Matk = statsSel.getMatk();//ħ������
        Wdef = statsSel.getWdef();//�������
        Mdef = statsSel.getMdef();//ħ������
        Hb = statsSel.getAvoid();//�ر�
        mz = statsSel.getAcc();//����
        ty = statsSel.getJump();//��Ծ
        yd = statsSel.getSpeed();//�ٶ�
        qh = statsSel.getUpgradeSlots();
        St = "\t\t\t ��ǿ������:" + qh + "#r(+1)#k\r\n\r\n#bÿ����Ҫ����һö#b����֮��#d#r\r\n\t\t\t\t�Ƿ�ȷ�ϸ��������о�������?(ȷ��������1)";
        cm.sendGetNumber("��һ������:#v" + statsSel.getItemId() + "##b#z" + statsSel.getItemId() + "# #r#e����������:" + statsSel.getUpgradeSlots() + "#k\r\n#k#n\r\n#k\r\n\t\t\t\t\t#d���Բ鿴\r\n" + St, 0, 1, 1);

    } else if (status == 3) {//��ʼ����
        var jl = Math.floor(Math.random() * 10 + 1);//�����
        var jl2;
        var jl3;
        var jlname;
        if (jl == 8) {
            //��������
            jl3 = 2;
            jl2 = 0.4;
            jlname = "��������";
        } else if (jl == 9) {
            //׿Խ����
            jl3 = 3;
            jl2 = 0.6;
            jlname = "׿Խ����";
        } else {
            //��������
            jl3 = 1;
            jl2 = 0.2;
            jlname = "��ͨ����";
        }
        for (var x = 0; x < invs.length; x++) {
            var inv = cm.getInventory(1);
            it1 = inv.getItem(1);
                itemid1 = it1.getItemId();
                Str = it1.getStr();//����
                Dex = it1.getDex();//����
                Luk = it1.getLuk();//����
                Int = it1.getInt();//����
                Hp = it1.getHp();//����ֵ
                Mp = it1.getMp();//ħ��ֵ
                Qh = (it1.getPotential1());
                if (it1.getWatk() != 0) {
                    Watk = it1.getWatk();//������
                } else {
                    Watk = it1.getWatk();//������
                }
                if (Matk != 0) {
                    Matk = it1.getMatk();//������
                } else {
                    Matk = it1.getMatk();//������
                }
                Wdef = it1.getWdef();//�������
                Mdef = it1.getMdef();//ħ������
                Hb = it1.getAvoid();//�ر�
                mz = it1.getAcc();//����
                ty = it1.getJump();//��Ծ
                yd = it1.getSpeed();//�ٶ�
                qh = it1.getUpgradeSlots()+1;
	        le = it1.getLevel();//��������
		fl = statsSel.getFlag();//��Ʒ״̬
            }
        
        //             //����ǿ����Ҫ����:#v4031875# "+statsSel.getPotential1()*3+",#v4000313#"+statsSel.getPotential1()*2+",ð�ձ� = "+(statsSel.getPotential1()*123456-7788)*2


        if (cm.haveItem(4001226, 1)) {
			//�۳���Ʒ
			cm.gainItem(4001226,-1);
			if(Math.round(Math.random() * 9 + 1) <= 4){
				cm.sendOk("��ѽ���ֶ���һ�¾���ʧ�ܡ�");
				//cm.����2(2,"װ������","��ϲ " + cm.getName() + " ����ʧ�� +" + it1.getLevel() + cm.getItemName(itemid1) +",�ǲ��Ǻ���" );
				cm.dispose();
			}else{
				//�ж�װ���Ƿ�һ��
                // �۳�װ��
                cm.gainItem(itemid1, -1);
                cm.sendOk("��ϲ��ɹ����о��ѡ�");
                cm.gainItem(itemid1, Str, Dex, Luk, Int, Hp, Mp, Watk, Matk, Wdef, Mdef, Hb, mz, ty, yd,qh,le,fl);
				//cm.����2(2,"װ������","��ϲ " + cm.getName() + " �ɹ����� +" + it1.getLevel() + cm.getItemName(itemid1) );
                cm.dispose();
			}

            
        } else {
            cm.sendOk("������˼ ����Ҫ����Ʒ���㰡��");
            cm.dispose();
        }
    }}