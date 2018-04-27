package fr.info.game.logic.math.interpolation;

import fr.info.game.logic.math.DynamicValueUpdater;
import fr.info.game.logic.math.MathUtils;

public class LinearInterpolation extends DynamicValueUpdater<Float> {

    private final float startValue;
    private final float endValue;

    public LinearInterpolation(int duration, float startValue, float endValue) {
        super(duration);
        this.startValue = startValue;
        this.endValue = endValue;
    }

    @Override
    protected Float getValue(float progress) {
        return MathUtils.lerp(this.startValue, this.endValue, progress);
    }
}
