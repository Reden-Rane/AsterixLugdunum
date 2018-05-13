package fr.info.game.logic.entity;

import fr.info.game.logic.AxisAlignedBoundingBox;

public abstract class Entity {

    private final AxisAlignedBoundingBox defaultAABB;

    private long existingTicks;
    private float prevX;
    private float prevY;
    private float prevZ;
    private float x;
    private float y;
    private float z;
    private float motionX;
    private float motionY;
    private float motionZ;
    private int width;
    private int height;

    private boolean isDead;

    public Entity() {
        this(0, 0);
    }

    public Entity(float x, float y) {
        this(x, y, 1, 1);
    }

    public Entity(float x, float y, int width, int height) {
        this(x, y, 0, width, height);
    }

    public Entity(float x, float y, float z, int width, int height) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
        this.width = width;
        this.height = height;
        this.defaultAABB = new AxisAlignedBoundingBox(0, 0, width, height);
    }

    public void update() {
        this.prevX = this.getX();
        this.prevY = this.getY();
        this.prevZ = this.getZ();
        this.setX(getX() + getMotionX());
        this.setY(getY() + getMotionY());
        this.existingTicks++;
    }

    public boolean collidesWith(Entity entity) {
        boolean flag1 = entity.getX() + entity.getCollisionBox().x < this.getX() + this.getCollisionBox().x + this.getCollisionBox().width;
        boolean flag2 = entity.getX() + entity.getCollisionBox().x + entity.getCollisionBox().width > this.getX() + this.getCollisionBox().x;
        boolean flag3 = entity.getY() + entity.getCollisionBox().y < this.getY() + this.getCollisionBox().y + this.getCollisionBox().height;
        boolean flag4 = entity.getY() + entity.getCollisionBox().y + entity.getCollisionBox().height > this.getY() + this.getCollisionBox().y;
        return flag1 && flag2 && flag3 && flag4;
    }

    public AxisAlignedBoundingBox getCollisionBox() {
        return defaultAABB;
    }

    public long getExistingTicks() {
        return existingTicks;
    }

    public void moveToX(float x) {
        this.setMotionX(x - getX());
    }

    public void moveToY(float y) {
        this.setMotionY(y - getY());
    }

    public void moveTo(float x, float y) {
        moveToX(x);
        moveToY(y);
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead() {
        isDead = true;
    }

    public float getMotionX() {
        return motionX;
    }

    public void setMotionX(float motionX) {
        this.motionX = motionX;
    }

    public float getMotionY() {
        return motionY;
    }

    public void setMotionY(float motionY) {
        this.motionY = motionY;
    }

    public float getMotionZ() {
        return motionZ;
    }

    public void setMotionZ(float motionZ) {
        this.motionZ = motionZ;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
