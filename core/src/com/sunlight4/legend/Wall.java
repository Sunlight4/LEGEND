package com.sunlight4.legend;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.sunlight4.legend.physics.GameObject;

public class Wall extends GameObject {

	public Wall(float x, float y, boolean wall, TextureRegion t, int mass, int bouncy) {
		super(x, y, wall, t, mass,new Vector2(0, 1), bouncy);
		// TODO Auto-generated constructor stub
	}
	@Override
	public Vector2 getNormal(Vector2 diff) {
		if (diff.angle()<45) {
			return new Vector2(-1, 0);
		}
		else if (diff.angle()<135) {
			return new Vector2(0, -1);
		}
		else if (diff.angle()<225) {
			return new Vector2(1, 0);
		}
		else if (diff.angle()<315) {
			return new Vector2(0, 1);
		}
		else {
			return new Vector2(-1, 0);
		}
	}
		
	
}
