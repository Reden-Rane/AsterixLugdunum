package fr.info.game.logic.entity.pacman;

import fr.info.game.logic.entity.Entity;
import fr.info.game.logic.level.tavern.TavernLevel;

public abstract class PacmanEntity extends Entity {

    private final TavernLevel tavernLevel;

    public PacmanEntity(TavernLevel tavernLevel, float width, float height) {
        super(width, height);
        this.tavernLevel = tavernLevel;
    }

    @Override
    protected void updateEntityPosition() {
        //Collision detection
        float newX = this.x + this.motionX;
        float newY = this.y + this.motionY;

        if (!canMoveTo(newX, newY)) {
            newX = x;
            newY = y;
            motionX = 0;
            motionY = 0;
        }

        this.x = newX;
        this.y = newY;
    }

    public boolean canMoveTo(float x, float y) {
        float leftX = x + getCollisionBox().x;
        float downY = y + getCollisionBox().y;
        float rightX = x + getCollisionBox().x + getCollisionBox().width;
        float upY = y + getCollisionBox().y + getCollisionBox().height;

        if (leftX > 0 && rightX < tavernLevel.getMapWidth() && downY > 0 && upY < tavernLevel.getMapHeight()) {
            /*
            We check that every corner and the center of the bounding box doesn't collides with any terrain tile
            On vérifie que chaque coin et le centre de la boite de collision n'entre pas en collision avec une tuile
             */
            return tavernLevel.getTerrain()[(int) leftX][(int) downY] == null
                    && tavernLevel.getTerrain()[(int) leftX][(int) upY] == null
                    && tavernLevel.getTerrain()[(int) rightX][(int) downY] == null
                    && tavernLevel.getTerrain()[(int) rightX][(int) upY] == null
                    && tavernLevel.getTerrain()[(int) ((rightX + leftX) / 2)][(int) ((upY + downY) / 2)] == null;
        }
        return false;
    }

}
