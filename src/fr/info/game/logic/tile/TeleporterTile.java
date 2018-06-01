package fr.info.game.logic.tile;

import fr.info.game.logic.entity.particle.OrbParticle;
import fr.info.game.logic.level.Level;

public class TeleporterTile extends Tile {

    TeleporterTile() {
        super("teleporter");
    }

    @Override
    public void update(Level level, int x, int y) {
        super.update(level, x, y);

        for (int i = 0; i < (int) (Math.random() * 2); i++) {
            float orbX = (float) (Math.random() + x - 0.1);
            float orbY = (float) (Math.random() + y);
            float scale = (float) ((Math.random() + 1) * 0.25F);
            OrbParticle orb = new OrbParticle(orbX, orbY, scale, scale, 20, 10, 5, (int) (Math.random() * 2));
            level.spawnEntity(orb);
        }

    }
}
