package client.inventory;

import java.io.Serializable;

import constants.GameConstants;

public class Item implements IItem, Serializable
{
    private final int id;
    private short position;
    private short quantity;
    private byte flag;
    private long expiration;
    private MaplePet pet;
    private int uniqueid;
    private String owner;
    private String GameMaster_log;
    private String giftFrom;
    protected MapleRing ring;
    private byte itemLevel;
    
    public Item(final int id, final short position, final short quantity, final byte flag, final int uniqueid) {
        this.expiration = -1L;
        this.pet = null;
        this.uniqueid = -1;
        this.owner = "";
        this.GameMaster_log = null;
        this.giftFrom = "";
        this.ring = null;
        this.id = id;
        this.position = position;
        this.quantity = quantity;
        this.flag = flag;
        this.uniqueid = uniqueid;
    }
    
    public Item(final int id, final short position, final short quantity, final byte flag) {
        this.expiration = -1L;
        this.pet = null;
        this.uniqueid = -1;
        this.owner = "";
        this.GameMaster_log = null;
        this.giftFrom = "";
        this.ring = null;
        this.id = id;
        this.position = position;
        this.quantity = quantity;
        this.flag = flag;
    }
    
    public Item(final int id, final byte position, final short quantity) {
        this.expiration = -1L;
        this.pet = null;
        this.uniqueid = -1;
        this.owner = "";
        this.GameMaster_log = null;
        this.giftFrom = "";
        this.ring = null;
        this.id = id;
        this.position = position;
        this.quantity = quantity;
        this.itemLevel = 1;
    }
    
    @Override
    public IItem copy() {
        final Item ret = new Item(this.id, this.position, this.quantity, this.flag, this.uniqueid);
        ret.pet = this.pet;
        ret.owner = this.owner;
        ret.GameMaster_log = this.GameMaster_log;
        ret.expiration = this.expiration;
        ret.giftFrom = this.giftFrom;
        return ret;
    }
    
    @Override
    public final void setPosition(final short position) {
        this.position = position;
        if (this.pet != null) {
            this.pet.setInventoryPosition(position);
        }
    }
    
    @Override
    public void setQuantity(final short quantity) {
        this.quantity = quantity;
    }
    
    @Override
    public final int getItemId() {
        return this.id;
    }
    
    @Override
    public final short getPosition() {
        return this.position;
    }
    
    @Override
    public final byte getFlag() {
        return this.flag;
    }
    
    @Override
    public final boolean getLocked() {
        return this.flag == ItemFlag.LOCK.getValue();
    }
    
    @Override
    public final short getQuantity() {
        return this.quantity;
    }
    
    @Override
    public byte getType() {
        return 2;
    }
    
    @Override
    public final String getOwner() {
        return this.owner;
    }
    
    @Override
    public final void setOwner(final String owner) {
        this.owner = owner;
    }
    
    @Override
    public final void setFlag(final byte flag) {
        this.flag = flag;
    }
    
    @Override
    public final void setLocked(final byte flag) {
        if (flag == 1) {
            this.setFlag((byte)ItemFlag.LOCK.getValue());
        }
        else if (flag == 0) {
            this.setFlag((byte)(this.getFlag() - ItemFlag.LOCK.getValue()));
        }
    }
    
    @Override
    public final long getExpiration() {
        return this.expiration;
    }
    
    @Override
    public final void setExpiration(final long expire) {
        this.expiration = expire;
    }
    
    @Override
    public final String getGMLog() {
        return this.GameMaster_log;
    }
    
    @Override
    public void setGMLog(final String GameMaster_log) {
        this.GameMaster_log = GameMaster_log;
    }
    
    @Override
    public final int getUniqueId() {
        return this.uniqueid;
    }
    
    @Override
    public final void setUniqueId(final int id) {
        this.uniqueid = id;
    }
    
    @Override
    public final MaplePet getPet() {
        return this.pet;
    }
    
    public final void setPet(final MaplePet pet) {
        this.pet = pet;
    }
    
    @Override
    public void setGiftFrom(final String gf) {
        this.giftFrom = gf;
    }
    
    @Override
    public String getGiftFrom() {
        return this.giftFrom;
    }
    
    @Override
    public void setEquipLevel(final byte gf) {
        this.itemLevel = gf;
    }
    
    @Override
    public byte getEquipLevel() {
        return this.itemLevel;
    }
    
    @Override
    public int compareTo(final IItem other) {
        if (Math.abs(this.position) < Math.abs(other.getPosition())) {
            return -1;
        }
        if (Math.abs(this.position) == Math.abs(other.getPosition())) {
            return 0;
        }
        return 1;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof IItem)) {
            return false;
        }
        final IItem ite = (IItem)obj;
        return this.uniqueid == ite.getUniqueId() && this.id == ite.getItemId() && this.quantity == ite.getQuantity() && Math.abs(this.position) == Math.abs(ite.getPosition());
    }
    
    @Override
    public String toString() {
        return "Item: " + this.id + " quantity: " + this.quantity;
    }
    
    @Override
    public MapleRing getRing() {
        if (!GameConstants.isEffectRing(this.id) || this.getUniqueId() <= 0) {
            return null;
        }
        if (this.ring == null) {
            this.ring = MapleRing.loadFromDb(this.getUniqueId(), this.position < 0);
        }
        return this.ring;
    }
    
    public void setRing(final MapleRing ring) {
        this.ring = ring;
    }
}
