package com.koczka.gameofpatterns;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.koczka.gameofpatterns.character.Enemy;
import com.koczka.gameofpatterns.character.Player;
import com.koczka.gameofpatterns.character.SoldierCharacterFactory;

public class GameOfPatterns extends ApplicationAdapter {

    public static final int PPM = 32;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private TiledMap map;
    private TiledMapRenderer renderer;
    private BitmapFont font;
    private World world;
    private Box2DDebugRenderer debugRenderer;

    private Player player;
    private Enemy enemy;

    private Array<Entity> activeEntities;
    private Array<Entity> destroyEntities;

    private Array<Enemy> enemies;

    @Override
    public void create() {
        activeEntities = new Array<Entity>();
        destroyEntities = new Array<Entity>();
        enemies = new Array<Enemy>();

        world = new World(new Vector2(0, 0), true);
        debugRenderer = new Box2DDebugRenderer();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        font = new BitmapFont();
        batch = new SpriteBatch();

        map = new TmxMapLoader().load("map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1f / PPM);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (w / h) * 320, 320);
        camera.zoom = 1f / PPM * 2;
        camera.translate(camera.viewportWidth / 2 * - (1f - camera.zoom), camera.viewportHeight / 2 * - (1f - camera.zoom));
        camera.update();

        player = SoldierCharacterFactory.makePlayer(world, this);
        this.enemies.add(SoldierCharacterFactory.makeEnemy(world, this));

        world.setContactListener(new EnemyBulletCollision(player, this));

        MapLayer layer = map.getLayers().get("WallObject");
        for (MapObject object : layer.getObjects()) {
            RectangleMapObject rectObject = (RectangleMapObject) object;
            Rectangle rectangle = rectObject.getRectangle();

            PolygonShape polygonShape = new PolygonShape();

            Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) / PPM, (rectangle.y + rectangle.height * 0.5f) / PPM);
            polygonShape.setAsBox(rectangle.width * 0.5f / PPM, rectangle.height * 0.5f / PPM, size, 0.0f);

            BodyDef bd = new BodyDef();
            bd.type = BodyDef.BodyType.StaticBody;
            Body body = world.createBody(bd);
            body.createFixture(polygonShape, 1);
            polygonShape.dispose();
        }
    }

    private void updatePositions() {
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        for (Body b : bodies) {
            Entity e = (Entity) b.getUserData();

            if (e != null) {
                e.setPosition(b.getPosition().x, b.getPosition().y);
                e.setRotation(MathUtils.radiansToDegrees * b.getAngle());
            }
        }
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(100f / 255f, 100f / 255f, 250f / 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        renderer.setView(camera);
        renderer.render();

        updatePositions();

        batch.begin();
        font.draw(batch, "Score: " + GameState.getInstance().getScore(), 80, 450);
        font.draw(batch, "Pohyb: W,S,A,D   Strelba: SPACE", 300, 470);
        font.draw(batch, "Zbrane strielajú diagonalne podla polohy mysi", 300, 440);
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        player.render(batch);

        for (Enemy enemy : enemies) {
            enemy.render(batch);
        }

        batch.end();

//        debugRenderer.render(world, camera.combined);
        world.step(1 / 60f, 6, 2);

        for (Entity entity : destroyEntities) {
            world.destroyBody(entity.getBody());
        }
        destroyEntities.clear();
    }

    public void enemyDead() {
        enemies.add(SoldierCharacterFactory.makeEnemy(world, this));
    }

    public void destroy(Entity entity) {
        if (!destroyEntities.contains(entity, true)) {
            destroyEntities.add(entity);
            activeEntities.removeValue(entity, true);
        }
    }
}
