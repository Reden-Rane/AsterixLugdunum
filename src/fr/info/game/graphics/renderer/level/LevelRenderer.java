package fr.info.game.graphics.renderer.level;

import fr.info.game.graphics.RenderUtils;
import fr.info.game.graphics.renderer.TileRenderer;
import fr.info.game.logic.entity.Entity;
import fr.info.game.logic.level.Camera;
import fr.info.game.logic.level.Level;
import fr.info.game.logic.math.MathUtils;
import fr.info.game.graphics.RenderMatrices;
import fr.info.game.graphics.RenderManager;
import fr.info.game.graphics.renderer.entity.EntityRenderer;
import fr.info.game.graphics.renderer.Renderer;
import fr.info.game.logic.tile.Tile;

import static fr.info.game.graphics.RenderMatrices.EnumMatrixMode.VIEW;

public abstract class LevelRenderer<L extends Level> extends Renderer<L> {

    public LevelRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(L level, float partialTicks) {
        updateView(level, partialTicks);
        RenderMatrices.setMatrixMode(RenderMatrices.EnumMatrixMode.MODEL);
        renderTerrain(level, partialTicks);
        renderEntities(level, partialTicks);
        renderHud(level, partialTicks);
        renderCartoonCircle(level, partialTicks);
    }

    protected void renderHud(L level, float partialTicks) {}

    protected void renderTerrain(L level, float partialTicks) {
        renderTiles(level, level.getTerrain(), partialTicks);
    }

    protected void renderTiles(Level level, Tile[][] tiles, float partialTicks) {
        if(tiles != null) {
            for (int x = 0; x < tiles.length; x++) {
                if(tiles[x] != null) {
                    for (int y = 0; y < tiles[x].length; y++) {
                        Tile tile = tiles[x][y];
                        if (tile != null) {
                            TileRenderer tileRenderer = (TileRenderer) renderManager.rendererRegistry.getRenderer(tiles[x][y]);

                            if(tileRenderer.shouldRender(tile, level, x * Tile.TILE_SIZE, y * Tile.TILE_SIZE, partialTicks)) {
                                tileRenderer.renderAt(tile, x * Tile.TILE_SIZE, y * Tile.TILE_SIZE, 0, partialTicks);
                            }
                        }
                    }
                }
            }
        }
    }

    protected void renderCartoonCircle(L level, float partialTicks) {
        float cartoonCircleRadius = MathUtils.lerp(level.getPrevCartoonCircleRadius(), level.getCartoonCircleRadius(), partialTicks);
        RenderUtils.renderCartoonCircleTransition(cartoonCircleRadius);
    }

    protected void renderEntities(L level, float partialTicks) {
        RenderMatrices.setMatrixMode(RenderMatrices.EnumMatrixMode.MODEL);

        for (Entity entity : level.getLoadedEntities()) {
            EntityRenderer<Entity> entityRenderer = (EntityRenderer<Entity>) renderManager.rendererRegistry.getRenderer(entity);
            if(entityRenderer != null && entityRenderer.shouldRender(entity, partialTicks)) {
                float x = entityRenderer.getRenderX(entity, partialTicks);
                float y = entityRenderer.getRenderY(entity, partialTicks);
                float z = entityRenderer.getRenderZ(entity, partialTicks);
                entityRenderer.renderAt(entity, x, y, z, partialTicks);
            }
        }
    }

    protected void updateView(L level, float partialTicks) {
        Camera camera = level.getCamera();
        float cameraOffsetX = MathUtils.lerp(camera.getPrevOffsetX(), camera.getOffsetX(), partialTicks) * Tile.TILE_SIZE;
        float cameraOffsetY = MathUtils.lerp(camera.getPrevOffsetY(), camera.getOffsetY(), partialTicks) * Tile.TILE_SIZE;
        float cameraZoom = MathUtils.lerp(camera.getPrevZoom(), camera.getZoom(), partialTicks);

        RenderMatrices.pushMatrix(VIEW);
        RenderMatrices.setMatrixMode(VIEW);
        RenderMatrices.scale(cameraZoom, cameraZoom, cameraZoom);

        double p = 50;
        /*
        We ceil the camera offset to avoid texture glitches due to float precision

        On arrondi à l'excès le décalage de la caméra pour éviter les glitchs de textures dûs à la précision des flottants
         */
        cameraOffsetX = (float) (Math.ceil(cameraOffsetX *  p) / p);
        cameraOffsetY = (float) (Math.ceil(cameraOffsetY * p) / p);

        RenderMatrices.translate(cameraOffsetX, cameraOffsetY, 0);
        renderManager.shaderManager.updateViewMatrixUniform();
        RenderMatrices.popMatrix(VIEW);
    }
}
