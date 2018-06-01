package fr.info.game.graphics.renderer;

import fr.info.game.graphics.RenderManager;
import fr.info.game.graphics.RenderUtils;
import fr.info.game.graphics.texture.TextureSprite;
import fr.info.game.logic.level.Level;
import fr.info.game.logic.math.MathUtils;
import fr.info.game.logic.tile.Tile;

import static fr.info.game.logic.tile.Tile.TILE_SIZE;

public class TileRenderer extends Renderer<Tile> {

    public TileRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void renderAt(Tile tile, float x, float y, float z, float partialTicks) {
        TextureSprite tileSprite = renderManager.textureManager.tileAtlas.getTextureSprite(tile.getTextureName());

        if(tileSprite == null) {
            System.out.println("No tile sprite found for " + tile.getTextureName());
        } else {
            renderManager.shaderManager.spriteShader.bind();
            RenderUtils.renderTextureSprite(tileSprite, x, y, z, TILE_SIZE, TILE_SIZE);
            renderManager.shaderManager.spriteShader.unbind();
        }
    }

    public boolean shouldRender(Tile tile, Level level, float x, float y, float partialTicks) {
        float cameraOffsetX = MathUtils.lerp(level.getCamera().getPrevOffsetX(), level.getCamera().getOffsetX(), partialTicks) * Tile.TILE_SIZE;
        float cameraZoom = MathUtils.lerp(level.getCamera().getPrevZoom(), level.getCamera().getZoom(), partialTicks);
        float renderX = cameraZoom * x;
        float renderSize = cameraZoom * TILE_SIZE;
        return renderX + renderSize + cameraOffsetX * cameraZoom > 0 && renderX + renderSize + cameraOffsetX * cameraZoom < renderManager.getDisplayWidth() + TILE_SIZE * cameraZoom;
    }
}
