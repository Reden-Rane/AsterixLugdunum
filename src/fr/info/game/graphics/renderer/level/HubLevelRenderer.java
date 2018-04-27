package fr.info.game.graphics.renderer.level;

import fr.info.game.graphics.RenderManager;
import fr.info.game.graphics.RenderMatrices;
import fr.info.game.graphics.RenderUtils;
import fr.info.game.graphics.texture.TextureSprite;
import fr.info.game.logic.level.hub.HubLevel;
import fr.info.game.logic.level.hub.HubLevelMarkerNode;
import org.joml.Vector4f;

public class HubLevelRenderer extends LevelRenderer<HubLevel> {

    public HubLevelRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(HubLevel level, float partialTicks) {
        TextureSprite mapSprite = renderManager.textureManager.hubMapAtlas.getTextureSprite("map0");
        updateView(level, partialTicks);
        RenderMatrices.setMatrixMode(RenderMatrices.EnumMatrixMode.MODEL);
        renderManager.shaderManager.spriteShader.bind();
        RenderUtils.renderTextureSprite(mapSprite, 0, 0, 0, level.mapWidth, level.mapHeight);
        renderManager.shaderManager.spriteShader.unbind();

        renderLevelMarkers(level, partialTicks);

        TextureSprite menhirSprite = renderManager.textureManager.guiAtlas.getTextureSprite("menhir");

        renderManager.shaderManager.spriteShader.bind();
        RenderUtils.renderTextureSprite(menhirSprite, - 35, renderManager.getDisplayHeight() / 3 - menhirSprite.height / 2, 0, menhirSprite.width / 2, menhirSprite.height / 2, new Vector4f(1, 1, 1, 1));
        RenderUtils.renderString("Lugdunum", renderManager.COMIC_STRIP_MN_FONT, 15, renderManager.getDisplayHeight() / 3 - menhirSprite.height / 2 + 17, 14, new Vector4f(0, 0, 0, 1));
        RenderUtils.renderString("Lugdunum", renderManager.COMIC_STRIP_MN_FONT, 14, renderManager.getDisplayHeight() / 3 - menhirSprite.height / 2 + 18, 14, new Vector4f(1, 1, 1, 1));
        renderManager.shaderManager.spriteShader.unbind();

        super.render(level, partialTicks);
    }

    private void renderLevelMarkers(HubLevel level, float partialTicks) {
        TextureSprite woodLogTexture = renderManager.textureManager.tileAtlas.getTextureSprite("log");

        renderManager.shaderManager.spriteShader.bind();

        TextureSprite titleBox = renderManager.textureManager.guiAtlas.getTextureSprite("brownBox");

        for(HubLevelMarkerNode marker : level.hubPath.getLevelMarkers()) {
            int fontSize = 9;
            int strWidth = RenderUtils.getStringWidth(marker.level.levelName, renderManager.COMIC_STRIP_MN_FONT, fontSize);

            int markerOffsetX = 17;
            int markerOffsetY = -15;

            float ratio = titleBox.width / strWidth;

            int padding = 3;
            int boxWidth = (int) (titleBox.width * 1F / ratio) + padding;
            int boxHeight = (int) (titleBox.height * 1F / ratio) + padding;

            int boxX = (int) (marker.x + markerOffsetX - boxWidth / 2F);
            int boxY = (int) (marker.y - boxHeight / 2F + markerOffsetY);

            RenderUtils.renderTextureSprite(titleBox, boxX, boxY, 0, boxWidth, boxHeight, new Vector4f(1, 1, 1, 1));
            RenderUtils.renderString(marker.level.levelName, renderManager.COMIC_STRIP_MN_FONT, marker.x + markerOffsetX - strWidth / 2, marker.y + markerOffsetY - 5, fontSize, new Vector4f(0.4F, 0, 0, 1));
            RenderUtils.renderTextureSprite(woodLogTexture, marker.x + 10, marker.y - 8, 0, 16, 16);
        }
        renderManager.shaderManager.spriteShader.unbind();
    }
}
