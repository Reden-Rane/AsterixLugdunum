package fr.info.game.logic.entity.particle;

public class SplashParticle extends Particle {

    public SplashParticle(long lifetime) {
        super(lifetime);
    }

    public SplashParticle(float x, float y, long lifetime) {
        super(x, y, lifetime);
    }

    public SplashParticle(float x, float y, int width, int height, long lifetime) {
        super(x, y, width, height, lifetime);
    }

    public SplashParticle(float x, float y, float z, int width, int height, long lifetime) {
        super(x, y, z, width, height, lifetime);
    }
}
