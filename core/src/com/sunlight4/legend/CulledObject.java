package com.sunlight4.legend;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CulledObject extends Actor {

    Rectangle actorRect = new Rectangle();
    Rectangle camRect = new Rectangle();
    boolean visible;

    protected boolean isCulled() {
    	return false;
    }
}
