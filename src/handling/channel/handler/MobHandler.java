package handling.channel.handler;

import java.awt.Point;
import java.util.List;

import client.MapleCharacter;
import client.MapleClient;
import client.anticheat.CheatingOffense;
import client.inventory.MapleInventoryType;
import server.MapleInventoryManipulator;
import server.Randomizer;
import server.life.MapleMonster;
import server.life.MobSkill;
import server.life.MobSkillFactory;
import server.maps.MapleMap;
import server.maps.MapleNodes;
import server.movement.AbstractLifeMovement;
import server.movement.LifeMovement;
import server.movement.LifeMovementFragment;
import tools.FileoutputUtil;
import tools.MaplePacketCreator;
import tools.Pair;
import tools.data.input.SeekableLittleEndianAccessor;
import tools.packet.MobPacket;

public class MobHandler
{
    public static final void MoveMonster(final SeekableLittleEndianAccessor slea, final MapleClient c, final MapleCharacter chr) {
        if (chr == null || chr.getMap() == null) {
            return;
        }
        final int oid = slea.readInt();
        final MapleMonster monster = chr.getMap().getMonsterByOid(oid);
        if (monster == null) {
            chr.addMoveMob(oid);
            return;
        }
        final short moveid = slea.readShort();
        final boolean useSkill = slea.readByte() > 0;
        final byte skill = slea.readByte();
        final int skill2 = slea.readByte() & 0xFF;
        final int skill3 = slea.readByte();
        final int skill4 = slea.readByte();
        final int skill5 = slea.readByte();
        int realskill = 0;
        int level = 0;
        if (useSkill) {
            final byte size = monster.getNoSkills();
            boolean used = false;
            if (size > 0) {
                final Pair<Integer, Integer> skillToUse = monster.getSkills().get((byte)Randomizer.nextInt(size));
                realskill = skillToUse.getLeft();
                level = skillToUse.getRight();
                final MobSkill mobSkill = MobSkillFactory.getMobSkill(realskill, level);
                if (mobSkill != null && !mobSkill.checkCurrentBuff(chr, monster)) {
                    final long now = System.currentTimeMillis();
                    final long ls = monster.getLastSkillUsed(realskill);
                    if (ls == 0L || now - ls > mobSkill.getCoolTime()) {
                        monster.setLastSkillUsed(realskill, now, mobSkill.getCoolTime());
                        final int reqHp = (int)(monster.getHp() / (float)monster.getMobMaxHp() * 100.0f);
                        if (reqHp <= mobSkill.getHP()) {
                            used = true;
                            mobSkill.applyEffect(chr, monster, true);
                        }
                    }
                }
            }
            if (!used) {
                realskill = 0;
                level = 0;
            }
        }
        slea.read(13);
        final Point startPos = slea.readPos();
        final List<LifeMovementFragment> res = MovementParse.parseMovement(slea, 2);
        c.getSession().write((Object)MobPacket.moveMonsterResponse(monster.getObjectId(), moveid, monster.getMp(), monster.isControllerHasAggro(), realskill, level));
        if (res != null && chr != null) {
            final MapleMap map = chr.getMap();
            MovementParse.updatePosition(res, monster, -1);
            map.moveMonster(monster, monster.getPosition());
            map.broadcastMessage(chr, MobPacket.moveMonster(useSkill, skill, skill2, skill3, skill4, skill5, monster.getObjectId(), startPos, monster.getPosition(), res), monster.getPosition());
        }
        try {
            final boolean fly = monster.getStats().getFly();
            if (!fly) {
                final MapleCharacter controller = monster.getController();
                final MapleMap map2 = chr.getMap();
                Point endPos = null;
                int reduce_x = 0;
                int reduce_y = 0;
                for (final LifeMovementFragment move : res) {
                    if (move instanceof AbstractLifeMovement) {
                        endPos = ((LifeMovement)move).getPosition();
                        try {
                            reduce_x = Math.abs(startPos.x - endPos.x);
                            reduce_y = Math.abs(startPos.y - endPos.y);
                        }
                        catch (Exception ex) {}
                    }
                }
                int GeneallyDistance_y = 150;
                int Check_x = 250;
                if (chr.getMapId() == 541020500) {
                    Check_x = 300;
                }
                if (monster.getId() == 4230100) {
                    GeneallyDistance_y = 200;
                }
                if (((reduce_x > 200 || reduce_y > GeneallyDistance_y) && reduce_y != 0) || (reduce_x > Check_x && reduce_y == 0)) {
                    chr.addMobVac(1);
                    if (c.getPlayer().getMobVac(1) % 50 == 0) {
                        c.getPlayer().getCheatTracker().registerOffense(CheatingOffense.����, "(�B��ͼ: " + chr.getMapId() + " ��������:" + chr.getMobVac(1) + ")");
                        FileoutputUtil.logToFile_chr(c.getPlayer(), "Logs/Log_����.txt", " ����: " + monster.getId() + " ����ʼ���� " + startPos.x + "," + startPos.y + " �������� �G" + endPos.x + "," + endPos.y + " ���x:" + reduce_x + "���y" + reduce_y);
                        if (chr.hasGmLevel(1)) {
                            c.getPlayer().dropMessage("��������");
                        }
                    }
                }
            }
        }
        catch (Exception ex2) {}
    }
    
    public static final void FriendlyDamage(final SeekableLittleEndianAccessor slea, final MapleCharacter chr) {
        final MapleMap map = chr.getMap();
        if (map == null) {
            return;
        }
        final MapleMonster mobfrom = map.getMonsterByOid(slea.readInt());
        slea.skip(4);
        final MapleMonster mobto = map.getMonsterByOid(slea.readInt());
        if (mobfrom != null && mobto != null && mobto.getStats().isFriendly()) {
            final int damage = mobto.getStats().getLevel() * Randomizer.nextInt(mobto.getStats().getLevel()) / 2;
            mobto.damage(chr, damage, true);
            checkShammos(chr, mobto, map);
        }
    }
    
    public static final void checkShammos(final MapleCharacter chr, final MapleMonster mobto, final MapleMap map) {
        if (!mobto.isAlive() && mobto.getId() == 9300275) {
            for (final MapleCharacter chrz : map.getCharactersThreadsafe()) {
                if (chrz.getParty() != null && chrz.getParty().getLeader().getId() == chrz.getId()) {
                    if (chrz.haveItem(2022698)) {
                        MapleInventoryManipulator.removeById(chrz.getClient(), MapleInventoryType.USE, 2022698, 1, false, true);
                        mobto.heal((int)mobto.getMobMaxHp(), mobto.getMobMaxMp(), true);
                        return;
                    }
                    break;
                }
            }
            map.broadcastMessage(MaplePacketCreator.serverNotice(6, "δ�ܱ������������."));
            final MapleMap mapp = chr.getClient().getChannelServer().getMapFactory().getMap(921120001);
            for (final MapleCharacter chrz2 : map.getCharactersThreadsafe()) {
                chrz2.changeMap(mapp, mapp.getPortal(0));
            }
        }
        else if (mobto.getId() == 9300275 && mobto.getEventInstance() != null) {
            mobto.getEventInstance().setProperty("HP", String.valueOf(mobto.getHp()));
        }
    }
    
    public static final void MonsterBomb(final int oid, final MapleCharacter chr) {
        final MapleMonster monster = chr.getMap().getMonsterByOid(oid);
        if (monster == null || !chr.isAlive() || chr.isHidden()) {
            return;
        }
        final byte selfd = monster.getStats().getSelfD();
        if (selfd != -1) {
            chr.getMap().killMonster(monster, chr, false, false, selfd);
        }
    }
    
    public static final void AutoAggro(final int monsteroid, final MapleCharacter chr) {
        if (chr == null || chr.getMap() == null || chr.isHidden()) {
            return;
        }
        final MapleMonster monster = chr.getMap().getMonsterByOid(monsteroid);
        if (monster != null && chr.getPosition().distanceSq(monster.getPosition()) < 200000.0) {
            if (monster.getController() != null) {
                if (chr.getMap().getCharacterById(monster.getController().getId()) == null) {
                    monster.switchController(chr, true);
                }
                else {
                    monster.switchController(monster.getController(), true);
                }
            }
            else {
                monster.switchController(chr, true);
            }
        }
    }
    
    public static final void HypnotizeDmg(final SeekableLittleEndianAccessor slea, final MapleCharacter chr) {
        final MapleMonster mob_from = chr.getMap().getMonsterByOid(slea.readInt());
        slea.skip(4);
        final int to = slea.readInt();
        slea.skip(1);
        final int damage = slea.readInt();
        final MapleMonster mob_to = chr.getMap().getMonsterByOid(to);
        if (mob_from != null && mob_to != null && mob_to.getStats().isFriendly()) {
            if (damage > 30000) {
                return;
            }
            mob_to.damage(chr, damage, true);
            checkShammos(chr, mob_to, chr.getMap());
        }
    }
    
    public static final void DisplayNode(final SeekableLittleEndianAccessor slea, final MapleCharacter chr) {
        final MapleMonster mob_from = chr.getMap().getMonsterByOid(slea.readInt());
        if (mob_from != null) {}
    }
    
    public static final void MobNode(final SeekableLittleEndianAccessor slea, final MapleCharacter chr) {
        final MapleMonster mob_from = chr.getMap().getMonsterByOid(slea.readInt());
        final int newNode = slea.readInt();
        final int nodeSize = chr.getMap().getNodes().size();
        if (mob_from != null && nodeSize > 0 && nodeSize >= newNode) {
            final MapleNodes.MapleNodeInfo mni = chr.getMap().getNode(newNode);
            if (mni == null) {
                return;
            }
            if (mni.attr == 2) {
                chr.getMap().talkMonster("��С�ĵػ�����.", 5120035, mob_from.getObjectId());
            }
            if (mob_from.getLastNode() >= newNode) {
                return;
            }
            mob_from.setLastNode(newNode);
            if (nodeSize == newNode) {
                int newMap = -1;
                switch (chr.getMapId() / 100) {
                    case 9211200: {
                        newMap = 921120100;
                        break;
                    }
                    case 9211201: {
                        newMap = 921120200;
                        break;
                    }
                    case 9211202: {
                        newMap = 921120300;
                        break;
                    }
                    case 9211203: {
                        newMap = 921120400;
                        break;
                    }
                    case 9211204: {
                        chr.getMap().removeMonster(mob_from);
                        break;
                    }
                }
                if (newMap > 0) {
                    chr.getMap().broadcastMessage(MaplePacketCreator.serverNotice(5, "������һ�׶�."));
                    chr.getMap().removeMonster(mob_from);
                }
            }
        }
    }
}
