package fr.info.game.graphics.renderer.entity.particle;

import fr.info.game.graphics.RenderManager;
import fr.info.game.graphics.renderer.entity.EntityRenderer;
import fr.info.game.graphics.texture.SpriteAnimation;
import fr.info.game.graphics.texture.TextureSprite;
import fr.info.game.logic.entity.particle.SplashParticle;

public class SplashParticleRenderer extends EntityRenderer<SplashParticle> {

    public SplashParticleRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public TextureSprite getEntityTexture(SplashParticle entity) {
        SpriteAnimation splashAnimation = renderManager.textureManager.particlesAtlas.getSpriteAnimation("splash");
        return splashAnimation.getFrameAt((entity.getExistingTicks() / (float) entity.lifetime * splashAnimation.duration) % splashAnimation.duration);
    }
}
