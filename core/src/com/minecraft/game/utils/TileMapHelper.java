package com.minecraft.game.utils;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.minecraft.game.view.GameScreen;
import com.minecraft.game.model.Player;

public class TileMapHelper {

    private TiledMap tiledMap;
    private GameScreen gameScreen;

    public TileMapHelper(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public OrthogonalTiledMapRenderer setupMap() {
        tiledMap = new TmxMapLoader().load("assets/map/mapExample2-64.tmx");
        createMapObjects();
        parseMapObjects(tiledMap.getLayers().get("collisions").getObjects());
        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    private void parseMapObjects(MapObjects mapObjects) {
        for (MapObject mapObject : mapObjects) {

            if (mapObject instanceof PolygonMapObject) {
                createStaticBody((PolygonMapObject) mapObject,
                        !mapObject.getProperties().get("collidable", Boolean.class), mapObject.getName());
            }

            if (mapObject instanceof RectangleMapObject) {
                Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
                String rectangleName = mapObject.getName();

                if (rectangleName.equals("player")) {
                    Body body = BodyHelperService.createBody(
                            rectangle.getX() + rectangle.getWidth() / 2,
                            rectangle.getY() + rectangle.getHeight() / 2,
                            rectangle.getWidth(),
                            rectangle.getHeight(),
                            false,
                            gameScreen.getWorld(),
                            "player");
                    gameScreen.setPlayer(new Player(rectangle.getHeight(), rectangle.getWidth(), body));
                }
            }
        }
    }

    /*
     * private void createStaticBody(PolygonMapObject polygonMapObject) {
     * BodyDef bodyDef = new BodyDef();
     * bodyDef.type = BodyDef.BodyType.StaticBody;
     * Body body = gameScreen.getWorld().createBody(bodyDef);
     * Shape shape = createPolygonShape(polygonMapObject);
     * body.createFixture(shape, 1000).setUserData(shape);;
     * shape.dispose();
     * }
     */

    private void createStaticBody(PolygonMapObject polygonMapObject, boolean isSensor, String userData) {
        int density;
        if ((boolean) polygonMapObject.getProperties().get("collidable")) {
            density = 1000;
        } else {
            density = 0;
        }

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = gameScreen.getWorld().createBody(bodyDef);
        Shape shape = createPolygonShape(polygonMapObject);
        FixtureDef fdef = new FixtureDef();
        fdef.shape = shape;
        // player goes through the tile if it is a sensor
        fdef.isSensor = isSensor;
        fdef.density = density;
        body.createFixture(fdef).setUserData(userData);
        shape.dispose();
    }

    private Shape createPolygonShape(PolygonMapObject polygonMapObject) {
        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; i++) {
            Vector2 current = new Vector2(vertices[i * 2] / Constants.PPM, vertices[i * 2 + 1] / Constants.PPM);
            worldVertices[i] = current;
        }

        PolygonShape shape = new PolygonShape();
        shape.set(worldVertices);
        return shape;
    }

    private void createMapObjects() {

        for (MapLayer layer : tiledMap.getLayers()) {
            // MapLayer layer = tiledMap.getLayers().get("mineable");

            // System.out.println("Layer is: " + layer.getName());

            // Access the tile layers
            // we have two map layers, liquid and mineable.
            // MapLayer layer = tiledMap.getLayers().get("mineable");

            if (layer instanceof TiledMapTileLayer) {
                TiledMapTileLayer tiledLayer = (TiledMapTileLayer) layer;

                // Iterate through each cell of the layer
                for (int y = 0; y < tiledLayer.getHeight(); y++) {
                    for (int x = 0; x < tiledLayer.getWidth(); x++) {
                        TiledMapTileLayer.Cell cell = tiledLayer.getCell(x, y);
                        if (cell != null) {
                            // Get the tile ID of the cell
                            int tileId = cell.getTile().getId();
                            // Do something with the tile ID, for example, print it
                            TileType tileType = TileType.getTileTypeWithId(tileId);
                            if (tileType != null) {

                                MapLayer objectLayer = tiledMap.getLayers().get("collisions");

                                float[] vertices = {
                                        x * tiledLayer.getTileWidth(), y * tiledLayer.getTileHeight(),
                                        (x + 1) * tiledLayer.getTileWidth(), y * tiledLayer.getTileHeight(),
                                        (x + 1) * tiledLayer.getTileWidth(), (y + 1) * tiledLayer.getTileHeight(),
                                        x * tiledLayer.getTileWidth(), (y + 1) * tiledLayer.getTileHeight()
                                };

                                MapObjects objects = objectLayer.getObjects();

                                // if (tileType.isCollidable()) {

                                PolygonMapObject polygon = new PolygonMapObject(vertices);
                                polygon.setName(tileType.getTextureName());
                                polygon.getProperties().put("id", tileType.getId());
                                polygon.getProperties().put("collidable", tileType.isCollidable());

                                // boolean collidable = (boolean) polygon.getProperties().get("collidable");
                                // int id = (int) polygon.getProperties().get("id");

                                // polygon.getPolygon().setVertices(vertices);
                                objects.add(polygon);

                                // }

                            }

                        }
                        // System.out.println("Tile at (" + x + ", " + y + ") has ID: " + tileId);
                    }
                }
            }
        }
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

    /*public void getTileType(int tileX, int tileY) {
        if (tiledMap == null) {
            System.out.println("TiledMap is null.");
        } else {
            System.out.println("TiledMap is not null.");
        }
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("collisions");
        TiledMapTileLayer.Cell cell = layer.getCell(tileX, tileY);
        if (cell != null) {
            int tileId = cell.getTile().getId();
            return TileType.getTileTypeWithId(tileId);
        } else {
            return null;
        }
    }*/

}
