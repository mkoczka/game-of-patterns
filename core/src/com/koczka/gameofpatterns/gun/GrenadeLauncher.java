package com.koczka.gameofpatterns.gun;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.koczka.gameofpatterns.GameOfPatterns;

public class GrenadeLauncher extends Gun {

    public GrenadeLauncher(World world) {
        this.world = world;

        this.reloadTime = 1500;
        this.bulletLifetime = 2f;
        this.bulletSpeed = 0.3f;
    }

    public void shoot(float x, float y, GameOfPatterns game) {
        bullets.add(new Bullet(x, y, this.bulletLifetime, this.bulletSpeed, this.world, game, "red"));
    }

    public void render(SpriteBatch batch) {
        for (Bullet bullet : this.bullets) {
            bullet.render(batch);
        }
    }

}
