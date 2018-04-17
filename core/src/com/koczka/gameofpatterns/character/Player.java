package com.koczka.gameofpatterns.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.koczka.gameofpatterns.GameOfPatterns;
import com.koczka.gameofpatterns.gun.Gun;
import com.koczka.gameofpatterns.weather.Weather;

public class Player extends Character {

    public float width;
    public float height;
    public float angle;

    public float x;
    public float y;

    public Texture texture;
    Sprite sprite;

    private World world;
    public Body body;

    Gun gun;

    GameOfPatterns game;

    private int playerVelocity = 5;

    long lastShot = System.currentTimeMillis();

    Player(float width, float height, float x, float y, World world, GameOfPatterns game, Gun gun) {
        this.width = width;
        this.height = height;
        this.world = world;
        this.game = game;

        this.gun = gun;

        this.setPosition(x / GameOfPatterns.PPM, y / GameOfPatterns.PPM);

        texture = new Texture(Gdx.files.internal("hero.png"));
        sprite = new Sprite(texture, 601, 408);

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

        body.createFixture(fixtureDef);

        shape.dispose();
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setRotation(float angle) {
        this.angle = angle;
    }

    public Body getBody() {
        return this.body;
    }

    private void move() {
        float xVel = 0;
        float yVel = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            this.body.setTransform(this.x, this.y, MathUtils.degreesToRadians * 90);
            xVel = -playerVelocity;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            this.body.setTransform(this.x, this.y, MathUtils.degreesToRadians * 270);
            xVel = playerVelocity;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            this.body.setTransform(this.x, this.y, MathUtils.degreesToRadians * 0);
            yVel = playerVelocity;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            this.body.setTransform(this.x, this.y, MathUtils.degreesToRadians * 180);
            yVel = -playerVelocity;
        }
        this.body.setLinearVelocity(xVel, yVel);
    }

    private void shoot() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && System.currentTimeMillis() - lastShot > this.gun.reloadTime) {
            lastShot = System.currentTimeMillis();
            this.gun.shoot(this.x, this.y, this.game);
        }
    }

    public void render(SpriteBatch batch) {
        sprite.setSize(this.width, this.height);
        sprite.setPosition((this.x * 24 - this.width * 0.5f), (this.y * 24 - this.height * 0.5f));
        sprite.setOrigin(body.getPosition().x / 24 + this.width * 0.5f, body.getPosition().y / 24 + this.height * 0.5f);
        sprite.setRotation(this.angle);
        sprite.draw(batch);

        this.move();
        this.shoot();
        this.gun.render(batch);
    }

    public void setVelocityByWeather(Weather weather) {
        if (weather == Weather.RAIN) {
            this.playerVelocity = 4;
        }
        if (weather == Weather.SNOW) {
            this.playerVelocity = 3;
        }
        if (weather == Weather.SUN) {
            this.playerVelocity = 5;
        }
    }

}
