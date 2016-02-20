package com.sunlight4.legend;

import java.util.ArrayList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.sunlight4.legend.physics.GameObject;

public class Character extends GameObject {
	public Character(float x, float y, boolean wall, TextureRegion t, int mass,
			Vector2 n, int bouncy) {
		super(x, y, wall, t, mass, n, bouncy);
		// TODO Auto-generated constructor stub
	}

	boolean pressure;
	Card selectedcard;
	Card using;
	Effect effect;
	static final int MAX_VELOCITY = 10000;
	ArrayList<Card> cards = new ArrayList<Card>();
	ArrayList<Card> primaries = new ArrayList<Card>();
	Legend game;
	AssetManager manager;
	boolean recharge;
	float a = 0;
	int time = 0;
	Card drawing = null;
	int track;
	AI ai;
	String sound = null;
	@Override
	public void act(float delta) {
		super.act(delta);
		if (using!=null) {
			using.execute(this);
		}
		ai.update();
		if (time>0) {
			time--;
			if (time==0) {
				effect = Effect.Normal;
			}
		}
	}

}
