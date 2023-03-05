package com.alejandroelv.adventuregame.Characters.Enemigos;

import com.alejandroelv.adventuregame.Characters.Golpeable;
import com.alejandroelv.adventuregame.Managers.AnimationManager;
import com.alejandroelv.adventuregame.Managers.LevelManager;
import com.alejandroelv.adventuregame.Managers.PhysicsManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Abeja extends Enemigo implements Golpeable{
    final float GRAVITY = -2.5f;
    final float MAX_VELOCITY = 2f;
    final float DAMPING = 0.87f;
    
    private boolean dead;
    private Rectangle hitbox;

    float time = 0;
    float xVelocity = MAX_VELOCITY;
    float yVelocity = 0;
    
    boolean isFacingRight = false;
    
    public Abeja(){
        this.setSize(0.97f, 1.22f);
        hitbox = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
    
    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
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
    public void act(float delta) {
        if(this.isDead()){
            die();
            return;
        }
        
        updateHitBox();
        time = time + delta;

        int distanciaConJugadorEnX = (int)this.getX() - (int)LevelManager.koala.getX();
        int distanciaConJugadorEnY = (int)this.getY() - (int)LevelManager.koala.getY();
        
        if(Math.abs(distanciaConJugadorEnX) < 5 &&  Math.abs(distanciaConJugadorEnY) < 5){
            perseguirJugador();
        }else{
            yVelocity = 0;
        }

        float x = this.getX();
        float y = this.getY();
        float xChange = xVelocity * delta;
        float yChange = yVelocity * delta;
        
        if (PhysicsManager.canMoveTo(x + xChange, y, this,this.layer) == false) {
            xVelocity *= -1;
            xChange = 0;
        }
        
        if (PhysicsManager.canMoveTo(x, y + yChange, this, this.layer) == false) {
            yVelocity = yChange = 0;
        }
        
        this.setPosition(x + xChange, y + yChange);
        
        xVelocity = xVelocity * DAMPING;
        //yVelocity = yVelocity * DAMPING;
        
        if (Math.abs(xVelocity) < 0.01f) {
            xVelocity = 0;
        }
        
        //Se ha caido del mapa
        if(this.getY() < 0){
            this.setDead(true);
        }
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame = (TextureRegion) AnimationManager.getAnimation("flyingBee").getKeyFrame(time);

        if (isFacingRight) {
            batch.draw(frame, this.getX() + this.getWidth(), this.getY(), -1 * this.getWidth(), this.getHeight());
        } else {
            batch.draw(frame, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        }
    }

    private void perseguirJugador(){
        int direccionX;
        int direccionY;

        if(LevelManager.koala.getX() > this.getX()){
            direccionX = 1;
            isFacingRight = true;
        }else{
            direccionX = -1;
            isFacingRight = false;
        }
        
        if(LevelManager.koala.getY() > this.getY()){
            direccionY = 1;
        }else{
            direccionY = -1;
        }

        this.xVelocity = MAX_VELOCITY * direccionX;
        this.yVelocity = MAX_VELOCITY * direccionY;
    }
    
    private void die(){
        LevelManager.enemigos.removeValue(this, true);
        LevelManager.stage.getActors().removeValue(this, true);
        this.remove();
    }
    
    @Override
    public void onHit(Actor actor) {
        if(!LevelManager.koala.isDead()){
            LevelManager.koala.setDead(true);
            LevelManager.currentLives -= 1;       
        }
    } 
}
