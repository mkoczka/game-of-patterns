package com.koczka.gameofpatterns;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Player implements Character {

    int width;
    int height;

    float x;
    float y;

    Texture texture;

    private World world;
    public Body body;

    final static int playerVelocity = 100;

    Player(int width, int height, float x, float y, World world) {
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
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            xVel = -playerVelocity;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            xVel = playerVelocity;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            yVel = playerVelocity;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            yVel = -playerVelocity;
        }
        this.body.setLinearVelocity(xVel, yVel);
    }

    private void shoot() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(200, 300);
        body = this.world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(this.width, this.height);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 10f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        body.createFixture(fixtureDef);

        shape.dispose();
    }

    public void render() {
        this.move();
    }

}
