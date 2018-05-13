package fr.info.game.graphics.texture;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.info.game.assets.TextureResource;
import fr.info.game.exception.render.TextureLoadingException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TextureManager {

    private final Gson gson;

    private final List<TextureAtlas> loadedAtlases = new ArrayList<>();

    //Entities Atlases
    public final TextureAtlas asterixAtlas;
    public final TextureAtlas boatAtlas;
    public final TextureAtlas cannonballAtlas;

    //GUI Atlases
    public final TextureAtlas introAtlas;
    public final TextureAtlas bdAtlas;
    public final TextureAtlas guiAtlas;

    //Terrain Atlases
    public final TextureAtlas hubMapAtlas;
    public final TextureAtlas tileAtlas;

    public final TextureAtlas skyParallaxAtlas;

    private Texture boundTexture;

    public TextureManager() throws IOException, TextureLoadingException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(TextureAtlas.class, new TextureAtlasDeserializer());
        this.gson = gsonBuilder.create();
        this.asterixAtlas = loadAtlas(new TextureResource("entity/asterix_atlas.json"));
        this.boatAtlas = loadAtlas(new TextureResource("entity/boat_atlas.json"));
        this.cannonballAtlas = loadAtlas(new TextureResource("entity/cannonball_atlas.json"));

        this.introAtlas = loadAtlas(new TextureResource("gui/intro_atlas.json"));
        this.bdAtlas = loadAtlas(new TextureResource("gui/bd_atlas.json"));
        this.guiAtlas = loadAtlas(new TextureResource("gui/gui_atlas.json"));

        this.hubMapAtlas = loadAtlas(new TextureResource("terrain/hub_map.json"));
        this.tileAtlas = loadAtlas(new TextureResource("terrain/tile_atlas.json"));
        this.skyParallaxAtlas = loadAtlas(new TextureResource("terrain/sky_parallax_atlas.json"));
    }

    public TextureAtlas loadAtlas(TextureResource textureResource) throws TextureLoadingException, IOException {

        InputStream is = textureResource.getResourceAsStream();

        if (is == null)
            throw new TextureLoadingException("Unable to find the atlas file: " + textureResource.getPath());

        InputStreamReader isr = new InputStreamReader(is);
        TextureAtlas atlas = this.gson.fromJson(isr, TextureAtlas.class);
        loadedAtlases.add(atlas);
        isr.close();
        return atlas;
    }

    public Texture getBoundTexture() {
        return boundTexture;
    }

    public void setBoundTexture(Texture boundTexture) {
        this.boundTexture = boundTexture;
    }

    public void destroy() {
        for (TextureAtlas atlas : this.loadedAtlases) {
            atlas.destroy();
        }
    }
}