package fr.info.game.logic.level.bridge;

import fr.info.game.logic.entity.Boat;
import fr.info.game.logic.level.GameLevel;

public class BridgeLevel extends GameLevel {

    private final Boat playerBoat;

    public BridgeLevel() {
        super("Le pont");
        this.playerBoat = new Boat(20, 50, 480, 390);
        spawnEntity(playerBoat);
    }

    @Override
    public void update() {

        this.playerBoat.setMotionX(1F);

        super.update();
    }
}
