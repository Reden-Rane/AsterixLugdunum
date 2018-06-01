package fr.info.game.logic.level.tavern;

import fr.info.game.assets.MapResource;
import fr.info.game.logic.AxisAlignedBoundingBox;
import fr.info.game.logic.entity.EntityItem;
import fr.info.game.logic.entity.PacmanPlayer;
import fr.info.game.logic.input.KeyboardCallback;
import fr.info.game.logic.item.Item;
import fr.info.game.logic.item.ItemRegistry;
import fr.info.game.logic.level.GameLevel;
import fr.info.game.logic.tile.Tile;
import fr.info.game.logic.tile.TileRegistry;
import fr.info.game.utils.FileUtils;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TavernLevel extends GameLevel {

    public final Tile[][] floor;
    public final Item[][] items;

    /**
     * The tiles where the enemies can walk onto
     */
//    public final Node[][] enemiesPassableNodes;

    private int score;

    private final List<EntityItem> entitiesItem = new ArrayList<>();

    private PacmanPlayer player;

    private boolean keyLeftPressed;
    private boolean keyRightPressed;
    private boolean keyUpPressed;
    private boolean keyDownPressed;

    private static final float INITIAL_PLAYER_X = 1;
    private static final float INITIAL_PLAYER_Y = 1;

    public TavernLevel() {
        super("La taverne");
        this.floor = FileUtils.loadMapTerrain(new MapResource("pacman_floor0.json"));
//        this.enemiesPassableNodes = getEnemiesPassableNodes();
        this.items = FileUtils.loadMapItems(new MapResource("pacman_items0.json"));
        resetLevel();
    }/*

    private Node[][] getEnemiesPassableNodes() {

        Node[][] passableTiles = new Node[getMapWidth()][getMapHeight()];

        for(int i = 0; i < passableTiles.length; i++) {
            for(int j = 0; j < passableTiles[i].length; j++) {
                Tile wallTile = getTerrain()[i][j];
                Tile floorTile = this.floor[i][j];
                if((wallTile == null || wallTile == TileRegistry.GHOST_HOME_DOORS[0] || wallTile == TileRegistry.GHOST_HOME_DOORS[1])
                        && floorTile != null) {
                    passableTiles[i][j] = new Node(i, j);
                }
            }
        }

        return passableTiles;
    }
*/
    private void spawnItems(Item[][] items) {
        for (int i = 0; i < items.length; i++) {
            for (int j = 0; j < items[i].length; j++) {
                if (items[i][j] != null) {
                    spawnItem(items[i][j], i + 0.2F, j + 0.2F, 0.8F, 0.8F);
                }
            }
        }
    }

    private void spawnItem(Item item, float x, float y, float width, float height) {
        EntityItem entityItem = new EntityItem(item, x, y, width, height);
        entityItem.setCollisionBox(new AxisAlignedBoundingBox(0.25F, 0.25F, 0.5F, 0.5F));
        entitiesItem.add(entityItem);
        spawnEntity(entityItem);
    }

    @Override
    public void resetLevel() {
        super.resetLevel();
        this.keyDownPressed = false;
        this.keyUpPressed = false;
        this.keyLeftPressed = false;
        this.keyRightPressed = false;
        this.entitiesItem.clear();
        this.score = 0;
        this.player = new PacmanPlayer(this, 1.375F, 2.75F);
        this.player.setCollisionBox(new AxisAlignedBoundingBox(0.3F, 0, 0.9375F, 0.9375F));
        spawnItems(this.items);
        spawnEntity(this.player);
        this.player.motionX = 0;
        this.player.motionY = 0;
        this.player.x = INITIAL_PLAYER_X;
        this.player.y = INITIAL_PLAYER_Y;
    }

    @Override
    public void update() {
        movePlayer(0.125F);
        pickupItem();
        handleTeleportation();
        updateInputs();
        super.update();
        updateTiles(this.floor);
    }

    private void movePlayer(float playerSpeed) {

        float newMotionX = 0;
        float newMotionY = 0;

        if (keyRightPressed) {
            newMotionX = playerSpeed;
            newMotionY = 0;
        } else if (keyLeftPressed) {
            newMotionX = -playerSpeed;
            newMotionY = 0;
        } else if (keyUpPressed) {
            newMotionX = 0;
            newMotionY = playerSpeed;
        } else if (keyDownPressed) {
            newMotionX = 0;
            newMotionY = -playerSpeed;
        }

        if (this.player.canMoveTo(player.x + newMotionX, player.y + newMotionY)) {
            player.motionX = newMotionX;
            player.motionY = newMotionY;
        }
    }

    private void pickupItem() {
        Iterator<EntityItem> itemIterator = entitiesItem.iterator();

        while (itemIterator.hasNext()) {
            EntityItem entityItem = itemIterator.next();

            if(entityItem.item == ItemRegistry.BOTTLES[0]) {
                if(entityItem.collidesWith(player)) {
                    score++;
                    entityItem.isDead = true;
                    itemIterator.remove();
                }
            }
        }
    }

    private void handleTeleportation() {

        int playerX = (int) (player.x + player.getCollisionBox().x);
        int playerY = (int) (player.y + player.getCollisionBox().y);
        boolean playerShouldTeleport = floor[playerX][playerY] == TileRegistry.TELEPORTER;

        if (playerShouldTeleport) {
            if(player.x + player.getCollisionBox().x <= 0.1F && playerY == 7) {
                player.teleportTo(getMapWidth() - 2, 7);
            } else if(playerX == getMapWidth() - 1 && playerY == 7) {
                player.teleportTo(1, 7);
            }
        }
    }

    private void updateInputs() {
        if (KeyboardCallback.isKeyDown(GLFW.GLFW_KEY_A)) {
            keyLeftPressed = true;
            keyRightPressed = false;
            keyDownPressed = false;
            keyUpPressed = false;
        } else if (KeyboardCallback.isKeyDown(GLFW.GLFW_KEY_D)) {
            keyLeftPressed = false;
            keyRightPressed = true;
            keyDownPressed = false;
            keyUpPressed = false;
        } else if (KeyboardCallback.isKeyDown(GLFW.GLFW_KEY_W)) {
            keyLeftPressed = false;
            keyRightPressed = false;
            keyDownPressed = false;
            keyUpPressed = true;
        } else if (KeyboardCallback.isKeyDown(GLFW.GLFW_KEY_S)) {
            keyLeftPressed = false;
            keyRightPressed = false;
            keyDownPressed = true;
            keyUpPressed = false;
        }
    }

    @Override
    public Tile[][] generateTerrain() {
        return FileUtils.loadMapTerrain(new MapResource("pacman_walls0.json"));
    }

    public int getMapWidth() {
        return floor.length;
    }

    public int getMapHeight() {
        if (floor.length > 0) {
            return floor[0].length;
        } else {
            return 0;
        }
    }

    public int getScore() {
        return score;
    }
}
