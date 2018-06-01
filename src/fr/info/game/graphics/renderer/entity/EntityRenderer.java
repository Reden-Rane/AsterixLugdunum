package fr.info.game.graphics.renderer.entity;

import fr.info.game.logic.entity.Entity;
import fr.info.game.graphics.RenderManager;
import fr.info.game.graphics.RenderUtils;
import fr.info.game.graphics.renderer.Renderer;
import fr.info.game.graphics.texture.TextureSprite;
import fr.info.game.logic.math.MathUtils;
import fr.info.game.logic.tile.Tile;
import org.joml.Matrix4f;
import org.joml.Vector4f;

public class EntityRenderer<E extends Entity> extends Renderer<E> {

    public static final boolean RENDER_DEBUG_BOUNDING_BOXES = false;

    public EntityRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void renderAt(E entity, float x, float y, float z, float partialTicks) {
        TextureSprite entityTexture = getEntityTexture(entity);

        if(RENDER_DEBUG_BOUNDING_BOXES) {
            renderBoundingBoxAt(entity, x, y, z);
        }

        if(entityTexture != null) {
            renderManager.shaderManager.spriteShader.bind();
            RenderUtils.renderTextureSprite(entityTexture, x, y, z, entity.width * Tile.TILE_SIZE, entity.height * Tile.TILE_SIZE);
            renderManager.shaderManager.spriteShader.unbind();
        } else {
            renderRenderBoxAt(entity, x, y, z);
            renderBoundingBoxAt(entity, x, y, z);
        }
    }

    protected void renderRenderBoxAt(E entity, float x, float y, float z) {
        renderManager.shaderManager.defaultShader.bind();
        renderManager.shaderManager.defaultShader.setUniformMat4f("modelMatrix", new Matrix4f());
        RenderUtils.renderRectangle(x + entity.getCollisionBox().x * Tile.TILE_SIZE, y + entity.width * Tile.TILE_SIZE, z, entity.height * Tile.TILE_SIZE, entity.getCollisionBox().height * Tile.TILE_SIZE, new Vector4f(0, 0.1F, 0.8F, 1));
        renderManager.shaderManager.defaultShader.unbind();
    }

    protected void renderBoundingBoxAt(E entity, float x, float y, float z) {
        renderManager.shaderManager.defaultShader.bind();
        renderManager.shaderManager.defaultShader.setUniformMat4f("modelMatrix", new Matrix4f());
        RenderUtils.renderRectangle(x + entity.getCollisionBox().x * Tile.TILE_SIZE, y + entity.getCollisionBox().y * Tile.TILE_SIZE, z, entity.getCollisionBox().width * Tile.TILE_SIZE, entity.getCollisionBox().height * Tile.TILE_SIZE, new Vector4f(1, 0, 0, 1));
        renderManager.shaderManager.defaultShader.unbind();
    }

    public TextureSprite getEntityTexture(E entity) {
        return null;
    }

    public float getRenderX(E entity, float partialTicks) {
        return MathUtils.lerp(entity.getPrevX(), entity.x, partialTicks) * Tile.TILE_SIZE;
    }

    public float getRenderY(E entity, float partialTicks) {
        return MathUtils.lerp(entity.getPrevY(), entity.y, partialTicks) * Tile.TILE_SIZE;
    }

    public float getRenderZ(E entity, float partialTicks) {
        return MathUtils.lerp(entity.getPrevZ(), entity.z, partialTicks) * Tile.TILE_SIZE;
    }

    public boolean shouldRender(Entity entity, float partialTicks) {
        return true;//TODO
    }

}
