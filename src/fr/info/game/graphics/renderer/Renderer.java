package fr.info.game.graphics.renderer;

import fr.info.game.graphics.RenderManager;

public abstract class Renderer<T> {

	protected final RenderManager renderManager;

	public Renderer(RenderManager renderManager) {
		this.renderManager = renderManager;
	}

	public abstract void render(T obj, float partialTicks);

}
