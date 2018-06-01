package fr.info.game.logic.level.bridge;

import fr.info.game.AsterixAndObelixGame;
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

public class BridgeLevel extends GameLevel {

    private static final float WATER_RESISTANCE = 0.005F;
    private static final float MAX_ACCELERATION = 0.03125F;
    private static final float INITIAL_PLAYER_X = 12;
    private static final float INITIAL_PLAYER_Y = 1.5F;
    private static final long CANNONBALL_SPAWN_PERIOD = 15000;//In milliseconds

    private static final int LEVEL_FINISH_LINE_DISTANCE = 100;

    private static boolean hasBestTime;
    private static long bestTime = Long.MAX_VALUE;

    private Boat playerBoat;

    private final List<Integer> keySequence = new ArrayList<>();

    /*
     * taille (possiblement aléatoire de chaque séquence de touches générée
     */
    private int initialKeySequenceSize;
    /*
     * compte le nombre de touches bonnes appuyées par le joueur pour attribuer le bonus de vitesse associé
     */
    private int validKeysCounter;


    private long lastKeyPressedTime = -1;
    /*
     * validité de la touche appuyée pour mettre en mouvement l'image de la touche (tremble si erreur, grossit si valide)
     */
    private boolean wasLastKeyValid;
    /*
     * liste les boulets se trouvant dans le niveau
     */
    private final List<Cannonball> cannonballList = new ArrayList<>();
    /*
     * met à jour la dernière apparition d'un boulet (apparition régulière)
     */
    private long lastCannonballSpawnTime;

    private boolean levelStarted;

    private long startTime;
    private long finishTime;
    private boolean levelFinished;
    private boolean newRecord;

    public BridgeLevel() {
        super("Le pont");
        this.getCamera().setZoom(2);
        this.resetLevel();
    }

    @Override
    public Tile[][] generateTerrain() {
        /*
         * génération de tuiles sur une distance plus grande que celle entre le départ et l'arrivée car le bateau garde se vélocité de fin de course
         */
        Tile[][] tiles = new Tile[LEVEL_FINISH_LINE_DISTANCE + 200][6];

        //TODO Générer une rive plus irrégulière pour le réalisme??

        /*
         * placement des tuiles (aléatoires)
         */
        for (int x = 0; x < tiles.length; x++) {

            for (int y = 0; y < tiles[x].length; y++) {
                if (y < 3) {

                    double rand = Math.random();
                    if (rand < 0.02) {
                        /*
                         * random pour choisir parmi différentes tuiles (de contenu semblable)
                         */
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

    /**
     * remet à zero les éléments du niveau (lorsqu'on quitte et revient)
     */
    @Override
    public void resetLevel() {
        super.resetLevel();
        this.cannonballList.clear();
        this.playerBoat = new Boat();
        this.spawnEntity(playerBoat);
        this.generateRandomKeySequence(4, new int[] {81, 87, 69, 82});
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

        /*
         * fait apparaître aléatoirement des boulets de cannon en fonction de leur période d'apparition
         */
        if(!isLevelFinished() && hasLevelStarted()) {
            if (System.currentTimeMillis() - lastCannonballSpawnTime >= CANNONBALL_SPAWN_PERIOD) {
                randomlyShootCannonball();
            }
        }

        applyWaterResistance();
        updateCamera();
        handleKeySequence();


        if(getPlayerProgress() >= 1 && !isLevelFinished()) {
            this.finishTime = System.currentTimeMillis();
            this.levelFinished = true;

            /*
             * temps écoulé lors de la course
             * soustraction de 3000 pour ne pas prendre en compte le compte à rebours
             */
            long time = finishTime - startTime - 3000;

            if(time < bestTime) {
                bestTime = time;
                newRecord = true;

                if(!hasBestTime) {
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

            /*
             * attribue le bonus de vitesse au bateau en fonction du nombre de touches valides
             */
            playerBoat.motionX = playerBoat.motionX + MAX_ACCELERATION * ratio;
            playerBoat.paddleRotationMotion = 0.5F * ratio;
            generateRandomKeySequence(4, new int[] {81, 87, 69, 82});
        }
    }

    /**
     * applique la résistance de l'eau sur la vitesse du bateau
     */
    private void applyWaterResistance() {
        //Apply water resistance
        playerBoat.motionX = MathUtils.clamp(playerBoat.motionX - WATER_RESISTANCE * playerBoat.motionX, 0, Float.MAX_VALUE);
        playerBoat.paddleRotationMotion = MathUtils.clamp(playerBoat.paddleRotationMotion - WATER_RESISTANCE * 4 * playerBoat.paddleRotationMotion, 0, Float.MAX_VALUE);
    }

    /**
     * génère l'apparition aléatoire d'un boulet
     */
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

            /*
             * les collisions des boulets avec le bateau
             */
            if (cannonball.collidesWith(this.playerBoat)) {
                playerBoat.hit();
                cannonball.isDead = true;
            } else if (cannonball.y <= this.playerBoat.y) {
                cannonball.isDead = true;//The cannonball went into the water
                spawnEntity(new SplashParticle(cannonball.x, cannonball.y, cannonball.width, cannonball.height, 20));
            }
        }
    }

    /**
     * méthode appelée dès qu'une touche est appuyée
     * @param key
     * @param action
     */
    @Override
    public void keyEvent(int key, int action) {
        super.keyEvent(key, action);

        /*
        démarre le jeu dans un niveau, après pression sur la touche ENTREE
         */
        if(!levelStarted) {
            if(KeyboardCallback.isKeyDown(GLFW_KEY_ENTER)) {
                this.startTime = System.currentTimeMillis();
                levelStarted = true;
                return;
            }
        }

        if(!isLevelFinished() && hasLevelStarted()) {


            List<Integer> pressedKeys = KeyboardCallback.getPressedKeys();

            /*
            touche à appuyer
             */
            int keyToPress = getKeyToPress();

            if (!keySequence.isEmpty()) {
                /*
                seulement quand une touche est appuyée
                 */
                if (pressedKeys.size() >= 1) {
                    /*
                    seulement si une seule touche est appuyée et si celle ci correspond à la touche demandée
                     */
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

    }

    public long getLastKeyPressedTime() {
        return lastKeyPressedTime;
    }

    public boolean wasLastKeyValid() {
        return wasLastKeyValid;
    }

    /**
     * génrère les séquences de touches
     * @param sequenceSize
     * @param keys
     */
    private void generateRandomKeySequence(int sequenceSize, int[] keys) {
        keySequence.clear();

        for(int i = 0; i < sequenceSize; i++) {
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

    /**
     * retourne la progression du joueur, allant de 0 (début) à 1 (fin)
     * @return
     */
    public float getPlayerProgress() {
        return MathUtils.clamp((playerBoat.x - INITIAL_PLAYER_X) / (float) LEVEL_FINISH_LINE_DISTANCE, 0, 1);
    }

    /**
     * retourne la durée de la course
     * @return
     */
    public long getDuration() {
        return System.currentTimeMillis() - startTime - 3000;
    }

    /**
     * retourne la prochaine touche à appuyer
     * @return
     */
    public int getKeyToPress() {
        return keySequence.size() > 0 ? keySequence.get(0) : -1;
    }

    public  List<Integer> getKeySequence() {
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

    /**
     * vrai si un nouveau record est atteint
     * @return
     */
    public boolean hasNewRecord() {
        return newRecord;
    }

    /**
     * enregistre le meilleur temps
     * @return
     */
    public static long getBestTime() {
        return bestTime;
    }

    /**
     * juste pour la première course
     * @return
     */
    public static boolean hasBestTime() {
        return hasBestTime;
    }
}
