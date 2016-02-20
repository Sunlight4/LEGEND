package com.sunlight4.legend;

import java.util.ArrayList;

import com.badlogic.gdx.maps.MapProperties;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.SnapshotArray;
import com.sunlight4.legend.physics.GameObject;

public class Switch extends CulledObject {
    private TextureRegion region;
	private Rectangle bounds;
	private Legend game;
	String action;
	String data;
	MapProperties props;
	boolean active = false;
    public Switch(float x, float y, TextureRegion img, String a, String d, Legend g, MapProperties p) {
    	super();
    	setX(x);
    	setY(y);
    	region=img;
    	props=p;
    	game=g;
    	action=a;
    	data = d;
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
		if (active && action.equals("move")) {
			SnapshotArray<Actor> array = game.platforms.getChildren();
	    	Actor[] items = array.begin();
	    	active=false;
	    	for (int i = 0, n = array.size; i < n; i++) {
	    	    GameObject platform = (GameObject) items[i];
	    	    if (platform.getName()==null) {
	    	    	continue;
	    	    }
	    	    if (platform.getName().equals(data)) {
	    	    	int y = Integer.valueOf((String) props.get("y"));
	    	    	if (platform.pos.y<y) {
	    	    		platform.pos.y+=1;
	    	    		active=true;
	    	    	}
	    	    	else if (platform.pos.y>y) {
	    	    		platform.pos.y-=1;
	    	    		active=true;
	    	    	}
	    	    }
	    	    
	    	}
	    	array.end();
		}
		else if (active && action.equals("elevator_rise")) {
			SnapshotArray<Actor> array = game.platforms.getChildren();
	    	Actor[] items = array.begin();
	    	active=false;
	    	for (int i = 0, n = array.size; i < n; i++) {
	    	    GameObject platform = (GameObject) items[i];
	    	    if (platform.getName()==null) {
	    	    	continue;
	    	    }
	    	    if (platform.getName().startsWith((String) props.get("data"))) {
	    	    platform.pos.y+=2;
	    	    }
	    	    
	    	}
	    	array.end();
		}
		else if (active && action.equals("elevator_left")) {
			SnapshotArray<Actor> array = game.platforms.getChildren();
	    	Actor[] items = array.begin();
	    	active=false;
	    	for (int i = 0, n = array.size; i < n; i++) {
	    	    GameObject platform = (GameObject) items[i];
	    	    if (platform.getName()==null) {
	    	    	continue;
	    	    }
	    	    if (platform.getName().startsWith((String) props.get("data"))) {
	    	    platform.pos.x-=2;
	    	    }
	    	    
	    	}
	    	array.end();
		}
		else if (active && action.equals("elevator_sink")) {
			SnapshotArray<Actor> array = game.platforms.getChildren();
	    	Actor[] items = array.begin();
	    	for (int i = 0, n = array.size; i < n; i++) {
	    	    GameObject platform = (GameObject) items[i];
	    	    if (platform.getName()==null) {
	    	    	continue;
	    	    }
	    	    if (platform.getName().startsWith((String) props.get("data"))) {
	    	    	platform.pos.y-=2;
	    	    }
	    	    
	    	}
	    	array.end();
		}
		else if (active && action.equals("elevator_right")) {
			SnapshotArray<Actor> array = game.platforms.getChildren();
	    	Actor[] items = array.begin();
	    	active=false;
	    	for (int i = 0, n = array.size; i < n; i++) {
	    	    GameObject platform = (GameObject) items[i];
	    	    if (platform.getName()==null) {
	    	    	continue;
	    	    }
	    	    if (platform.getName().startsWith((String) props.get("data"))) {
	    	    	platform.pos.x+=2;
	    	    }
	    	    
	    	}
	    	array.end();
		}
		if (game.player.bounds.overlaps(bounds)) {
			if (action.equals("light")) {

				game.dark=false;
			}
			else if (action.equals("move")) {
				active=true;
				if (game.activeswitch!=null && game.activeswitch!=this) {
					game.activeswitch.active=false;
				}
				game.activeswitch=this;
				active=true;
			}
			else if (action.equals("elevator_rise")) {
				active=true;
				if (game.activeswitch!=null && game.activeswitch!=this) {
					game.activeswitch.active=false;
				}
				game.activeswitch=this;
				active=true;
				SnapshotArray<Actor> array = game.platforms.getChildren();
		    	Actor[] items = array.begin();
		    	for (int i = 0, n = array.size; i < n; i++) {
		    	    GameObject platform = (GameObject) items[i];
		    	    if (platform.getName()==null) {
		    	    	continue;
		    	    }
		    	    if (platform.getName().equals(props.get("data")+"_bar")) {
		    	    	platform.hidden=false;
		    	    }
		    	    
		    	}
		    	array.end();
			}
			else if (action.equals("elevator_right")) {
				active=true;
				if (game.activeswitch!=null && game.activeswitch!=this) {
					game.activeswitch.active=false;
				}
				game.activeswitch=this;
				active=true;
				SnapshotArray<Actor> array = game.platforms.getChildren();
		    	Actor[] items = array.begin();
		    	for (int i = 0, n = array.size; i < n; i++) {
		    	    GameObject platform = (GameObject) items[i];
		    	    if (platform.getName()==null) {
		    	    	continue;
		    	    }
		    	    if (platform.getName().equals(props.get("data")+"_bar")) {
		    	    	platform.hidden=false;
		    	    }
		    	    
		    	}
		    	array.end();
			}
			else if (action.equals("elevator_left")) {
				active=true;
				if (game.activeswitch!=null && game.activeswitch!=this) {
					game.activeswitch.active=false;
				}
				game.activeswitch=this;
				active=true;
				SnapshotArray<Actor> array = game.platforms.getChildren();
		    	Actor[] items = array.begin();
		    	for (int i = 0, n = array.size; i < n; i++) {
		    	    GameObject platform = (GameObject) items[i];
		    	    if (platform.getName()==null) {
		    	    	continue;
		    	    }
		    	    if (platform.getName().equals(props.get("data")+"_bar")) {
		    	    	platform.hidden=false;
		    	    }
		    	    
		    	}
		    	array.end();
			}
			else if (action.equals("elevator_sink")) {
				active=true;
				if (game.activeswitch!=null && game.activeswitch!=this) {
					game.activeswitch.active=false;
				}
				game.activeswitch=this;
				active=true;
				SnapshotArray<Actor> array = game.platforms.getChildren();
		    	Actor[] items = array.begin();
		    	for (int i = 0, n = array.size; i < n; i++) {
		    	    GameObject platform = (GameObject) items[i];
		    	    if (platform.getName()==null) {
		    	    	continue;
		    	    }
		    	    if (platform.getName().equals(props.get("data")+"_bar")) {
		    	    	platform.hidden=false;
		    	    }
		    	    
		    	}
		    	array.end();
			}
			else if (action.equals("stop")) {
				game.activeswitch.active=false;
			}
			else if (action.equals("pressure")) {
				game.player.pressure=true;
			}
			else if (action.equals("no_pressure")) {
				game.player.pressure=false;
			}
			else if (action.equals("freeze")) {
				game.player.effect=Effect.Frozen;
			}
			else if (action.equals("burn")) {
				game.player.effect=Effect.Burning;
			}
			else if (action.equals("volt")) {
				game.player.effect=Effect.Volt;
			}
			else if (action.equals("enchant")) {
				game.player.effect=Effect.Enchanted;
			}
			else if (action.equals("elevator_stop")) {
				if (game.activeswitch!=null) {
					game.activeswitch.active=false;
				}
				SnapshotArray<Actor> array = game.platforms.getChildren();
		    	Actor[] items = array.begin();
		    	for (int i = 0, n = array.size; i < n; i++) {
		    	    GameObject platform = (GameObject) items[i];
		    	    if (platform.getName()==null) {
		    	    	continue;
		    	    }
		    	    if (platform.getName().equals(props.get("data")+"_bar")) {
		    	    	platform.hidden=true;
		    	    }
		    	    
		    	}
		    	array.end();
			}
			
			
		}
		
	}
}
