package fr.info.game.logic.level.introduction;

public abstract class IntroductionPart {

    protected final IntroductionLevel introductionLevel;
    protected int ticks;

    public IntroductionPart(IntroductionLevel introductionLevel) {
        this.introductionLevel = introductionLevel;
    }

    public abstract boolean isFinished();

    public void update() {
        this.ticks++;
    }

}
