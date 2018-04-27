package fr.info.game.graphics.renderer.level.introduction;

import fr.info.game.logic.level.introduction.credits.IntroductionCredit;
import fr.info.game.logic.level.introduction.credits.IntroductionCredits;
import fr.info.game.graphics.RenderManager;
import fr.info.game.graphics.renderer.Renderer;

public class IntroductionCreditsRenderer extends Renderer<IntroductionCredits> {

    public IntroductionCreditsRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(IntroductionCredits credits, float partialTicks) {
        IntroductionCredit credit = credits.getCurrentCredit();
        Renderer<IntroductionCredit> renderer = renderManager.rendererRegistry.getRenderer(credit);

        if(renderer != null) {
            renderer.render(credit, partialTicks);
        }
    }
}
