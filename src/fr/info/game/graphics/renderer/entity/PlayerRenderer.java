package fr.info.game.graphics.renderer.entity;

import fr.info.game.logic.entity.EnumDirection;
import fr.info.game.logic.entity.Player;
import fr.info.game.graphics.RenderManager;
import fr.info.game.graphics.texture.SpriteAnimation;
import fr.info.game.graphics.texture.TextureSprite;

import static fr.info.game.logic.entity.Player.JUMP_TICKS_DURATION;

public class PlayerRenderer extends EntityRenderer<Player> {

    private SpriteAnimation walkRightAnimation = renderManager.textureManager.asterixAtlas.getSpriteAnimation("walkRight");
    private SpriteAnimation walkLeftAnimation = renderManager.textureManager.asterixAtlas.getSpriteAnimation("walkLeft");
    private SpriteAnimation idleRightAnimation = renderManager.textureManager.asterixAtlas.getSpriteAnimation("idleRight");
    private SpriteAnimation idleLeftAnimation = renderManager.textureManager.asterixAtlas.getSpriteAnimation("idleLeft");
    private SpriteAnimation jumpRightAnimation = renderManager.textureManager.asterixAtlas.getSpriteAnimation("jumpRight");
    private SpriteAnimation jumpLeftAnimation = renderManager.textureManager.asterixAtlas.getSpriteAnimation("jumpLeft");

    public PlayerRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public TextureSprite getEntityTexture(Player player) {
        SpriteAnimation animation;

        if (player.isJumping()) {
            animation = getJumpRightAnimation(player);
            return animation.getFrameAt(JUMP_TICKS_DURATION - player.getJumpCounter());
        } else if (player.motionX != 0 || player.motionY != 0) {
            animation = getWalkAnimation(player);
            return animation.getFrameAt(player.getExistingTicks() % animation.duration);
        } else {
            animation = getIdleAnimation(player);
        }

        return animation.getFrameAt(player.getExistingTicks() % animation.duration);
    }

    private SpriteAnimation getWalkAnimation(Player player) {
        return player.getDirection() == EnumDirection.LEFT ? walkLeftAnimation : walkRightAnimation;
    }

    private SpriteAnimation getIdleAnimation(Player player) {
        return player.getDirection() == EnumDirection.LEFT ? idleLeftAnimation : idleRightAnimation;
    }

    private SpriteAnimation getJumpRightAnimation(Player player) {
        return player.getDirection() == EnumDirection.LEFT ? jumpLeftAnimation : jumpRightAnimation;
    }

}
