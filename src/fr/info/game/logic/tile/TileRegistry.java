package fr.info.game.logic.tile;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class TileRegistry {

    static final List<Tile> registeredTiles = new ArrayList<>();

    public static final MetadataTile[] WATERS = createMetadataTiles("water", 3);
    public static final MetadataTile[] LILYPADS = createMetadataTiles("lilypad", 5);
    public static final MetadataTile[] GRASSES = createMetadataTiles("grass", 4);
    public static final MetadataTile[] WATER_GRASSES = createMetadataTiles("grass_water", 2);

    public static final Tile WOOD_PLANKS = new Tile("woodPlanks");

    public static final TeleporterTile TELEPORTER = new TeleporterTile();

    public static final MetadataTile[] TABLES = createMetadataTiles("table", 5);
    public static final MetadataTile[] GHOST_HOME_DOORS = createMetadataTiles("ghostHomeDoor", 1);
    public static final MetadataTile[] WOOD_CRATES = createMetadataTiles("woodCrate", 15);
    public static final MetadataTile[] WOOD_BASKETS = createMetadataTiles("woodBasket", 15);

    public static MetadataTile[] createMetadataTiles(String tileName, int maxMetadata) {
        return createMetadataTiles(tileName, MetadataTile.class, maxMetadata);
    }

    public static MetadataTile[] createMetadataTiles(String tileName, Class<? extends MetadataTile> tileClass, int maxMetadata) {

        MetadataTile[] tiles = new MetadataTile[maxMetadata + 1];

        try {
            Constructor<? extends MetadataTile> tileConstructor = tileClass.getDeclaredConstructor(String.class, int.class);

            for (int i = 0; i <= maxMetadata; i++) {
                tiles[i] = tileConstructor.newInstance(tileName, i);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

        return tiles;
    }

    public static Tile getTile(String tileName) {
        for (Tile tile : registeredTiles) {
            if (tile.getTextureName().equals(tileName)) {
                return tile;
            }
        }
        return null;
    }

    public static Tile getTile(String tileName, int meta) {
        return getTile(tileName + meta);
    }
}
