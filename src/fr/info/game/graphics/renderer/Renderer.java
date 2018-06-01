package fr.info.game.graphics.renderer;

import fr.info.game.graphics.RenderManager;

public abstract class Renderer<T> {

	protected final RenderManager renderManager;

	public Renderer(RenderManager renderManager) {
		this.renderManager = renderManager;
	}

    public void render(T obj, float partialTicks) {
	    this.renderAt(obj, 0, 0, 0, partialTicks);
    }

	public void renderAt(T obj, float x, float y, float z, float partialTicks) {}

}
