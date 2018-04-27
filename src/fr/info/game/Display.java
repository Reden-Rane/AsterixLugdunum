package fr.info.game;

import fr.info.game.exception.GameException;
import fr.info.game.graphics.cursor.Cursor;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import org.lwjgl.opengl.GL;

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
        glfwShowWindow(windowID);
        GL.createCapabilities();

        System.out.println("Initialized display.");
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
