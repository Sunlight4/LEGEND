package com.sunlight4.legend;

import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class CardButton extends ImageButton {
	Card card;
	Player p;
	public CardButton(Card card, Player player, int x, int y) {
		super(new TextureRegionDrawable(new TextureRegion(card.img)));
		this.card=card;
		p=player;
		setX(x);
		setY(y);
		// TODO Auto-generated constructor stub
	}

	public void act(float delta) {
		super.act(delta);
		if (isPressed()) {
			p.select(card);
		}
	}
}
