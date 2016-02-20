package com.sunlight4.legend;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.SnapshotArray;

public class PressureCard extends Actor {
	Card card;
	Rectangle bounds;
	boolean used = false;
	TextureRegion region;
	boolean fading = false;
	Character user, victim;
	Effect effect;
	int track;
	float x, y;
	int direction;
	public PressureCard(int t, Card c, Character p, int d) {
		
		effect=p.effect;
		if (effect == Effect.Enchanted) {
			d=-d;
		}
		region = new TextureRegion(c.img);
		card = c;
		track = t;
		direction = d;
		x=(((3-direction)/2)-1)*640;
		y=12+(((3-track)/2)-1)*72;;
		user=p;
		setWidth(region.getRegionWidth());
		setHeight(region.getRegionHeight());
		updateBounds();
		victim = p.game.track(t);
	}
	public void updateBounds() {
		bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
	public void act(float delta) {
			effect.act(this);
			if (effect==Effect.Volt) {
				
			}
			float ux = victim.pos.x;
			float px = victim.game.player.pos.x;
			float ax = (ux-px)+213;
			if (Math.abs(x-ax)<=48) {
				if (!card.primary) {
					card.execute(user);
				}
				else {
					victim.using=card;
				}
				remove();
			};
			
			        
			 
		}
	
		 public void draw (Batch batch, float parentAlpha) {
		    	//setRotation(body.getAngle());
			 	setX(x);
			 	setY(y);
			 	setColor(effect.color);
		        Color color = getColor();
		        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
		        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
		        //batch.draw(region, pos.x, pos.y);
		    }
	}

