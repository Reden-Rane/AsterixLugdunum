package fr.info.game.logic.entity;

public abstract class Entity {

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

    public Entity() {
        this(0, 0);
    }

    public Entity(int x, int y) {
        this(x, y, 1, 1);
    }

    public Entity(int x, int y, int width, int height) {
        this(x, y, 0, width, height);
    }

    public Entity(int x, int y, int z, int width, int height) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
        this.width = width;
        this.height = height;
    }

    public void update() {
        this.prevX = this.getX();
        this.prevY = this.getY();
        this.prevZ = this.getZ();
        this.setX(getX() + getMotionX());
        this.setY(getY() + getMotionY());
        this.existingTicks++;
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
