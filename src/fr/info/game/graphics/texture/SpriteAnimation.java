package fr.info.game.graphics.texture;

public class SpriteAnimation {

	private TextureAtlas parentAtlas;
	public final String name;
	public final TextureSprite[] frames;
	public final double[] periods;
	public final double duration;

	public SpriteAnimation(String name, TextureSprite[] frames, double[] periods) {
		this.name = name;
		this.frames = frames;
		this.periods = periods;
		this.duration = computeDuration();
	}

	public int computeDuration() {
		int duration = 0;
		for(double period : periods) {
			duration += period;
		}
		return duration;
	}

	public TextureSprite getFrameAt(double time) {
		int frame = 0;
		double t = 0;
		while (t + periods[frame] < time) {
			t += periods[frame];
			frame++;
		}
		return frames[frame];
	}

	public TextureAtlas getParentAtlas() {
		return parentAtlas;
	}

	public void setParentAtlas(TextureAtlas parentAtlas) {
		this.parentAtlas = parentAtlas;
	}
}
