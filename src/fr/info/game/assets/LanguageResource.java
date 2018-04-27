package fr.info.game.assets;

import fr.info.game.AsterixAndObelixGame;

public class LanguageResource extends Resource {

    public LanguageResource(String path) {
        super(path);
    }

    @Override
    public String getPath() {
        return AsterixAndObelixGame.LANG_PATH + super.getPath();
    }
}
