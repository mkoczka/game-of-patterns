package com.koczka.gameofpatterns;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Entity {

    float x;

    float y;

    float angle;

    public World world;

    public Body body;

    public abstract void setPosition(float x, float y);

    public abstract void setRotation(float angle);

    public abstract Body getBody();

}
