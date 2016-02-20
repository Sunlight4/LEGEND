package com.sunlight4.legend;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.sunlight4.legend.physics.GameObject;

public class MoveRight extends Card {
	public MoveRight(TextureRegion t) {
		super(t);
		keycode=Keys.RIGHT;
		// TODO Auto-generated constructor stub
		primary = true;
		name="right";
	}
	@Override
	void execute(Character object) {
		// TODO Auto-generated method stub
		int num = (object.pressure ? 4 : 3);
		
		if (object.vel.x<num) {
			object.forces.add(new Vector2(num, 0).scl(object.mass));
		}
	}
	
	

}
