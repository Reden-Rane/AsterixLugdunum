package fr.info.game.logic.level.introduction;

import fr.info.game.AsterixAndObelixGame;
import fr.info.game.logic.math.interpolation.FadeInInterpolation;
import fr.info.game.logic.math.interpolation.FadeOutInterpolation;
import fr.info.game.graphics.texture.TextureSprite;

public class IntroductionGameBrand extends IntroductionPart {

    private final int duration = 70;
    public final TextureSprite gameBrandSprite = AsterixAndObelixGame.INSTANCE.getRenderManager().textureManager.introAtlas.getTextureSprite("gameBrand");

    private float alpha;
    private float prevAlpha;

    private final FadeInInterpolation fadeInInterpolation = new FadeInInterpolation(20);
    private final FadeOutInterpolation fadeOutInterpolation = new FadeOutInterpolation(20);

    public IntroductionGameBrand(IntroductionLevel introductionLevel) {
        super(introductionLevel);
    }

    @Override
    public void update() {

        prevAlpha = alpha;

        if(this.ticks <= fadeInInterpolation.duration) {
            fadeInInterpolation.update();
            alpha = fadeInInterpolation.getValue();
        } else if(this.ticks >= this.duration - fadeOutInterpolation.duration) {
            fadeOutInterpolation.update();
            alpha = fadeOutInterpolation.getValue();
        }

        super.update();
    }

    @Override
    public boolean isFinished() {
        return this.ticks >= this.duration;
    }

    public float getAlpha() {
        return alpha;
    }

    public float getPrevAlpha() {
        return prevAlpha;
    }
}
