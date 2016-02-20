package com.sunlight4.legend;

public class AnimatedTile extends Wall {
	Legend game;
	String animid;
	int frame;
	int update = 0;
	public AnimatedTile(float x, float y, boolean wall, int mass, int bouncy, Legend g, int f, String a) {
		super(x, y, wall, g.animations.get(a).get(f), mass, bouncy);
		game=g;
		animid=a;
		frame=f;
		// TODO Auto-generated constructor stub
	}
	public void act(float delta) {
		super.act(delta);
		update++;
		if (update == 12) {
		update = 0;
		frame++;
		if (!game.animations.get(animid).containsKey(frame)) {
			frame=0;
		}
		region=game.animations.get(animid).get(frame);
		}
	}

}
