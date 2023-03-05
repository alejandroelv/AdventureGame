package com.alejandroelv.adventuregame.Managers;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PhysicsManager {
    public static boolean canMoveTo(float startX, float startY, Actor actor, TiledMapTileLayer layer) {
        float endX = startX + actor.getWidth();
        float endY = startY + actor.getHeight();

        int x = (int) startX;
        while (x < endX) {

            int y = (int) startY;
            while (y < endY) {
                if (layer.getCell(x, y) != null) {
                    return false;
                }
                y = y + 1;
            }
            x = x + 1;
        }

        return true;
    }
}
