package fr.info.game.logic.entity;

import fr.info.game.logic.item.Item;

public class EntityItem extends Entity {

    public final Item item;

    public EntityItem(Item item) {
        this.item = item;
    }

    public EntityItem(Item item, float x, float y) {
        super(x, y);
        this.item = item;
    }

    public EntityItem(Item item, float x, float y, float width, float height) {
        super(x, y, width, height);
        this.item = item;
    }

    public EntityItem(Item item, float x, float y, float z, float width, float height) {
        super(x, y, z, width, height);
        this.item = item;
    }
}
