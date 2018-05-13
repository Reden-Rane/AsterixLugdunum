package fr.info.game.graphics.renderer;

import fr.info.game.graphics.RenderManager;
import fr.info.game.graphics.RenderUtils;
import fr.info.game.graphics.texture.TextureSprite;
import fr.info.game.logic.math.MathUtils;
import fr.info.game.logic.tile.Tile;

import static fr.info.game.logic.tile.Tile.TILE_SIZE;

public class TileRenderer extends Renderer<Tile> {

    public TileRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(Tile tile, float partialTicks) {
        TextureSprite tileSprite = renderManager.textureManager.tileAtlas.getTextureSprite(tile.tileSpriteName);

        if(tileSprite == null) {
            System.out.println("No tile sprite found for " + tile.tileSpriteName);
        } else {
            renderManager.shaderManager.spriteShader.bind();
            RenderUtils.renderTextureSprite(tileSprite, tile.x * TILE_SIZE, tile.y * TILE_SIZE, 0, TILE_SIZE, TILE_SIZE);
            renderManager.shaderManager.spriteShader.unbind();
        }
    }

    @Override
    public boolean shouldRender(Tile tile, float partialTicks) {
        float cameraOffsetX = MathUtils.lerp(tile.level.getCamera().getPrevOffsetX(), tile.level.getCamera().getOffsetX(), partialTicks);
        float cameraZoom = MathUtils.lerp(tile.level.getCamera().getPrevZoom(), tile.level.getCamera().getZoom(), partialTicks);
        float renderX = cameraZoom * tile.x * TILE_SIZE;
        float renderSize = cameraZoom * TILE_SIZE;
        return renderX + renderSize + cameraOffsetX * cameraZoom > 0 && renderX + renderSize + cameraOffsetX * cameraZoom < renderManager.getDisplayWidth() + TILE_SIZE * cameraZoom;
    }
}
