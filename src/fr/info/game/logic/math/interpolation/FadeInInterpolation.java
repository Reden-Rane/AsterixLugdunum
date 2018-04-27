package fr.info.game.logic.math.interpolation;

public class FadeInInterpolation extends LinearInterpolation {

    public FadeInInterpolation(int duration) {
        super(duration, 0, 1);
    }

}
