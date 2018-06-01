package fr.info.game.graphics;

import fr.info.game.assets.ShaderResource;

import java.util.ArrayList;
import java.util.List;

public class ShaderManager {

    private final List<ShaderProgram> loadedShaders = new ArrayList<>();

    public final ShaderProgram defaultShader;
    public final ShaderProgram spriteShader;
    public final ShaderProgram hudShader;

    public final ShaderProgram transitionShader;

    private ShaderProgram boundShaderProgram;

    public ShaderManager() {
        System.out.println("Loading shaders...");

        this.defaultShader = loadShader("default.vert", "default.frag");
        this.spriteShader = loadShader("sprite.vert", "sprite.frag");
        this.hudShader = loadShader("hud.vert", "hud.frag");
        this.transitionShader = loadShader("transition.vert", "transition.frag");
    }

    public ShaderProgram loadShader(String vertex, String fragment) {
        ShaderProgram shader = new ShaderProgram(this, new ShaderResource(vertex), new ShaderResource(fragment));
        loadedShaders.add(shader);
        return shader;
    }

    public void destroy() {
        for (ShaderProgram shader : loadedShaders) {
            shader.destroy();
        }
    }

    public void updateTextureUnitUniform() {
        this.spriteShader.setUniform1i("tex", 0);
        this.hudShader.setUniform1i("tex", 0);
        this.transitionShader.setUniform1i("tex", 0);
    }

    public void updateProjectionMatrixUniform() {
        for (ShaderProgram shader : loadedShaders) {
            shader.setUniformMat4f("projectionMatrix", RenderMatrices.getProjectionMatrix());
        }
    }

    public void updateViewMatrixUniform() {
        for (ShaderProgram shader : loadedShaders) {
            shader.setUniformMat4f("viewMatrix", RenderMatrices.getViewMatrix());
        }
    }

    public ShaderProgram getBoundShaderProgram() {
        return boundShaderProgram;
    }

    public void setBoundShaderProgram(ShaderProgram shaderProgram) {
        this.boundShaderProgram = shaderProgram;
    }
}
