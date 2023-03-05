package com.alejandroelv.adventuregame.Managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private static Map<String, Sound> sonidos = new HashMap();
    private static Map<String, Texture> texturas = new HashMap();

    public static void loadSounds() {	
        // Carga los sonidos del juego
        loadSound("coin",Gdx.audio.newSound(Gdx.files.internal("sounds/coin.wav")));
        loadSound("click", Gdx.audio.newSound(Gdx.files.internal("sounds/click.wav")));   
        loadSound("jump", Gdx.audio.newSound(Gdx.files.internal("sounds/Jump.wav")));
        loadSound("hit", Gdx.audio.newSound(Gdx.files.internal("sounds/Hit.wav")));
        loadSound("key", Gdx.audio.newSound(Gdx.files.internal("sounds/Key.wav")));
        loadSound("lifeUp", Gdx.audio.newSound(Gdx.files.internal("sounds/Life_up.wav")));
        loadSound("level_clear", Gdx.audio.newSound(Gdx.files.internal("sounds/Open_chest.wav")));
        loadSound("mainMenu", Gdx.audio.newSound(Gdx.files.internal("sounds/Main_Menu_Theme.mp3")));
        loadSound("level1", Gdx.audio.newSound(Gdx.files.internal("sounds/level1.mp3")));
        loadSound("level2", Gdx.audio.newSound(Gdx.files.internal("sounds/level2.mp3")));
        loadSound("level3", Gdx.audio.newSound(Gdx.files.internal("sounds/level3.mp3")));
        loadSound("level4", Gdx.audio.newSound(Gdx.files.internal("sounds/level4.mp3")));
        loadSound("gameOver", Gdx.audio.newSound(Gdx.files.internal("sounds/Game_Over.mp3")));
    }

    public static void loadTextures() {
        loadTexture("background", new Texture(Gdx.files.internal("textures/Background.png")));
        loadTexture("pause", new Texture(Gdx.files.internal("textures/Pause.png")));
        loadTexture("play", new Texture(Gdx.files.internal("textures/Play.png")));
        loadTexture("quit", new Texture(Gdx.files.internal("textures/Quit.png")));
        loadTexture("resume", new Texture(Gdx.files.internal("textures/Resume.png")));
        loadTexture("soundOn", new Texture(Gdx.files.internal("textures/soundOn.png")));
        loadTexture("soundOff", new Texture(Gdx.files.internal("textures/soundOff.png")));
        loadTexture("gameOver", new Texture(Gdx.files.internal("textures/GameOver.jpg")));
        loadTexture("pocion", new Texture(Gdx.files.internal("textures/Pocion.png")));
        loadTexture("coin", new Texture(Gdx.files.internal("textures/Coin.png")));
        loadTexture("key", new Texture(Gdx.files.internal("textures/Key.png")));
        loadTexture("chest", new Texture(Gdx.files.internal("textures/Chest.png")));
        loadTexture("openChest", new Texture(Gdx.files.internal("textures/Open_Chest.png")));
        loadCharacterTextures();
    }
    
    private static void loadCharacterTextures(){
        loadTexture("idleBoar", new Texture(Gdx.files.internal("mob/Boar/Idle/Idle-sheet.png")));
        loadTexture("runningBoar", new Texture(Gdx.files.internal("mob/Boar/Run/Run-Sheet.png")));
        loadTexture("walkingBoar", new Texture(Gdx.files.internal("mob/Boar/Walk/Walk-Base-Sheet.png")));
        loadTexture("flyingBee", new Texture(Gdx.files.internal("mob/Small Bee/Fly/Fly-Sheet.png")));   
    }

    private static void loadTexture(String name, Texture texture) {
        texturas.put(name, texture);
    }

    public static Texture getTexture(String name) {
        return texturas.get(name);
    }

    private static void loadSound(String name, Sound sound) {
        sonidos.put(name, sound);
    }

    public static Sound getSound(String name) {
        return sonidos.get(name);
    }
    
    public static void playSound (Sound sound) {
        if (SettingsManager.sonidoActivado) sound.play(1);
    }
}
