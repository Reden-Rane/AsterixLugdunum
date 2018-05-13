package fr.info.game.logic.entity;

import fr.info.game.logic.AxisAlignedBoundingBox;

public class Boat extends Entity {

    private final AxisAlignedBoundingBox boatAABB;

    public Boat() {
        super(0, 0, 240, 195);
        this.boatAABB = new AxisAlignedBoundingBox(0, 0, getWidth(), 50);
    }

    @Override
    public AxisAlignedBoundingBox getCollisionBox() {
        return boatAABB;
    }
}
