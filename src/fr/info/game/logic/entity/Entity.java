package fr.info.game.logic.entity;

import fr.info.game.logic.AxisAlignedBoundingBox;
import fr.info.game.logic.level.Level;

public abstract class Entity {

    private AxisAlignedBoundingBox collisionBox;

    public Level level;

    private long existingTicks;
    private float prevX;
    private float prevY;
    private float prevZ;
    public float x;
    public float y;
    public float z;
    public float motionX;
    public float motionY;
    public float width;
    public float height;

    public boolean isDead;

    public Entity() {
        this(0, 0);
    }

    public Entity(float x, float y) {
        this(x, y, 1, 1);
    }

    public Entity(float x, float y, float width, float height) {
        this(x, y, 0, width, height);
    }

    public Entity(float x, float y, float z, float width, float height) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
        this.width = width;
        this.height = height;
        this.collisionBox = new AxisAlignedBoundingBox(0, 0, width, height);
    }

    public void update() {
        this.prevX = this.x;
        this.prevY = this.y;
        this.prevZ = this.z;
        updateEntityPosition();
        this.existingTicks++;
    }

    protected void updateEntityPosition() {
        this.x += this.motionX;
        this.y += this.motionY;
    }

    public boolean collidesWith(Entity entity) {
        boolean flag1 = entity.x + entity.getCollisionBox().x < this.x + this.getCollisionBox().x + this.getCollisionBox().width;
        boolean flag2 = entity.x + entity.getCollisionBox().x + entity.getCollisionBox().width > this.x + this.getCollisionBox().x;
        boolean flag3 = entity.y + entity.getCollisionBox().y < this.y + this.getCollisionBox().y + this.getCollisionBox().height;
        boolean flag4 = entity.y + entity.getCollisionBox().y + entity.getCollisionBox().height > this.y + this.getCollisionBox().y;
        return flag1 && flag2 && flag3 && flag4;
    }

    public AxisAlignedBoundingBox getCollisionBox() {
        return collisionBox;
    }

    public void setCollisionBox(AxisAlignedBoundingBox collisionBox) {
        this.collisionBox = collisionBox;
    }

    public long getExistingTicks() {
        return existingTicks;
    }

    public void moveToX(float x) {
        this.motionX = x - this.x;
    }

    public void moveToY(float y) {
        this.motionY = y - this.y;
    }

    public void moveTo(float x, float y) {
        moveToX(x);
        moveToY(y);
    }

    public void teleportTo(float x, float y) {
        this.x = x;
        this.y = y;
        this.prevX = x;
        this.prevY = y;
    }

    public float getPrevX() {
        return prevX;
    }

    public float getPrevY() {
        return prevY;
    }

    public float getPrevZ() {
        return prevZ;
    }
}
