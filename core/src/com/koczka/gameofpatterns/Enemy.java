package com.koczka.gameofpatterns;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;

public class Enemy implements Character {

    int width;
    int height;

    float x;
    float y;

    Texture texture;

    private World world;
    public Body body;

    private final static int playerVelocity = 100;
    private long lastTurn = System.currentTimeMillis();
    private double randomX = 0;
    private double randomY = 0;

    private long lastShot = System.currentTimeMillis();

    Enemy(int width, int height, float x, float y, World world) {
        this.width = width;
        this.height = height;
        this.world = world;
        this.setPosition(x, y);

        texture = new Texture(Gdx.files.internal("hero.png"));

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);
        body.setUserData(this);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(this.width * 0.75f, this.height * 0.75f);

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

    private void shoot() {
        boolean leftPressed = Gdx.input.isButtonPressed(Input.Buttons.LEFT);

        if (System.currentTimeMillis() - this.lastShot >= 3000) {
            this.lastShot = System.currentTimeMillis();
        }

        if (Gdx.input.justTouched()) {
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
            bodyDef.position.set(this.x, this.y);
            body = this.world.createBody(bodyDef);

            CircleShape shape = new CircleShape();
            shape.setRadius(10);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = shape;
            fixtureDef.density = 10f;
            fixtureDef.friction = 0.4f;
            fixtureDef.restitution = 0.6f;

            body.createFixture(fixtureDef);

            shape.dispose();
        }
    }

    public void render() {
        this.move();
        this.shoot();
    }

}
