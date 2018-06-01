package fr.info.game.logic.entity.particle;

public class OrbParticle extends Particle {

    public final int variant;
    public final long fadeInDuration;
    public final long fadeOutDuration;

    public OrbParticle(float x, float y, float width, float height, long lifetime, long fadeInDuration, long fadeOutDuration, int variant) {
        super(x, y, width, height, lifetime);
        this.variant = variant;
        this.fadeInDuration = fadeInDuration;
        this.fadeOutDuration = fadeOutDuration;
        this.motionY = 0.01F;
    }

    @Override
    public void update() {
        super.update();
    }
}
