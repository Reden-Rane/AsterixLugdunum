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

    public static void renderTextureSprite(TextureSprite sprite, float x, float y, float z, float width, float height) {
        renderTextureSprite(sprite, x, y, z, width, height, new Vector4f(1, 1, 1, 1));
    }

    public static void renderTextureSprite(TextureSprite sprite, float x, float y, float z, float width, float height, Vector4f colorTint) {
        ShaderProgram shader = AsterixAndObelixGame.INSTANCE.getRenderManager().shaderManager.getBoundShaderProgram();

        if(shader == null) {
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
        for(char c : str.toCharArray()) {
            TextureSprite glyph = font.getCharSprite(c);
            if(glyph != null) {
                float ptRatio = pt / font.getFontSize();
                renderTextureSprite(glyph, x, y, 0, glyph.width * ptRatio, glyph.height * ptRatio, color);
                x += glyph.width * ptRatio;
            }
        }
    }

    public static void renderCartoonCircle(float progress) {
        RenderManager renderManager = AsterixAndObelixGame.INSTANCE.getRenderManager();
        renderManager.shaderManager.cartoonOpeningShader.bind();
        renderManager.shaderManager.cartoonOpeningShader.setUniformMat4f("projectionMatrix", new Matrix4f().ortho(0, renderManager.getDisplayWidth(), 0, renderManager.getDisplayHeight(), -1, 3000));
        renderManager.shaderManager.cartoonOpeningShader.setUniform2f("circleCenter", renderManager.getDisplayWidth() / 2, renderManager.getDisplayHeight() / 2);
        renderManager.shaderManager.cartoonOpeningShader.setUniform1f("circleRadius", Math.max(renderManager.getDisplayWidth(), renderManager.getDisplayHeight()) * 2 * progress);
        RenderUtils.renderRectangle(0, 0, 0, renderManager.getDisplayWidth(), renderManager.getDisplayHeight(), new Vector4f(0, 0, 0, 1));
        renderManager.shaderManager.cartoonOpeningShader.unbind();
    }

    public static int getStringWidth(String text, Font font, int textSize) {
        int width = 0;

        for(char c : text.toCharArray()) {
            TextureSprite glyph = font.getCharSprite(c);
            if(glyph != null) {
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
