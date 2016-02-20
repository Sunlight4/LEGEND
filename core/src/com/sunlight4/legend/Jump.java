package com.sunlight4.legend;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Jump extends Card {
	public Jump(TextureRegion t) {
		super(t);
		keycode=Keys.SPACE;
		name="jump";
		// TODO Auto-generated constructor stub
	}

	@Override
	void execute(Character object) {
		if (object.normals.size()>0) {
			object.vel = object.vel.add(new Vector2(object.game.gravity.direction).scl(-30));
			object.pos.y+=48;
		}
		// TODO Auto-generated method stub
	}
	
	

}
