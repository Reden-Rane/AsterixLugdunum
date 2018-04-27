package fr.info.game;

import fr.info.game.assets.TextureResource;
import fr.info.game.exception.render.TextureLoadingException;
import fr.info.game.graphics.RenderManager;
import fr.info.game.graphics.texture.TextureAtlas;
import fr.info.game.graphics.texture.TextureSprite;

import java.io.IOException;

public class Font {

    private final TextureAtlas fontAtlas;
    private final float fontSize;

    public Font(RenderManager renderManager, TextureResource fontAtlasPath, float fontSize) throws IOException, TextureLoadingException {
        this.fontAtlas = renderManager.textureManager.loadAtlas(fontAtlasPath);
        this.fontSize = fontSize;
    }

    public TextureSprite getCharSprite(char c) {
        return fontAtlas.getTextureSprite(String.valueOf(Integer.valueOf(c)));
    }

    public float getFontSize() {
        return fontSize;
    }

    public void destroy() {
        this.fontAtlas.destroy();
    }
}