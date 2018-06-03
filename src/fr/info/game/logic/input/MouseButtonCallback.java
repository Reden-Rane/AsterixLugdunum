package fr.info.game.logic.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

public class MouseButtonCallback extends GLFWMouseButtonCallback {

    private static boolean[] pressedButtons = new boolean[12];
    private static int[] buttonActions = new int[12];

    @Override
    public void invoke(long window, int button, int action, int mods) {
        if (button >= 0 && button < pressedButtons.length) {
            pressedButtons[button] = action != GLFW.GLFW_RELEASE;
            buttonActions[button] = action;
        }
    }

    public static boolean isButtonPressed(int button) {
        return pressedButtons[button];
    }

    public static int getButtonAction(int button) {
        return buttonActions[button];
    }

}
