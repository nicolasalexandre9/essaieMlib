package com.nicolas.mariobros.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.nicolas.mariobros.MarioBros;
import com.nicolas.mariobros.Scenes.Hud;
import com.nicolas.mariobros.Sprites.Mario;
import com.nicolas.mariobros.Tools.B2WorldCreator;
import com.nicolas.mariobros.Tools.WorldContactListener;


public class PlayScreen implements Screen {
    //reference to our Game, used to set Screens
    private MarioBros game;
    private TextureAtlas atlas;

    //basic playscreen variables
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    //Tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //box2d variables
    private World world ;
    private Box2DDebugRenderer b2dr;

    private Mario player;



    public PlayScreen(MarioBros game){
        atlas = new TextureAtlas("Mario_and_Enemies.pack");

        this.game = game;
        // create cam used to follow mario through cam world
        gamecam = new OrthographicCamera();
        //create a FitViewport to maintain virtual aspect ratio despite
        gamePort = new FitViewport(MarioBros.v_width / MarioBros.PPM, MarioBros.v_height / MarioBros.PPM, gamecam);
        //create our game HUD for score, timer, level info
        hud = new Hud(game.batch);

        //Load our map and setup our map renderer
        maploader = new TmxMapLoader();
        map = maploader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MarioBros.PPM);

        //initially set our gamcam to be centered correctly at the start of map
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        new B2WorldCreator(world,map);

        //create mario in our game world
        player = new Mario(world, this);

        world.setContactListener(new WorldContactListener());
    }

    public TextureAtlas getAtlas(){
        return atlas;
    }

    public void show() {

    }

    public void handleInput(float dt){
        // if our user is holding down mouse move our camera through the game world.
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            player.b2body.applyLinearImpulse(new Vector2(0,4f), player.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <=2){
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(),true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x <=2){
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(),true);
        }


    }

    public void update(float dt){
        //handle user input first
        handleInput(dt);

        //takes 1 step in the phsysics simulation(60 times per second)
        world.step(1/60f, 6, 2);

        player.update(dt);

        //attach our gamecam to our players.x coordinates...
        gamecam.position.x = player.b2body.getPosition().x;

        //update our gamecam with correct coordinates after changes
        gamecam.update();
        //tell our renderer to draw only what our can see in our game world
        renderer.setView(gamecam);
    }


    public void render(float delta) {
        //separate our update logic from render
        update(delta);

        // clear the game screen with Black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // render our game map
        renderer.render();

        //render our Box2DDebugLines
        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        //set our batch to now draw what the hud camera sees
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();


    }


    public void resize(int width, int height) {
        //update our game viewport
        gamePort.update(width,height);
    }


    public void pause() {

    }


    public void resume() {

    }


    public void hide() {

    }


    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
