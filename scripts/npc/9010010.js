importPackage(java.lang);
importPackage(Packages.tools);
importPackage(Packages.client);
var jobName;
 var job;
 var text11,text30,text00;
 var choose;
 var initial,virtue;
 var camp1,camp2,camp3;

 var showjob1 = ["սʿ","����ʿ","ս��"];
 var showjob2 = ["��ʦ","����ʿ"];
 var showjob3 = ["����","����ʹ"];
 var showjob4 = ["����","ҹ����"];
 var showjob5 = ["����","��Ϯ��"];
 var showname;
 var selectjob;
 var campis;

var camp = 1; // 1-3

function start() {
     status = -1;
     action(1, 0, 0);
     campis = Integer.valueOf(cm.getPlayer().getJob() / 1000);
     campis2 = cm.getPlayer().getJob() / 1000;
 }

function action(mode, type, selection) {
     if (mode == -1) {
         cm.dispose();
     } else {
         if (mode == 1)
             status++;
         else
             status--;
         if (status == 0) {
             cm.sendNext("���, ����#bתְ#k��#b����#kָ��Ա.");
         } else if (status == 1) {
             text11 = "ף����ﵽ��#b"+ cm.getLevel() +"��#k. ��ô����ѡ��� #r��һְҵ#k ��?#b\r\n";
             text30 = "ף����ﵽ��30��. ����תְΪ: #b#k\r\n";
             text00 = "ף����ﵽ��" + cm.getLevel() + "��. ���Ƿ���תְΪ #r";
             if (cm.getLevel() < 200 && (campis2 == 0.0 || campis2 == 1.0 || campis2 == 2.0)) {
                 if (cm.getLevel() < 8) {
                     cm.sendOk("�Բ���, ������Ҫ�ﵽ#b8��#k�Ҳ���Ϊ�����.");
                     cm.dispose();
                 } else {
					 if(cm.getLevel() < 10){
						 text11 += "#L" + 200 + "#" + showjob2[0] + "#l \t\t"
					 }else{
					   for(var j = 1;j <= 5; j++){
                         for(var i = 0;i < camp; i++){
                             selectjob = i * 1000 + 100 * j;
                             if (j == 1) 
                                 showname = showjob1[i];
                             if (j == 2) 
                                 showname = showjob2[i];
                             if (j == 3) 
                                 showname = showjob3[i];
                             if (j == 4) 
                                 showname = showjob4[i];    
                             if (j == 5) 
                                 showname = showjob5[i];       
                             if (showname == null)
                                 break;          
                             text11 += "#L" + selectjob + "#" + showname + "#l \t\t"
                         }
							text11 +="\r\n";
						}
					 }
                     cm.sendSimple(text11);
                     initial = 11;
                 }
             } else if (cm.getLevel() < 30){
                 cm.sendOk("�Բ���, �����ﵽ#b30��#k���ܽ���#r�ڶ���תְ#k.");
                 cm.dispose();
             } else if (cm.getPlayer().getJob() == 100) {
                 camp1 = 30.110;
                 text30 += "#L110#����#l\r\n#L120#׼��ʿ#l\r\n#L130#ǹսʿ#l";
                 cm.sendSimple(text30);
             } else if (cm.getPlayer().getJob() == 200) {
                 camp1 = 30.210;
                 text30 += "#L210#�𶾷�ʦ#l\r\n#L220#���׷�ʦ#l\r\n#L230#��ʦ#l";
                 cm.sendSimple(text30);
             } else if (cm.getPlayer().getJob() == 300) {
                 camp1 = 30.310;
                 text30 += "#L310#����#l\r\n#L320#����#l";
                 cm.sendSimple(text30);
             } else if (cm.getPlayer().getJob() == 400) {
                 camp1 = 30.410;
                 text30 += "#L410#�̿�#l\r\n#L420#����#l";
                 cm.sendSimple(text30);
             } else if (cm.getPlayer().getJob() == 500) {
                 camp1 = 30.510;
                 text30 += "#L510#ȭ��#l\r\n#L520#��ǹ��#l";
                 cm.sendSimple(text30);
 /*
 } else if (cm.getPlayer().getJob().equals(MapleJob.DAWNWARRIOR1)) { // ����ʿ�ŵ����ε�����
                 camp1 = 31.1110;
                 jobName = "����ʿ ��";
                 job = MapleJob.DAWNWARRIOR2;
                 cm.sendYesNo(text00 + jobName);
             } else if (cm.getPlayer().getJob().equals(MapleJob.BLAZEWIZARD1)) {
                 camp1 = 31.1210;
                 jobName = "����ʿ ��";
                 job = MapleJob.BLAZEWIZARD2;
                 cm.sendYesNo(text00 + jobName);
             } else if (cm.getPlayer().getJob().equals(MapleJob.WINDARCHER1)) {
                 camp1 = 31.1310;
                 jobName = "����ʹ ��";
                 job = MapleJob.WINDARCHER2;
                 cm.sendYesNo(text00 + jobName);
             } else if (cm.getPlayer().getJob().equals(MapleJob.NIGHTWALKER1)) {
                 camp1 = 31.1410;
                 jobName = "ҹ���� ��";
                 job = MapleJob.NIGHTWALKER2;
                 cm.sendYesNo(text00 + jobName);
             } else if (cm.getPlayer().getJob().equals(MapleJob.THUNDERBREAKER1)) {
                 camp1 = 31.1510;
                 jobName = "��Ϯ�� ��";
                 job = MapleJob.THUNDERBREAKER2;
                 cm.sendYesNo(text00 + jobName);

} else if (cm.getPlayer().getJob().equals(MapleJob.ARAN2)) { // ��ս������ε�����
                 camp3 = 32.2110;
                 jobName = "ս�� ��";
                 job = MapleJob.ARAN3;
                 cm.sendYesNo(text00 + jobName);
 */
             
             } else if (cm.getLevel() < 70) {
                 cm.sendOk("�Բ���, �����ﵽ#b70��#k���ܽ���#r������תְ#k.");
                 cm.dispose();
             } else if (cm.getPlayer().getJob() == 110) {
                 camp1 = 70.111;
                 jobName = "��ʿ";
                 job = 111;
                 cm.sendYesNo(text00 + jobName);
             } else if (cm.getPlayer().getJob() == 120) {
                 camp1 = 70.121;
                 jobName = "��ʿ";
                 job = 121;
                 cm.sendYesNo(text00 + jobName);
             } else if (cm.getPlayer().getJob() == 130) {
                 camp1 = 70.131;
                 jobName = "����ʿ";
                 job = 131;
                 cm.sendYesNo(text00 + jobName);
             } else if (cm.getPlayer().getJob() == 210) {
                 camp1 = 70.211;
                 jobName = "����ʦ";
                 job = 211;
                 cm.sendYesNo(text00 + jobName);
             } else if (cm.getPlayer().getJob() == 220) {
                 camp1 = 70.221;
                 jobName = "������ʦ";
                 job = 221;
                 cm.sendYesNo(text00 + jobName);
             } else if (cm.getPlayer().getJob() == 230) {
                 camp1 = 70.231;
                 jobName = "��˾";
                 job = 231;
                 cm.sendYesNo(text00 + jobName);
             } else if (cm.getPlayer().getJob() == 310) {
                 camp1 = 70.311;
                 jobName = "����";
                 job = 311;
                 cm.sendYesNo(text00 + jobName);
             } else if (cm.getPlayer().getJob() == 320) {
                 camp1 = 70.321;
                 jobName = "����";
                 job = 321;
                 cm.sendYesNo(text00 + jobName);
             } else if (cm.getPlayer().getJob() == 410) {
                 camp1 = 70.411;
                 jobName = "��Ӱ��";
                 job = 411;
                 cm.sendYesNo(text00 + jobName);
             } else if (cm.getPlayer().getJob() == 420) {
                 camp1 = 70.421;
                 jobName = "���п�";
                 job = 421;
                 cm.sendYesNo(text00 + jobName);
             } else if (cm.getPlayer().getJob() == 510) {
                 camp1 = 70.511;
                 jobName = "��ʿ";
                 job = 511;
                 cm.sendYesNo(text00 + jobName);
             } else if (cm.getPlayer().getJob() == 520) {
                 camp1 = 70.521;
                 jobName = "��";
                 job = 521;
                 cm.sendYesNo(text00 + jobName);
 /*
 } else if (cm.getPlayer().getJob().equals(MapleJob.DAWNWARRIOR2)) { // ����ʿ�ŵ����ε�����
                 camp1 = 71.1111;
                 jobName = "����ʿ ��";
                 job = MapleJob.DAWNWARRIOR3;
                 cm.sendYesNo(text00 + jobName);
             } else if (cm.getPlayer().getJob().equals(MapleJob.BLAZEWIZARD2)) {
                 camp1 = 71.1211;
                 jobName = "����ʿ ��";
                 job = MapleJob.BLAZEWIZARD3;
                     cm.sendYesNo(text00 + jobName);
             } else if (cm.getPlayer().getJob().equals(MapleJob.WINDARCHER2)) {
                 camp1 = 71.1311;
                 jobName = "����ʹ ��";
                 job = MapleJob.WINDARCHER3;
                 cm.sendYesNo(text00 + jobName);
             } else if (cm.getPlayer().getJob().equals(MapleJob.NIGHTWALKER2)) {
                 camp1 = 71.1411;
                 jobName = "ҹ���� ��";
                 job = MapleJob.NIGHTWALKER3;
                 cm.sendYesNo(text00 + jobName);
             } else if (cm.getPlayer().getJob().equals(MapleJob.THUNDERBREAKER2)) {
                 camp1 = 71.1511;
                 jobName = "��Ϯ�� ��";
                 job = MapleJob.THUNDERBREAKER3;
                 cm.sendYesNo(text00 + jobName);
   
 } else if (cm.getPlayer().getJob().equals(MapleJob.ARAN3)) { // ��ս������ε�����
                 camp3 = 72.2111;
                 jobName = "ս�� ��";
                 job = MapleJob.ARAN4;
                 cm.sendYesNo(text00 + jobName);
 */
             } else if (cm.getLevel() < 120) {
                 cm.sendOk("�Բ���, �����ﵽ#b120��#k���ܽ���#r���Ĵ�תְ#k.");
                 cm.dispose();
             } else if (cm.getPlayer().getJob() == 111) {
                 camp1 = 120.112;
                 jobName = "Ӣ��";
                 job = 112;
                 cm.sendYesNo(text00 + jobName);
             } else if (cm.getPlayer().getJob() == 121) {
                 camp1 = 120.122;
                 jobName = "ʥ��ʿ";
                 job = 122;
                 cm.sendYesNo(text00 + jobName);
             } else if (cm.getPlayer().getJob() == 131) {
                 camp1 = 120.132;
                 jobName = "����ʿ";
                 job = 132;
                 cm.sendYesNo(text00 + jobName);
             } else if (cm.getPlayer().getJob() == 211) {
                 camp1 = 120.212;
                 jobName = "��ħ��ʿ";
                 job = 212;
                 cm.sendYesNo(text00 + jobName);
             } else if (cm.getPlayer().getJob() == 221) {
                 camp1 = 120.222;
                 jobName = "����ħ��ʿ";
                 job = 222;
                 cm.sendYesNo(text00 + jobName);
             } else if (cm.getPlayer().getJob() == 231) {
                 camp1 = 120.232;
                 jobName = "����";
                 job = 232;
                 cm.sendYesNo(text00 + jobName);
             } else if (cm.getPlayer().getJob() == 311) {
                 camp1 = 120.312;
                 jobName = "����";
                 job = 312;
                 cm.sendYesNo(text00 + jobName);
             } else if (cm.getPlayer().getJob() == 321) {
                 camp1 = 120.322;
                 jobName = "����";
                 job = 322;
                 cm.sendYesNo(text00 + jobName);
             } else if (cm.getPlayer().getJob() == 411) {
                 camp1 = 120.412;
                 jobName = "��ʿ";
                 job = 412;
                 cm.sendYesNo(text00 + jobName);
             } else if (cm.getPlayer().getJob() == 421) {
                 camp1 = 120.422;
                 jobName = "����";
                 job = 422;
                 cm.sendYesNo(text00 + jobName);
             } else if (cm.getPlayer().getJob() == 511) {
                 camp1 = 120.512;
                 jobName = "���ӳ�";
                 job = 512;
                 cm.sendYesNo(text00 + jobName);
             } else if (cm.getPlayer().getJob() == 521) {
                 camp1 = 120.522;
                 jobName = "����";
                 job = 522;
                 cm.sendYesNo(text00 + jobName);
 /*
 } else if (cm.getPlayer().getJob().equals(MapleJob.ARAN4)) { // ��ս������ε�����
                 camp3 = 122.2112;
                 jobName = "ս�� ��";
                 job = MapleJob.ARAN5;
                 cm.sendYesNo(text00 + jobName);
 */
             } else if (cm.getLevel() >= 200 || (cm.getLevel() >= 120 && campis == 1)) {//cm.getJobId() == 1111
                 getCostAp();
				 cm.sendYesNo("����... ΰ���#b#h ##k�����Ѿ�ͨ��һ��������������ս�ĵ�·,���ڳ�Ϊ�˷�����ӿ����� \r\n������ܸ���#bʥ��#k #v4031454#�� �ҿ������ҵ�Ǭ����Ų���ķ����������Ͷ̥ת���� ������Ϊ1����#b����#k, ����������#b����#k��Ȼ�����#r"+ "ת����� * 100" +"#k�����Ե㡣\r\n#k�㵱ǰת������Ϊ��#r" + cm.getPlayer().getPrizeLog("ת��") + "#k�Σ����Ƿ���#rת��#k��?");

            //     cm.sendYesNo("����... ΰ���#b#h ##k�����Ѿ�ͨ��һ��������������ս�ĵ�·,���ڳ�Ϊ�˷�����ӿ����� \r\n������ܸ���#bð�յ������#k #v4001129#(�½���ɫʱ��1��)�� �ҿ������ҵ�Ǭ����Ų���ķ����������Ͷ̥ת���� ������Ϊ1����#b����#k, ����������#b����#k��Ȼ��۳�#r"+costAp+"#k�����Ե㡣\r\n#k�㵱ǰת������Ϊ��#r" + cm.getChar().getReborns() + "#k�Σ����Ƿ���#rת��#k��?");
                 initial = 1;
             //} else if (cm.getLevel() < 200) {
             //    cm.sendOk("�Բ���, ���Ѿ���������е�תְ. \r\n\r\nȻ������������������, ��#baexr#k��ӡ��ħ������������, ���Ĳ��������ܼ�,����Ҫ�����ĸ���ǿ������������е���. \r\n����#r200��#k��ʱ���������Ұ�.");
             //    cm.dispose();    
             } else {
                 cm.dispose();
             }
         } else if (status == 2) {
             choose = selection;
             setJob();
             if (initial == 11) {
                 cm.sendYesNo("�����Ϊ #r" + jobName + "#k ��?");
             } else if (camp1 > 30 && camp1 < 31)  {
                 cm.sendYesNo("�����Ϊ #r" + jobName + "#k ��?");
             } else if (camp2 > 31 && camp2 < 32) {
                 cm.changeJob(job);
                 cm.sendOk("��ȥ��. ��������ʤ����̰� :)");
                 cm.dispose();
             } else if (camp3 > 32 && camp3 < 33) {
                 cm.changeJob(job);
                 cm.sendOk("��ȥ��. Ҳ���õĽ������ܼ����� :)");
                 cm.dispose();    
             } else if (camp1 > 70 && camp1 < 71) {
                 cm.changeJob(job);
                 cm.sendOk("��ȥ��. ��������ʤ����̰� :)");
                 cm.dispose();
             } else if (camp2 > 71 && camp2 < 72)  {
                 cm.changeJob(job);
                 cm.sendOk("��ȥ��. ���Ѿ�ûʲô���Խ������ :)");
                 cm.dispose();
             } else if (camp3 > 72 && camp3 < 73)  {
                 cm.changeJob(job);
                 cm.sendOk("��ȥ��. ��������ʤ����̰� :)");
                 cm.dispose();    
             } else if (camp1 > 120 && camp1 < 121)  {
                 cm.changeJob(job);
                 setSkill();
                 cm.sendOk("��ȥ��. ���Ѿ�ûʲô���Խ������ :)");
                 cm.dispose();
             } else if (camp3 > 122 && camp3 < 123)  {
                 cm.changeJob(job);
                 cm.sendOk("��ȥ��. ���Ѿ�ûʲô���Խ������ :)");
                 setSkill();
                 cm.dispose();    
             } else if (initial == 1)  {
                 doReborn();
             }
         } else if (status == 3) {
             if (initial == 11) {
                   cm.changeJob(job);
                   cm.sendOk("��ȥ��. δ�����������ǵ� :)");
                   cm.dispose();
             } else if (camp1 > 30 && camp1 < 31)  {
                 cm.changeJob(job);
                 cm.sendOk("��ȥ��. Ҳ���õĽ������ܼ����� :)");
                 cm.dispose();
             }
         }
     }
 }


function setJob() {
     if (choose == 100) {
         jobName = "սʿ";
         job = 100;
         virtue = 11.1;
     } else if (choose == 200) {
         jobName = "��ʦ";
         job = 200;
         virtue = 11.2;
     } else if (choose == 300) {
         jobName = "����";
         job = 300;
         virtue = 11.3;
     } else if (choose == 400) {
         jobName = "����";
         job = 400;
         virtue = 11.4;
     } else if (choose == 500) {
         jobName = "����";
         job = 500;
         virtue = 11.5;
     } else if (choose == 1100) {
         jobName = "����ʿ";
         job = 1100;
         virtue = 11.1;
     } else if (choose == 1200) {
         jobName = "����ʿ";
         job = 1200;
         virtue = 11.2;
     } else if (choose == 1300) {
         jobName = "����ʹ��";
         job = 1300;
         virtue = 11.3;
     } else if (choose == 1400) {
         jobName = "ҹ����";
         job = 1400;
         virtue = 11.4;
     } else if (choose == 1500) {
         jobName = "��Ϯ��";
         job = 1500;
         virtue = 11.5;
     } else if (choose == 2100) {
         jobName = "ս��";
         job = 2100;
         virtue = 11.1;    
     } else if (choose == 110) {
         jobName = "����";
         job = 110;
     } else if (choose == 120) {
         jobName = "׼��ʿ";
         job = 120;
     } else if (choose == 130) {
         jobName = "ǹսʿ";
         job = 130;
     } else if (choose == 210) {
         jobName = "�𶾷�ʦ";
         job = 210;
     } else if (choose == 220) {
         jobName = "���׷�ʦ";
         job = 220;
     } else if (choose == 230) {
         jobName = "��ʦ";
         job = 230;
     } else if (choose == 310) {
         jobName = "����";
         job = 310;
     } else if (choose == 320) {
         jobName = "����";
         job = 320;
     } else if (choose == 410) {
         jobName = "�̿�";
         job = 410;
     } else if (choose == 420) {
         jobName = "����";
         job = 420;
     } else if (choose == 510) {
         jobName = "ȭ��";
         job = 510;
     } else if (choose == 520) {
         jobName = "��ǹ��";
         job = 520;
     }
 }

function setSkill() {
     if (cm.getPlayer().getJob() == 112) {
         cm.teachSkill(1120003,0,10);
         cm.teachSkill(1120004,0,10);
         cm.teachSkill(1120005,0,10);
         cm.teachSkill(1121001,0,10);
         cm.teachSkill(1121002,0,10);
         cm.teachSkill(1121006,0,10);
         cm.teachSkill(1121008,0,10);
         cm.teachSkill(1121010,0,10); // ��������[��Ч]
         cm.teachSkill(1121000,0,10); // ð�յ���ʿ
         cm.teachSkill(1121011,0,5);  // ��ʿ����־
     } else if (cm.getPlayer().getJob() == 122) {
         cm.teachSkill(1220005,0,10);
         cm.teachSkill(1220006,0,10);
         cm.teachSkill(1220010,0,10);
         cm.teachSkill(1221001,0,10);
         cm.teachSkill(1221002,0,10);
         cm.teachSkill(1221003,0,10);
         cm.teachSkill(1221004,0,10);
         cm.teachSkill(1221007,0,10);
         cm.teachSkill(1221009,0,10);
         cm.teachSkill(1221011,0,10);
         cm.teachSkill(1221000,0,10);
         cm.teachSkill(1221012,0,5);
     } else if (cm.getPlayer().getJob() == 132) {
         cm.teachSkill(1320005,0,10);
         cm.teachSkill(1320006,0,10);
         cm.teachSkill(1321007,0,10);
         cm.teachSkill(1320008,0,10);
         cm.teachSkill(1320009,0,10);
         cm.teachSkill(1321001,0,10);
         cm.teachSkill(1321002,0,10);
         cm.teachSkill(1321003,0,10);
         cm.teachSkill(1321000,0,10);
         cm.teachSkill(1321010,0,5);
     } else if (cm.getPlayer().getJob() == 212) {
         cm.teachSkill(2121001,0,10);
         cm.teachSkill(2121002,0,10);
         cm.teachSkill(2121003,0,10);
         cm.teachSkill(2121004,0,10);
         cm.teachSkill(2121005,0,10);
         cm.teachSkill(2121006,0,10);
         cm.teachSkill(2121007,0,10);
         cm.teachSkill(2121000,0,10);
         cm.teachSkill(2121008,0,5);
     } else if (cm.getPlayer().getJob() == 222) {
         cm.teachSkill(2221001,0,10);
         cm.teachSkill(2221002,0,10);
         cm.teachSkill(2221003,0,10);
         cm.teachSkill(2221004,0,10);
         cm.teachSkill(2221005,0,10);
         cm.teachSkill(2221006,0,10);
         cm.teachSkill(2221007,0,10);
         cm.teachSkill(2221000,0,10);
         cm.teachSkill(2221008,0,5);
     } else if (cm.getPlayer().getJob() == 232) {
         cm.teachSkill(2321000,0,10);
         cm.teachSkill(2321001,0,10);
         cm.teachSkill(2321002,0,10);
         cm.teachSkill(2321003,0,10);
         cm.teachSkill(2321004,0,10);
         cm.teachSkill(2321005,0,10);
         cm.teachSkill(2321006,0,10);
         cm.teachSkill(2321007,0,10);
         cm.teachSkill(2321008,0,10);
         cm.teachSkill(2321009,0,5);
     } else if (cm.getPlayer().getJob() == 312) {
         cm.teachSkill(3120005,0,10);
         cm.teachSkill(3121002,0,10);
         cm.teachSkill(3121003,0,10);
         cm.teachSkill(3121004,0,10);
         cm.teachSkill(3121006,0,10);
         cm.teachSkill(3121007,0,10);
         cm.teachSkill(3121008,0,10);
         cm.teachSkill(3121000,0,10);
         cm.teachSkill(3121009,0,5);
     } else if (cm.getPlayer().getJob() == 322) {
         cm.teachSkill(3220004,0,10);
         cm.teachSkill(3221001,0,10);
         cm.teachSkill(3221002,0,10);
         cm.teachSkill(3221003,0,10);
         cm.teachSkill(3221005,0,10);
         cm.teachSkill(3221006,0,10);
         cm.teachSkill(3221007,0,10);
         cm.teachSkill(3221000,0,10);
         cm.teachSkill(3221008,0,5);
     } else if (cm.getPlayer().getJob() == 412) {
         cm.teachSkill(4120002,0,10);
         cm.teachSkill(4121003,0,10);
         cm.teachSkill(4121006,0,10);
         cm.teachSkill(4121007,0,10);
         cm.teachSkill(4121008,0,10);
         cm.teachSkill(4120005,0,10); // �����ö�Һ
         cm.teachSkill(4121004,0,10); // ���߷���
         cm.teachSkill(4121000,0,10);
         cm.teachSkill(4121009,0,5);
     } else if (cm.getPlayer().getJob() == 422) {
         cm.teachSkill(4220002,0,10);
         cm.teachSkill(4220005,0,10);
         cm.teachSkill(4221001,0,10);
         cm.teachSkill(4221007,0,10);
         cm.teachSkill(4221004,0,10); // ���߷���
         cm.teachSkill(4221006,0,10); // ��Ļ��
         cm.teachSkill(4221003,0,10); // ����
         cm.teachSkill(4221000,0,10);
         cm.teachSkill(4221008,0,5);
     } else if (cm.getPlayer().getJob() == 512) {
         cm.teachSkill(5121001,0,10);
         cm.teachSkill(5121002,0,10);
         cm.teachSkill(5121003,0,10);
         cm.teachSkill(5121004,0,10);
         cm.teachSkill(5121005,0,10);
         cm.teachSkill(5121007,0,10);
         cm.teachSkill(5121009,0,10);
         cm.teachSkill(5121010,0,10);
         cm.teachSkill(5121000,0,10);
         cm.teachSkill(5121008,0,5);
     } else if (cm.getPlayer().getJob() == 522) {
         cm.teachSkill(5220001,0,10);
         cm.teachSkill(5220002,0,10);
         cm.teachSkill(5220011,0,10);
         cm.teachSkill(5221003,0,10);
         cm.teachSkill(5221004,0,10);
         cm.teachSkill(5221006,0,10);
         cm.teachSkill(5221007,0,10);
         cm.teachSkill(5221008,0,10);
         cm.teachSkill(5221009,0,10);
         cm.teachSkill(5221000,0,10);
         cm.teachSkill(5221010,0,5);
 /*
 } else if (cm.getPlayer().getJob() == 2112) { // ��ս������ε�����
         cm.teachSkill(21120001,0,10);
         cm.teachSkill(21120002,0,10);
         cm.teachSkill(21120004,0,10);
         cm.teachSkill(21120005,0,10);
         cm.teachSkill(21120006,0,10);
         cm.teachSkill(21120007,0,10);
         cm.teachSkill(21120009,0,10);
         cm.teachSkill(21120010,0,10);
         cm.teachSkill(21121000,0,10);
         cm.teachSkill(21121003,0,10);
         //cm.teachSkill(21121008,0,5);
 */
     }
 }

var newAp,newStr,newDex,newInt,newLuk;
 var costAp;
 var maxReborns = 5;

function getCostAp() {
     if (campis == 1) {
         costAp = 1000;
     } else {
         costAp = 600;
     }
 }

function doReborn() {
     var p = cm.getPlayer();
     newStr =  p.getStr();
     newDex =  p.getDex();
     newInt =  p.getInt();
     newLuk =  p.getLuk();
     var totStat = newStr + newDex + newInt + newLuk - 16;
     
     if (!cm.haveItem(4031454)) { 
         cm.sendOk("��û�д���#b����֮��#k!");
         cm.dispose();
     } else {
		 newStat();
      //   if(p.getRemainingAp() >= costAp){
       //      newAp = p.getRemainingAp() - costAp;
     //        newStat();
      //   } else {
      //       newAp = 0;
      //       costAp = costAp - p.getRemainingAp();
      //       if (totStat >= costAp) {
         //        for (var i = 0; i <= costAp; i++) {
      //               if (newStr > 4) {
        //                 newStr -=1;
       //                  costAp -=1;
        //             }
     //                if (newDex > 4) {
            //             newDex -=1;
        //                 costAp -=1;
           //          }
        //             if (newInt > 4) {
           //              newInt -=1;
            //             costAp -=1;
           //          }
            //         if (newLuk > 4) {
          //               newLuk -=1;
                         costAp -=1;
           //          }
         //        }
           //      newStat();
         //    } else {
            //     getCostAp();
         //        cm.sendOk("���Ե㲻��,�޷�ת��!��ȷ�����AP�����������ܺ�-16��ﵽ#b"+costAp+"#k.");
          //       cm.dispose();
          //   }
         //}
         
     }
 }

function newStat() {
	 cm.clearSkills();
	 var ch = cm.getChar();
	 ch.setLevel(2);
     cm.gainItem(4031454,-1);
     cm.getPlayer().setPrizeLog("ת��");
	 cm.changeJob(0);
 
     var statup = new java.util.ArrayList();
     var p = cm.getPlayer();
     cm.getPlayer().getStat().setStr(4);
     cm.getPlayer().getStat().setDex(4);
     cm.getPlayer().getStat().setInt(4);
     cm.getPlayer().getStat().setLuk(4);
     cm.getPlayer().setRemainingAp(cm.getPlayer().getPrizeLog("ת��") * 100);
     statup.add(new Pair(MapleStat.STR, Integer.valueOf(4)));
     statup.add(new Pair(MapleStat.DEX, Integer.valueOf(4)));
     statup.add(new Pair(MapleStat.INT, Integer.valueOf(4)));
     statup.add(new Pair(MapleStat.LUK, Integer.valueOf(4)));
     statup.add(new Pair(MapleStat.AVAILABLEAP, Integer.valueOf(cm.getPlayer().getRemainingAp())));
     statup.add(new Pair(MapleStat.EXP, Integer.valueOf(0)));
     statup.add(new Pair(MapleStat.LEVEL, Integer.valueOf(1)));
     statup.add(new Pair(MapleStat.JOB, Integer.valueOf(campis * 1000)));
     p.getClient().getSession().write(MaplePacketCreator.updatePlayerStats(statup,0));
     //cm.unequipEverything();

     cm.sendOk("#b�����÷ǳ���#k, Ϊ��ɹ�#eͶ̥ת��#n���˰ɣ�");
	 cm.serverNotice("[ת������]:��ϲ"+ cm.getChar().getName() +"����ɵ�" + cm.getPlayer().getPrizeLog("ת��") +"��ת��! �ֱ��һ��������!");
     cm.dispose();
 }
