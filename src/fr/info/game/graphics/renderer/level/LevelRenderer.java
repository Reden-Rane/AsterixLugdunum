package fr.info.game.graphics.renderer.level;

import fr.info.game.graphics.RenderUtils;
import fr.info.game.logic.entity.Entity;
import fr.info.game.logic.level.Camera;
import fr.info.game.logic.level.Level;
import fr.info.game.logic.math.MathUtils;
import fr.info.game.graphics.RenderMatrices;
import fr.info.game.graphics.RenderManager;
import fr.info.game.graphics.renderer.entity.EntityRenderer;
import fr.info.game.graphics.renderer.Renderer;

import static fr.info.game.graphics.RenderMatrices.EnumMatrixMode.VIEW;

public abstract class LevelRenderer<L extends Level> extends Renderer<L> {

    public LevelRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(L level, float partialTicks) {
        updateView(level, partialTicks);
        renderTerrain(level, partialTicks);
        renderEntities(level, partialTicks);
        renderCartoonCircle(level, partialTicks);
    }

    protected void renderTerrain(L level, float partialTicks) {

    }

    protected void renderCartoonCircle(L level, float partialTicks) {
        float cartoonCircleRadius = MathUtils.lerp(level.getPrevCartoonCircleRadius(), level.getCartoonCircleRadius(), partialTicks);
        RenderUtils.renderCartoonCircle(cartoonCircleRadius);
    }

    protected void renderEntities(L level, float partialTicks) {
        RenderMatrices.setMatrixMode(RenderMatrices.EnumMatrixMode.MODEL);

        for (Entity entity : level.getLoadedEntities()) {
            EntityRenderer entityRenderer = (EntityRenderer) renderManager.rendererRegistry.getRenderer(entity);
            entityRenderer.render(entity, partialTicks);
        }
    }

    protected void updateView(L level, float partialTicks) {
        Camera camera = level.getCamera();
        float cameraOffsetX = MathUtils.lerp(camera.getPrevOffsetX(), camera.getOffsetX(), partialTicks);
        float cameraOffsetY = MathUtils.lerp(camera.getPrevOffsetY(), camera.getOffsetY(), partialTicks);
        float cameraOffsetZ = MathUtils.lerp(camera.getPrevOffsetZ(), camera.getZoom(), partialTicks);

        RenderMatrices.pushMatrix(VIEW);
        RenderMatrices.setMatrixMode(VIEW);
        RenderMatrices.scale(cameraOffsetZ, cameraOffsetZ, cameraOffsetZ);
        RenderMatrices.translate(cameraOffsetX, cameraOffsetY, 0);
        renderManager.shaderManager.updateViewMatrixUniform();
        RenderMatrices.popMatrix(VIEW);
    }
}
