package fr.info.game.graphics;

import fr.info.game.AsterixAndObelixGame;
import fr.info.game.Font;
import fr.info.game.assets.TextureResource;
import fr.info.game.exception.render.TextureLoadingException;
import fr.info.game.logic.level.Level;
import fr.info.game.graphics.cursor.CursorManager;
import fr.info.game.graphics.renderer.level.LevelRenderer;
import fr.info.game.graphics.renderer.RendererRegistry;
import fr.info.game.graphics.texture.TextureManager;
import org.joml.Matrix4f;

import java.io.IOException;

import static fr.info.game.AsterixAndObelixGame.RESOLUTION_X;
import static fr.info.game.AsterixAndObelixGame.RESOLUTION_Y;
import static org.lwjgl.opengl.GL11.*;

public class RenderManager {

    public final MeshManager meshManager = new MeshManager();
    public final TextureManager textureManager = new TextureManager();
    public final ShaderManager shaderManager = new ShaderManager();
    public final CursorManager cursorManager = new CursorManager();

    public final RendererRegistry rendererRegistry = new RendererRegistry(this);

    public final Font COMIC_STRIP_MN_FONT = new Font(this, new TextureResource("font/comic_strip_mn_atlas.json"), 64);

    public RenderManager() throws IOException, TextureLoadingException {
        RenderMatrices.init(this);
        this.cursorManager.normalCursor.use();
        System.out.println("Initialized graphics manager.");
    }

    public void render(Level level, float partialTicks) {
        glClearColor(0, 0, 0, 1);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        this.shaderManager.updateTextureUnitUniform();
        RenderMatrices.updateMatrix(RenderMatrices.getViewMatrix(), new Matrix4f());
        RenderMatrices.updateMatrix(RenderMatrices.getModelMatrix(), new Matrix4f());
        this.shaderManager.updateViewMatrixUniform();
        RenderMatrices.setMatrixMode(RenderMatrices.EnumMatrixMode.MODEL);
        LevelRenderer<Level> levelRenderer = (LevelRenderer<Level>) this.rendererRegistry.getRenderer(level);
        if (levelRenderer != null) {
            levelRenderer.render(level, partialTicks);
        }
    }

    public void updateProjectionMatrix() {
        RenderMatrices.updateMatrix(RenderMatrices.getProjectionMatrix(), new Matrix4f().ortho(0, RESOLUTION_X, 0, RESOLUTION_Y, -1, 3000));
        this.shaderManager.updateProjectionMatrixUniform();
    }

    public void destroy() {
        this.meshManager.destroy();
        this.textureManager.destroy();
        this.shaderManager.destroy();
        this.cursorManager.destroy();
        this.COMIC_STRIP_MN_FONT.destroy();
    }

    public int getDisplayWidth() {
        return AsterixAndObelixGame.INSTANCE.getDisplay().getWidth();
    }

    public int getDisplayHeight() {
        return AsterixAndObelixGame.INSTANCE.getDisplay().getHeight();
    }
}
