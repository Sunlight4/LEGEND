package com.sunlight4.legend;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.Actor;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.sunlight4.legend.physics.Force;
import com.sunlight4.legend.physics.GameObject;
import com.sunlight4.legend.physics.Gravity;
public class Legend extends ApplicationAdapter {
	ArrayList<Card> savedcards = new ArrayList<Card>();
	Stage stage;
	Stage hud;
	public AssetManager manager;
	BitmapFont font;
	Batch batch;
	World world;
	boolean test = false;
	Group gameobjects;
	Switch activeswitch = null;
	ImageButton viewhand;
	Group platforms;
	Group pauseui;
	Character enemy;
	Player player;
	Group players;
	Gravity gravity;
	Image track1;
	Image track0;
	float x = 1200;
	String nextlevel;
	boolean saved=false;
	Map<String,Map<Integer, TextureRegion>> animations = new HashMap<String,Map<Integer, TextureRegion>>();
	String level;
	ArrayList<Force> forces = new ArrayList<Force>();
	Group ui;
	ArrayList<CardButton> cards = new ArrayList<CardButton>();
	int a = 1;
	int tutorial = 0;
	InputListener touchhandler = new InputListener() {
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			player.selectedcard.play(player);
			return true;
		}
	};
	String state = "menu";
	private ImageButton resume;
	boolean dark = false;
	private InputListener start;
	private ImageButton menu;
	public void toGame() {
		hud.addListener(touchhandler);
		state = "game";
		stage.addActor(objects);
		hud.addActor(gameui);
		stage.setKeyboardFocus(player);
	}
	public void fromHand() {
		handui.remove();
		
	}
	public Character track(int t) {
		switch (t) {
		case 1:return player;
		case 2:return enemy;
		default:return null;
		}
	}
	public void fromLoading() {
		manager.load("data/lvl/"+level, TiledMap.class);
    	objects = new Group();
    	gameobjects = new Group();
    	objects.addActor(gameobjects);
    	platforms = new Group();
    	platforms.setName("platforms");
    	bg = new Group();
    	objects.addActor(bg);
    	gameobjects.addActor(platforms);
    	players = new Group();
    	players.setName("players");
    	gameobjects.addActor(players);
    	ui = new Group();
    	ui.setName("ui");
    	gameui = new Group();
    	ui.addActor(gameui);
    	hud.addActor(ui);
    	track1 = new Image(manager.get("data/img/cards/track_player.png", Texture.class));
    	track0 = new Image(manager.get("data/img/cards/track_enemy.png", Texture.class));
    	track0.setY(72);
    	gameui.addActor(track1);
    	gameui.addActor(track0);
    	loadLevel(manager.get("data/lvl/"+level, TiledMap.class));
         // we are done loading, let's move to another screen!
    	
    	gravity = new Gravity(new Vector2(0, -1));
    	forces.add(gravity);
    	viewhand = new ImageButton(new TextureRegionDrawable(new TextureRegion(manager.get("data/img/cards/viewhand.png", Texture.class))));
    	gameui.addActor(viewhand);
    	viewhand.setY(408);
    	viewhand.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
            	fromGame();
               toHand();
            }
        });
    	pause = new ImageButton(new TextureRegionDrawable(new TextureRegion(manager.get("data/img/cards/pause.png", Texture.class))));
    	pause.setX(516);
    	pause.setY(432);
    	pause.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
            	fromGame();
               toPause();
            }
        });
    	gameui.addActor(pause);
    	
	    
    	
 
	}
	public void toHand() {
		 state = "hand";
         objects.remove();
         handui = new Group();
         handui.setName("ui");
         ui.addActor(handui);
         player.setUpHand(handui);
	}
	public void fromGame() {
		objects.remove();
		gameui.remove();
		stage.removeListener(touchhandler);
	}
	public void toPause() {
		state = "pause";
		pauseui = new Group();
		pauseui.setName("ui");
		ui.addActor(pauseui);
		resume = new ImageButton(new TextureRegionDrawable(new TextureRegion(manager.get("data/img/cards/resume.png", Texture.class))));
    	resume.setY(432);
    	resume.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
            	fromPause();
            	toGame();
            }
        });
    	pauseui.addActor(resume);
    	menu = new ImageButton(new TextureRegionDrawable(new TextureRegion(manager.get("data/img/cards/menu.png", Texture.class))));
    	menu.setY(384);
    	menu.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
            	fromPause();
            	savedcards=player.cards;
            	toMenu();
            }
        });
    	pauseui.addActor(menu);
	}
	
	public void toMenu() {
		state="menu";
		// TODO Auto-generated method stub
		start = new InputListener() {
			public boolean keyDown(InputEvent event, int keycode) {
				if (keycode == Keys.SPACE) {
				fromMenu();
				toLoading();
				}
				else if (keycode == Keys.TAB) {
					level="sector187.tmx";
					manager.load("data/lvl/"+level, TiledMap.class);
					Preferences prefs = Gdx.app.getPreferences("Save State");
					prefs.clear();
					menuscreen.remove();
					menuscreen = null;
					menuscreen = new Image(new TextureRegion(new Texture(Gdx.files.internal("data/img/menu/"+level.replace(".tmx", ".png")))));
			    	stage.addActor(menuscreen);
			    	menuscreen.setX(0);
			    	menuscreen.setY(0);
				}
				else if (keycode == Keys.ESCAPE) {
					Preferences prefs = Gdx.app.getPreferences("Save State");
					prefs.putString("level", level);
					List<String> names = new ArrayList<String>();
					for (Card c : savedcards) {
						names.add(c.name);
					}
					prefs.putString("cards", String.join(":", names));
					prefs.flush();
					Gdx.app.exit();
				}
				return true;
			}
		};
		hud.addListener(start);
		menuscreen = new Image(new TextureRegion(new Texture(Gdx.files.internal("data/img/menu/"+level.replace(".tmx", ".png")))));
    	stage.addActor(menuscreen);
    	menuscreen.setX(0);
    	menuscreen.setY(0);
		
	}
	public void fromPause() {
		pauseui.remove();
		
		
	}
	public void fromMenu() {
		menuscreen.remove();
		hud.removeListener(start);
	}
	public void toLoading() {
		state="loading";
	}
	public void createTile(float x, float y, TiledMapTile tile) {
		
		String type = (String) tile.getProperties().get("kind");
		TextureRegion img = tile.getTextureRegion();
		Preferences prefs = Gdx.app.getPreferences("Save State");
		if (type==null) {
			return;
		}
		if	(type.equals("marker") && tile.getProperties().get("data").equals("player")) {
			List<String> scards = new ArrayList<String>();
			 if (prefs.contains("cards")) {
			    	scards = Arrays.asList(prefs.getString("cards").split(":"));
			    	
			  }
			 else {
				 scards.add("left");
				 scards.add("right");
				 scards.add("jump");
				 scards.add("slow");
				 scards.add("fast");
				 scards.add("enchant");
				 scards.add("volt");
			 }
			 player = new Player(x, y, false, new TextureRegion(manager.get("data/img/player.png", Texture.class)), 50, new Vector2(0, 1), 0,  this, manager, scards, Effect.Normal);
			players.addActor(player);
			

		}
		else if (type.equals("switch")) {
			
			Switch s = new Switch(x, y, img, (String) tile.getProperties().get("action"),(String) tile.getProperties().get("data"), this, tile.getProperties());
			bg.addActor(s);
		}
		else if (type.equals("script")) {
			
			Script s = new Script(x, y, img, (String) tile.getProperties().get("action"),(String) tile.getProperties().get("data"), this, tile.getProperties());
			bg.addActor(s);
		}
		else if (type.equals("marker") && tile.getProperties().get("data").equals("exit")) {
			this.x=x;
			nextlevel=(String) tile.getProperties().get("sector");
		}
		else if (type.equals("bg")) {
			Image i = new Image(img);
			i.setX(x);
			i.setY(y);
			bg.addActor(i);
		}
		else if (type.equals("card")) {
			CardObject j = new CardObject(x, y, img, player, (String) tile.getProperties().get("card"));
			bg.addActor(j);
		}
		else if (type.equals("radio")) {
			AliceRadio r = new AliceRadio(x, y, img, player);
			bg.addActor(r);
		}
		else if (type.equals("terrain") && tile.getProperties().get("data").equals("special")) {
			Wall g = new Wall(x, y, true, img, 50, 2);
			platforms.addActor(g);
			g.setName((String) tile.getProperties().get("name"));
		}
		else if (type.equals("terrain") && tile.getProperties().get("data").equals("animated")) {
			int frame = (Integer.valueOf((String) tile.getProperties().get("frame")));
			String animid = ((String) tile.getProperties().get("name"));
			if (!animations.containsKey(animid)) {
				animations.put(animid, new HashMap<Integer, TextureRegion>());
			}
			if (!animations.get(animid).containsKey(frame)) {
				animations.get(animid).put(frame, img);
			}
			AnimatedTile g = new AnimatedTile(x, y, true, 50, 1, this, frame, animid);
			platforms.addActor(g);
		}
		else if (type.equals("terrain") && tile.getProperties().get("data").equals("hidden")) {
			Wall g = new Wall(x, y, true, img, 50, 2);
			platforms.addActor(g);
			g.hidden=true;
			g.setName((String) tile.getProperties().get("name"));
		}
		else if (type.equals("ground")) {
			Wall g = new Wall(x, y, true, img, 50, 2);
			platforms.addActor(g);
			
		}
		else if (type.equals("marker") && tile.getProperties().get("data").equals("dark")) {
			dark =true;
		}
		else if (type.equals("terrain") && tile.getProperties().get("data").equals("glowing")) {
			Wall g = new Wall(x, y, true, img, 50, 2);
			g.glowing=true;
			platforms.addActor(g);
		}
		else {
		
		}
	}
	public void loadLevel(TiledMap tmx) {
		TiledMapTileLayer layer = (TiledMapTileLayer)tmx.getLayers().get(0);
		for (int i = 0; i<layer.getWidth(); i++) {
			
			for (int j = 0; j < layer.getHeight(); j++) {
				float x = i * 24;
				float y = j * 24;
				if (layer.getCell(i, j)==null) {
					continue;
				}
				String type = (String) layer.getCell(i, j).getTile().getProperties().get("kind");
				createTile(x, y, layer.getCell(i, j).getTile());
			}
		}
	}
	public void create () {
		
		System.out.println("Starting creation... 0%");
	    stage = new Stage(new ScreenViewport());
	    Gdx.input.setInputProcessor(stage);
	    hud = new Stage(new ScreenViewport());
	    Gdx.input.setInputProcessor(hud);
	    System.out.println("Starting creation... 25%");
	    manager = new AssetManager();
	    manager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
	    manager.load("data/img/cards/leftmove.png", Texture.class);
	    manager.load("data/img/cards/rightmove.png", Texture.class);
	    manager.load("data/img/cards/jump.png", Texture.class);
	    manager.load("data/img/cards/viewhand.png", Texture.class);
	    manager.load("data/img/cards/pause.png", Texture.class);
	    manager.load("data/img/cards/menu.png", Texture.class);
	    manager.load("data/img/cards/radio.png", Texture.class);
	    manager.load("data/img/cards/rewind.png", Texture.class);
	    manager.load("data/img/cards/track_enemy.png", Texture.class);
	    manager.load("data/img/cards/track_player.png", Texture.class);
	    manager.load("data/img/cards/slow.png", Texture.class);
	    manager.load("data/img/cards/fast.png", Texture.class);
	    manager.load("data/img/cards/volt.png", Texture.class);
	    manager.load("data/img/player.png", Texture.class);
	    Preferences prefs = Gdx.app.getPreferences("Save State");
	    /*if (prefs.contains("level")) {
	    	level = prefs.getString("level");
	    }
	    else {*/
	    	level = "isaac.tmx";
	    //}
    	
	    nextlevel=level;
	    manager.load("data/lvl/"+level, TiledMap.class);
	    manager.load("data/img/cards/resume.png", Texture.class);
	    System.out.println("Starting creation... 50%");
	    font = new BitmapFont();
	    font.setColor(new Color(1,1,1,1));
	    System.out.println("Starting creation... 75%");
	    batch = new SpriteBatch();
	    System.out.println("Starting creation... Done.");
	    toMenu();
	    
		
		
	    
	    
	}
	public void resize (int width, int height) {
	    // See below for what true means.
	    stage.getViewport().update(width, height, true);
	}
	@SuppressWarnings("unused")
	private float accumulator = 0;
	private Group objects;
	protected Group handui;
	private ImageButton pause;
	private Group gameui;
	private Group bg;
	private Image menuscreen;


	public void render () {
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    stage.act();
	    if (state == "menu") {
	    	stage.draw();
	    }
	    
	    else if (state == "loading") {
		    stage.draw();
	    	if(manager.update()) {
		    	fromLoading();
		    	toGame();
	    	}
	    	
		    // display loading information
		    float progress = manager.getProgress();
		    batch.begin();
		    font.draw(batch, level + " " + progress*100+"%", 200,200);
		    batch.end();
		    
	    }
	    else if (state=="game") {
	    	//rayHandler.setCombinedMatrix((OrthographicCamera) stage.getViewport().getCamera());
		    //rayHandler.updateAndRender();
		    for (Force force : forces) {
		    	force.execute(gameobjects);
		    }
		    	stage.getViewport().getCamera().position.set(player.getX()+107, player.getY(), 0);
		    	stage.getCamera().update();
		    	stage.getViewport().apply();
		    	if (dark) {
		    		Batch b = stage.getBatch();
		    		Camera cam = stage.getViewport().getCamera();
		    		b.setProjectionMatrix(cam.combined);
		    		b.begin();
			    	player.draw(b, 1f);
			    	b.end();
			    	SnapshotArray<Actor> array = platforms.getChildren();
			    	Actor[] items = array.begin();
			    	b.begin();
			    	for (int i = 0, n = array.size; i < n; i++) {
			    	    GameObject platform = (GameObject) items[i];
			    	    if (platform.glowing) {
			    	    	platform.draw(b, 1f);
			    	    }
			    	    else if (platform.pos.dst2(player.pos)<7500) {
			    	    	platform.draw(b, 0.2f);
			    	    }
			    	    
			    	}
			    	b.end();
			    	array.end();	
			    	array = bg.getChildren();
			    	items = array.begin();
			    	b.begin();
			    	for (int i = 0, n = array.size; i < n; i++) {
			    		Actor platform = items[i];
			    		float x = platform.getX();
			    		float y = platform.getY();
			    		Vector2 pos = new Vector2(x, y);
			    	    if (pos.dst2(player.pos)<7500) {
			    	    	platform.draw(b, 0.2f);
			    	    }
			    	    
			    	}
			    	b.end();
			    	array.end();
			    	hud.getViewport().setScreenX(0);
			    	hud.getViewport().setScreenY(0);
			    	hud.getViewport().apply();
			    	hud.act();
				    hud.draw();
				    b.begin();
				    players.draw(b, 1f);
				    b.end();
			    	
		    	}
		    	else {
		    		Batch b = stage.getBatch();
		    		Camera cam = stage.getViewport().getCamera();
		    		b.setProjectionMatrix(cam.combined);
		    		b.begin();
		    		platforms.draw(b, 1f);
		    		bg.draw(b, 1f);
		    		b.end();
		    		hud.getViewport().setScreenX(0);
		        	hud.getViewport().setScreenY(0);
		        	hud.getViewport().apply();
		        	hud.act();
		    	    hud.draw();
		    	    b.begin();
		    	    players.draw(b, 1f);
		    	    b.end();
		    		
		    		
		    		
		    		
		    	}
		    	if (player.pos.x>=x) {
		    		level=nextlevel;
		    		state="loading";
		    		manager.load("data/lvl/"+level, TiledMap.class);
		    		objects.remove();
		    		savedcards=player.cards;
		    		System.out.println(savedcards);
		    		saved=true;
		    		Preferences prefs = Gdx.app.getPreferences("Save State");
		    		prefs.putString("level", level);
		    		prefs.flush();
		    	}
	    }
	    else if (state=="hand") {
	    	
		    stage.draw();
		    hud.getViewport().setScreenX(0);
	    	hud.getViewport().setScreenY(0);
	    	hud.getViewport().apply();
	    	hud.act();
		    hud.draw();
	    }
	    else {
		    stage.draw();
		    hud.getViewport().setScreenX(0);
	    	hud.getViewport().setScreenY(0);
	    	hud.getViewport().apply();
	    	hud.act();
		    hud.draw();
	    }
		
	    
	    
	    
	    
	    
	    
	}

	public void dispose() {
	    stage.dispose();
	}
	public void runAction(String action) {
		if (action=="light") {
			dark=false;
		}
	}
}
