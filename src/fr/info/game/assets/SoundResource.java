package fr.info.game.assets;

import fr.info.game.AsterixAndObelixGame;

public class SoundResource extends Resource {

    public SoundResource(String path) {
        super(path);
    }

    @Override
    public String getPath() {
        return AsterixAndObelixGame.SOUNDS_PATH + super.getPath();
    }
}
