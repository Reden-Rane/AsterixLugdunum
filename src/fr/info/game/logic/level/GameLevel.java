package fr.info.game.logic.level;

import fr.info.game.AsterixAndObelixGame;
import fr.info.game.ScheduledTask;
import fr.info.game.logic.input.KeyboardCallback;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class GameLevel extends Level {

    public GameLevel(String levelName) {
        super(levelName);
    }

    @Override
    public void update() {

        if (KeyboardCallback.isKeyDown(GLFW_KEY_ESCAPE)) {
            AsterixAndObelixGame.INSTANCE.addScheduledTask(new ScheduledTask(0, 20) {
                @Override
                protected void updateTask() {
                    if (getTicks() == 0) {
                        closeLevel(20);
                    }
                }

                @Override
                protected void onFinish() {
                    AsterixAndObelixGame.INSTANCE.enterHub();
                }
            });
        }

        super.update();
    }
}
