package fr.info.game.graphics.renderer.level;

import fr.info.game.graphics.RenderManager;
import fr.info.game.graphics.RenderUtils;
import fr.info.game.logic.input.MousePositionCallback;
import fr.info.game.logic.level.bridge.CannonballLevel;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;

public class CannonballLevelRenderer extends LevelRenderer<CannonballLevel> {

    public CannonballLevelRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    protected void renderHud(CannonballLevel level, float partialTicks) {
        GL11.glClearColor(1, 1, 1, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        super.renderHud(level, partialTicks);
        renderManager.shaderManager.hudShader.bind();

        RenderUtils.renderRectangle((float) MousePositionCallback.getMouseX(), (float) MousePositionCallback.getMouseY(), 0, 50, 50, new Vector4f(0, 0, 0, 1));

        renderManager.shaderManager.hudShader.unbind();
    }
}
