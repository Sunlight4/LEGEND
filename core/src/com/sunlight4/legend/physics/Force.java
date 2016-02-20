package com.sunlight4.legend.physics;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.sunlight4.legend.physics.GameObject;

public abstract class Force {
	abstract void execute(GameObject object);
	public void execute(Group group) {
		for (Actor g : group.getChildren()) {
			if (g instanceof Group) {
				execute((Group) g);
			}
			else {
				GameObject go = (GameObject) g;
				execute(go);
			}
			
		}
	}
}

