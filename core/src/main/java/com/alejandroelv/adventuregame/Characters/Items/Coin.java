package com.alejandroelv.adventuregame.Characters.Items;

import com.alejandroelv.adventuregame.Characters.Golpeable;
import com.alejandroelv.adventuregame.Managers.LevelManager;
import com.alejandroelv.adventuregame.Managers.ResourceManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Coin extends Item implements Golpeable{
    private Rectangle hitbox;
        
    public Coin(){
        this.setSize(0.6f, 0.7f);
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
        batch.draw(ResourceManager.getTexture("coin"), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    public void onHit(Actor actor) {
        ResourceManager.playSound(ResourceManager.getSound("coin"));
        LevelManager.totalCoins += 1;
        LevelManager.items.removeValue(this, true);
        LevelManager.stage.getActors().removeValue(this, true);
        this.remove();
    }
}
