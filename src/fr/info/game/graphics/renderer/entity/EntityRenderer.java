package fr.info.game.graphics.renderer.entity;

import fr.info.game.logic.entity.Entity;
import fr.info.game.graphics.RenderManager;
import fr.info.game.graphics.RenderUtils;
import fr.info.game.graphics.renderer.Renderer;
import fr.info.game.graphics.texture.TextureSprite;
import fr.info.game.logic.math.MathUtils;

public abstract class EntityRenderer<E extends Entity> extends Renderer<E> {

    public EntityRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(E entity, float partialTicks) {
        TextureSprite entityTexture = getEntityTexture(entity);
        renderManager.shaderManager.spriteShader.bind();
        float x = MathUtils.lerp(entity.getPrevX(), entity.getX(), partialTicks);
        float y = MathUtils.lerp(entity.getPrevY(), entity.getY(), partialTicks);
        float z = MathUtils.lerp(entity.getPrevZ(), entity.getZ(), partialTicks);
        RenderUtils.renderTextureSprite(entityTexture, x, y, z, entity.getWidth(), entity.getHeight());
        renderManager.shaderManager.spriteShader.unbind();
    }

    public abstract TextureSprite getEntityTexture(E entity);

}
