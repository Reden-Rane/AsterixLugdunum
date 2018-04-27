package fr.info.game.logic.input;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

/**
 * This is a callback, {@link KeyboardCallback#invoke} is called every time GLFW detect a change with the keyboard
 */
public class KeyboardCallback extends GLFWKeyCallback {

    public static boolean[] pressedKeys = new boolean[65536];

    public void invoke(long window, int key, int scancode, int action, int mods) {
        if (key > 0 && key < pressedKeys.length)
            pressedKeys[key] = action != GLFW.GLFW_RELEASE;
    }

    /**
     * @param keycode
     * @return Return true if the given key is down
     */
    public static boolean isKeyDown(int keycode) {
        return pressedKeys[keycode];
    }

}
