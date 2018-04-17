package com.koczka.gameofpatterns.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.koczka.gameofpatterns.GameOfPatterns;
import com.koczka.gameofpatterns.weather.Weather;

public class Enemy extends Character {

    public float width;
    public float height;
    float angle;

    public float x;
    public float y;

    Texture texture;

    private World world;
    private GameOfPatterns game;
    public Body body;
    private Fixture fixture;

    int health = 2;

    private int playerVelocity = 3;

    long lastTurn = System.currentTimeMillis();
    double randomX;
    double randomY;

    public boolean destroy = false;

    Enemy(float width, float height, float x, float y, World world, GameOfPatterns game) {
        this.width = width;
        this.height = height;
        this.world = world;
        this.game = game;
        this.setPosition(x / GameOfPatterns.PPM, y / GameOfPatterns.PPM);

        texture = new Texture(Gdx.files.internal("hero.png"));

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.fixedRotation = true;
        bodyDef.position.set(x / GameOfPatterns.PPM, y / GameOfPatterns.PPM);
        body = world.createBody(bodyDef);
        body.setUserData(this);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(this.width / GameOfPatterns.PPM, this.height / GameOfPatterns.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 10f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        this.fixture = body.createFixture(fixtureDef);

        shape.dispose();
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setRotation(float angle) {
        this.angle = angle;
    }

    @Override
    public Body getBody() {
        return this.body;
    }

    private void move() {
        float xVel = 0;
        float yVel = 0;

        if (System.currentTimeMillis() - this.lastTurn >= 3000) {
            this.randomX = Math.random() * 10;
            this.randomY = Math.random() * 10;
            this.lastTurn = System.currentTimeMillis();
        }

        if (this.randomX < 4) {
            xVel = -playerVelocity;
        }
        if (this.randomX > 6) {
            xVel = playerVelocity;
        }

        if (this.randomY < 4) {
            yVel = -playerVelocity;
        }
        if (this.randomY > 6) {
            yVel = playerVelocity;
        }

        this.body.setLinearVelocity(xVel, yVel);
    }

    public void hit(int damage) {
        this.health -= damage;

        if (this.health < 1) {
            this.destroy();
        }
    }

    private void destroy() {
        this.destroy = true;
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run () {
                world.destroyBody(body);
                game.enemyDead();
            }
        });
    }

    public void render(SpriteBatch batch) {
        if (!this.destroy) {
            batch.draw(this.texture, (this.x * 24 - this.width * 0.5f), (this.y * 24 - this.height * 0.5f), this.width, this.height);
            this.move();
        }
    }

    public void setVelocityByWeather(Weather weather) {
        if (weather == Weather.RAIN) {
            this.playerVelocity = 2;
        }
        if (weather == Weather.SNOW) {
            this.playerVelocity = 1;
        }
        if (weather == Weather.SUN) {
            this.playerVelocity = 3;
        }
    }

}
