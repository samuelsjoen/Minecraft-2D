package com.minecraft.game.utils;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
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

    /**
     * Setup the map
     * 
     * @return OrthogonalTiledMapRenderer
     */
    public OrthogonalTiledMapRenderer setupMap() {
        tiledMap = new TmxMapLoader().load("assets/map/mapExample2-64.tmx");
        createMapObjects();
        parseMapObjects(tiledMap.getLayers().get("collisions").getObjects());
        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    // Parse the map objects and based on the type of map object, either create a
    // static body or a player
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
                            Constants.CATEGORY_PLAYER,
                            Constants.MASK_PLAYER,
                            "player"); // New stuff added
                    gameScreen.setPlayer(new Player(rectangle.getHeight(), rectangle.getWidth(), body));
                }
            }
        }
    }

    // Create a static body for a polygon map object and add it to the world
    private void createStaticBody(PolygonMapObject polygonMapObject, boolean isSensor, String userData) {

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = gameScreen.getWorld().createBody(bodyDef);
        Shape shape = createPolygonShape(polygonMapObject);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = isSensor;
        fixtureDef.density = 1f; // Adjust as needed
        fixtureDef.filter.categoryBits = Constants.CATEGORY_WORLD; // World category
        fixtureDef.filter.maskBits = Constants.MASK_WORLD; // Mask for world, collides with player and enemy

        body.createFixture(fixtureDef).setUserData(userData);
        polygonMapObject.getProperties().put("body", body);

        shape.dispose();
    }

    // Remove a static body from the world
    private void removeStaticBody(PolygonMapObject polygonMapObject) {
        Body body = (Body) polygonMapObject.getProperties().get("body");
        if (body != null) {
            gameScreen.getWorld().destroyBody(body);
        }
    }

    // Create a polygon shape
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

    // Create a map object for each tile in the map
    private void createMapObjects() {

        for (MapLayer layer : tiledMap.getLayers()) {

            if (layer instanceof TiledMapTileLayer) {
                TiledMapTileLayer tiledLayer = (TiledMapTileLayer) layer;

                // Iterate through each cell of the layer
                for (int y = 0; y < tiledLayer.getHeight(); y++) {
                    for (int x = 0; x < tiledLayer.getWidth(); x++) {
                        Cell cell = tiledLayer.getCell(x, y);
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
                                // So that we can get the coordinates of the tile/polygon
                                String coordinatesTile = (x * tiledLayer.getTileWidth()) + ", "
                                        + (y * tiledLayer.getTileHeight());

                                MapObjects objects = objectLayer.getObjects();

                                PolygonMapObject polygon = new PolygonMapObject(vertices);
                                polygon.setName(coordinatesTile);
                                polygon.getProperties().put("id", tileType.getId());
                                polygon.getProperties().put("collidable", tileType.isCollidable());

                                objects.add(polygon);
                            }
                        }
                        // System.out.println("Tile at (" + x + ", " + y + ") has ID: " + tileId);
                    }
                }
            }
        }
    }

    // Create a polygon map object
    private PolygonMapObject createPolygonMapObject(int x, int y, int tileId, TiledMap tiledMap) {

        MapLayer objectLayer = tiledMap.getLayers().get("collisions");
        TiledMapTileLayer tiledLayer = (TiledMapTileLayer) tiledMap.getLayers().get("mineable");
        TileType tileType = TileType.getTileTypeWithId(tileId);

        float[] vertices = {
                x * tiledLayer.getTileWidth(), y * tiledLayer.getTileHeight(),
                (x + 1) * tiledLayer.getTileWidth(), y * tiledLayer.getTileHeight(),
                (x + 1) * tiledLayer.getTileWidth(), (y + 1) * tiledLayer.getTileHeight(),
                x * tiledLayer.getTileWidth(), (y + 1) * tiledLayer.getTileHeight()
        };
        // So that we can get the coordinates of the tile/polygon
        String coordinatesTile = (x * tiledLayer.getTileWidth()) + ", "
                + (y * tiledLayer.getTileHeight());

        MapObjects objects = objectLayer.getObjects();

        PolygonMapObject polygon = new PolygonMapObject(vertices);
        polygon.setName(coordinatesTile);
        polygon.getProperties().put("id", tileType.getId());
        polygon.getProperties().put("collidable", tileType.isCollidable());

        objects.add(polygon);

        return polygon;
    }

    // Remove a tile from the map
    private void removeTile(int x, int y, TiledMap tiledMap) {
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("mineable");
        layer.setCell(x, y, null);
    }

    // Remove a map object from the map and the static body from the world
    private void removeMapObject(int x, int y, TiledMap tiledMap) {
        MapLayer objectLayer = tiledMap.getLayers().get("collisions");
        MapObjects objects = objectLayer.getObjects();
        String coordinatesTile = x * Constants.TILE_SIZE + ", " + y * Constants.TILE_SIZE;
        PolygonMapObject polygon = (PolygonMapObject) objects.get(coordinatesTile);
        removeStaticBody(polygon);
        int objectId = (int) polygon.getProperties().get("id");
        objects.remove(objectId);
    }

    // Add a tile to the map
    private void addTile(int x, int y, TileType tileType, TiledMap tiledMap) {
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("mineable");

        TiledMapTileSets tileSets = tiledMap.getTileSets();
        TiledMapTile tile = tileSets.getTile(tileType.getId());
        Cell cell = new Cell();
        cell.setTile(tile);
        layer.setCell(x, y, cell);
    }

    // Add a map object to the map
    private void addMapObject(int x, int y, int tileId, TiledMap tiledMap) {

        PolygonMapObject mapObject = createPolygonMapObject(x, y, tileId, tiledMap);

        createStaticBody((PolygonMapObject) mapObject,
                !mapObject.getProperties().get("collidable", Boolean.class), mapObject.getName());
    }

    /**
     * Remove a block from the map
     * 
     * @param x
     * @param y
     * @param tiledMap
     */
    public void removeBlock(int x, int y, TiledMap tiledMap) {
        removeTile(x, y, tiledMap);
        removeMapObject(x, y, tiledMap);
    }

    /**
     * Add a block to the map
     * 
     * @param x
     * @param y
     * @param tileType
     * @param tiledMap
     */
    public void addBlock(int x, int y, TileType tileType, TiledMap tiledMap) {
        addTile(x, y, tileType, tiledMap);
        addMapObject(x, y, tileType.getId(), tiledMap);
    }

    // Getter
    public TiledMap getTiledMap() {
        return tiledMap;
    }
}
