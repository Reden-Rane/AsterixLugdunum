package fr.info.game.graphics;

import fr.info.game.assets.ShaderResource;
import fr.info.game.utils.FileUtils;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {

    public static final int VERTEX_ATTRIB = 0;
    public static final int TCOORD_ATTRIB = 1;

    private final ShaderManager shaderManager;
    private final int shaderID;

    /**
     * Cached uniform's location to updateTask the shaders faster
     */
    private Map<String, Integer> uniformLocationCache = new HashMap<>();

    public ShaderProgram(ShaderManager shaderManager, ShaderResource vertexShader, ShaderResource fragmentShader) {
        this.shaderManager = shaderManager;
        this.shaderID = createShaderProgram(vertexShader, fragmentShader);
    }

    /**
     * Create a shader program in the OpenGL system.
     *
     * @param vertexShader   The vertex shader's sources
     * @param fragmentShader The fragment shader's sources
     * @return Return the program ID.
     */
    private int createShaderProgram(ShaderResource vertexShader, ShaderResource fragmentShader) {
        int program = glCreateProgram();
        int vertID = glCreateShader(GL_VERTEX_SHADER);
        int fragID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(vertID, FileUtils.loadAsString(vertexShader));
        glShaderSource(fragID, FileUtils.loadAsString(fragmentShader));

        glCompileShader(vertID);
        if (glGetShaderi(vertID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Failed to compile vertex shader!");
            System.err.println(glGetShaderInfoLog(vertID));
            return -1;
        }

        glCompileShader(fragID);
        if (glGetShaderi(fragID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Failed to compile fragment shader!");
            System.err.println(glGetShaderInfoLog(fragID));
            return -1;
        }

        glAttachShader(program, vertID);
        glAttachShader(program, fragID);
        glLinkProgram(program);
        glValidateProgram(program);

        glDeleteShader(vertID);
        glDeleteShader(fragID);

        return program;
    }

    public int getUniformLocation(String name) {
        if (uniformLocationCache.containsKey(name))
            return uniformLocationCache.get(name);

        int result = glGetUniformLocation(shaderID, name);
        if (result == -1)
            System.err.println("Could not find uniform variable '" + name + "'!");
        else
            uniformLocationCache.put(name, result);
        return result;
    }

    public void setUniform1i(String name, int value) {
        if (!isShaderBound()) bind();
        glUniform1i(getUniformLocation(name), value);
    }

    public void setUniform1f(String name, float value) {
        if (!isShaderBound()) bind();
        glUniform1f(getUniformLocation(name), value);
    }

    public void setUniform2f(String name, float x, float y) {
        if (!isShaderBound()) bind();
        glUniform2f(getUniformLocation(name), x, y);
    }

    public void setUniform3f(String name, Vector3f vector) {
        if (!isShaderBound()) bind();
        glUniform3f(getUniformLocation(name), vector.x, vector.y, vector.z);
    }

    public void setUniform4f(String name, Vector4f vector) {
        if (!isShaderBound()) bind();
        glUniform4f(getUniformLocation(name), vector.x, vector.y, vector.z, vector.w);
    }

    public void setUniformMat4f(String name, Matrix4f matrix) {
        if (!isShaderBound()) bind();
        FloatBuffer fb = BufferUtils.createFloatBuffer(16);
        matrix.get(fb);
        glUniformMatrix4fv(getUniformLocation(name), false, fb);
    }

    /**
     * Enable the shader in order to graphics elements with it
     */
    public void bind() {
        if(!isShaderBound()) {
            glUseProgram(shaderID);
            shaderManager.setBoundShaderProgram(this);
        }
    }

    public void unbind() {
        if(shaderManager.getBoundShaderProgram() == this) {
            glUseProgram(0);
            shaderManager.setBoundShaderProgram(null);
        }
    }

    public boolean isShaderBound() {
        return shaderManager.getBoundShaderProgram() == this;
    }

    /**
     * Free the memory when the shader is no longer used
     */
    public void destroy() {
        glDeleteProgram(this.shaderID);
    }
}
