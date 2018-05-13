package fr.info.game.graphics.renderer.level.introduction;

import fr.info.game.AsterixAndObelixGame;
import fr.info.game.Display;
import fr.info.game.logic.level.introduction.IntroductionGameBrand;
import fr.info.game.logic.math.MathUtils;
import fr.info.game.graphics.RenderManager;
import fr.info.game.graphics.RenderUtils;
import fr.info.game.graphics.renderer.Renderer;
import org.joml.Vector4f;

public class IntroductionGameBrandRenderer extends Renderer<IntroductionGameBrand> {

    public IntroductionGameBrandRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(IntroductionGameBrand gameBrand, float partialTicks) {
        Display display = AsterixAndObelixGame.INSTANCE.getDisplay();
        int w = (int) (display.getWidth() * 3 / 4F);
        int h = (int) (gameBrand.gameBrandSprite.height / (float) gameBrand.gameBrandSprite.width * w);
        int x = (display.getWidth() - w) / 2;
        int y = (display.getHeight() - h) / 2;
        float alpha = MathUtils.lerp(gameBrand.getPrevAlpha(), gameBrand.getAlpha(), partialTicks);
        AsterixAndObelixGame.INSTANCE.getRenderManager().shaderManager.spriteShader.bind();
        RenderUtils.renderTextureSprite(gameBrand.gameBrandSprite, x, y, 0, w, h, new Vector4f(1, 1, 1, alpha));
        AsterixAndObelixGame.INSTANCE.getRenderManager().shaderManager.spriteShader.unbind();
    }

    @Override
    public boolean shouldRender(IntroductionGameBrand obj, float partialTicks) {
        return true;
    }
}
