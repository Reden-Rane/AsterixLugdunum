package fr.info.game.logic.level.introduction.credits;

import fr.info.game.graphics.texture.TextureSprite;

public abstract class IntroductionCreditDev extends IntroductionCredit {

    /**
     * The character sprite to be rendered next to the name
     */
    private final TextureSprite characterSprite;

    protected float characterX;
    protected float characterY;
    protected float prevCharacterX;
    protected float prevCharacterY;
    protected float characterRotZ;
    protected float prevCharacterRotZ;

    public IntroductionCreditDev(String devName, TextureSprite characterSprite) {
        super(42, 5, 5, devName, 80);
        this.characterSprite = characterSprite;
    }

    public float getAlpha() {
        return this.alpha;
    }

    public float getPrevAlpha() {
        return prevAlpha;
    }

    public TextureSprite getCharacterSprite() {
        return characterSprite;
    }

    public float getCharacterX() {
        return characterX;
    }

    public float getCharacterY() {
        return characterY;
    }

    public float getPrevCharacterX() {
        return prevCharacterX;
    }

    public float getPrevCharacterY() {
        return prevCharacterY;
    }

    public float getCharacterRotZ() {
        return characterRotZ;
    }

    public float getPrevCharacterRotZ() {
        return prevCharacterRotZ;
    }
}
