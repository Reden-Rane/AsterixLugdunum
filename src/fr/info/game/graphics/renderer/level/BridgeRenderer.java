package fr.info.game.graphics.renderer.level;

import fr.info.game.graphics.RenderManager;
import fr.info.game.graphics.RenderUtils;
import fr.info.game.graphics.texture.TextureSprite;
import fr.info.game.logic.level.bridge.BridgeLevel;
import fr.info.game.logic.math.MathUtils;
import org.joml.Matrix4f;
import org.joml.Vector4f;

import static fr.info.game.AsterixAndObelixGame.RESOLUTION_X;
import static fr.info.game.AsterixAndObelixGame.RESOLUTION_Y;
import static fr.info.game.logic.tile.Tile.TILE_SIZE;
import static org.lwjgl.opengl.GL11.*;

public class BridgeRenderer extends LevelRenderer<BridgeLevel> {

    public BridgeRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    protected void renderTerrain(BridgeLevel level, float partialTicks) {
        super.renderTerrain(level, partialTicks);

        float cameraOffsetX = MathUtils.lerp(level.getCamera().getPrevOffsetX(), level.getCamera().getOffsetX(), partialTicks);

        renderManager.shaderManager.spriteShader.bind();
        renderManager.shaderManager.spriteShader.setUniformMat4f("viewMatrix", new Matrix4f());
        renderManager.shaderManager.spriteShader.setUniformMat4f("modelMatrix", new Matrix4f());

        TextureSprite mountains0Sprite = renderManager.textureManager.skyParallaxAtlas.getTextureSprite("mountains1");
        TextureSprite mountains1Sprite = renderManager.textureManager.skyParallaxAtlas.getTextureSprite("mountains0");
        TextureSprite clouds0Sprite = renderManager.textureManager.skyParallaxAtlas.getTextureSprite("clouds0");
        TextureSprite clouds2Sprite = renderManager.textureManager.skyParallaxAtlas.getTextureSprite("clouds2");
        TextureSprite clouds3Sprite = renderManager.textureManager.skyParallaxAtlas.getTextureSprite("clouds3");

        renderLayer(clouds0Sprite, cameraOffsetX / 5F, TILE_SIZE * 17, renderManager.getDisplayWidth() / 2, true);
        renderLayer(mountains1Sprite, cameraOffsetX / 4F, TILE_SIZE * 15, renderManager.getDisplayWidth() / 2, true);
        renderLayer(clouds2Sprite, cameraOffsetX / 3F, TILE_SIZE * 17, renderManager.getDisplayWidth() / 4, true);
        renderLayer(mountains0Sprite, cameraOffsetX / 2F, TILE_SIZE * 12, renderManager.getDisplayWidth() / 3, true);
        renderLayer(clouds3Sprite, cameraOffsetX / 1.2F, TILE_SIZE * 12, renderManager.getDisplayWidth() / 4, true);

        renderManager.shaderManager.spriteShader.unbind();
        updateView(level, partialTicks);
    }

    private void renderLayer(TextureSprite layerSprite, float offsetX, int y, int width, boolean repeat) {
        float layerRatio = layerSprite.height / (float) layerSprite.width;

        offsetX = offsetX % width;
        int n;

        if (repeat) {
            n = (int) Math.ceil(renderManager.getDisplayWidth() / (float) width) + 1;
        } else {
            n = 1;
        }

        for (int i = 0; i < n; i++) {
            RenderUtils.renderTextureSprite(layerSprite, offsetX + i * width, y, 0, width, width * layerRatio);
        }
    }

    @Override
    public void render(BridgeLevel level, float partialTicks) {
        glClearColor(0.5F, 0.8F, 1F, 1);
        glClear(GL_COLOR_BUFFER_BIT);

        updateView(level, partialTicks);
        renderTerrain(level, partialTicks);
        renderEntities(level, partialTicks);

        renderManager.shaderManager.hudShader.bind();
        renderManager.shaderManager.hudShader.setUniformMat4f("projectionMatrix", new Matrix4f().ortho(0, RESOLUTION_X, 0, RESOLUTION_Y, -1, 3000));
        renderManager.shaderManager.hudShader.setUniformMat4f("viewMatrix", new Matrix4f());
        renderManager.shaderManager.hudShader.setUniformMat4f("modelMatrix", new Matrix4f());

        RenderUtils.renderString("Score: " + String.valueOf((int) level.getScore()), renderManager.COMIC_STRIP_MN_FONT, 30, renderManager.getDisplayHeight() - 40, 60, new Vector4f(0, 0, 0, 1));

        String text = "";
        switch (level.getKeyToPress()) {
            case 262:
                text = "DROITE";
                break;
            case 263:
                text = "GAUCHE";
                break;
            case 264:
                text = "BAS";
                break;
            case 265:
                text = "HAUT";
                break;
        }

        RenderUtils.renderString(text, renderManager.COMIC_STRIP_MN_FONT, 50, renderManager.getDisplayHeight() - 200, 60, new Vector4f(1, 1, 1, 1));

        String validKeyText = level.isLastKeyValid() ? "Bonne touche!" : "Mauvaise touche!";
        float alpha = MathUtils.lerp(1, 0, (float) ((System.currentTimeMillis() - level.getLastKeyPressedTime()) / 800D));

        if (level.getLastKeyPressedTime() == -1) {
            alpha = 0;
        }

        int textWidth = RenderUtils.getStringWidth(validKeyText, renderManager.COMIC_STRIP_MN_FONT, 60);
        int textHeight = RenderUtils.getStringHeight(validKeyText, renderManager.COMIC_STRIP_MN_FONT, 60);
        RenderUtils.renderString(validKeyText, renderManager.COMIC_STRIP_MN_FONT, (renderManager.getDisplayWidth() - textWidth) / 2, (renderManager.getDisplayHeight() - textHeight) / 2, 60, new Vector4f(1, 1, 1, alpha));

        renderManager.shaderManager.hudShader.unbind();

        renderCartoonCircle(level, partialTicks);

    }

}
