package fr.info.game.graphics.texture;

import com.google.gson.*;
import fr.info.game.assets.TextureResource;
import fr.info.game.exception.GameException;
import fr.info.game.exception.render.TextureLoadingException;

import java.lang.reflect.Type;

public class TextureAtlasDeserializer implements JsonDeserializer<TextureAtlas> {

    @Override
    public TextureAtlas deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {

        try {
            JsonObject mainObj = json.getAsJsonObject();
            String atlasName = mainObj.getAsJsonPrimitive("name").getAsString();

            if (atlasName.trim().isEmpty()) {
                throw new TextureLoadingException("Atlas name can't be empty.");
            }

            String texturePath = mainObj.getAsJsonPrimitive("texture_path").getAsString();
            int minFilter = mainObj.getAsJsonPrimitive("min_filter").getAsInt();
            int magFilter = mainObj.getAsJsonPrimitive("mag_filter").getAsInt();
            TextureSprite[] sprites = parseTextureSprites(mainObj.getAsJsonArray("sprites"));
            SpriteAnimation[] animations = parseSpritesAnimations(mainObj.getAsJsonArray("animations"), sprites);
            TextureAtlas atlas = new TextureAtlas(atlasName, new TextureResource(texturePath), minFilter, magFilter, sprites, animations);

            for (TextureSprite sprite : sprites) {
                sprite.setParentAtlas(atlas);
            }

            for (SpriteAnimation animation : animations) {
                animation.setParentAtlas(atlas);
            }

            return atlas;
        } catch (GameException e) {
            e.printStackTrace();
        }
        return null;
    }

    private TextureSprite[] parseTextureSprites(JsonArray spritesArray) throws TextureLoadingException {
        if (spritesArray == null) {
            throw new TextureLoadingException("The atlas requires at least one sprite.");
        }

        TextureSprite[] sprites = new TextureSprite[spritesArray.size()];

        for (int i = 0; i < spritesArray.size(); i++) {
            JsonObject spriteObj = spritesArray.get(i).getAsJsonObject();
            String spriteName = spriteObj.getAsJsonPrimitive("name").getAsString();

            if (spriteName.trim().isEmpty()) {
                throw new TextureLoadingException("Sprite name can't be empty.");
            }

            Integer x = spriteObj.getAsJsonPrimitive("x").getAsInt();
            Integer y = spriteObj.getAsJsonPrimitive("y").getAsInt();
            Integer width = spriteObj.getAsJsonPrimitive("width").getAsInt();
            Integer height = spriteObj.getAsJsonPrimitive("height").getAsInt();
            sprites[i] = new TextureSprite(spriteName, x, y, width, height);
        }

        return sprites;
    }

    private SpriteAnimation[] parseSpritesAnimations(JsonArray animationsArray, TextureSprite[] sprites) throws GameException, TextureLoadingException {
        if (animationsArray == null) {
            return new SpriteAnimation[0];
        }

        SpriteAnimation[] animations = new SpriteAnimation[animationsArray.size()];

        for (int i = 0; i < animationsArray.size(); i++) {
            JsonObject animationObj = animationsArray.get(i).getAsJsonObject();
            String animationName = animationObj.getAsJsonPrimitive("name").getAsString();

            if (animationName.trim().isEmpty()) {
                throw new TextureLoadingException("Sprite name can't be empty.");
            }

            //Parse the animation's frames
            JsonArray frameNamesArray = animationObj.getAsJsonArray("frames");
            TextureSprite[] frames = new TextureSprite[frameNamesArray.size()];

            for (int j = 0; j < frameNamesArray.size(); j++) {
                String frameName = frameNamesArray.get(j).getAsString();

                boolean found = false;
                for (TextureSprite sprite : sprites) {
                    if (sprite.name.equals(frameName)) {
                        frames[j] = sprite;
                        found = true;
                    }
                }

                if (!found) {
                    throw new TextureLoadingException("Unable to find sprite named " + frameName + " for animation " + animationName);
                }
            }

            //Parse the animation frames' periods
            JsonArray framePeriodsArray = animationObj.getAsJsonArray("periods");

            if (framePeriodsArray.size() != frames.length) {
                throw new TextureLoadingException("The number of periods must be equal to the number of frames.");
            }

            double[] periods = new double[framePeriodsArray.size()];

            for (int j = 0; j < framePeriodsArray.size(); j++) {
                periods[j] = framePeriodsArray.get(j).getAsDouble();
            }

            animations[i] = new SpriteAnimation(animationName, frames, periods);
        }
        return animations;
    }

}