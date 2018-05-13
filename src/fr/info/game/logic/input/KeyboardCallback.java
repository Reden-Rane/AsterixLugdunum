package fr.info.game.logic.input;

import fr.info.game.AsterixAndObelixGame;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * This is a callback, {@link KeyboardCallback#invoke} is called every time GLFW detect a change with the keyboard
 */
public class KeyboardCallback extends GLFWKeyCallback {

    public static int[] oldKeyState = new int[65536];
    public static int[] keyState = new int[65536];
    public static boolean[] pressedKeys = new boolean[65536];

    public void invoke(long window, int key, int scancode, int action, int mods) {
        if (key > 0 && key < pressedKeys.length) {
            oldKeyState[key] = keyState[key];
            keyState[key] = action;
            pressedKeys[key] = action != GLFW_RELEASE;
            AsterixAndObelixGame.INSTANCE.getCurrentLevel().keyEvent(key, action);
        }
    }

    public static boolean isKeyJustPressed(int keycode) {
        return oldKeyState[keycode] == GLFW_RELEASE && keyState[keycode] == GLFW_PRESS;
    }

    /**
     * @param keycode
     * @return Return true if the given key is down
     */
    public static boolean isKeyDown(int keycode) {
        return pressedKeys[keycode];
    }

    public static List<Integer> getPressedKeys() {
        List<Integer> pressedKeysList = new ArrayList<>();
        for(int i = 0; i < pressedKeys.length; i++) {
            if(pressedKeys[i]) {
                pressedKeysList.add(i);
            }
        }
        return pressedKeysList;
    }

}
