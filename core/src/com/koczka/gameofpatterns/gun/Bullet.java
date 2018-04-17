package com.koczka.gameofpatterns.gun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.koczka.gameofpatterns.Entity;
import com.koczka.gameofpatterns.GameOfPatterns;

public class Bullet extends Entity {

    float width;
    float height;

    float angle;

    float x;
    float y;

    public Texture texture;
    private Sprite sprite;

    World world;
    GameOfPatterns game;

    Body body;

    private long bulletStartTime = System.currentTimeMillis();
    private float bulletLifetime;
    private float bulletSpeed;

    private boolean destroy = false;

    Bullet(float x, float y, float bulletLifetime, float bulletSpeed, World world, GameOfPatterns game) {
        this.width = 7;
        this.height = 31;

        this.x = x;
        this.y = y;
        this.world = world;
        this.game = game;

        this.bulletLifetime = bulletLifetime;
        this.bulletSpeed = bulletSpeed;

        float mouseY = (float) (320 - Gdx.input.getY()) / GameOfPatterns.PPM + 7f;
        float mouseX = (float) (Gdx.input.getX()) / GameOfPatterns.PPM + 1f;
        float bulletY;
        float bulletX;
        float impulseY;
        float impulseX;

        if (mouseY > y) {
            impulseY = bulletSpeed;
            bulletY = 100;
        } else {
            impulseY = -bulletSpeed;
            bulletY = -100;
        }

        if (mouseX > x) {
            impulseX = bulletSpeed;
            bulletX = 100;
        } else {
            impulseX = -bulletSpeed;
            bulletX = -100;
        }

        texture = new Texture(Gdx.files.internal("bullet.png"));
        this.sprite = new Sprite(texture, 7, 31);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        float xPos = x + bulletX / GameOfPatterns.PPM;
        float yPos = y + bulletY / GameOfPatterns.PPM;
        bodyDef.position.set(xPos, yPos);
        this.body = world.createBody(bodyDef);

        if (bulletX < 0 && bulletY < 0) {
            this.body.setTransform(xPos, yPos, MathUtils.degreesToRadians * 135);
        }
        if (bulletX < 0 && bulletY > 0) {
            this.body.setTransform(xPos, yPos, MathUtils.degreesToRadians * 45);
        }
        if (bulletX > 0 && bulletY < 0) {
            this.body.setTransform(xPos, yPos, MathUtils.degreesToRadians * 45);
        }
        if (bulletX > 0 && bulletY > 0) {
            this.body.setTransform(xPos, yPos, MathUtils.degreesToRadians * 135);
        }

        this.body.setUserData(this);

        CircleShape shape = new CircleShape();
        shape.setRadius(10f / GameOfPatterns.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        this.body.setBullet(true);
        fixtureDef.density = 0.1f;

        this.body.createFixture(fixtureDef);

        this.body.applyLinearImpulse(impulseX, impulseY, this.body.getWorldCenter().x, this.body.getWorldCenter().y, true);

        shape.dispose();
    }

    void bulletDistanceTimer() {
        if (!this.destroy && System.currentTimeMillis() - bulletStartTime > this.bulletLifetime * 1000) {
            this.destroy = true;
            this.game.destroy(this);
        }
    }

    @Override
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void setRotation(float angle) {
        this.angle = angle;
    }

    @Override
    public Body getBody() {
        return this.body;
    }

    void render(SpriteBatch batch) {
        if (!this.destroy) {
            sprite.setSize(this.width, this.height);
            sprite.setPosition((this.x * 24 - this.width * 0.5f), (this.y * 24 - this.height * 0.5f));
            sprite.setOrigin(body.getPosition().x / 24 + this.width * 0.5f, body.getPosition().y / 24 + this.height * 0.5f);
            sprite.setRotation(this.angle);
            sprite.draw(batch);
            this.bulletDistanceTimer();
        }
    }

    public void destroy() {
        this.destroy = true;
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run () {
                world.destroyBody(body);
            }
        });
    }
}
