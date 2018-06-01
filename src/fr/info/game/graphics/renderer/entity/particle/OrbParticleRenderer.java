package fr.info.game.graphics.renderer.entity.particle;

import fr.info.game.graphics.RenderManager;
import fr.info.game.graphics.RenderUtils;
import fr.info.game.graphics.renderer.entity.EntityRenderer;
import fr.info.game.graphics.texture.TextureSprite;
import fr.info.game.logic.entity.particle.OrbParticle;
import fr.info.game.logic.tile.Tile;
import org.joml.Vector4f;

public class OrbParticleRenderer extends EntityRenderer<OrbParticle> {

    public OrbParticleRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void renderAt(OrbParticle orb, float x, float y, float z, float partialTicks) {
        TextureSprite entityTexture = getEntityTexture(orb);
        renderManager.shaderManager.spriteShader.bind();

        float p = 1;

        if(orb.getExistingTicks() >= orb.lifetime - orb.fadeOutDuration) {
            p = (orb.lifetime - orb.getExistingTicks()) / (float) orb.fadeOutDuration;
        } else if(orb.getExistingTicks() <= orb.fadeInDuration) {
            p = orb.getExistingTicks() / (float) orb.fadeInDuration;
        }

        RenderUtils.renderTextureSprite(entityTexture, x, y, z, orb.width * Tile.TILE_SIZE, orb.height * Tile.TILE_SIZE, new Vector4f(1, 1, 1, p));
        renderManager.shaderManager.spriteShader.unbind();
    }

    @Override
    public TextureSprite getEntityTexture(OrbParticle orb) {
        return renderManager.textureManager.particlesAtlas.getTextureSprite("orb" + orb.variant);
    }
}
