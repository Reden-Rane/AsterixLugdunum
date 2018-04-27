package fr.info.game.logic.input;

import fr.info.game.AsterixAndObelixGame;
import org.lwjgl.glfw.GLFWCursorPosCallback;

public class MousePositionCallback extends GLFWCursorPosCallback {

    private static double mouseX;
    private static double mouseY;

    @Override
    public void invoke(long window, double xpos, double ypos) {
        int height = AsterixAndObelixGame.INSTANCE.getRenderManager().getDisplayHeight();
        mouseX = xpos;
        mouseY = height - ypos;
    }

    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }
}
