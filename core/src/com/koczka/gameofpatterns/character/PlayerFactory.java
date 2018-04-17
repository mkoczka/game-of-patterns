package com.koczka.gameofpatterns.character;

import com.badlogic.gdx.physics.box2d.World;
import com.koczka.gameofpatterns.GameOfPatterns;
import com.koczka.gameofpatterns.gun.Gun;

public class PlayerFactory {

    static Player makePlayer(Gun gun, World world, GameOfPatterns game) {
        return new Player(60, 40, 400, 500, world, game, gun);
    }
}
