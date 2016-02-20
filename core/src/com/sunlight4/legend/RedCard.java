package com.sunlight4.legend;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RedCard extends Card {

	public RedCard(TextureRegion t) {
		super(t);
		// TODO Auto-generated constructor stub
	}
	@Override
	void execute(Character object) {
		// TODO Auto-generated method stub

	}
	public void play(Character p) {
		System.out.println(name);
		if (p.pressure) {
			p.game.hud.addActor(new PressureCard(1, this, p, -1));
		}
		else {
			execute(p);
		}
	}

}
