package com.sunlight4.legend;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class CardObject extends CulledObject {
	 private TextureRegion region;
	protected Player player;
	private Card card;
	private Rectangle bounds;

	public CardObject(float x, float y, TextureRegion img, Player g, String c) {
	    	super();
	    	setX(x);
	    	setY(y);
	    	region=new TextureRegion(img);
	    	player=g;
	    	if (c.equals("left")) {
	    		card = new MoveLeft(img);
	    	}
	    	else if (c.equals("right")) {
	    		card = new MoveRight(img);
	    	}
	    	else if (c.equals("jump")) {
	    		card = new Jump(img);
	    	}
	    	else if (c.equals("radio")) {
	    		card = new Radio(img);
	    	}
			setWidth(region.getRegionWidth());
			setHeight(region.getRegionHeight());
	    	bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
	    	
	    	
	    }
	@Override
    public void draw (Batch batch, float parentAlpha) {
		if (isCulled()) return;
    	//setRotation(body.getAngle());
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        
    }
	public void act(float delta) { 
		if (isCulled()) return;
		if (player.bounds.overlaps(bounds)) {
			player.ai.addCard(card);
			remove();
		}
	}
}
