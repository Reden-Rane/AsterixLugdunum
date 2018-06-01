package fr.info.game.logic.level;

import fr.info.game.AsterixAndObelixGame;
import fr.info.game.audio.Sound;
import fr.info.game.audio.SoundSource;
import fr.info.game.logic.entity.Entity;
import fr.info.game.logic.math.interpolation.LinearInterpolation;
import fr.info.game.logic.tile.Tile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Level {

    public final String levelName;
    private long levelTicks;
    private final List<Entity> loadedEntities = new ArrayList<>();

    private final Camera camera = new Camera();

    protected final SoundSource sfxSource = new SoundSource();

    private LinearInterpolation cartoonCircleAnimation;
    protected float cartoonCircleRadius;
    protected float prevCartoonCircleRadius;

    private boolean isClosingLevel = false;
    private boolean isOpeningLevel = false;

    public Level(String levelName) {
        this.levelName = levelName;
    }

    public void update() {

        this.prevCartoonCircleRadius = this.cartoonCircleRadius;
        if (this.cartoonCircleAnimation != null) {
            this.cartoonCircleAnimation.update();
            this.cartoonCircleRadius = this.cartoonCircleAnimation.getValue();
        }

        Iterator<Entity> iterator = getLoadedEntities().iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            if(entity.isDead) {
                iterator.remove();
            } else {
                entity.update();
            }
        }

        camera.update();
        this.levelTicks++;
    }

    public void openLevel(long openingDuration, boolean playOpeningSound) {

        if(!this.isOpeningLevel) {
            this.cartoonCircleAnimation = new LinearInterpolation((int) openingDuration, 0, 1);

            if (playOpeningSound) {
                sfxSource.stop();
                Sound openingSFX = AsterixAndObelixGame.INSTANCE.getAudioManager().getSound("opening_world");
                if (openingSFX != null) {
                    sfxSource.play(openingSFX);
                }
            }

            this.isClosingLevel = false;
            this.isOpeningLevel = true;
        }

    }

    public void closeLevel(long closingDuration) {
        if(!this.isClosingLevel) {
            this.cartoonCircleAnimation = new LinearInterpolation((int) closingDuration, 1, 0);
            this.isClosingLevel = true;
            this.isOpeningLevel = false;
        }
    }

    public void keyEvent(int key, int action) {}

    public float getCartoonCircleRadius() {
        return cartoonCircleRadius;
    }

    public float getPrevCartoonCircleRadius() {
        return prevCartoonCircleRadius;
    }

    public void spawnEntity(Entity entity) {
        loadedEntities.add(entity);
        entity.level = this;
    }

    public List<Entity> getLoadedEntities() {
        return loadedEntities;
    }

    public Camera getCamera() {
        return camera;
    }

    public long getLevelTicks() {
        return levelTicks;
    }

    public Tile[][] getTerrain() {
        return null;
    }

    public void resetLevel() {
        for(Entity entity : loadedEntities) {
            entity.isDead = true;
        }
    }
}
