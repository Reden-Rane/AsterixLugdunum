package fr.info.game.graphics.renderer.level.introduction;

import fr.info.game.logic.level.introduction.credits.IntroductionCredit;
import fr.info.game.logic.level.introduction.credits.IntroductionCreditDev;
import fr.info.game.logic.math.MathUtils;
import fr.info.game.graphics.RenderManager;
import fr.info.game.graphics.RenderMatrices;
import fr.info.game.graphics.RenderUtils;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class IntroductionCreditDevRenderer extends IntroductionCreditRenderer {

    public IntroductionCreditDevRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(IntroductionCredit credit, float partialTicks) {
        super.render(credit, partialTicks);
        IntroductionCreditDev devCredit = (IntroductionCreditDev) credit;
        int width = devCredit.getCharacterSprite().width / 2;
        int height = devCredit.getCharacterSprite().height / 2;
        RenderMatrices.pushMatrix(RenderMatrices.EnumMatrixMode.MODEL);

        float x = MathUtils.lerp(devCredit.getPrevCharacterX(), devCredit.getCharacterX(), partialTicks);
        float y = MathUtils.lerp(devCredit.getPrevCharacterY(), devCredit.getCharacterY(), partialTicks);
        float rotZ = MathUtils.lerp(devCredit.getPrevCharacterRotZ(), devCredit.getCharacterRotZ(), partialTicks);
        float alpha = MathUtils.lerp(devCredit.getPrevAlpha(), devCredit.getAlpha(), partialTicks);

        RenderMatrices.getModelMatrix().translate(x + width / 2, y, 0);
        RenderMatrices.getModelMatrix().rotate(rotZ, new Vector3f(0, 0, 1));
        RenderMatrices.getModelMatrix().translate(-width / 2, 0, 0);
        renderManager.shaderManager.spriteShader.bind();
        RenderUtils.renderTextureSprite(devCredit.getCharacterSprite(), 0, 0, 0, width, height, new Vector4f(1, 1, 1, alpha));
        renderManager.shaderManager.spriteShader.unbind();
        RenderMatrices.popMatrix(RenderMatrices.EnumMatrixMode.MODEL);
    }
}
