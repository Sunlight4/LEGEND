package com.sunlight4.legend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

public enum Effect {
Frozen(new Color(0.3f,0.3f,1,1)) {
	@Override
	void act(PressureCard c) {
		c.x+=c.direction*0.5;
		
	}
},
Burning(new Color(1,0.3f,0.3f,1)) {
	@Override
	void act(PressureCard c) {
		c.x+=c.direction*2;
		
	}
},
Enchanted(new Color(0.3f, 1, 0.3f, 1)) {
	@Override
	void act(PressureCard c) {
		c.x+=c.direction;
		
	}
},
Normal(new Color(1, 1, 1, 1)) {
	@Override
	void act(PressureCard c) {
		c.x+=c.direction;
		
	}
},
Volt(new Color(1, 1, 0, 1)) {
	@Override
	void act(PressureCard c) {
		if (Gdx.input.getX()>c.x) {
			c.direction = 1;
		}
		else {
			c.direction = -1;
		}
		
	}
};
Color color;
abstract void act(PressureCard c);
Effect(Color c) {
	color = c;
}
}
