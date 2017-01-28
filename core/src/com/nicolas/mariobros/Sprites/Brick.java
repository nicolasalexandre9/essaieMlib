package com.nicolas.mariobros.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.nicolas.mariobros.MarioBros;


public class Brick extends InteractiveTileObject {

    public Brick(World world , TiledMap map, Rectangle bounds){
        super(world, map, bounds);
        fixture.setUserData(this);
        setcategoryFilter(MarioBros.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick", "Collision");
        setcategoryFilter(MarioBros.DESTROYED_BIT);
        getCell().setTile(null);
    }
}
