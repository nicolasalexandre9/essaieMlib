package com.nicolas.mariobros.Sprites;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.nicolas.mariobros.MarioBros;
import com.nicolas.mariobros.Scenes.Hud;
import com.nicolas.mariobros.Screens.PlayScreen;


public class Coin extends InteractiveTileObject{

    private static TiledMapTileSet tiledSet;
    private final int BLANCK_COIN = 28;

    public Coin(PlayScreen screen, Rectangle bounds){
        super(screen, bounds);
        tiledSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setcategoryFilter(MarioBros.COIN_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Coin", "Collision");
        if(getCell().getTile().getId() == BLANCK_COIN){
            MarioBros.manager.get("audio/sounds/bump.wav", Sound.class).play();
        }else{
            MarioBros.manager.get("audio/sounds/coin.wav", Sound.class).play();

        }
        getCell().setTile(tiledSet.getTile(BLANCK_COIN));
        Hud.addScore(100);
    }
}
