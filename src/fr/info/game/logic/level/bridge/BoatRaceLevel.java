package fr.info.game.logic.level.bridge;

import fr.info.game.AsterixAndObelixGame;
import fr.info.game.ScheduledTask;
import fr.info.game.audio.Sound;
import fr.info.game.logic.entity.Boat;
import fr.info.game.logic.entity.Cannonball;
import fr.info.game.logic.entity.particle.SplashParticle;
import fr.info.game.logic.input.KeyboardCallback;
import fr.info.game.logic.level.GameLevel;
import fr.info.game.logic.math.MathUtils;
import fr.info.game.logic.tile.Tile;
import fr.info.game.logic.tile.TileRegistry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class BoatRaceLevel extends GameLevel {

    private static final float WATER_RESISTANCE = 0.005F;
    private static final float INITIAL_PLAYER_X = 12;
    private static final float INITIAL_PLAYER_Y = 1.5F;
    private static final long CANNONBALL_SPAWN_PERIOD = 15000;//In milliseconds

    private static final int LEVEL_FINISH_LINE_DISTANCE = 100;

    private static boolean hasBestTime;
    private static long bestTime = Long.MAX_VALUE;

    private Boat playerBoat;

    private final List<Integer> keySequence = new ArrayList<>();
    private int initialKeySequenceSize;
    private int validKeysCounter;

    private long lastKeyPressedTime = -1;
    private boolean wasLastKeyValid;

    private final List<Cannonball> cannonballList = new ArrayList<>();
    private long lastCannonballSpawnTime;

    private boolean levelStarted;

    private long startTime;
    private long finishTime;
    private boolean levelFinished;
    private boolean newRecord;

    public BoatRaceLevel() {
        super("Le pont");
        this.getCamera().setZoom(2);
        this.resetLevel();
    }

    @Override
    public Tile[][] generateTerrain() {
        Tile[][] tiles = new Tile[LEVEL_FINISH_LINE_DISTANCE + 200][6];

        //TODO Générer une rive plus irrégulière pour le réalisme??

        for (int x = 0; x < tiles.length; x++) {

            for (int y = 0; y < tiles[x].length; y++) {
                if (y < 3) {

                    double rand = Math.random();
                    if (rand < 0.02) {
                        tiles[x][y] = TileRegistry.LILYPADS[(int) (Math.random() * 6)];
                    } else {
                        tiles[x][y] = TileRegistry.WATERS[x % 2 + y % 2];
                    }

                } else if (y == 3) {
                    tiles[x][y] = TileRegistry.WATER_GRASSES[1];
                } else {
                    tiles[x][y] = TileRegistry.GRASSES[(int) (Math.random() * 4)];
                }
            }
        }

        return tiles;
    }

    @Override
    public void resetLevel() {
        super.resetLevel();
        this.cannonballList.clear();
        this.playerBoat = new Boat();
        this.spawnEntity(playerBoat);
        this.generateRandomKeySequence(4, new int[]{81, 87, 69, 82});
        this.playerBoat.motionX = 0;
        this.playerBoat.paddleRotationMotion = 0;
        this.playerBoat.paddleRotation = 0;
        this.playerBoat.x = INITIAL_PLAYER_X;
        this.playerBoat.y = INITIAL_PLAYER_Y;
        this.levelFinished = false;
        this.levelStarted = false;
        this.newRecord = false;
    }

    @Override
    public void update() {
        super.update();
        updateCannonballs();

        if (!isLevelFinished() && hasLevelStarted() && System.currentTimeMillis() - startTime >= 3000) {
            if (System.currentTimeMillis() - lastCannonballSpawnTime >= CANNONBALL_SPAWN_PERIOD) {
                randomlyShootCannonball();
            }
        }

        moveBoat();
        updateCamera();
        handleKeySequence();

        if (getPlayerProgress() >= 1 && !isLevelFinished()) {
            this.finishTime = System.currentTimeMillis();
            this.levelFinished = true;

            Sound finishSFX = AsterixAndObelixGame.INSTANCE.getAudioManager().getSound("finish");
            sfxSource.stop();
            sfxSource.play(finishSFX);

            long time = finishTime - startTime - 3000;

            if (time < bestTime) {
                bestTime = time;
                newRecord = true;

                Sound newRecordSFX = AsterixAndObelixGame.INSTANCE.getAudioManager().getSound("new_record");
                AsterixAndObelixGame.INSTANCE.addScheduledTask(new ScheduledTask(22, 1) {
                    @Override
                    protected void updateTask() {
                        if(getTicks() == 0) {
                            sfxSource.stop();
                            sfxSource.play(newRecordSFX);
                        }
                    }
                });

                if (!hasBestTime) {
                    hasBestTime = true;
                }
            }
        }
    }

    private void updateCamera() {
        //We follow the boat
        float x = (AsterixAndObelixGame.INSTANCE.getRenderManager().getDisplayWidth() / (float) Tile.TILE_SIZE) / (2 * getCamera().getZoom()) - playerBoat.width * Tile.TILE_SIZE / (float) (Tile.TILE_SIZE * 2);
        getCamera().setOffsetX(-playerBoat.x + x);
    }

    private void handleKeySequence() {
        if (isSequenceFinished()) {
            float ratio = validKeysCounter / (float) initialKeySequenceSize;

            if (ratio > 0) {
                sfxSource.stop();
                Sound rowingSFX = AsterixAndObelixGame.INSTANCE.getAudioManager().getSound("water_rowing");
                sfxSource.play(rowingSFX);
            }

            playerBoat.motionX = playerBoat.motionX + 0.03125F * ratio;
            playerBoat.paddleRotationMotion = 0.5F * ratio;
            generateRandomKeySequence(4, new int[]{81, 87, 69, 82});
        }
    }

    private void moveBoat() {
        //Apply water resistance
        playerBoat.motionX = MathUtils.clamp(playerBoat.motionX - WATER_RESISTANCE * playerBoat.motionX, 0, Float.MAX_VALUE);
        playerBoat.paddleRotationMotion = MathUtils.clamp(playerBoat.paddleRotationMotion - WATER_RESISTANCE * 4 * playerBoat.paddleRotationMotion, 0, Float.MAX_VALUE);
    }

    private void randomlyShootCannonball() {
        double r = Math.random();

        if (r < 0.1) {
            shootCannonball();
        }
    }

    private void shootCannonball() {
        Cannonball cannonball = new Cannonball((int) (-1.6F - getCamera().getOffsetX()), 12);
        cannonball.motionX = 0.5625F + playerBoat.motionX;
        cannonball.motionY = 0;
        spawnEntity(cannonball);
        lastCannonballSpawnTime = System.currentTimeMillis();
        cannonballList.add(cannonball);
    }

    private void updateCannonballs() {
        Iterator<Cannonball> cannonballIterator = cannonballList.iterator();
        while (cannonballIterator.hasNext()) {
            Cannonball cannonball = cannonballIterator.next();
            if (cannonball.isDead) {
                cannonballIterator.remove();
                continue;
            }

            if (cannonball.collidesWith(this.playerBoat)) {
                playerBoat.hit();
                cannonball.isDead = true;
            } else if (cannonball.y <= this.playerBoat.y) {
                cannonball.isDead = true;//The cannonball went into the water
                spawnEntity(new SplashParticle(cannonball.x, cannonball.y, cannonball.width, cannonball.height, 20));
            }
        }
    }

    @Override
    public void keyEvent(int key, int action) {

        if (KeyboardCallback.isKeyDown(GLFW_KEY_ESCAPE)) {
            return;//The escape key must only be used to go back to the hub
        }

        if (KeyboardCallback.isKeyDown(GLFW_KEY_ENTER)) {
            if (!hasLevelStarted()) {
                Sound countdownSFX = AsterixAndObelixGame.INSTANCE.getAudioManager().getSound("countdown");
                sfxSource.stop();
                sfxSource.play(countdownSFX);
                this.startTime = System.currentTimeMillis();
                levelStarted = true;
                return;
            } else if (isLevelFinished()) {
                this.resetLevel();
                return;
            }
        }

        if (!isLevelFinished() && hasLevelStarted() && System.currentTimeMillis() - startTime >= 3000) {

            List<Integer> pressedKeys = KeyboardCallback.getPressedKeys();

            if (getKeyToPress() != -1) {
                if (pressedKeys.size() >= 1) {

                    int keyToPress = getKeyToPress();

                    if (pressedKeys.size() == 1 && KeyboardCallback.isKeyJustPressed(keyToPress)) {
                        this.wasLastKeyValid = true;
                        this.validKeysCounter++;
                    } else {
                        this.wasLastKeyValid = false;
                    }

                    removeFirstKeyFromSequence();
                    this.lastKeyPressedTime = System.currentTimeMillis();
                }
            }
        }

        super.keyEvent(key, action);
    }

    public long getLastKeyPressedTime() {
        return lastKeyPressedTime;
    }

    public boolean wasLastKeyValid() {
        return wasLastKeyValid;
    }

    private void generateRandomKeySequence(int sequenceSize, int[] keys) {
        keySequence.clear();

        for (int i = 0; i < sequenceSize; i++) {
            keySequence.add(keys[(int) (Math.random() * keys.length)]);
        }

        initialKeySequenceSize = sequenceSize;
        validKeysCounter = 0;
    }

    public int getInitialKeySequenceSize() {
        return initialKeySequenceSize;
    }

    private boolean isSequenceFinished() {
        return keySequence.size() == 0;
    }

    private void removeFirstKeyFromSequence() {
        if (keySequence.size() > 0) {
            keySequence.remove(0);
        }
    }

    public float getPlayerProgress() {
        return MathUtils.clamp((playerBoat.x - INITIAL_PLAYER_X) / (float) LEVEL_FINISH_LINE_DISTANCE, 0, 1);
    }

    public long getDuration() {
        return System.currentTimeMillis() - startTime - 3000;
    }

    public int getKeyToPress() {
        return keySequence.size() > 0 ? keySequence.get(0) : -1;
    }

    public List<Integer> getKeySequence() {
        return keySequence;
    }

    public long getStartTime() {
        return startTime;
    }

    public boolean isLevelFinished() {
        return levelFinished;
    }

    public boolean hasLevelStarted() {
        return levelStarted;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public boolean hasNewRecord() {
        return newRecord;
    }

    public static long getBestTime() {
        return bestTime;
    }

    public static boolean hasBestTime() {
        return hasBestTime;
    }
}
