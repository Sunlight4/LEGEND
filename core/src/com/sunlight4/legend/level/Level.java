package com.sunlight4.legend.level;

import com.badlogic.gdx.maps.tiled.TiledMapTile;

public interface Level {
	public abstract void createTile(float x, float y, TiledMapTile tile);
}
