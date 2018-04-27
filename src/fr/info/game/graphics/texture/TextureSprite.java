package fr.info.game.graphics.texture;

public class TextureSprite {

	private TextureAtlas parentAtlas;
	public final String name;
	public final int x;
	public final int y;
	public final int width;
	public final int height;

	public TextureSprite(String name, int x, int y, int width, int height) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public TextureAtlas getParentAtlas() {
		return parentAtlas;
	}

	public void setParentAtlas(TextureAtlas parentAtlas) {
		this.parentAtlas = parentAtlas;
	}
}
