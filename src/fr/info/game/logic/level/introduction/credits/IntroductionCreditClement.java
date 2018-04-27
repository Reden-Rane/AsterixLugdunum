package fr.info.game.logic.level.introduction.credits;

import fr.info.game.AsterixAndObelixGame;
import fr.info.game.logic.math.interpolation.SinusoidalInterpolation;

public class IntroductionCreditClement extends IntroductionCreditDev {

    private final SinusoidalInterpolation rotationZInterpolation = new SinusoidalInterpolation(duration, (float) (Math.PI / 16), 2, 0);

    public IntroductionCreditClement() {
        super("Clément Pambrun", AsterixAndObelixGame.INSTANCE.getRenderManager().textureManager.introAtlas.getTextureSprite("clement"));
    }

    @Override
    public void update() {
        rotationZInterpolation.update();
        int x = (AsterixAndObelixGame.INSTANCE.getRenderManager().getDisplayWidth() - getCharacterSprite().width / 2) / 2;
        int y = AsterixAndObelixGame.INSTANCE.getRenderManager().getDisplayHeight() / 2 + 50;
        prevCharacterX = characterX;
        prevCharacterY = characterY;
        prevCharacterRotZ = characterRotZ;
        characterX = x;
        characterY = y;
        characterRotZ = rotationZInterpolation.getValue();
        super.update();
    }
}
