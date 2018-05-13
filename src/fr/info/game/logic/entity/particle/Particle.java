package fr.info.game.logic.entity.particle;

import fr.info.game.logic.entity.Entity;

public abstract class Particle extends Entity {

    private final long lifetime;

    public Particle(long lifetime) {
        this.lifetime = lifetime;
    }

    public Particle(float x, float y, long lifetime) {
        super(x, y);
        this.lifetime = lifetime;
    }

    public Particle(float x, float y, int width, int height, long lifetime) {
        super(x, y, width, height);
        this.lifetime = lifetime;
    }

    public Particle(float x, float y, float z, int width, int height, long lifetime) {
        super(x, y, z, width, height);
        this.lifetime = lifetime;
    }

    @Override
    public void update() {
        super.update();
        if(this.getExistingTicks() >= this.lifetime) {
            setDead();
        }
    }

    public long getLifetime() {
        return lifetime;
    }
}
