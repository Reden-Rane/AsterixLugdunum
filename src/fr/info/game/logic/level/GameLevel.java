package fr.info.game.logic.level;

import fr.info.game.AsterixAndObelixGame;
import fr.info.game.ScheduledTask;
import fr.info.game.logic.input.KeyboardCallback;
import fr.info.game.logic.tile.Tile;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public abstract class GameLevel extends Level {

    private final Tile[][] tiles;

    public GameLevel(String levelName) {
        super(levelName);
        this.tiles = generateTerrain();
    }

    public abstract Tile[][] generateTerrain();

    @Override
    public void update() {

        if (KeyboardCallback.isKeyDown(GLFW_KEY_ESCAPE)) {
            AsterixAndObelixGame.INSTANCE.addScheduledTask(new ScheduledTask(0, 20) {
                @Override
                protected void updateTask() {
                    if (getTicks() == 0) {
                        closeLevel(20);
                    }
                }

                @Override
                protected void onFinish() {
                    AsterixAndObelixGame.INSTANCE.enterHub();
                }
            });
        }

        super.update();
    }

    public Tile[][] getTerrain() {
        return tiles;
    }
}
