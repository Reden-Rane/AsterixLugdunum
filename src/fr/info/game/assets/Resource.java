package fr.info.game.assets;

import fr.info.game.AsterixAndObelixGame;

import java.io.InputStream;

public class Resource {

    private final String path;

    public Resource(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public InputStream getResourceAsStream() {
        return AsterixAndObelixGame.INSTANCE.getClass().getClassLoader().getResourceAsStream(getPath());
    }

}
