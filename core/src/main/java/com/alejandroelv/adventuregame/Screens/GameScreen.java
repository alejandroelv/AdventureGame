package com.alejandroelv.adventuregame.Screens;

import com.alejandroelv.adventuregame.AdventureGame;
import com.alejandroelv.adventuregame.Characters.Golpeable;
import com.alejandroelv.adventuregame.Characters.Koala;
import com.alejandroelv.adventuregame.Managers.LevelManager;
import com.alejandroelv.adventuregame.Managers.ResourceManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GameScreen implements Screen{
    AdventureGame juego;
    OrthogonalTiledMapRenderer renderer;
    OrthographicCamera camera;
    public int lives;
    public int level;
    private BitmapFont fuente;
    private Batch batch;

    public GameScreen(AdventureGame juego){
        this.juego = juego;
        this.fuente = new BitmapFont();
        this.batch = new SpriteBatch();
        lives = LevelManager.currentLives;
        // Crea y carga el mapa
        LevelManager.loadMap();
    }
    
    @Override
    public void show() {


        final float pixelsPerTile = 16;
        renderer = new OrthogonalTiledMapRenderer(LevelManager.map, 1 / pixelsPerTile);
        camera = new OrthographicCamera();
        
        LevelManager.stage.getViewport().setCamera(camera);
        
        //Se crea al jugador
        LevelManager.koala = new Koala(LevelManager.xJugador, LevelManager.yJugador);
        LevelManager.koala.layer = LevelManager.collisionLayer;
        
        LevelManager.stage.addActor(LevelManager.koala);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        checkCollisions();

        //Si el jugador pierde 1 vida, se reinicia el nivel
        if(LevelManager.koala.isDead()){
            ResourceManager.playSound(ResourceManager.getSound("hit"));
            reiniciarNivel();
        }
        
        //Si el Koala muere, se muestra la pantalla de fin
        if(LevelManager.currentLives == 0){
            LevelManager.clearLevel();
            juego.setScreen(new GameOverScreen(juego));
        }
        
        if(LevelManager.chest.isOpen){
            acabarNivel();
        }
        
        //Limites de la camara
        if(LevelManager.koala.getX() < LevelManager.limiteIzquierdo)
            camera.position.x = LevelManager.limiteIzquierdo;
        else if(LevelManager.koala.getX() > LevelManager.limiteDerecho)
            camera.position.x = LevelManager.limiteDerecho;
        else
            camera.position.x = LevelManager.koala.getX();
        
        if(LevelManager.koala.getY() < LevelManager.limiteInferior)
            camera.position.y = LevelManager.limiteInferior;
        else if(LevelManager.koala.getY() > LevelManager.limiteSuperior)
            camera.position.y = LevelManager.limiteSuperior;
        else
            camera.position.y = LevelManager.koala.getY();
        
        camera.update();

        renderer.setView(camera);
        renderer.render();
        
        LevelManager.stage.act(delta);
        LevelManager.stage.draw();
        
        batch.begin();
        pintarTextos();
        batch.end();
    }
    
    private void checkCollisions(){
    
        for(Actor actor: LevelManager.stage.getActors()){
            if(actor != LevelManager.koala){
                Golpeable actorGolpeable = (Golpeable)actor;
                if(actorGolpeable.getHitBox().overlaps(LevelManager.koala.getHitBox()))
                    actorGolpeable.onHit(LevelManager.koala);
            }
        }
    }
    
    private void acabarNivel(){
        LevelManager.finishLevel();
        if(LevelManager.currentLevel == 5){
            this.juego.setScreen(new MainMenuScreen(juego));
            this.dispose();
        }else{
            this.juego.setScreen(new GameScreen(juego));
            this.dispose();
        }
    }
    
    private void reiniciarNivel(){
        LevelManager.clearLevel();
        this.juego.setScreen(new GameScreen(juego));
        this.dispose();
    }

    private void pintarTextos(){
        fuente.draw(batch, "Monedas: " + LevelManager.totalCoins, 140, 480);
        fuente.draw(batch, "Vidas: " + LevelManager.currentLives, 70, 480);
        fuente.draw(batch, "Nivel: " + LevelManager.currentLevel, 0, 480);
        
        if(LevelManager.key.isCollected){
            fuente.draw(batch, "Llave: Si", 230, 480);
        }else{
            fuente.draw(batch, "Llave: No", 230, 480);
        }
    }
    
    @Override
    public void dispose() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, 20 * width / height, 20);
    }

    @Override
    public void resume() {
    }  
}
