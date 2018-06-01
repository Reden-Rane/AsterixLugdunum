package fr.info.game.assets;

import fr.info.game.AsterixAndObelixGame;

public class MapResource extends TextResource {

    public MapResource(String path) {
        super(path);
    }

    @Override
    public String getPath() {
        return AsterixAndObelixGame.MAPS_PATH + super.getPath();
    }
}
