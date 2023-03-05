package com.alejandroelv.adventuregame.Managers;

import com.alejandroelv.adventuregame.Characters.Enemigos.Abeja;
import com.alejandroelv.adventuregame.Characters.Enemigos.Enemigo;
import com.alejandroelv.adventuregame.Characters.Enemigos.Jabali;
import com.alejandroelv.adventuregame.Characters.Items.Item;
import com.alejandroelv.adventuregame.Characters.Items.Key;
import com.alejandroelv.adventuregame.Characters.Items.Coin;
import com.alejandroelv.adventuregame.Characters.Items.Pocion;

public class NPCGenerator {
    
    public static Enemigo generarEnemigo(String tipo){
        Enemigo enemigoGenerado;
        
        switch(tipo){
            case "Jabali":
                enemigoGenerado = new Jabali();
                break;
            case "Abeja":
                enemigoGenerado = new Abeja();
                break;
            default:
                enemigoGenerado = new Abeja();
                break;
        }
        
        return enemigoGenerado;
    }
        
    public static Item generarItem(String tipo){
        Item itemGenerado;
        
        switch(tipo){
            case "Pocion":
                itemGenerado = new Pocion();
                break;
            case "Key":
                itemGenerado = new Key();
                break;
            case "Moneda":
                itemGenerado = new Coin();
                break;
            default:
                itemGenerado = new Coin();
                break;
        }
        
        return itemGenerado;
    }
}
