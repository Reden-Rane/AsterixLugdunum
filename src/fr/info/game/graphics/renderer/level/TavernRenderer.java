package fr.info.game.graphics.renderer.level;

import fr.info.game.graphics.RenderManager;
import fr.info.game.graphics.RenderUtils;
import fr.info.game.logic.level.tavern.TavernLevel;
import fr.info.game.logic.tile.Tile;
import org.joml.Matrix4f;
import org.joml.Vector4f;

import static fr.info.game.AsterixAndObelixGame.RESOLUTION_X;
import static fr.info.game.AsterixAndObelixGame.RESOLUTION_Y;
import static org.lwjgl.opengl.GL11.*;

public class TavernRenderer extends LevelRenderer<TavernLevel> {

    public TavernRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    protected void renderTerrain(TavernLevel level, float partialTicks) {
        renderTiles(level, level.floor, partialTicks);
        super.renderTerrain(level, partialTicks);
    }

    @Override
    protected void renderHud(TavernLevel level, float partialTicks) {
        super.renderHud(level, partialTicks);
        renderManager.shaderManager.hudShader.bind();
        renderManager.shaderManager.hudShader.setUniformMat4f("projectionMatrix", new Matrix4f().ortho(0, RESOLUTION_X, 0, RESOLUTION_Y, -1, 3000));
        renderManager.shaderManager.hudShader.setUniformMat4f("viewMatrix", new Matrix4f());
        renderManager.shaderManager.hudShader.setUniformMat4f("modelMatrix", new Matrix4f());
        RenderUtils.renderString("Score: " + String.valueOf(level.getScore()), renderManager.COMIC_STRIP_MN_FONT, 30, renderManager.getDisplayHeight() - 40, 60, new Vector4f(1, 1, 1, 1));

        RenderUtils.renderString("Work In Progress", renderManager.COMIC_STRIP_MN_FONT, 30, renderManager.getDisplayHeight() - 110, 60, new Vector4f(1, 0, 0, 1));

        renderManager.shaderManager.hudShader.unbind();
    }

    @Override
    public void render(TavernLevel level, float partialTicks) {
        level.getCamera().setOffsetX(renderManager.getDisplayWidth() / (Tile.TILE_SIZE * 2) - level.getMapWidth() / 2);
        level.getCamera().setOffsetY(renderManager.getDisplayHeight() / (Tile.TILE_SIZE * 2) - level.getMapHeight() / 2 + 1.5F);
        glClearColor(0, 0, 0, 1);
        glClear(GL_COLOR_BUFFER_BIT);
        super.render(level, partialTicks);
    }
}
