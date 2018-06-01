package fr.info.game.logic.item;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ItemRegistry {

    static final List<Item> registeredItems = new ArrayList<>();

    public static final Item[] BOTTLES = createMetadataItem("bottle", 1);
    public static final Item KEYS = new Item("keys");
    public static final Item BARREL = new Item("barrel");

    public static MetadataItem[] createMetadataItem(String itemName, int maxMetadata) {
        return createMetadataItem(itemName, MetadataItem.class, maxMetadata);
    }

    public static MetadataItem[] createMetadataItem(String itemName, Class<? extends MetadataItem> itemClass, int maxMetadata) {

        MetadataItem[] items = new MetadataItem[maxMetadata + 1];

        try {
            Constructor<? extends MetadataItem> itemConstructor = itemClass.getDeclaredConstructor(String.class, int.class);

            for (int i = 0; i <= maxMetadata; i++) {
                items[i] = itemConstructor.newInstance(itemName, i);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }

        return items;
    }

    public static Item getItem(String itemName) {
        for (Item item : registeredItems) {
            if (item.getTextureName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    public static Item getItem(String itemName, int meta) {
        return getItem(itemName + meta);
    }
}
