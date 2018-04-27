package fr.info.game.assets;

import fr.info.game.AsterixAndObelixGame;

public class ShaderResource extends TextResource {

    public ShaderResource(String path) {
        super(path);
    }

    @Override
    public String getPath() {
        return AsterixAndObelixGame.SHADERS_PATH + super.getPath();
    }
}
