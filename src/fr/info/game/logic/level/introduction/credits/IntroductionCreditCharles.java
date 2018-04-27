package fr.info.game.logic.level.introduction.credits;

import fr.info.game.AsterixAndObelixGame;
import fr.info.game.logic.math.interpolation.LinearInterpolation;
import fr.info.game.logic.math.interpolation.SinusoidalInterpolation;

public class IntroductionCreditCharles extends IntroductionCreditDev {

    private final LinearInterpolation xPosInterpolation = new LinearInterpolation(duration, -250, 250);
    private final SinusoidalInterpolation yPosInterpolation = new SinusoidalInterpolation(duration, 50, 2, 0);

    public IntroductionCreditCharles() {
        super("Charles Javerliat", AsterixAndObelixGame.INSTANCE.getRenderManager().textureManager.introAtlas.getTextureSprite("charles"));
    }

    @Override
    public void update() {
        xPosInterpolation.update();
        yPosInterpolation.update();
        int x = (int) ((AsterixAndObelixGame.INSTANCE.getRenderManager().getDisplayWidth() - getCharacterSprite().width / 2) / 2 + xPosInterpolation.getValue());
        int y = (int) (AsterixAndObelixGame.INSTANCE.getRenderManager().getDisplayHeight() / 2 + 25 + Math.abs(yPosInterpolation.getValue()));
        prevCharacterX = characterX;
        prevCharacterY = characterY;
        characterX = x;
        characterY = y;
        super.update();
    }
}
