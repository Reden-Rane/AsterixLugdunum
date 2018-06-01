package fr.info.game.graphics;

import fr.info.game.AsterixAndObelixGame;
import fr.info.game.Font;
import fr.info.game.graphics.texture.TextureSprite;
import org.joml.Matrix4f;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class RenderUtils {

    public static String formatMilliseconds(long milliseconds) {
        float minutes = milliseconds / (60 * 1000F);
        float seconds = (minutes - (int) minutes) * 60;
        float ms = (seconds - (int) seconds) * 1000;
        // ()int) minutes + ":" + (int) seconds + ":" + (int) (ms / 10F) * 10
        return String.format("%02d:%02d:%03d", (int) minutes, (int) seconds, (int) ms);
    }

    public static void renderTextureSprite(TextureSprite sprite, float x, float y, float z, float width, float height) {
        renderTextureSprite(sprite, x, y, z, width, height, new Vector4f(1, 1, 1, 1));
    }

    public static void renderTextureSprite(TextureSprite sprite, float x, float y, float z, float width, float height, Vector4f colorTint) {
        ShaderProgram shader = AsterixAndObelixGame.INSTANCE.getRenderManager().shaderManager.getBoundShaderProgram();

        if (shader == null) {
            throw new IllegalStateException("No shader is bound.");
        }

        RenderMatrices.pushMatrix(RenderMatrices.EnumMatrixMode.MODEL);
        RenderMatrices.translate(x, y, z);
        RenderMatrices.scale(width, height, 1);

        shader.setUniformMat4f("modelMatrix", RenderMatrices.getModelMatrix());
        shader.setUniform2f("atlasSize", sprite.getParentAtlas().getWidth(), sprite.getParentAtlas().getHeight());
        shader.setUniform2f("spriteCoords", sprite.x, sprite.y);
        shader.setUniform2f("spriteSize", sprite.width, sprite.height);
        shader.setUniform4f("colorTint", colorTint);
        glEnable(GL_TEXTURE_2D);
        glActiveTexture(GL_TEXTURE0);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        sprite.getParentAtlas().bind();
        AsterixAndObelixGame.INSTANCE.getRenderManager().meshManager.texturedRectangle.render();

        RenderMatrices.popMatrix(RenderMatrices.EnumMatrixMode.MODEL);
    }

    public static void renderRectangle(float x, float y, float z, float width, float height, Vector4f color) {
        ShaderProgram shader = AsterixAndObelixGame.INSTANCE.getRenderManager().shaderManager.getBoundShaderProgram();
        RenderMatrices.pushMatrix(RenderMatrices.EnumMatrixMode.MODEL);
        RenderMatrices.translate(x, y, z);
        RenderMatrices.scale(width, height, 1);
        shader.setUniformMat4f("modelMatrix", RenderMatrices.getModelMatrix());
        shader.setUniform4f("colorTint", color);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        AsterixAndObelixGame.INSTANCE.getRenderManager().meshManager.rectangle.render();
        RenderMatrices.popMatrix(RenderMatrices.EnumMatrixMode.MODEL);
    }

    public static void renderString(String str, Font font, int x, int y, float pt, Vector4f color) {
        for (char c : str.toCharArray()) {
            TextureSprite glyph = font.getCharSprite(c);
            if (glyph != null) {
                float ptRatio = pt / font.getFontSize();
                renderTextureSprite(glyph, x, y, 0, glyph.width * ptRatio, glyph.height * ptRatio, color);
                x += glyph.width * ptRatio;
            }
        }
    }

    public static void renderCartoonCircleTransition(float progress) {
        renderTransition("cartoonCircle", progress);
    }

    public static void renderSpiralTransition(float progress) {
        renderTransition("spiral", progress);
    }

    public static void renderFightTransition(float progress) {
        renderTransition("fight", progress);
    }

    public static void renderTransition(String name, float progress) {
        RenderManager renderManager = AsterixAndObelixGame.INSTANCE.getRenderManager();
        renderManager.shaderManager.transitionShader.bind();
        renderManager.shaderManager.transitionShader.setUniform1f("progress", 1 - progress);
        renderManager.shaderManager.transitionShader.setUniformMat4f("viewMatrix", new Matrix4f());
        renderManager.shaderManager.transitionShader.setUniformMat4f("projectionMatrix", new Matrix4f().ortho(0, 1920, 0, 1013, 0, 3000));
        TextureSprite transitionTextureMap = renderManager.textureManager.transitionAtlas.getTextureSprite(name);
        if (transitionTextureMap != null) {
            RenderUtils.renderTextureSprite(transitionTextureMap, 0, 0, 0, renderManager.getDisplayWidth(), renderManager.getDisplayHeight());
        }
        renderManager.shaderManager.transitionShader.unbind();
    }

    public static int getStringWidth(String text, Font font, int textSize) {
        int width = 0;

        for (char c : text.toCharArray()) {
            TextureSprite glyph = font.getCharSprite(c);
            if (glyph != null) {
                float ptRatio = textSize / font.getFontSize();
                width += glyph.width * ptRatio;
            }
        }
        return width;
    }

    public static int getStringHeight(String text, Font font, int textSize) {
        TextureSprite glyph = font.getCharSprite('a');
        return (int) (glyph.height * textSize / font.getFontSize());
    }
}
