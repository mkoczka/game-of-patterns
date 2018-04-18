package com.koczka.gameofpatterns;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.koczka.gameofpatterns.character.Enemy;
import com.koczka.gameofpatterns.character.Player;
import com.koczka.gameofpatterns.gun.Bullet;

public class EnemyBulletCollision implements ContactListener {

    private Player player;
    private GameOfPatterns game;

    EnemyBulletCollision(Player player, GameOfPatterns game) {
        this.player = player;
        this.game = game;
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void beginContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Enemy enemy = null;
        Bullet bullet = null;

        if (contact.getFixtureA().getBody().getUserData() instanceof Bullet) {
            bullet = (Bullet) contact.getFixtureA().getBody().getUserData();
        }

        if (contact.getFixtureB().getBody().getUserData() instanceof Bullet) {
            bullet = (Bullet) contact.getFixtureB().getBody().getUserData();
        }

        if (contact.getFixtureA().getBody().getUserData() instanceof Enemy) {
            enemy = (Enemy) contact.getFixtureA().getBody().getUserData();
        }

        if (contact.getFixtureB().getBody().getUserData() instanceof Enemy) {
            enemy = (Enemy) contact.getFixtureB().getBody().getUserData();
        }

        if (bullet != null && enemy != null) {
            GameState.getInstance().addScore();
            bullet.destroy();
            enemy.hit(1);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
};