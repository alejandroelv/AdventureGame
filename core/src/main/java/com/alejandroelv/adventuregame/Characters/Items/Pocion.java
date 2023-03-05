package com.alejandroelv.adventuregame.Characters.Items;

import com.alejandroelv.adventuregame.Characters.Golpeable;
import com.alejandroelv.adventuregame.Managers.LevelManager;
import com.alejandroelv.adventuregame.Managers.ResourceManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Pocion extends Item implements Golpeable{
    private Rectangle hitbox;
    private final float width = 15;
    private final float height = 20;
        
    public Pocion(){
        this.setSize(1, height / width);
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
        batch.draw(ResourceManager.getTexture("pocion"), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    public void onHit(Actor actor) {
        ResourceManager.playSound(ResourceManager.getSound("lifeUp"));
        LevelManager.currentLives += 1;
        LevelManager.items.removeValue(this, true);
        LevelManager.stage.getActors().removeValue(this, true);
        this.remove();
    }
}
