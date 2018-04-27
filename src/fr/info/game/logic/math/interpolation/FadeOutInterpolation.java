package fr.info.game.logic.math.interpolation;

public class FadeOutInterpolation extends LinearInterpolation {

    public FadeOutInterpolation(int duration) {
        super(duration, 1, 0);
    }

}
