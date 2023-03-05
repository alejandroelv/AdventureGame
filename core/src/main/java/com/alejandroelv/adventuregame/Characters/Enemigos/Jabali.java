package com.alejandroelv.adventuregame.Characters.Enemigos;

import com.alejandroelv.adventuregame.Characters.Golpeable;
import com.alejandroelv.adventuregame.Managers.AnimationManager;
import com.alejandroelv.adventuregame.Managers.LevelManager;
import com.alejandroelv.adventuregame.Managers.PhysicsManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Jabali extends Enemigo implements Golpeable{
    public enum State{
        IDLE, RUNNING
    }
    
    final float GRAVITY = -2.5f;
    final float MAX_VELOCITY = 10f;
    final float DAMPING = 0.9f;
    
    private State estado;
    private boolean dead;
    private Rectangle hitbox;
    
    final float width = 18;
    final float height = 26;
    float time = 0;
    float xVelocity = 0;
    float yVelocity = 0;

    boolean isFacingRight = true;
    
    public Jabali(){
        this.setSize(1.55f, 1.44f);
        hitbox = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        estado = State.IDLE;
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

        if((int)LevelManager.koala.getY() == (int)this.getY() && estado == State.IDLE){
            perseguirJugador();
        }
        
        yVelocity = yVelocity + GRAVITY;

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
        if (Math.abs(xVelocity) < 0.01f) {
            estado = State.IDLE;
            xVelocity = 0;
        }
        
        //Se ha caido del mapa
        if(this.getY() < 0){
            this.setDead(true);
        }
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame;

        if (yVelocity != 0) {
            frame = (TextureRegion) AnimationManager.getAnimation("runningBoar").getKeyFrame(time);
        } else if (xVelocity != 0) {
            frame = (TextureRegion) AnimationManager.getAnimation("runningBoar").getKeyFrame(time);
        } else {
            frame = (TextureRegion) AnimationManager.getAnimation("idleBoar").getKeyFrame(time);
        }

        if (isFacingRight) {
            batch.draw(frame, this.getX() + this.getWidth(), this.getY(), -1 * this.getWidth(), this.getHeight());
        } else {
            batch.draw(frame, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        }
    }

    private void perseguirJugador(){
        int direccion;

        if(LevelManager.koala.getX() > this.getX()){
            direccion = 1;
            isFacingRight = true;
        }else{
            direccion = -1;
            isFacingRight = false;
        }

        this.xVelocity = MAX_VELOCITY * direccion;
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