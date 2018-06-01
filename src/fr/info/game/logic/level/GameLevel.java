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
        updateTiles(this.tiles);
        super.update();
    }

    @Override
    public void keyEvent(int key, int action) {
        // en appuyant sur echap à partir d'un niveau, on retourne sur le hub
        if (KeyboardCallback.isKeyDown(GLFW_KEY_ESCAPE)) {
            AsterixAndObelixGame.INSTANCE.addScheduledTask(new ScheduledTask(0, 15) {
                @Override
                protected void updateTask() {
                    if (getTicks() == 0) {
                        closeLevel(15);
                    }
                }

                @Override
                protected void onFinish() {
                    AsterixAndObelixGame.INSTANCE.enterHub();
                }
            });
        } else {
            super.keyEvent(key, action);
        }
    }

    public void updateTiles(Tile[][] tiles) {
        for(int i = 0; i < tiles.length; i++) {
            for(int j = 0; j < tiles[i].length; j++) {
                if(tiles[i][j] != null) {
                    tiles[i][j].update(this, i, j);
                }
            }
        }
    }

    public Tile[][] getTerrain() {
        return tiles;
    }
}
