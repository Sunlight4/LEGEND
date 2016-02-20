package com.sunlight4.legend.physics;

import com.badlogic.gdx.math.Vector2;

public class Gravity extends Force {
	public Vector2 direction;
	public Gravity(Vector2 d) {
		direction=d;
	}
	@Override
	void execute(GameObject object) {
		
		object.forces.add(new Vector2(direction).scl(2).scl(object.mass));
	}

}
