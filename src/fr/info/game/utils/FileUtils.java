package fr.info.game.utils;

import com.google.gson.GsonBuilder;
import fr.info.game.assets.MapResource;
import fr.info.game.assets.TextResource;
import fr.info.game.logic.item.Item;
import fr.info.game.logic.item.ItemRegistry;
import fr.info.game.logic.level.Level;
import fr.info.game.logic.tile.Tile;
import fr.info.game.logic.tile.TileRegistry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtils {

    private static String[][] loadMapResource(MapResource resource) {
        String map = loadAsString(resource);
        return new GsonBuilder().create().fromJson(map, String[][].class);
    }

    public static Item[][] loadMapItems(MapResource resource) {
        String[][] itemNames = loadMapResource(resource);

        Item[][] items = new Item[itemNames.length][];
        for(int i = 0; i < items.length; i++) {

            items[i] = new Item[itemNames[i].length];

            for(int j = 0; j < items[i].length; j++) {
                String itemName = itemNames[i][j];

                if(!itemName.isEmpty()) {
                    items[i][j] = ItemRegistry.getItem(itemName);
                }
            }
        }
        return items;
    }

    public static Tile[][] loadMapTerrain(MapResource resource) {
        String[][] tilesNames = loadMapResource(resource);

        Tile[][] terrain = new Tile[tilesNames.length][];
        for(int i = 0; i < terrain.length; i++) {

            terrain[i] = new Tile[tilesNames[i].length];

            for(int j = 0; j < terrain[i].length; j++) {
                String tileName = tilesNames[i][j];

                if(!tileName.isEmpty()) {
                    terrain[i][j] = TileRegistry.getTile(tileName);
                }
            }
        }
        return terrain;
    }

    /**
     * @param resource The file containing the text to extract
     * @return Return the text contained in the file
     */
    public static String loadAsString(TextResource resource) {

        StringBuilder result = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getResourceAsStream()));
            String buffer;

            while ((buffer = reader.readLine()) != null) {
                result.append(buffer).append('\n');
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

}
