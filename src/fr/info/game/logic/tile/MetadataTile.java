package fr.info.game.logic.tile;

public class MetadataTile extends Tile {

    public final int metadata;

    MetadataTile(String textureName, int metadata) {
        super(textureName);
        this.metadata = metadata;
    }

    @Override
    public String getTextureName() {
        return super.getTextureName() + metadata;
    }
}
