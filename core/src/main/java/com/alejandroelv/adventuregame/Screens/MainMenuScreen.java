package com.alejandroelv.adventuregame.Screens;

import com.alejandroelv.adventuregame.AdventureGame;
import com.alejandroelv.adventuregame.Managers.LevelManager;
import com.alejandroelv.adventuregame.Managers.ResourceManager;
import com.alejandroelv.adventuregame.Managers.SettingsManager;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen implements Screen {
    AdventureGame game;
    OrthographicCamera guiCam;
    Rectangle soundBounds;
    Rectangle playBounds;
    Rectangle quitBounds;
    Vector3 touchPoint;

    public MainMenuScreen (AdventureGame game) {
        this.game = game;
        LevelManager.currentLevel = 1;
        LevelManager.currentLives = 3;
        guiCam = new OrthographicCamera(320, 480);
        guiCam.position.set(320 / 2, 480 / 2, 0);
        soundBounds = new Rectangle(320-32, 480-32, 32, 32);
        playBounds = new Rectangle(320/2 - 28, 200 + 18, 55, 36);
        quitBounds = new Rectangle(320/2 - 28, 200 - 36, 55, 36);
        touchPoint = new Vector3();
        
        if(SettingsManager.sonidoActivado)
            ResourceManager.getSound("mainMenu").play();
    }

    public void update () {
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (playBounds.contains(touchPoint.x, touchPoint.y)) {
                ResourceManager.playSound(ResourceManager.getSound("click"));
                ResourceManager.getSound("mainMenu").pause();
                game.setScreen(new GameScreen(game));
                this.dispose();
            }
            
            if (quitBounds.contains(touchPoint.x, touchPoint.y)) {
                ResourceManager.playSound(ResourceManager.getSound("click"));
                closeGame();
            }
            
            if (soundBounds.contains(touchPoint.x, touchPoint.y)) {
                ResourceManager.playSound(ResourceManager.getSound("click"));
                SettingsManager.sonidoActivado = !SettingsManager.sonidoActivado;
                if (SettingsManager.sonidoActivado)
                    ResourceManager.getSound("mainMenu").play();
                else
                    ResourceManager.getSound("mainMenu").pause();
            }
        }
    }

    public void draw () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        guiCam.update();
        game.batch.setProjectionMatrix(guiCam.combined);

        game.batch.disableBlending();
        game.batch.begin();
        game.batch.draw(ResourceManager.getTexture("background"), 0, 0, 320, 480);
        game.batch.end();

        game.batch.enableBlending();
        game.batch.begin();

        game.batch.draw(ResourceManager.getTexture("play"), 320/2 - 28, 200 + 18, 55, 36);
        game.batch.draw(ResourceManager.getTexture("quit"), 320/2 - 28, 200 - 36, 55, 36);
        game.batch.draw(SettingsManager.sonidoActivado ? ResourceManager.getTexture("soundOn") : 
                ResourceManager.getTexture("soundOff"), 320-32, 480-32, 32, 32);
        game.batch.end();	
    }

    private void closeGame(){
        Gdx.app.exit();
    }
    
    @Override
    public void render (float delta) {
        update();
        draw();
    }

    @Override
    public void pause () {
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int i, int i1) {

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
