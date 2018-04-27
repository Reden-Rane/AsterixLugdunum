package fr.info.game.graphics.renderer.level.introduction;

import fr.info.game.logic.level.introduction.IntroductionGameTitle;
import fr.info.game.logic.math.MathUtils;
import fr.info.game.graphics.RenderManager;
import fr.info.game.graphics.RenderMatrices;
import fr.info.game.graphics.RenderUtils;
import fr.info.game.graphics.renderer.Renderer;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class IntroductionGameTitleRenderer extends Renderer<IntroductionGameTitle> {

    public IntroductionGameTitleRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(IntroductionGameTitle gameTitle, float partialTicks) {
        RenderMatrices.pushMatrix(RenderMatrices.EnumMatrixMode.MODEL);

        int width = renderManager.getDisplayWidth() * 2;
        int height = (int) (gameTitle.backgroundSprite.height / (float) gameTitle.backgroundSprite.width * width);

        float bgOffset = MathUtils.lerp(gameTitle.getPrevBackgroundOffset(), gameTitle.getBackgroundOffset(), partialTicks);
        float alpha = MathUtils.lerp(gameTitle.getPrevAlpha(), gameTitle.getAlpha(), partialTicks);
        float titleScale = MathUtils.lerp(gameTitle.getPrevTitleScaleFactor(), gameTitle.getTitleScaleFactor(), partialTicks);
        float subTitleOffset = MathUtils.lerp(gameTitle.getPrevSubTitleOffset(), gameTitle.getSubTitleOffset(), partialTicks);

        RenderMatrices.translate(width / 2, 0, 0);
        RenderMatrices.rotate((float) Math.toRadians(45), new Vector3f(0, 0, 1));
        RenderMatrices.translate(-width / 2 + bgOffset, -bgOffset, 0);
        renderManager.shaderManager.spriteShader.bind();
        RenderUtils.renderTextureSprite(gameTitle.backgroundSprite, 0, 0, 0, width, height, new Vector4f(1, 1, 1, 0.2F * alpha));
        RenderMatrices.popMatrix(RenderMatrices.EnumMatrixMode.MODEL);

        String subTitle = "À l'INSA Lugdunum";
        int titleWidth = (int) (gameTitle.gameTitleSprite.width * titleScale);
        int titleHeight = (int) (gameTitle.gameTitleSprite.height * titleScale);
        int subTitleWidth = RenderUtils.getStringWidth(subTitle, renderManager.COMIC_STRIP_MN_FONT, 64);
        int subTitleHeight = RenderUtils.getStringHeight(subTitle, renderManager.COMIC_STRIP_MN_FONT, 64);
        int titleX = (renderManager.getDisplayWidth() - titleWidth) / 2;
        int titleY = (renderManager.getDisplayHeight() - titleHeight) / 2 + subTitleHeight;
        RenderUtils.renderTextureSprite(gameTitle.gameTitleSprite, titleX, titleY, 0, titleWidth, titleHeight, new Vector4f(1, 1, 1, alpha));

        int subTitleX = (int) (-(renderManager.getDisplayWidth() / 2 + subTitleWidth) * (1 - subTitleOffset) + (renderManager.getDisplayWidth() - subTitleWidth) / 2);
        int subTitleY = titleY - subTitleHeight;
        RenderUtils.renderString(subTitle, renderManager.COMIC_STRIP_MN_FONT, subTitleX, subTitleY, 64, new Vector4f(1, 1, 1, alpha));
        renderManager.shaderManager.spriteShader.unbind();
    }
}
