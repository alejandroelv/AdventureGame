package com.alejandroelv.adventuregame;

import com.alejandroelv.adventuregame.Managers.AnimationManager;
import com.alejandroelv.adventuregame.Managers.ResourceManager;
import com.alejandroelv.adventuregame.Screens.MainMenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class AdventureGame extends Game {
    public Batch batch;
    
    @Override
    public void create() {
        batch = new SpriteBatch();
        ResourceManager.loadSounds();
        ResourceManager.loadTextures();
        AnimationManager.loadAnimations();
        this.setScreen(new MainMenuScreen(this));
    }
    
    @Override
    public void dispose(){
        batch.dispose();
    }
}
