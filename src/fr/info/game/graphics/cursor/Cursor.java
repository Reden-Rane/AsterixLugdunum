package fr.info.game.graphics.cursor;

import fr.info.game.AsterixAndObelixGame;
import fr.info.game.graphics.texture.Texture;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;

import static org.lwjgl.glfw.GLFW.glfwDestroyCursor;

public class Cursor {

    public final Texture texture;
    public final long cursorID;

    public Cursor(Texture texture, int xHot, int yHot) {
        this.texture = texture;
        GLFWImage glfwImage = GLFWImage.create();
        glfwImage.width(texture.getWidth());
        glfwImage.height(texture.getHeight());
        glfwImage.pixels(texture.getImageData());
        this.cursorID = GLFW.glfwCreateCursor(glfwImage, xHot, yHot);
    }

    public void use() {
        AsterixAndObelixGame.INSTANCE.getDisplay().setCursor(this);
    }

    public void destroy() {
        glfwDestroyCursor(this.cursorID);
    }

}
