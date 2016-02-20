package com.sunlight4.legend;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Fast extends RedCard {

	public Fast(TextureRegion t) {
		super(t);
		keycode=Keys.X;
		
		// TODO Auto-generated constructor stub
	}

	@Override
	void execute(Character object) {
		// TODO Auto-generated method stub
		object.effect=Effect.Burning;
		object.time=600;
	}

}
