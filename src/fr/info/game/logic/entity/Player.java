package fr.info.game.logic.entity;

public class Player extends Entity {

    public static final int JUMP_TICKS_DURATION = 10;

    private int jumpCounter;
    private boolean isJumping;

    public Player() {
        super(0, 0, 1, 2);
    }

    public Player(float width, float height) {
        super(0, 0, width, height);
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

    public EnumDirection getDirection() {
        if(motionX < 0) {
            return EnumDirection.LEFT;
        } else {
            return EnumDirection.RIGHT;
        }
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
