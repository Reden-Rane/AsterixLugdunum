package fr.info.game.logic.level;

public class Camera {

    private float offsetX;
    private float offsetY;
    private float zoom;
    private float prevOffsetX;
    private float prevOffsetY;
    private float prevOffsetZ;

    public Camera() {
        this(0, 0, 1);
    }

    public Camera(float offsetX, float offsetY, float zoom) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.zoom = zoom;
        this.prevOffsetX = offsetX;
        this.prevOffsetY = offsetY;
        this.prevOffsetZ = zoom;
    }

    public void update() {
        this.prevOffsetX = offsetX;
        this.prevOffsetY = offsetY;
        this.prevOffsetZ = zoom;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
    }

    public float getPrevOffsetX() {
        return prevOffsetX;
    }

    public float getPrevOffsetY() {
        return prevOffsetY;
    }

    public float getPrevOffsetZ() {
        return prevOffsetZ;
    }
}
