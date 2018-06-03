package fr.info.game.graphics.renderer.level;

import fr.info.game.graphics.RenderManager;
import fr.info.game.logic.level.gates.GatesLevel;

import static org.lwjgl.opengl.GL11.*;

public class GatesLevelRenderer extends LevelRenderer<GatesLevel> {

    public GatesLevelRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(GatesLevel level, float partialTicks) {
        glClearColor(1, 1, 1, 1);
        glClear(GL_COLOR_BUFFER_BIT);
        super.render(level, partialTicks);
    }

}
