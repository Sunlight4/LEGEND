package com.sunlight4.legend;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Volt extends RedCard {

	public Volt(TextureRegion t) {
		super(t);
		keycode=Keys.V;
		
		// TODO Auto-generated constructor stub
	}

	@Override
	void execute(Character object) {
		// TODO Auto-generated method stub
		object.effect=Effect.Volt;
		object.time=600;
	}

}
