package com.alejandroelv.adventuregame.Characters;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public interface Golpeable {
    public void onHit(Actor actor);
    public Rectangle getHitBox();
    public void updateHitBox();
}
