package fr.info.game.graphics.renderer.level;

import fr.info.game.graphics.RenderManager;
import fr.info.game.graphics.RenderMatrices;
import fr.info.game.graphics.RenderUtils;
import fr.info.game.graphics.texture.TextureSprite;
import fr.info.game.logic.level.bridge.BridgeLevel;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL11.*;

public class BridgeRenderer extends LevelRenderer<BridgeLevel> {

    public BridgeRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    protected void renderTerrain(BridgeLevel level, float partialTicks) {

        RenderMatrices.setMatrixMode(RenderMatrices.EnumMatrixMode.MODEL);
        renderManager.shaderManager.spriteShader.bind();
        TextureSprite waterSprite = renderManager.textureManager.tileAtlas.getTextureSprite("water");

        int n = (int) Math.ceil(renderManager.getDisplayWidth() / (float) waterSprite.width);

        for (int x = 0; x < n; x++) {
            for (int y = 0; y < 2; y++) {
                RenderUtils.renderTextureSprite(waterSprite, waterSprite.width * x, y * waterSprite.height, 0, waterSprite.width, waterSprite.height, new Vector4f(1, 1, 1, 1));
            }
        }

        renderManager.shaderManager.spriteShader.unbind();
    }

    @Override
    public void render(BridgeLevel level, float partialTicks) {
        glClearColor(0.5F, 0.8F, 0.9F, 1);
        glClear(GL_COLOR_BUFFER_BIT);
        renderManager.shaderManager.spriteShader.bind();
        super.render(level, partialTicks);
        renderManager.shaderManager.spriteShader.unbind();
    }

}
