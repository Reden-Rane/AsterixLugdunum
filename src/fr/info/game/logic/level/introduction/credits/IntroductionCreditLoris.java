package fr.info.game.logic.level.introduction.credits;

import fr.info.game.AsterixAndObelixGame;
import fr.info.game.logic.math.interpolation.SinusoidalInterpolation;

public class IntroductionCreditLoris extends IntroductionCreditDev {

    private final SinusoidalInterpolation yPosInterpolation = new SinusoidalInterpolation(duration, 10, 4F, 0);

    public IntroductionCreditLoris() {
        super("Loris Leong", AsterixAndObelixGame.INSTANCE.getRenderManager().textureManager.introAtlas.getTextureSprite("loris"));
    }

    @Override
    public void update() {
        yPosInterpolation.update();
        int x = AsterixAndObelixGame.INSTANCE.getRenderManager().getDisplayWidth() / 2 - 550;
        int y = (int) (AsterixAndObelixGame.INSTANCE.getRenderManager().getDisplayHeight() / 2 + yPosInterpolation.getValue()) - 150;
        prevCharacterX = characterX;
        prevCharacterY = characterY;
        characterX = x;
        characterY = y;
        super.update();
    }
}
