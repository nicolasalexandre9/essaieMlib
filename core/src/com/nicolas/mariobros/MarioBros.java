package com.nicolas.mariobros;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.nicolas.mariobros.Screens.PlayScreen;

public class MarioBros extends Game {

    public static final int v_width = 400;
    public static final int v_height = 208;
    public static final float PPM = 100;

	public final static short GROUND_BIT = 1;
	public static final short MARIO_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;
    public static final short OBJECT_BIT = 32;
    public static final short ENEMY_BIT = 64;
	public static final short ENEMY_HEAD_BIT = 128;

	public SpriteBatch batch;

    /* warning using AssetManager in a static way can cause issues, especially on.;
    Instead you may want to pass around AssetManager to those thie classes that...
    We will ise it in the static context to save time for now.*/
    public static AssetManager manager;

    @Override
    public void dispose() {
        super.dispose();
        manager.dispose();
        batch.dispose();
    }

    @Override
	public void create () {
		batch = new SpriteBatch();
        manager = new AssetManager();
        manager.load("audio/music/mario_music.ogg",Music.class );
        manager.load("audio/sounds/coin.wav", Sound.class);
        manager.load("audio/sounds/bump.wav", Sound.class);
        manager.load("audio/sounds/breakblock.wav", Sound.class);
        manager.finishLoading();
        setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
        //manager.update();
	}
	

}
