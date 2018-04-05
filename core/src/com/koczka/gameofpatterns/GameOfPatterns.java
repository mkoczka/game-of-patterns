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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

public class GameOfPatterns extends ApplicationAdapter {

    static final int PPM = 1;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private TiledMap map;
    private TiledMapRenderer renderer;
    private BitmapFont font;
    private OrthoCamController cameraController;
    private World world;
    private Box2DDebugRenderer debugRenderer;

    private Player player;
    private Enemy enemy;

    @Override
    public void create() {

        world = new World(new Vector2(0, 0), true);
        debugRenderer = new Box2DDebugRenderer();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        font = new BitmapFont();
        batch = new SpriteBatch();

        map = new TmxMapLoader().load("map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (w / h) * 320, 320);
        camera.translate(220, 180);
        camera.zoom = 2;
        camera.update();

        cameraController = new OrthoCamController(camera);
        Gdx.input.setInputProcessor(cameraController);

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

        player = new Player(48, 64, 300, 200, world);
        enemy = new Enemy(48, 64, 400, 200, world);
    }

    private void updatePositions() {
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);

        for (Body b : bodies) {
            Character e = (Character) b.getUserData();

            if (e != null) {
                e.setPosition(b.getPosition().x, b.getPosition().y);
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
        batch.draw(player.texture, player.x * 0.75f - player.width * 0.5f, player.y * 0.75f - player.height * 0.5f);
        batch.draw(enemy.texture, enemy.x * 0.75f - enemy.width * 0.5f, enemy.y * 0.75f - enemy.height * 0.5f);
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        batch.end();

        player.render();
        enemy.render();

        debugRenderer.render(world, camera.combined);
        world.step(1 / 60f, 6, 2);
    }
}
