package fr.info.game.graphics.renderer.entity;

import fr.info.game.graphics.RenderManager;
import fr.info.game.graphics.RenderMatrices;
import fr.info.game.graphics.RenderUtils;
import fr.info.game.graphics.texture.TextureSprite;
import fr.info.game.logic.entity.Boat;
import fr.info.game.logic.math.MathUtils;
import fr.info.game.logic.tile.Tile;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class BoatRenderer extends EntityRenderer<Boat> {

    private final TextureSprite boatSprite = renderManager.textureManager.boatAtlas.getTextureSprite("boat");
    private final TextureSprite paddleSprite = renderManager.textureManager.boatAtlas.getTextureSprite("paddle");

    public BoatRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void renderAt(Boat boat, float x, float y, float z, float partialTicks) {

        if(RENDER_DEBUG_BOUNDING_BOXES) {
            super.renderBoundingBoxAt(boat, x, y, z);
        }

        TextureSprite boatTexture = getEntityTexture(boat);
        renderManager.shaderManager.spriteShader.bind();

        float p = MathUtils.clamp((boat.level.getLevelTicks() - boat.getLastHitTime()) / (float) Boat.HIT_FLICKER_PERIOD, 0, 1);
        float f = 1 - (float) (Math.sin((1-p) * Math.PI * 2 - Math.PI / 2) + 1) / 2F * 0.5F;
        Vector4f tint = new Vector4f(1, f, f, 1);

        RenderUtils.renderTextureSprite(boatTexture, x, y, z, boat.width * Tile.TILE_SIZE, boat.height * Tile.TILE_SIZE, tint);

        float paddleRotation = MathUtils.lerp(boat.getPrevPaddleRotation(), boat.paddleRotation, partialTicks);

        for(int i = 0; i < 7; i++) {
            RenderMatrices.pushMatrix(RenderMatrices.EnumMatrixMode.MODEL);
            RenderMatrices.translate(x + paddleSprite.width + 18 * (i + 1), y + paddleSprite.height + 7, 0);
            RenderMatrices.rotate(paddleRotation, new Vector3f(0, 0, 1));
            RenderMatrices.translate(-paddleSprite.width, -paddleSprite.height, 0);
            RenderUtils.renderTextureSprite(paddleSprite, 0, 0, z, paddleSprite.width, paddleSprite.height, tint);
            RenderMatrices.popMatrix(RenderMatrices.EnumMatrixMode.MODEL);
        }

        renderManager.shaderManager.spriteShader.unbind();
    }

    @Override
    public TextureSprite getEntityTexture(Boat entity) {
        return boatSprite;
    }
}
