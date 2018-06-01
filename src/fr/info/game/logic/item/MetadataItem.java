package fr.info.game.logic.item;

public class MetadataItem extends Item {

    public final int metadata;

    public MetadataItem(String textureName, int metadata) {
        super(textureName);
        this.metadata = metadata;
    }

    @Override
    public String getTextureName() {
        return super.getTextureName() + metadata;
    }
}
