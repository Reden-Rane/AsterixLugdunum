package fr.info.game.graphics.renderer.level;

import fr.info.game.graphics.RenderManager;
import fr.info.game.logic.level.campus.CampusLevel;

import static org.lwjgl.opengl.GL11.*;

public class CampusLevelRenderer extends LevelRenderer<CampusLevel> {

    public CampusLevelRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(CampusLevel level, float partialTicks) {
        glClearColor(1, 1, 1, 1);
        glClear(GL_COLOR_BUFFER_BIT);
        super.render(level, partialTicks);
    }

}
