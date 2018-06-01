package fr.info.game.graphics.renderer.entity;

import fr.info.game.graphics.RenderManager;
import fr.info.game.graphics.texture.TextureSprite;
import fr.info.game.logic.entity.EntityItem;

public class ItemRenderer extends EntityRenderer<EntityItem> {

    public ItemRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public TextureSprite getEntityTexture(EntityItem entityItem) {
        return renderManager.textureManager.itemAtlas.getTextureSprite(entityItem.item.getTextureName());
    }
}
