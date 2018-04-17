package com.koczka.gameofpatterns.gun;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.koczka.gameofpatterns.GameOfPatterns;

public class Shotgun extends Gun {

    private Array<Bullet> bullets = new Array<Bullet>();

    public Shotgun(World world) {
        this.world = world;

        this.reloadTime = 1000;
        this.bulletLifetime = 1.2f;
        this.bulletSpeed = 0.2f;
    }

    public void shoot(float x, float y, GameOfPatterns game) {
        bullets.add(new Bullet(x, y, this.bulletLifetime, this.bulletSpeed, this.world, game));
    }

    public void render(SpriteBatch batch) {
        for (Bullet bullet : this.bullets) {
            bullet.render(batch);
        }
    }

}
