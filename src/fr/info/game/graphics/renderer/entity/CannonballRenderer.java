package fr.info.game.graphics.renderer.entity;

import fr.info.game.graphics.RenderManager;
import fr.info.game.graphics.texture.TextureSprite;
import fr.info.game.logic.entity.Cannonball;

public class CannonballRenderer extends EntityRenderer<Cannonball> {

    public CannonballRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public TextureSprite getEntityTexture(Cannonball entity) {
        return renderManager.textureManager.cannonballAtlas.getTextureSprite("cannonball0");
    }
}
