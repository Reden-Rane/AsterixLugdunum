package fr.info.game.logic.entity;

public class Player extends Entity {

    public static final int JUMP_TICKS_DURATION = 10;

    private EnumDirection direction;
    private int jumpCounter;
    private boolean isJumping;

    public Player() {
        super(0, 0, 32, 64);
    }

    @Override
    public void update() {

        if(isJumping()) {
            if(this.jumpCounter > 0) {
                this.jumpCounter--;
            } else {
                this.isJumping = false;
            }
        }

        super.update();
    }

    @Override
    public void setMotionX(float motionX) {
        super.setMotionX(motionX);

        if(getMotionX() < 0) {
            setDirection(EnumDirection.LEFT);
        } else {
            setDirection(EnumDirection.RIGHT);
        }
    }

    public void setDirection(EnumDirection direction) {
        this.direction = direction;
    }

    public EnumDirection getDirection() {
        return direction;
    }

    public void jump() {
        this.isJumping = true;
        this.jumpCounter = JUMP_TICKS_DURATION;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public int getJumpCounter() {
        return jumpCounter;
    }
}
