package fr.info.game.logic.entity;

public class Cannonball extends Entity {

    public Cannonball(float x, float y) {
        super(x, y, 50, 50);
    }

    @Override
    public void update() {
        super.update();
        setMotionY(getMotionY() - 0.6F);
    }
}
