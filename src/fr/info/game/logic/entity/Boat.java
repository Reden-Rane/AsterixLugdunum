package fr.info.game.logic.entity;

import fr.info.game.logic.AxisAlignedBoundingBox;

public class Boat extends Entity {

    public static final long HIT_FLICKER_PERIOD = 20;
    private final AxisAlignedBoundingBox boatAABB;

    private float prevPaddleRotation;
    public float paddleRotation;
    public float paddleRotationMotion;

    private long lastHitTime = -HIT_FLICKER_PERIOD;

    public Boat() {
        super(0, 0, 7.5F, 6.09375F);
        this.boatAABB = new AxisAlignedBoundingBox(0, 0, 7.5F, 1.5625F);
    }

    public void hit() {
        if(level != null) {
            lastHitTime = level.getLevelTicks();
            motionX -= 0.003125F;
        }
    }

    public long getLastHitTime() {
        return lastHitTime;
    }

    @Override
    public void update() {
        super.update();
        this.prevPaddleRotation = this.paddleRotation;
        this.paddleRotation = (this.paddleRotation - this.paddleRotationMotion) % 360;
    }

    @Override
    public AxisAlignedBoundingBox getCollisionBox() {
        return boatAABB;
    }

    public float getPrevPaddleRotation() {
        return prevPaddleRotation;
    }
}
