package fr.info.game.logic.entity;

public class Cannonball extends Entity {

    public Cannonball(float x, float y) {
        super(x, y, 1.5625F, 1.5625F);
    }

    @Override
    public void update() {
        super.update();
        //Gravity
        this.motionY -= 0.01875F;
    }
}
