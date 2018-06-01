package fr.info.game;

import fr.info.game.assets.TextureResource;
import fr.info.game.exception.GameException;
import fr.info.game.graphics.cursor.Cursor;
import fr.info.game.graphics.texture.Texture;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import org.lwjgl.opengl.GL;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Display implements GLFWWindowSizeCallbackI {

    public final long windowID;
    private String title;
    private int width;
    private int height;

    public Display(String title, int width, int height) throws GameException {
        this.title = title;
        this.width = width;
        this.height = height;

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        this.windowID = glfwCreateWindow(width, height, title, NULL, NULL);
        if (this.windowID == NULL) {
            throw new GameException("Could not create GLFW window.");
        }

        glfwSetWindowSizeCallback(windowID, this);
        glfwMakeContextCurrent(windowID);

        GL.createCapabilities();
        loadWindowIcon(new Texture(new TextureResource("gui/window/window_icon64x64.png")));
    }

    public void show() {
        glfwShowWindow(windowID);
    }

    private void loadWindowIcon(Texture texture) {
        GLFWImage.Buffer icons = GLFWImage.malloc(1);
        icons.position(0).width(texture.getWidth()).height(texture.getHeight()).pixels(texture.getImageData());
        glfwSetWindowIcon(windowID, icons);
        icons.free();
    }

    public void destroy() {
        glfwDestroyWindow(windowID);
        GL.destroy();
    }

    @Override
    public void invoke(long window, int width, int height) {
        this.width = width;
        this.height = height;
    }

    public boolean shouldClose() {
        return glfwWindowShouldClose(windowID);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        glfwSetWindowTitle(windowID, title);
    }

    public void setCursor(Cursor cursor) {
        glfwSetCursor(windowID, cursor.cursorID);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
