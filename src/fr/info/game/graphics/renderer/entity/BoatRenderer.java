package fr.info.game.graphics.renderer.entity;

import fr.info.game.graphics.RenderManager;
import fr.info.game.graphics.RenderMatrices;
import fr.info.game.graphics.RenderUtils;
import fr.info.game.graphics.texture.TextureSprite;
import fr.info.game.logic.entity.Boat;
import fr.info.game.logic.math.MathUtils;
import org.joml.Vector3f;

public class BoatRenderer extends EntityRenderer<Boat> {

    private final TextureSprite boatSprite = renderManager.textureManager.boatAtlas.getTextureSprite("boat");
    private final TextureSprite paddleSprite = renderManager.textureManager.boatAtlas.getTextureSprite("paddle");

    public BoatRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(Boat entity, float partialTicks) {
        TextureSprite boatTexture = getEntityTexture(entity);
        renderManager.shaderManager.spriteShader.bind();
        float x = MathUtils.lerp(entity.getPrevX(), entity.getX(), partialTicks);
        float y = MathUtils.lerp(entity.getPrevY(), entity.getY(), partialTicks);
        float z = MathUtils.lerp(entity.getPrevZ(), entity.getZ(), partialTicks);
        RenderUtils.renderTextureSprite(boatTexture, x, y, z, entity.getWidth(), entity.getHeight());

        for(int i = 0; i < 7; i++) {
            RenderMatrices.pushMatrix(RenderMatrices.EnumMatrixMode.MODEL);
            RenderMatrices.translate(x + paddleSprite.width + 18 * (i + 1), y + paddleSprite.height + 7, 0);

            float rotation = (float) ((-x / 10.0 * Math.PI) % (2 * Math.PI));

            RenderMatrices.rotate(rotation, new Vector3f(0, 0, 1));
            RenderMatrices.translate(-paddleSprite.width, -paddleSprite.height, 0);
            RenderUtils.renderTextureSprite(paddleSprite, 0, 0, z, paddleSprite.width, paddleSprite.height);
            RenderMatrices.popMatrix(RenderMatrices.EnumMatrixMode.MODEL);
        }

        renderManager.shaderManager.spriteShader.unbind();
    }

    @Override
    public TextureSprite getEntityTexture(Boat entity) {
        return boatSprite;
    }
}
