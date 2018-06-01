package fr.info.game.logic.item;

public class Item {

    private final String textureName;

    public Item(String textureName) {
        this.textureName = textureName;
        ItemRegistry.registeredItems.add(this);
    }

    public String getTextureName() {
        return textureName;
    }
}
