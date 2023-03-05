package com.alejandroelv.adventuregame.Screens;

import com.alejandroelv.adventuregame.AdventureGame;
import com.alejandroelv.adventuregame.Managers.ResourceManager;
import com.alejandroelv.adventuregame.Managers.SettingsManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class GameOverScreen implements Screen{
    AdventureGame game;
    OrthographicCamera guiCam;
    Rectangle continueBounds;
    Rectangle quitBounds;
    Vector3 touchPoint;
        
    public GameOverScreen(AdventureGame game){
        this.game = game;
        guiCam = new OrthographicCamera(320, 480);
        guiCam.position.set(320 / 2, 480 / 2, 0);
        continueBounds = new Rectangle(320/2 - 28, 50 + 18, 55, 36);
        quitBounds = new Rectangle(320/2 - 28, 50 - 36, 55, 36);
        touchPoint = new Vector3();
        
        if(SettingsManager.sonidoActivado)
            ResourceManager.getSound("gameOver").play();
    }
    
    public void update () {
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (continueBounds.contains(touchPoint.x, touchPoint.y)) {
                ResourceManager.playSound(ResourceManager.getSound("click"));
                ResourceManager.getSound("gameOver").pause();
                game.setScreen(new MainMenuScreen(game));
                return;
            }
            
            if (quitBounds.contains(touchPoint.x, touchPoint.y)) {
                ResourceManager.playSound(ResourceManager.getSound("click"));
                closeGame();
            }
        }
    }

    public void draw () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        guiCam.update();
        game.batch.setProjectionMatrix(guiCam.combined);

        game.batch.disableBlending();
        game.batch.begin();
        game.batch.draw(ResourceManager.getTexture("gameOver"), 0, 0, 320, 480);
        game.batch.end();
                
        game.batch.enableBlending();
        game.batch.begin();
        game.batch.draw(ResourceManager.getTexture("play"), 320/2 - 28, 50 + 18, 55, 36);
        game.batch.draw(ResourceManager.getTexture("quit"), 320/2 - 28, 50 - 36, 55, 36);
        game.batch.end();
    }
    
    private void closeGame(){
        Gdx.app.exit();
    }
        
    @Override
    public void show() {
        
    }

    @Override
    public void render(float f) {
        update();
        draw();
    }

    @Override
    public void resize(int i, int i1) {
        
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        
    }
}
