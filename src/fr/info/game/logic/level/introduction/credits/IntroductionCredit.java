package fr.info.game.logic.level.introduction.credits;

import fr.info.game.logic.math.interpolation.FadeInInterpolation;
import fr.info.game.logic.math.interpolation.FadeOutInterpolation;

public class IntroductionCredit {

    public final int duration;
    protected int ticks;

    /**
     * The text of the credit
     */
    private final String text;
    private final int textSize;

    protected float alpha;
    protected float prevAlpha;

    protected FadeInInterpolation alphaFadeIn;
    protected FadeOutInterpolation alphaFadeOut;

    public IntroductionCredit(int duration, int fadeInDuration, int fadeOutDuration, String text, int textSize) {
        this.duration = duration;
        this.text = text;
        this.textSize = textSize;
        this.alphaFadeIn = new FadeInInterpolation(fadeInDuration);
        this.alphaFadeOut = new FadeOutInterpolation(fadeOutDuration);
    }

    public void update() {
        prevAlpha = alpha;

        if(this.ticks <= alphaFadeIn.duration) {
            alphaFadeIn.update();
            alpha = alphaFadeIn.getValue();
        } else if(this.ticks >= this.duration - alphaFadeOut.duration) {
            alphaFadeOut.update();
            alpha = alphaFadeOut.getValue();
        }

        this.ticks++;
    }

    public boolean isFinished() {
        return this.ticks >= this.duration;
    }

    public float getAlpha() {
        return this.alpha;
    }

    public float getPrevAlpha() {
        return prevAlpha;
    }

    public String getText() {
        return text;
    }

    public int getTextSize() {
        return textSize;
    }
}
