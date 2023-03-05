package com.alejandroelv.adventuregame.Managers;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import java.util.HashMap;
import java.util.Map;

public class AnimationManager {
    private static Map<String, Animation> animaciones = new HashMap();
       
    private static final int boarTextureWidth = 47;
    private static final int boarTextureHeight = 32;
    
    private static final int beeTextureWidth = 62;
    private static final int beeTextureHeight = 64;
    
    public static void loadAnimations(){
        loadBoarAnimations();
        loadBeeAnimations();
    }
    
    private static void loadBoarAnimations(){     
        //Animacion estatica
        Array<TextureRegion> idleBoarTextures = new Array<>(TextureRegion.split(ResourceManager.getTexture("idleBoar"), boarTextureWidth, boarTextureHeight)[0]); 
        Animation idle = new Animation(0.30f, idleBoarTextures);
        idle.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        AnimationManager.loadAnimation("idleBoar", idle);
        
        //Animacion de correr
        Array<TextureRegion> runningBoarTextures = new Array<>(TextureRegion.split(ResourceManager.getTexture("runningBoar"), boarTextureWidth, boarTextureHeight)[0]);
        Animation running = new Animation(0.30f, runningBoarTextures);
        running.setPlayMode(Animation.PlayMode.LOOP);
        AnimationManager.loadAnimation("runningBoar", running);
        
        //Animacion de andar
        Array<TextureRegion> walkingBoarTextures = new Array<>(TextureRegion.split(ResourceManager.getTexture("walkingBoar"), boarTextureWidth, boarTextureHeight)[0]); 
        Animation walking = new Animation(0.30f, walkingBoarTextures);
        walking.setPlayMode(Animation.PlayMode.LOOP);
        AnimationManager.loadAnimation("idleBoar", walking);
    }
    
    private static void loadBeeAnimations(){
        Array<TextureRegion> flyingBeeTextures = new Array<>(TextureRegion.split(ResourceManager.getTexture("flyingBee"), beeTextureWidth, beeTextureHeight)[0]); 
        Animation idle = new Animation(0.30f, flyingBeeTextures);
        idle.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        AnimationManager.loadAnimation("flyingBee", idle);
    }
    
    private static void loadAnimation(String name, Animation animacion) {
        animaciones.put(name, animacion);
    }

    public static Animation getAnimation(String name) {
        return animaciones.get(name);
    }
}
