package fr.info.game.graphics.renderer.level.introduction;


import fr.info.game.logic.level.introduction.IntroductionLevel;
import fr.info.game.logic.level.introduction.IntroductionPart;
import fr.info.game.graphics.RenderManager;
import fr.info.game.graphics.renderer.level.LevelRenderer;
import fr.info.game.graphics.renderer.Renderer;

public class IntroductionLevelRenderer extends LevelRenderer<IntroductionLevel> {

    public IntroductionLevelRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(IntroductionLevel level, float partialTicks) {
        IntroductionPart introductionPart = level.getCurrentIntroductionPart();

        Renderer<IntroductionPart> renderer = renderManager.rendererRegistry.getRenderer(introductionPart);
        if(renderer != null) {
            renderer.render(introductionPart, partialTicks);
        }

        super.render(level, partialTicks);
    }
}
