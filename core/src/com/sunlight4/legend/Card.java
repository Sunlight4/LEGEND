package com.sunlight4.legend;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Card {
	abstract void execute(Character object);
	boolean primary = false;
	int keycode;
	String name;
	TextureRegion img;
	public Card(TextureRegion t) {
		img = t;
		
	}
	public void play(Character p) {
		if (p.pressure) {
			p.game.hud.addActor(new PressureCard(p.track, this, p, 1));
		}
		else {
			execute(p);
		}
	}

}
