package com.sunlight4.legend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class PlayerAI extends AI {
	public PlayerAI(Character o) {
		super(o);
		// TODO Auto-generated constructor stub
	}
	@Override
		public void addCard(final Card c) {
			owner.cards.add(c);
			if (!c.primary) {
			owner.game.hud.addListener(new InputListener() {
				public boolean keyDown(InputEvent event, int keycode) {
					if (c.keycode == keycode) {
						c.play(owner);
						owner.drawing=c;
						return true;
					}
					return false;
				}
			});
			}
			else {
				owner.primaries.add(c);
			}
	}
	public void update() {
		boolean moved = false;
		for (Card c : owner.primaries) {
			if (Gdx.input.isKeyPressed(c.keycode)) {
				c.play(owner);
				moved=true;
				owner.drawing=c;
			}
		}
		if (!moved && !owner.pressure) {
			owner.vel.x=0;
		}
		if (owner.pos.y<0) {
			owner.game.stage.clear();
			owner.game.hud.clear();
			owner.game.fromGame();
			owner.game.toLoading();
		}
	}

}
