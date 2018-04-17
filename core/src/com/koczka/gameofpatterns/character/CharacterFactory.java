package com.koczka.gameofpatterns.character;

import com.badlogic.gdx.physics.box2d.World;
import com.koczka.gameofpatterns.GameOfPatterns;
import com.koczka.gameofpatterns.gun.Gun;
import com.koczka.gameofpatterns.gun.Shotgun;

abstract public class CharacterFactory {

    public static Player makePlayer(World world, GameOfPatterns game) {
        Gun gun = new Shotgun(world);
        return PlayerFactory.makePlayer(gun, world, game);
    }

    public static Enemy makeEnemy(World world, GameOfPatterns game) {
        Gun gun = new Shotgun(world);
        return EnemyFactory.makeEnemy(gun, world, game);
    }

}
