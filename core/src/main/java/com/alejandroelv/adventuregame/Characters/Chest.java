package com.alejandroelv.adventuregame.Characters;

import com.alejandroelv.adventuregame.Managers.LevelManager;
import com.alejandroelv.adventuregame.Managers.ResourceManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Chest extends Image implements Golpeable{
    public boolean isOpen;
    private Rectangle hitbox;
        
    public Chest(){
        this.setSize(2, 2);

        isOpen = false;
        hitbox = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
    
    @Override
    public Rectangle getHitBox(){
        return this.hitbox;
    }
    
    @Override
    public void updateHitBox() {
        hitbox.set(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
    
    @Override
    public void act(float delta){
        updateHitBox();
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {        
        if(isOpen){
            batch.draw(ResourceManager.getTexture("openChest"), this.getX(), this.getY(), this.getWidth(), this.getHeight());
        }else{
            batch.draw(ResourceManager.getTexture("chest"), this.getX(), this.getY(), this.getWidth(), this.getHeight());
        }
    }

    @Override
    public void onHit(Actor actor) {
        if(isOpen){
            return;
        }
        
        if(LevelManager.key.isCollected)
            isOpen = true;
    }
}
