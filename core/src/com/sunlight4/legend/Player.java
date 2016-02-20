package com.sunlight4.legend;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Player extends Character {
	Card selectedcard;
	static final int MAX_VELOCITY = 10000;
	AssetManager manager;
	boolean recharge;
	float a = 0;
	String sound = null;
	public void select(Card c) {
		selectedcard = c;
		game.fromHand();
		game.toGame();
		
	}
	public Player(float x, float y, boolean wall, TextureRegion t, int mass,
			Vector2 n, int bouncy, Legend g, AssetManager m, List<String> cs, Effect e) {
		super(x, y, wall, t, mass, n, bouncy);
		game=g;
		effect=e;
		manager = m;
		recharge = false;
		cards = new ArrayList<Card>();
		primaries = new ArrayList<Card>();
		pressure=false;
		ai = new PlayerAI(this);
		for (String c : cs) {
			ai.addCard(fromName(c));
		}
		selectedcard = cards.get(0);
		// TODO Auto-generated constructor stub
		updateBounds();
		track=1;
	}
	@Override
	public void draw(Batch b, float parentAlpha) {
		setColor(effect.color);
		super.draw(b, parentAlpha);
		if (sound==null) {
			
		}
		else {
			if (a>0 && !game.dark) {
				a-=(1.0f/120f);
				game.font.setColor(1,1,1,a);
				game.font.draw(b, sound, pos.x, pos.y);
			}
		}
		
		Batch batch = game.hud.getBatch();
		if (!pressure && drawing != null) {
			batch.begin();
			batch.draw(drawing.img, 213, 12);
			batch.end();
		}
		Color color = getColor();
		batch.begin();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(region, 213, 240, getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        batch.end();
	}
	public void setSound(String s) {
		sound=s;
		a=1;
	}
		
	public void setUpHand(Group ui) {
		int x = 0;
		for (Card c : cards) {
			CardButton button = new CardButton(c, this, x, 0);
			x += 48;
			ui.addActor(button);
		}
	}
	public void act(float delta) {
		super.act(delta);
		
		updateBounds();
	}
	public void drawHand(Batch batch) {
		// TODO Auto-generated method stub
			
	}
	public Card fromName(String name) {
		if (name.equals("left")) {
			return new MoveLeft(new TextureRegion(manager.get("data/img/cards/leftmove.png", Texture.class)));
		}
		else if (name.equals("right")) {
			return new MoveRight(new TextureRegion(manager.get("data/img/cards/rightmove.png", Texture.class)));
		}
		else if (name.equals("jump")) {
			return new Jump(new TextureRegion(manager.get("data/img/cards/jump.png", Texture.class))); 
		}
		else if (name.equals("radio")) {
			return new Radio(new TextureRegion(manager.get("data/img/cards/radio.png", Texture.class)));
		}
		else if (name.equals("slow")) {
			return new Slow(new TextureRegion(manager.get("data/img/cards/slow.png", Texture.class)));
		}
		else if (name.equals("fast")) {
			return new Fast(new TextureRegion(manager.get("data/img/cards/fast.png", Texture.class)));
		}
		else if (name.equals("enchant")) {
			return new Enchant(new TextureRegion(manager.get("data/img/cards/rewind.png", Texture.class)));
		}
		else if (name.equals("volt")) {
			return new Volt(new TextureRegion(manager.get("data/img/cards/volt.png", Texture.class)));
		}
		else {
			return null;
		}
	}
	
}
