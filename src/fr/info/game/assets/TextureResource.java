package fr.info.game.assets;

import fr.info.game.AsterixAndObelixGame;

public class TextureResource extends Resource {

    public TextureResource(String path) {
        super(path);
    }

    @Override
    public String getPath() {
        return AsterixAndObelixGame.TEXTURES_PATH + super.getPath();
    }
}
