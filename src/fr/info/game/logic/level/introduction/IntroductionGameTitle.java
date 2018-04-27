package fr.info.game.logic.level.introduction;

import fr.info.game.AsterixAndObelixGame;
import fr.info.game.graphics.texture.TextureSprite;
import fr.info.game.logic.math.interpolation.FadeInInterpolation;
import fr.info.game.logic.math.interpolation.LinearInterpolation;

public class IntroductionGameTitle extends IntroductionPart {

    private final int blackScreenDuration = 30;
    private final int duration = 150 + blackScreenDuration;

    public final TextureSprite backgroundSprite = AsterixAndObelixGame.INSTANCE.getRenderManager().textureManager.bdAtlas.getTextureSprite("page0");
    public final TextureSprite gameTitleSprite = AsterixAndObelixGame.INSTANCE.getRenderManager().textureManager.introAtlas.getTextureSprite("gameTitle");

    private final LinearInterpolation gameTitleScaleFactorInterpolation = new LinearInterpolation(20, 0, 1);
    private float titleScaleFactor;
    private float prevTitleScaleFactor;

    private final LinearInterpolation backgroundOffsetInterpolation = new LinearInterpolation(this.duration, 200, 500);
    private float backgroundOffset;
    private float prevBackgroundOffset;

    private final FadeInInterpolation alphaFadeInInterpolation = new FadeInInterpolation(10);
    private float alpha;
    private float prevAlpha;

    private final LinearInterpolation subTitleOffsetInterpolation = new LinearInterpolation(40, -1, 1);
    private float subTitleOffset = -1;
    private float prevSubTitleOffset = -1;

    public IntroductionGameTitle(IntroductionLevel introductionLevel) {
        super(introductionLevel);
    }

    @Override
    public void update() {
        this.prevTitleScaleFactor = this.titleScaleFactor;
        this.prevBackgroundOffset = this.backgroundOffset;
        this.prevAlpha = this.alpha;
        this.prevSubTitleOffset = this.subTitleOffset;

        if (this.ticks <= this.alphaFadeInInterpolation.duration) {
            this.alphaFadeInInterpolation.update();
            this.alpha = alphaFadeInInterpolation.getValue();
        }

        if (this.ticks >= 20) {
            this.gameTitleScaleFactorInterpolation.update();
            this.titleScaleFactor = this.gameTitleScaleFactorInterpolation.getValue();
        }

        if(this.ticks >= 40) {
            this.subTitleOffsetInterpolation.update();
            this.subTitleOffset = this.subTitleOffsetInterpolation.getValue();
        }

        int closingDuration = 10;

        if(this.ticks == this.duration - this.blackScreenDuration - closingDuration) {
            introductionLevel.closeLevel(closingDuration);
        }

        this.backgroundOffsetInterpolation.update();
        this.backgroundOffset = this.backgroundOffsetInterpolation.getValue();

        super.update();
    }

    @Override
    public boolean isFinished() {
        return this.ticks >= this.duration;
    }

    public float getTitleScaleFactor() {
        return titleScaleFactor;
    }

    public float getPrevTitleScaleFactor() {
        return prevTitleScaleFactor;
    }

    public float getBackgroundOffset() {
        return backgroundOffset;
    }

    public float getPrevBackgroundOffset() {
        return prevBackgroundOffset;
    }

    public float getAlpha() {
        return alpha;
    }

    public float getPrevAlpha() {
        return prevAlpha;
    }

    public float getSubTitleOffset() {
        return subTitleOffset;
    }

    public float getPrevSubTitleOffset() {
        return prevSubTitleOffset;
    }
}
