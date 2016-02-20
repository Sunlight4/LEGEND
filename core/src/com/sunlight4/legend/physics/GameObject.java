package com.sunlight4.legend.physics;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.SnapshotArray;
import com.sunlight4.legend.CulledObject;

public class GameObject extends CulledObject {
	public Vector2 pos;
	public boolean glowing = false;
	public Vector2 vel;
	public ArrayList<Vector2> forces = new ArrayList<Vector2>();
	public ArrayList<Vector2> normals = new ArrayList<Vector2>();
	public ArrayList<GameObject> platforms = new ArrayList<GameObject>();
	protected int id;
	public Rectangle bounds;
	float platformx = 0;
	public int mass;
	static int nextid = 0;
	protected TextureRegion region;
	public boolean hidden = false;
	boolean wall;
	protected int bouncy = 1;
	protected boolean update;
	public Vector2 normal = new Vector2(0, 0);
	public boolean jump;
	Vector2 lastpos;
	public float friction = 0.1f;
	public GameObject(float x, float y, boolean wall, TextureRegion t, int mass, Vector2 n, int bouncy) {
		super();
		this.wall=wall;
		pos = new Vector2(x, y);
		lastpos=pos;
		vel = new Vector2(0, 0);
		id = nextid;
		nextid ++;
		normal = n;
		this.mass=mass;
		region = t;
		setWidth(region.getRegionWidth());
		setHeight(region.getRegionHeight());
		updateBounds();
	}
	
	public Vector2 getNormal(Vector2 diff) {
		return normal; 
	}
	public void updateBounds() {
		bounds = new Rectangle(pos.x, pos.y, getWidth(), getHeight());
	}
	public void frictionUpdate(GameObject platform) {
		if (vel.x>0) {
			vel.x-=platform.friction;
		}
		if (0<vel.x && vel.x<platform.friction) {
			vel.x=0;
		}
		if (vel.x<0) {
			vel.x+=platform.friction;
		}
		if (0>vel.x && vel.x>-platform.friction) {
			vel.x=0;
		}
	}
    @Override
    public void draw (Batch batch, float parentAlpha) {
    	if (hidden) return;
    	if (isCulled()) return;
    	setX((pos.x+lastpos.x)/2f);
       	setY((pos.y+lastpos.y)/2f);
    	update = !update;
    	//setRotation(body.getAngle());
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(region, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        //batch.draw(region, pos.x, pos.y);
    }
    
    public Vector2 sumForces() {
    	Vector2 result = new Vector2(0, 0);
    	for (Vector2 f : forces) {
    		result = result.add(f.scl(1.0f/mass));
    	}
    	forces = new ArrayList<Vector2>();
    	return result;
    }
    public Vector2 sumForcesXOnly() {
    	Vector2 result = new Vector2(0, 0);
    	for (Vector2 f : forces) {
    		result = result.add(f.scl(1.0f/mass).x, 0);
    	}
    	return result;
    }
    public void act(float delta) {
    	
    	normals = new ArrayList<Vector2>();
    	lastpos=pos;
    	if (isCulled()) return;
    	updateBounds();
    	if (wall) {
    		return;
    	}
    	platforms = new ArrayList<GameObject>();
    	//vel=sumForces();
    	//TODO:Find platform
    	SnapshotArray<Actor> array = ((Group) getParent().getParent().findActor("platforms")).getChildren();
    	Actor[] items = array.begin();
    	
    	Rectangle predict = new Rectangle(bounds);
    	predict.x += vel.x/2.0;
    	predict.y += vel.y/2.0;
    	for (int i = 0, n = array.size; i < n; i++) {
    		GameObject platform = (GameObject) items[i];
    		if (platform.hidden) continue;
    		if (platform.bounds.overlaps(predict) || platform.bounds.overlaps(bounds)) {
    			platforms.add(platform);
    			
    		}
    	}
    	while (platforms.size() > 0) {
    		GameObject platform = platforms.get(0);
    		if (!platform.bounds.overlaps(bounds)) {
    			platforms.remove(0);
    		}
    		else {
    			
    			Vector2 diff = new Vector2(platform.pos).add(new Vector2(pos).scl(-1));
    			Vector2 normal = platform.getNormal(diff);
    			normals.add(normal);
    			pos = pos.add(new Vector2(normal).scl(diff.len()/24f));
    			updateBounds();
    			vel = new Vector2(0,0);
    			// Calculate relative velocity
    			Vector2 rv =  new Vector2(vel).scl(-1);    			 
    			// Calculate relative velocity in terms of the normal direction
    			  float velAlongNormal = Math.abs(rv.dot(normal));
    			 vel = vel.add(normal.scl(velAlongNormal).scl(platform.bouncy));
    			 
    		}
    	}
    	Vector2 sum = sumForces();
    	vel = vel.add(new Vector2(sum));
    	if (vel.y < -15) {
    		vel.y = 0;
    	}
    	pos  = pos.add(vel);
     	array.end();
     	
    }

	
	
}
