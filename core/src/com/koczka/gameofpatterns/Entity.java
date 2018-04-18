package com.koczka.gameofpatterns;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Entity {

    protected float x;

    protected float y;

    protected float width;

    protected float height;

    protected float angle;

    protected World world;

    protected Body body;

    protected Texture texture;

    public abstract void setPosition(float x, float y);

    public abstract void setRotation(float angle);

    public abstract Body getBody();

}
