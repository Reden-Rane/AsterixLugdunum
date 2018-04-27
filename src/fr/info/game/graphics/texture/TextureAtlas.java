package fr.info.game.graphics.texture;

import fr.info.game.AsterixAndObelixGame;
import fr.info.game.assets.TextureResource;

public class TextureAtlas {

    private final Texture texture;
    public final String name;
    public final TextureResource texturePath;
    public final TextureSprite[] sprites;
    public final SpriteAnimation[] animations;

	public TextureAtlas(String name, Texture texture, TextureSprite[] sprites) {
	    this.name = name;
	    this.texture = texture;
	    this.texturePath = null;
	    this.sprites = sprites;
	    this.animations = new SpriteAnimation[0];
    }

    public TextureAtlas(String name, TextureResource texturePath, int minFilter, int magFilter, TextureSprite[] sprites, SpriteAnimation[] animations) {
        this.name = name;
        this.texturePath = texturePath;
        this.sprites = sprites;
        this.animations = animations;
        this.texture = new Texture(texturePath, minFilter, magFilter);
    }

    public void bind() {
	    if(AsterixAndObelixGame.INSTANCE.getRenderManager().textureManager.getBoundTexture() != getTexture()) {
            getTexture().bind();
            AsterixAndObelixGame.INSTANCE.getRenderManager().textureManager.setBoundTexture(getTexture());
        }
    }

    public void unbind() {
	    if(AsterixAndObelixGame.INSTANCE.getRenderManager().textureManager.getBoundTexture() == getTexture()) {
            getTexture().unbind();
            AsterixAndObelixGame.INSTANCE.getRenderManager().textureManager.setBoundTexture(null);
        }
    }

    public void destroy() {
        getTexture().destroy();
    }

    public int getWidth() {
        return getTexture().getWidth();
    }

    public int getHeight() {
        return getTexture().getHeight();
    }

    public Texture getTexture() {
        return texture;
    }

    public TextureSprite getTextureSprite(String spriteName) {
        for (TextureSprite sprite : sprites) {
            if(sprite != null) {
                if (sprite.name.equals(spriteName)) {
                    return sprite;
                }
            }
        }
        return null;
    }

    public SpriteAnimation getSpriteAnimation(String animationName) {
        for (SpriteAnimation animation : animations) {
            if(animation != null) {
                if (animation.name.equals(animationName)) {
                    return animation;
                }
            }
        }
        return null;
    }
}
