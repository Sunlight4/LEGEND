package com.sunlight4.legend;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class MoveLeft extends Card {
	
	public MoveLeft(TextureRegion t) {
		super(t);
		// TODO Auto-generated constructor stub
		keycode=Keys.LEFT;
		 primary = true;
		 name="left";
	}
	@Override
	void execute(Character object) {
		// TODO Auto-generated method stub
		int num = (object.pressure ? 4 : 3);
		if (object.vel.x>-num) {
			object.forces.add(new Vector2(-num, 0).scl(object.mass));
		}
	}
	

}
