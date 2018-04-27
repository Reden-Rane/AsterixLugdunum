package fr.info.game.logic.level.introduction;

import fr.info.game.AsterixAndObelixGame;
import fr.info.game.audio.Sound;
import fr.info.game.audio.SoundSource;
import fr.info.game.logic.input.KeyboardCallback;
import fr.info.game.logic.level.Level;
import fr.info.game.logic.level.introduction.credits.IntroductionCredits;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;

public class IntroductionLevel extends Level {

    private final List<IntroductionPart> introductionParts = new ArrayList<>();

    public IntroductionLevel() {
        super("Introduction");
        this.playIntroductionMusic();
        this.introductionParts.add(new IntroductionGameBrand(this));
        this.introductionParts.add(new IntroductionCredits(this));
        this.introductionParts.add(new IntroductionGameTitle(this));
        this.cartoonCircleRadius = 1;
        this.prevCartoonCircleRadius = 1;
    }

    private void playIntroductionMusic() {
        Sound introSound = AsterixAndObelixGame.INSTANCE.getAudioManager().getSound("intro");
        if (introSound != null) {
            SoundSource source = AsterixAndObelixGame.INSTANCE.getAudioManager().getMusicSource();
            source.play(introSound);
        }
    }

    @Override
    public void update() {
        if (this.introductionParts.size() != 0) {
            IntroductionPart introPart = this.introductionParts.get(0);
            introPart.update();

            if (introPart.isFinished()) {
                nextIntroductionPart();
            }
            super.update();
        }

        if(isFinished()) {
            AsterixAndObelixGame.INSTANCE.getAudioManager().getMusicSource().stop();
            AsterixAndObelixGame.INSTANCE.enterHub();
        }
    }

    private boolean isFinished() {
        return this.introductionParts.size() == 0 || KeyboardCallback.isKeyDown(GLFW_KEY_ESCAPE);
    }

    private void nextIntroductionPart() {
        this.introductionParts.remove(0);
    }

    public IntroductionPart getCurrentIntroductionPart() {
        if(this.introductionParts.size() > 0) {
            return this.introductionParts.get(0);
        } else {
            return null;
        }
    }
}
