package fr.info.game.logic.entity.particle;

import fr.info.game.logic.entity.Entity;

public abstract class Particle extends Entity {

    public final long lifetime;

    public Particle(long lifetime) {
        this.lifetime = lifetime;
    }

    public Particle(float x, float y, long lifetime) {
        super(x, y);
        this.lifetime = lifetime;
    }

    public Particle(float x, float y, float width, float height, long lifetime) {
        super(x, y, width, height);
        this.lifetime = lifetime;
    }

    public Particle(float x, float y, float z, float width, float height, long lifetime) {
        super(x, y, z, width, height);
        this.lifetime = lifetime;
    }

    @Override
    public void update() {
        super.update();
        if(this.getExistingTicks() >= this.lifetime) {
            isDead = true;
        }
    }
}
