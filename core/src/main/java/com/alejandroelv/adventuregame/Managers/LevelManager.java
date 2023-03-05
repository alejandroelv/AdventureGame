package com.alejandroelv.adventuregame.Managers;

import com.alejandroelv.adventuregame.Characters.Chest;
import com.alejandroelv.adventuregame.Characters.Enemigos.Enemigo;
import com.alejandroelv.adventuregame.Characters.Items.Item;
import com.alejandroelv.adventuregame.Characters.Items.Key;
import com.alejandroelv.adventuregame.Characters.Koala;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public class LevelManager {
    // Info del LevelManager
    public static final String LEVEL_DIR = "levels";
    public static final String LEVEL_PREFIX = "level";
    public static final String LEVEL_EXTENSION = ".tmx";
    
    // NPC del nivel actual 
    public static Stage stage = new Stage();
    public static Array<Enemigo> enemigos = new Array<>();
    public static Array<Item> items = new Array<>();
    public static Chest chest;
    public static Key key;
    public static Koala koala;
    
    //Mapa del nivel actual
    public static TiledMap map;
    
    //Parámetros del nivel
    public static int currentLevel;
    public static int currentLives;
    public static int totalCoins;
    public static TiledMapTileLayer collisionLayer;
    public static float limiteIzquierdo;
    public static float limiteInferior;
    public static float limiteDerecho;
    public static float limiteSuperior;
    public static float xJugador;
    public static float yJugador;
    
    public static void passLevel() {
        currentLevel++;
    }

    public static String getCurrentLevelName() {
        return LEVEL_PREFIX + LevelManager.currentLevel;
    }

    public static String getCurrentLevelPath() {
        return LEVEL_DIR + "/" + LevelManager.getCurrentLevelName() + LEVEL_EXTENSION;
    }
    
    public static void loadMap() {
        LevelManager.map = new TmxMapLoader().load(LevelManager.getCurrentLevelPath());
        LevelManager.collisionLayer = (TiledMapTileLayer) LevelManager.map.getLayers().get("Terreno");        
        loadObjetos();
        
        //Se cargan las propiedades del mapa
        LevelManager.limiteIzquierdo = (LevelManager.map.getProperties().get("limiteIzquierdo", Float.class) / 16);
        LevelManager.limiteInferior = (LevelManager.map.getProperties().get("limiteInferior", Float.class) / 16);
        LevelManager.limiteDerecho = (LevelManager.map.getProperties().get("limiteDerecho", Float.class) / 16);
        LevelManager.limiteSuperior = (LevelManager.map.getProperties().get("limiteSuperior", Float.class) / 16);
        LevelManager.xJugador = (LevelManager.map.getProperties().get("xJugador", Float.class) / 16);
        LevelManager.yJugador = (LevelManager.map.getProperties().get("yJugador", Float.class) / 16);
        
        //Comienza la musica
        if(SettingsManager.sonidoActivado)
            ResourceManager.getSound(LevelManager.getCurrentLevelName()).play();
    }
    
    private static void loadObjetos(){
        for (MapObject object : LevelManager.map.getLayers().get("Objetos").getObjects()) {

            if (object instanceof TextureMapObject) {
                TextureMapObject textureObject = (TextureMapObject) object;
                if (textureObject.getProperties().containsKey("enemigo")) {
                    agregarEnemigo(textureObject);
                }else if(textureObject.getProperties().containsKey("item")){
                    agregarItem(textureObject);
                }else if(textureObject.getProperties().containsKey("chest")){
                    agregarChest(textureObject);
                }
            }
        }
    }
    
    private static void agregarEnemigo(TextureMapObject objetoEnemigo){
        Rectangle rect = generarRectangulo(objetoEnemigo);
        
        String tipoEnemigo = objetoEnemigo.getProperties().get("enemigo", String.class);

        Enemigo enemigo = NPCGenerator.generarEnemigo(tipoEnemigo);
        enemigo.setPosition(rect.x, rect.y);
        enemigo.layer = LevelManager.collisionLayer;
        LevelManager.enemigos.add(enemigo);
        LevelManager.stage.addActor(enemigo);
    }
    
    private static void agregarItem(TextureMapObject objetoItem){
        Rectangle rect = generarRectangulo(objetoItem);
        String tipoItem = objetoItem.getProperties().get("item", String.class);

        Item item = NPCGenerator.generarItem(tipoItem);
        item.setPosition(rect.x , rect.y);

        if(tipoItem.equals("Key"))
            LevelManager.key = (Key) item;
            
        LevelManager.items.add(item);
        LevelManager.stage.addActor(item);
    }
    
    private static void agregarChest(TextureMapObject chest){
        Rectangle rect = generarRectangulo(chest);

        Chest cofre = new Chest();
        cofre.setPosition(rect.x, rect.y);
        LevelManager.chest = cofre;
        LevelManager.stage.addActor(cofre);
    }
    
    private static Rectangle generarRectangulo(TextureMapObject objeto){
        float x = objeto.getProperties().get("x", Float.class);
        float y = objeto.getProperties().get("y", Float.class);
        float width = objeto.getProperties().get("width", Float.class);
        float height = objeto.getProperties().get("height", Float.class);
        
        return new Rectangle(x / 16, y / 16, width / 16 , height / 16);
    }
    
    public static void clearLevel() {
        LevelManager.stage.clear();
        ResourceManager.getSound(LevelManager.getCurrentLevelName()).stop();
        LevelManager.totalCoins = 0;
        LevelManager.enemigos.clear();
        LevelManager.items.clear();
    }

    public static void finishLevel() {
        ResourceManager.playSound(ResourceManager.getSound("level_clear"));
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ie) {}

        LevelManager.clearLevel();
        LevelManager.currentLevel++;      
    }
}
