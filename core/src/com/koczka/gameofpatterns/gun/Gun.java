package com.koczka.gameofpatterns.gun;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.koczka.gameofpatterns.GameOfPatterns;

abstract public class Gun {

    protected World world;

    protected float bulletLifetime;

    protected float bulletSpeed;

    protected Array<Bullet> bullets = new Array<Bullet>();

    public float reloadTime;

    abstract public void shoot(float x, float y, GameOfPatterns game);

    abstract public void render(SpriteBatch batch);

}
