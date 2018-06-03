package fr.info.game.logic.level.bridge;

import fr.info.game.logic.input.MouseButtonCallback;
import fr.info.game.logic.input.MousePositionCallback;
import fr.info.game.logic.level.GameLevel;
import fr.info.game.logic.tile.Tile;
import org.lwjgl.glfw.GLFW;

public class CannonballLevel extends GameLevel {

    public CannonballLevel() {
        super("Tir au boulet");
    }

    @Override
    public Tile[][] generateTerrain() {
        return new Tile[0][];
    }

    @Override
    public void update() {
        super.update();

        if(MouseButtonCallback.isButtonPressed(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
            System.out.println("Mouse dragged to: " + MousePositionCallback.getMouseX() + " | " + MousePositionCallback.getMouseY());
        }
    }
}
