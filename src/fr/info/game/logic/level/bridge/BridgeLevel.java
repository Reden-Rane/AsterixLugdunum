package fr.info.game.logic.level.bridge;

import fr.info.game.logic.entity.Boat;
import fr.info.game.logic.entity.Cannonball;
import fr.info.game.logic.entity.particle.SplashParticle;
import fr.info.game.logic.input.KeyboardCallback;
import fr.info.game.logic.level.GameLevel;
import fr.info.game.logic.math.MathUtils;
import fr.info.game.logic.tile.LilypadTile;
import fr.info.game.logic.tile.Tile;
import fr.info.game.logic.tile.WaterTile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class BridgeLevel extends GameLevel {

    private static float maxScore;

    private float score;
    private final Boat playerBoat;

    private int keyToPress;

    private long lastKeyPressedTime = -1;
    private boolean lastKeyValid;

    private final List<Cannonball> cannonballList = new ArrayList<>();

    public BridgeLevel() {
        super("Le pont");
        this.playerBoat = new Boat();
        this.spawnEntity(playerBoat);
        this.getCamera().setZoom(2);
        this.resetLevel();
    }

    @Override
    public Tile[][] generateTerrain() {
        Tile[][] tiles = new Tile[5000][6];

        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[x].length; y++) {
                if (y < 3) {

                    double rand = Math.random();
                    if (rand < 0.02) {
                        tiles[x][y] = new LilypadTile(this, (int) (Math.random() * 6), x, y);
                    } else {
                        tiles[x][y] = new WaterTile(this, x % 2 + y % 2, x, y);
                    }
                    tiles[x][y] = new WaterTile(this, x % 2 + y % 2, x, y);

                } else if (y == 3) {
                    tiles[x][y] = new Tile(this, "grass_water1", x, y);
                } else {
                    tiles[x][y] = new Tile(this, "grass" + (int) (Math.random() * 4), x, y);
                }
            }
        }

        return tiles;
    }

    private void spawnCannonball() {
        Cannonball cannonball = new Cannonball((int) (-100 - getCamera().getOffsetX()), 400);
        cannonball.setMotionX(20F);
        cannonball.setMotionY(0);
        spawnEntity(cannonball);
        cannonballList.add(cannonball);
    }

    @Override
    public void resetLevel() {
        super.resetLevel();
        this.generateRandomKey();
        this.playerBoat.setMotionX(0);
        this.playerBoat.setX(360);
        this.playerBoat.setY(40);
        this.setScore(0);
    }

    @Override
    public void update() {
        super.update();

        Iterator<Cannonball> cannonballIterator = cannonballList.iterator();
        while (cannonballIterator.hasNext()) {
            Cannonball cannonball = cannonballIterator.next();
            if (cannonball.isDead()) {
                cannonballIterator.remove();
                continue;
            }

            if (cannonball.collidesWith(this.playerBoat)) {
                //Hurt player
                cannonball.setDead();
            } else if (cannonball.getY() <= this.playerBoat.getY()) {
                cannonball.setDead();//The cannonball went into the water
                spawnEntity(new SplashParticle(cannonball.getX(), cannonball.getY(), cannonball.getWidth(), cannonball.getHeight(), 20));
            }
        }

        if (getLevelTicks() % 20 == 0) {
            spawnCannonball();
        }

        //Water resistance
        playerBoat.setMotionX(MathUtils.clamp(playerBoat.getMotionX() - 0.01F * playerBoat.getMotionX(), 0, Float.MAX_VALUE));

        setScore(getScore() + playerBoat.getMotionX());
        getCamera().setOffsetX(-playerBoat.getX() + 360);
    }

    @Override
    public void keyEvent(int key, int action) {

        if (KeyboardCallback.isKeyDown(GLFW_KEY_ESCAPE)) {
            return;//The escape key must only be used to go back to the hub
        }

        List<Integer> pressedKeys = KeyboardCallback.getPressedKeys();

        if (pressedKeys.size() >= 1) {
            if (pressedKeys.size() == 1 && KeyboardCallback.isKeyJustPressed(keyToPress)) {
                this.lastKeyValid = true;
                playerBoat.setMotionX(MathUtils.clamp(playerBoat.getMotionX() + 1F, 0, Float.MAX_VALUE));
            } else {
                this.lastKeyValid = false;
                playerBoat.setMotionX(MathUtils.clamp(playerBoat.getMotionX() - 1F, 0, Float.MAX_VALUE));
            }

            generateRandomKey();

            this.lastKeyPressedTime = System.currentTimeMillis();
        }

        super.keyEvent(key, action);
    }

    public long getLastKeyPressedTime() {
        return lastKeyPressedTime;
    }

    public boolean isLastKeyValid() {
        return lastKeyValid;
    }

    private void generateRandomKey() {
        keyToPress = 262 + (int) (Math.random() * 4);
    }

    public int getKeyToPress() {
        return keyToPress;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
