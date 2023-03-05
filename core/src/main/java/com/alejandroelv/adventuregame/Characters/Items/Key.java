package com.alejandroelv.adventuregame.Characters.Items;

import com.alejandroelv.adventuregame.Characters.Golpeable;
import com.alejandroelv.adventuregame.Managers.LevelManager;
import com.alejandroelv.adventuregame.Managers.ResourceManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Key extends Item implements Golpeable{
    public boolean isCollected;
    private Rectangle hitbox;
    
    public Key(){
        this.setSize(0.8f, 0.6f);
        
        isCollected = false;
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
        if(!isCollected)
            updateHitBox();
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {        
        batch.draw(ResourceManager.getTexture("key"), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
    
    @Override
    public void onHit(Actor actor) {
        if(isCollected){
            return;
        }
        
        ResourceManager.playSound(ResourceManager.getSound("key"));
        isCollected = true;
        LevelManager.items.removeValue(this, true);
        LevelManager.stage.getActors().removeValue(this, true);
        this.remove();
    }
}