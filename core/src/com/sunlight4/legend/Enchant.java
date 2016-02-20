package com.sunlight4.legend;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enchant extends RedCard {

	public Enchant(TextureRegion t) {
		super(t);
		keycode=Keys.C;
		
		// TODO Auto-generated constructor stub
	}

	@Override
	void execute(Character object) {
		// TODO Auto-generated method stub
		object.effect=Effect.Enchanted;
		object.time=600;
	}

}
