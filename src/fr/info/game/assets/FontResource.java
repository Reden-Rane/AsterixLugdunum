package fr.info.game.assets;

import fr.info.game.AsterixAndObelixGame;

public class FontResource extends Resource {

    public FontResource(String path) {
        super(path);
    }

    @Override
    public String getPath() {
        return AsterixAndObelixGame.FONT_PATH + super.getPath();
    }
}
