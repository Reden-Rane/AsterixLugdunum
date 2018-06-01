package fr.info.game.graphics.renderer.level;

import fr.info.game.graphics.RenderManager;
import fr.info.game.graphics.RenderUtils;
import fr.info.game.graphics.texture.TextureSprite;
import fr.info.game.logic.level.bridge.BridgeLevel;
import fr.info.game.logic.math.MathUtils;
import fr.info.game.logic.tile.Tile;
import org.joml.Matrix4f;
import org.joml.Vector4f;

import static fr.info.game.AsterixAndObelixGame.RESOLUTION_X;
import static fr.info.game.AsterixAndObelixGame.RESOLUTION_Y;
import static fr.info.game.logic.tile.Tile.TILE_SIZE;
import static org.lwjgl.glfw.GLFW.glfwGetKeyName;
import static org.lwjgl.opengl.GL11.*;

public class BridgeRenderer extends LevelRenderer<BridgeLevel> {

    public BridgeRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    protected void renderTerrain(BridgeLevel level, float partialTicks) {

        super.renderTerrain(level, partialTicks);

        float cameraOffsetX = MathUtils.lerp(level.getCamera().getPrevOffsetX(), level.getCamera().getOffsetX(), partialTicks) * Tile.TILE_SIZE;

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
        super.render(level, partialTicks);
    }

    @Override
    protected void renderHud(BridgeLevel level, float partialTicks) {
        super.renderHud(level, partialTicks);

        renderManager.shaderManager.hudShader.bind();
        renderManager.shaderManager.hudShader.setUniformMat4f("projectionMatrix", new Matrix4f().ortho(0, RESOLUTION_X, 0, RESOLUTION_Y, -1, 3000));
        renderManager.shaderManager.hudShader.setUniformMat4f("viewMatrix", new Matrix4f());
        renderManager.shaderManager.hudShader.setUniformMat4f("modelMatrix", new Matrix4f());

        renderProgressBar(level);

        if(level.hasLevelStarted()) {
            if(level.getDuration() >= 0) {
                renderTimer(level);
                renderKeySequence(level);
            } else {
                int countdown = (int) ((-level.getDuration() / 1000) + 1);
                String countdownString = String.valueOf(countdown);
                int countdownTextSize = 60;
                int w = RenderUtils.getStringWidth(countdownString, renderManager.OBELIX_PRO_FONT, countdownTextSize);
                int h = RenderUtils.getStringHeight(countdownString, renderManager.OBELIX_PRO_FONT, countdownTextSize);
                RenderUtils.renderString(countdownString, renderManager.OBELIX_PRO_FONT, (renderManager.getDisplayWidth() - w) / 2 + 5, (renderManager.getDisplayHeight() - h) / 2 - 15 + 210, countdownTextSize, new Vector4f(0, 0, 0, 0.4F));
                RenderUtils.renderString(countdownString, renderManager.OBELIX_PRO_FONT, (renderManager.getDisplayWidth() - w) / 2, (renderManager.getDisplayHeight() - h) / 2 + 200, countdownTextSize, new Vector4f(255 / 255F, 179 / 255F, 26 / 255F, 1));
            }
        }

        if(!level.hasLevelStarted()) {
            String finishString = "Press enter to start!";
            int fontSize = 50;
            int finishStringWidth = RenderUtils.getStringWidth(finishString, renderManager.OBELIX_PRO_FONT, fontSize);
            int finishStringHeight = RenderUtils.getStringHeight(finishString, renderManager.OBELIX_PRO_FONT, fontSize);
            RenderUtils.renderString(finishString, renderManager.OBELIX_PRO_FONT, (renderManager.getDisplayWidth() - finishStringWidth) / 2 + 5, (renderManager.getDisplayHeight() - finishStringHeight) / 2 - 15 + 210, fontSize, new Vector4f(0, 0, 0, 0.4F));
            RenderUtils.renderString(finishString, renderManager.OBELIX_PRO_FONT, (renderManager.getDisplayWidth() - finishStringWidth) / 2, (renderManager.getDisplayHeight() - finishStringHeight) / 2 + 200, fontSize, new Vector4f(255 / 255F, 179 / 255F, 26 / 255F, 1));
        }

        if(level.isLevelFinished()) {

            String finishString = "Finish!";
            double p = 1 + (Math.sin((System.currentTimeMillis() - level.getFinishTime()) / 1000F * Math.PI * 2) + 1) / 2F * 0.5F;
            int fontSize = (int) (100 * p);
            int finishStringWidth = RenderUtils.getStringWidth(finishString, renderManager.OBELIX_PRO_FONT, fontSize);
            int finishStringHeight = RenderUtils.getStringHeight(finishString, renderManager.OBELIX_PRO_FONT, fontSize);
            RenderUtils.renderString(finishString, renderManager.OBELIX_PRO_FONT, (renderManager.getDisplayWidth() - finishStringWidth) / 2 + 15, (renderManager.getDisplayHeight() - finishStringHeight) / 2 - 15 + 200, fontSize, new Vector4f(0, 0, 0, 0.5F));
            RenderUtils.renderString(finishString, renderManager.OBELIX_PRO_FONT, (renderManager.getDisplayWidth() - finishStringWidth) / 2, (renderManager.getDisplayHeight() - finishStringHeight) / 2 + 200, fontSize, new Vector4f(255 / 255F, 179 / 255F, 26 / 255F, 1));

            if(level.hasNewRecord()) {
                String newRecordString = "New Record!";
                int newRecordFontSize = 60;
                int newRecordStringWidth = RenderUtils.getStringWidth(newRecordString, renderManager.OBELIX_PRO_FONT, newRecordFontSize);
                int newRecordStringHeight = RenderUtils.getStringHeight(newRecordString, renderManager.OBELIX_PRO_FONT, newRecordFontSize);
                RenderUtils.renderString(newRecordString, renderManager.OBELIX_PRO_FONT, (renderManager.getDisplayWidth() - newRecordStringWidth) / 2 + 5, (renderManager.getDisplayHeight() - newRecordStringHeight) / 2 - 90 + 120, newRecordFontSize, new Vector4f(4 / 255F, 55 / 255F, 55 / 255F, 0.9F));
                RenderUtils.renderString(newRecordString, renderManager.OBELIX_PRO_FONT, (renderManager.getDisplayWidth() - newRecordStringWidth) / 2, (renderManager.getDisplayHeight() - newRecordStringHeight) / 2 - 85 + 120, newRecordFontSize, new Vector4f(104 / 255F, 155 / 255F, 155 / 255F, 1));
            }
        }

        renderManager.shaderManager.hudShader.unbind();
    }

    private void renderKeySequence(BridgeLevel level) {
        TextureSprite keyButtonSprite = renderManager.textureManager.guiAtlas.getTextureSprite("keyButton");

        for(int i = 0; i < level.getKeySequence().size(); i++) {
            int key = level.getKeySequence().get(i);

            String keyName = glfwGetKeyName(key, 0);

            if(keyName == null) {
                keyName = "";
            }

            int buttonX;
            int buttonY;
            int buttonWidth;
            int buttonHeight;
            float alpha;

            if(i == 0) {
                buttonWidth = keyButtonSprite.width * 2;
                buttonHeight = keyButtonSprite.height * 2;
                buttonX = (renderManager.getDisplayWidth() - buttonWidth) / 2;
                buttonY = renderManager.getDisplayHeight() - buttonHeight * 2;

                long dt = System.currentTimeMillis() - level.getLastKeyPressedTime();
                float p = MathUtils.clamp(dt / 400F, 0, 1);

                if(!level.wasLastKeyValid()) {
                    buttonX += Math.sin(p * Math.PI * 5) * 10;
                } else {
                    buttonWidth += Math.sin(p * Math.PI * 2) * 10;
                    buttonHeight += Math.sin(p * Math.PI * 2) * 10;
                    buttonX += -Math.sin(p * Math.PI * 2) * 5;
                    buttonY += Math.sin(p * Math.PI * 2) * 5;
                }

                alpha = 1;
            } else {
                float p = MathUtils.clamp((level.getInitialKeySequenceSize() - i) / (float) level.getInitialKeySequenceSize(), 0, 1);
                buttonWidth = (int) (keyButtonSprite.width * 2 * p);
                buttonHeight = (int) (keyButtonSprite.height * 2 * p);

                int prevWidth = 0;

                for(int j = 0; j < i; j++) {
                    float p1 = MathUtils.clamp((level.getInitialKeySequenceSize() - j) / (float) level.getInitialKeySequenceSize(), 0, 1);
                    prevWidth += (int) (keyButtonSprite.width * 2 * p1);
                }

                buttonX = (renderManager.getDisplayWidth() - buttonWidth) / 2 + prevWidth;
                buttonY = renderManager.getDisplayHeight() - 96 * 2;
                alpha = p;
            }

            renderKeyButton(keyName, buttonX, buttonY, buttonWidth, buttonHeight, alpha, i == 0);
        }
    }

    private void renderTimer(BridgeLevel level) {
        long duration = level.getDuration();

        if (level.isLevelFinished()) {
            duration = level.getFinishTime() - level.getStartTime() - 3000;
        }

        String timerString = RenderUtils.formatMilliseconds(duration);
        RenderUtils.renderString(timerString, renderManager.COMIC_STRIP_MN_FONT, 34, renderManager.getDisplayHeight() - 44, 60, new Vector4f(151 / 255F, 131 / 255F, 83 / 255F, 1));
        RenderUtils.renderString(timerString, renderManager.COMIC_STRIP_MN_FONT, 30, renderManager.getDisplayHeight() - 40, 60, new Vector4f(211 / 255F, 191 / 255F, 143 / 255F, 1));

        if(BridgeLevel.hasBestTime()) {
            String bestTimeString = RenderUtils.formatMilliseconds(BridgeLevel.getBestTime());
            RenderUtils.renderString(bestTimeString, renderManager.COMIC_STRIP_MN_FONT, 30, renderManager.getDisplayHeight() - 80, 30, new Vector4f(1, 1, 1, 1));
        }
    }

    private void renderProgressBar(BridgeLevel level) {
        TextureSprite progressLeftSprite = renderManager.textureManager.guiAtlas.getTextureSprite("progressLeft");
        TextureSprite progressMiddleSprite = renderManager.textureManager.guiAtlas.getTextureSprite("progressMiddle");
        TextureSprite progressRightSprite = renderManager.textureManager.guiAtlas.getTextureSprite("progressRight");
        TextureSprite flagSprite = renderManager.textureManager.guiAtlas.getTextureSprite("flag");

        TextureSprite asterixHeadSprite = renderManager.textureManager.guiAtlas.getTextureSprite("asterixHead");

        int progressBarWidth = 500;
        int progressBarHeight = progressMiddleSprite.height;
        int progressBarX = (renderManager.getDisplayWidth() - progressBarWidth) / 2;
        int progressBarY = renderManager.getDisplayHeight() - progressBarHeight * 2;

        RenderUtils.renderTextureSprite(progressLeftSprite, progressBarX, progressBarY, 0, progressLeftSprite.width, progressLeftSprite.height);
        RenderUtils.renderTextureSprite(progressMiddleSprite, progressBarX + progressLeftSprite.width, progressBarY, 0, progressBarWidth - (progressLeftSprite.width + progressRightSprite.width), progressMiddleSprite.height);
        RenderUtils.renderTextureSprite(progressRightSprite, progressBarX + progressBarWidth - progressRightSprite.width, progressBarY, 0, progressRightSprite.width, progressRightSprite.height);

        RenderUtils.renderTextureSprite(asterixHeadSprite, progressBarX + progressBarWidth * level.getPlayerProgress() - asterixHeadSprite.width, progressBarY, 0, asterixHeadSprite.width * 2, asterixHeadSprite.height * 2);
        RenderUtils.renderTextureSprite(flagSprite, progressBarX + progressBarWidth, progressBarY + 5, 0, flagSprite.width, flagSprite.height);
    }

    private void renderKeyButton(String keyName, int x, int y, int width, int height, float alpha, boolean dropShadow) {
        TextureSprite keyButtonSprite = renderManager.textureManager.guiAtlas.getTextureSprite("keyButton");
        RenderUtils.renderTextureSprite(keyButtonSprite, x, y, 0, width, height, new Vector4f(1, 1, 1, alpha));
        int textSize = (int) (50 * (width / (float) (keyButtonSprite.width * 2)));
        int textX = x + (width - RenderUtils.getStringWidth(keyName, renderManager.OBELIX_PRO_FONT, textSize)) / 2;
        int textY = y + (height - RenderUtils.getStringHeight(keyName, renderManager.OBELIX_PRO_FONT, textSize)) / 2 + 10;

        if(!dropShadow) {
            textY -= 5;
        }

        if(dropShadow) {
            RenderUtils.renderString(keyName, renderManager.OBELIX_PRO_FONT, textX, textY - 5, textSize, new Vector4f(157 / 255F, 140 / 255F, 100 / 255F, alpha));
        }
        RenderUtils.renderString(keyName, renderManager.OBELIX_PRO_FONT, textX, textY, textSize, new Vector4f(177 / 255F, 160 / 255F, 120 / 255F, alpha));
    }
}
