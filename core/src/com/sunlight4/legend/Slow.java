package com.sunlight4.legend;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Slow extends RedCard {

	public Slow(TextureRegion t) {
		super(t);
		keycode=Keys.Z;
		
		// TODO Auto-generated constructor stub
	}

	@Override
	void execute(Character object) {
		// TODO Auto-generated method stub
		object.effect=Effect.Frozen;
		object.time=600;
	}

}
