package fr.info.game.logic.math;

public abstract class DynamicValueUpdater<E extends Number> {

    public final DynamicValue<E> value;
    public final int duration;

    private int ticks;
    private boolean finished;

    public DynamicValueUpdater(int duration) {
        this.value = new DynamicValue<>();
        this.duration = duration;
    }

    private float getProgress() {
        return MathUtils.clamp(ticks / (float) duration, 0, 1);
    }

    public void update() {
        if(!isFinished()) {
            value.setValue(getValue(getProgress()));
            this.ticks++;

            if (this.ticks > duration) {
                this.finished = true;
            }
        }
    }

    protected abstract E getValue(float progress);

    public E getValue() {
        return value.getValue();
    }

    public boolean isFinished() {
        return finished;
    }

}
