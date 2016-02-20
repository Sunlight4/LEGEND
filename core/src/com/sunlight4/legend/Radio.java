package com.sunlight4.legend;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.SnapshotArray;
import com.sunlight4.legend.physics.GameObject;

public class Radio extends Card {
	int message;
	public Radio(TextureRegion t) {
		super(t);
		keycode=Keys.Z;
		// TODO Auto-generated constructor stub
		primary = false;
		message=0;
		name="radio";
	}
	@Override
	void execute(Character c) {
		Player player = (Player) c;
		System.out.println(player.game.level);
		if (player.game.dark) {
			return;
		}
		message++;
		if (player.game.level.equals("sector185.tmx")) {
			switch (message) {
			case 1:
				player.setSound("???: Great! You're now in Area 185. I'm in the next sector.");
				break;
			default:
				player.setSound("<static>");
				break;
			}
		}
		else {
			player.setSound("<no signal>");
		}
		
		
	}
}
	
	

