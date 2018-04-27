package fr.info.game.graphics;

import fr.info.game.utils.BufferUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class VertexArray {

    private static int boundVAO = -1;
    private static int boundIBO = -1;

    private int vao, vbo, ibo, tbo;
    private int count;

    public VertexArray(int count) {
        this.count = count;
        vao = glGenVertexArrays();
    }

    public VertexArray(float[] vertices, byte[] indices, float[] textureCoordinates) {
        count = indices.length;

        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(vertices), GL_STATIC_DRAW);
        glVertexAttribPointer(ShaderProgram.VERTEX_ATTRIB, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(ShaderProgram.VERTEX_ATTRIB);

        if (textureCoordinates != null) {
            tbo = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, tbo);
            glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(textureCoordinates), GL_STATIC_DRAW);
            glVertexAttribPointer(ShaderProgram.TCOORD_ATTRIB, 2, GL_FLOAT, false, 0, 0);
            glEnableVertexAttribArray(ShaderProgram.TCOORD_ATTRIB);
        } else {
            tbo = -1;
        }

        ibo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createByteBuffer(indices), GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public void bind() {
        if(boundVAO != vao) {
            boundVAO = vao;
            glBindVertexArray(vao);
        }

        if(ibo > 0 && boundIBO != ibo) {
            boundIBO = ibo;
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        }
    }

    public void unbind() {
        if(ibo > 0 && boundIBO > 0) {
            boundIBO = -1;
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        }

        if(boundVAO > 0) {
            boundVAO = -1;
            glBindVertexArray(0);
        }
    }

    public void draw() {
        if (ibo > 0)
            glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_BYTE, 0);
        else
            glDrawArrays(GL_TRIANGLES, 0, count);
    }

    public void render() {
        bind();
        draw();
    }

    public void destroy() {
        glDeleteBuffers(this.ibo);
        if(this.tbo != -1) {
            glDeleteBuffers(this.tbo);
        }
        glDeleteBuffers(this.vbo);
        glDeleteVertexArrays(this.vao);
    }

}
