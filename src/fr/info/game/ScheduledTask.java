package fr.info.game;

import fr.info.game.logic.math.MathUtils;

public abstract class ScheduledTask {

    private long cooldown;
    private final long duration;
    private long ticks;

    public ScheduledTask() {
        this(0);
    }

    public ScheduledTask(long cooldown) {
        this(cooldown, 1);
    }

    public ScheduledTask(long cooldown, long duration) {
        this.cooldown = MathUtils.clamp(cooldown, 0, Long.MAX_VALUE);
        this.duration = duration;
    }

    public void update() {
        cooldown--;

        if(isRunning()) {
            updateTask();
            ticks++;

            if(isFinished()) {
                onFinish();
            }
        }
    }

    public boolean isRunning() {
        return cooldown <= 0;
    }

    public long getTicks() {
        return ticks;
    }

    public long getDuration() {
        return duration;
    }

    public boolean isFinished() {
        return ticks > duration;
    }

    protected abstract void updateTask();

    protected void onFinish() {}

}
