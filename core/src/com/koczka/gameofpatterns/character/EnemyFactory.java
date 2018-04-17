package com.koczka.gameofpatterns.character;

import com.badlogic.gdx.physics.box2d.World;
import com.koczka.gameofpatterns.GameOfPatterns;
import com.koczka.gameofpatterns.gun.Gun;

public class EnemyFactory {

    static Enemy makeEnemy(Gun gun, World world, GameOfPatterns game) {
        return new Enemy(60, 40, (float) Math.random() * 300 + 200, (float) Math.random() * 300 + 200, world, game);
    }

}
