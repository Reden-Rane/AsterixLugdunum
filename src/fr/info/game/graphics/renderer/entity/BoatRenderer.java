package fr.info.game.graphics.renderer.entity;

import fr.info.game.graphics.RenderManager;
import fr.info.game.graphics.texture.TextureSprite;
import fr.info.game.logic.entity.Boat;

public class BoatRenderer extends EntityRenderer<Boat> {

    private final TextureSprite boatSprite = renderManager.textureManager.boatAtlas.getTextureSprite("boat_0");

    public BoatRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public TextureSprite getEntityTexture(Boat entity) {
        return boatSprite;
    }
}
