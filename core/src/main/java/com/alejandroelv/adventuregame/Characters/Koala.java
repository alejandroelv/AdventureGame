package com.alejandroelv.adventuregame.Characters;

import com.alejandroelv.adventuregame.Managers.LevelManager;
import com.alejandroelv.adventuregame.Managers.PhysicsManager;
import com.alejandroelv.adventuregame.Managers.ResourceManager;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class Koala extends Image {
    TextureRegion stand, jump;
    Animation walk;
    
    private boolean dead;
    private Rectangle hitbox;
    final float width = 18;
    final float height = 26;
    float time = 0;
    float xVelocity = 0;
    float yVelocity = 0;
    boolean canJump = false;
    boolean isFacingRight = true;
    public TiledMapTileLayer layer;

    final float GRAVITY = -2.5f;
    final float MAX_VELOCITY = 10f;
    final float DAMPING = 0.87f;

    public Koala(float x, float y) {
        this.setSize(1, height / width);
        this.setPosition(x, y);
        
        hitbox = new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        Texture koalaTexture = new Texture("koalio.png");
        TextureRegion[][] grid = TextureRegion.split(koalaTexture, (int) width, (int) height);

        stand = grid[0][0];
        jump = grid[0][1];
        walk = new Animation(0.15f, grid[0][2], grid[0][3], grid[0][4]);
        walk.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public Rectangle getHitBox(){
        return this.hitbox;
    }
    
    public void updateHitBox() {
        hitbox.set(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
    
    @Override
    public void act(float delta) {
        if(this.isDead()){
            return;
        }
        updateHitBox();
        time = time + delta;

        boolean upTouched = Gdx.input.isTouched() && Gdx.input.getY() < Gdx.graphics.getHeight() / 2;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || upTouched) {
            saltar();
        }

        boolean leftTouched = Gdx.input.isTouched() && Gdx.input.getX() < Gdx.graphics.getWidth() / 3;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || leftTouched) {
            correr(-1 * MAX_VELOCITY, false);
        }

        boolean rightTouched = Gdx.input.isTouched() && Gdx.input.getX() > Gdx.graphics.getWidth() * 2 / 3;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || rightTouched) {
            correr(MAX_VELOCITY, true);
        }

        yVelocity = yVelocity + GRAVITY;

        float x = this.getX();
        float y = this.getY();
        float xChange = xVelocity * delta;
        float yChange = yVelocity * delta;
        
        if (PhysicsManager.canMoveTo(x + xChange, y, this,this.layer) == false) {
            xVelocity = xChange = 0;
        }

        if (PhysicsManager.canMoveTo(x, y + yChange, this, this.layer) == false) {
            canJump = yVelocity < 0;
            yVelocity = yChange = 0;
        }

        this.setPosition(x + xChange, y + yChange);

        //Se ha caido del mapa
        if(this.getY() < 0){
            this.setDead(true);
            LevelManager.currentLives -= 1;
        }
        
        xVelocity = xVelocity * DAMPING;
        if (Math.abs(xVelocity) < 0.5f) {
            xVelocity = 0;
        }
    }

    private void saltar(){
        if (canJump) {
            ResourceManager.playSound(ResourceManager.getSound("jump"));
            yVelocity = yVelocity + MAX_VELOCITY * 4;
        }
        canJump = false;
    }
    
    private void correr(float velocidad, boolean isFacingRight){
        this.xVelocity = velocidad;
        this.isFacingRight = isFacingRight;
    }
    
    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion frame;

        if (yVelocity != 0) {
            frame = jump;
        } else if (xVelocity != 0) {
            frame = (TextureRegion) walk.getKeyFrame(time);
        } else {
            frame = stand;
        }

        if (isFacingRight) {
            batch.draw(frame, this.getX(), this.getY(), this.getWidth(), this.getHeight());
        } else {
            batch.draw(frame, this.getX() + this.getWidth(), this.getY(), -1 * this.getWidth(), this.getHeight());
        }
    }
}
