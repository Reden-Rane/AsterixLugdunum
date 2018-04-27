package fr.info.game.graphics.renderer.level;

import fr.info.game.graphics.RenderManager;
import fr.info.game.logic.level.tavern.TavernLevel;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

public class TavernRenderer extends LevelRenderer<TavernLevel> {

    public TavernRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(TavernLevel level, float partialTicks) {
        glClearColor(1, 1, 1, 1);
        glClear(GL_COLOR_BUFFER_BIT);
        super.render(level, partialTicks);
    }
}
