package com.koczka.gameofpatterns.gun;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.koczka.gameofpatterns.GameOfPatterns;

abstract public class Gun {

    World world;

    float bulletLifetime;

    float bulletSpeed;

    public float reloadTime;

    abstract public void shoot(float x, float y, GameOfPatterns game);

    abstract public void render(SpriteBatch batch);

}
