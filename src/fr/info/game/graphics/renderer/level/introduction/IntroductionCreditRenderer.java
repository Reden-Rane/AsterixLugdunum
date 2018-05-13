package fr.info.game.graphics.renderer.level.introduction;

import fr.info.game.logic.level.introduction.credits.IntroductionCredit;
import fr.info.game.logic.math.MathUtils;
import fr.info.game.graphics.RenderManager;
import fr.info.game.graphics.RenderUtils;
import fr.info.game.graphics.renderer.Renderer;
import org.joml.Vector4f;

public class IntroductionCreditRenderer extends Renderer<IntroductionCredit> {

    public IntroductionCreditRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(IntroductionCredit credit, float partialTicks) {
        int textX = (renderManager.getDisplayWidth() - RenderUtils.getStringWidth(credit.getText(), renderManager.COMIC_STRIP_MN_FONT, credit.getTextSize())) / 2;
        int textY = (renderManager.getDisplayHeight() - RenderUtils.getStringHeight(credit.getText(), renderManager.COMIC_STRIP_MN_FONT, credit.getTextSize())) / 2;
        float alpha = MathUtils.lerp(credit.getPrevAlpha(), credit.getAlpha(), partialTicks);
        renderManager.shaderManager.spriteShader.bind();
        RenderUtils.renderString(credit.getText(), renderManager.COMIC_STRIP_MN_FONT, textX, textY, credit.getTextSize(), new Vector4f(1, 1, 1, alpha));
        renderManager.shaderManager.spriteShader.unbind();
    }

    @Override
    public boolean shouldRender(IntroductionCredit obj, float partialTicks) {
        return true;
    }
}
