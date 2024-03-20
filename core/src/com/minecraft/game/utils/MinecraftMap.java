package com.minecraft.game.utils;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.minecraft.game.model.Player;
//import com.minecraft.game.view.screens.GameScreen;

public class MinecraftMap {

    private TiledMap tiledMap;
    //private GameScreen gameScreen;
    private String mapPath;
    private World world;

    private Player player;

    public MinecraftMap() {// GameScreen gameScreen) {
        //this.gameScreen = gameScreen;
        this.mapPath = "assets/map/mapExample3-64.tmx";
        this.world = new World(new Vector2(0, -25f), false);
    }

    public OrthogonalTiledMapRenderer setupMap() {
        tiledMap = TileMapLoader.loadTileMap(mapPath);
        createMapObjectsForAllTiles();
        parseMapObjects(tiledMap.getLayers().get("collisions").getObjects());
        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    public World getWorld() {
        return world;
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
                            null,
                            false,
                            getWorld(),
                            //gameScreen.getWorld(),
                            Constants.CATEGORY_PLAYER,
                            Constants.MASK_PLAYER,
                            "player",
                            false);
                    this.player = new Player(rectangle.getHeight(), rectangle.getWidth(), body);
                    //gameScreen.setPlayer(new Player(rectangle.getHeight(), rectangle.getWidth(), body));
                }
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    // Create a static body for a polygon map object and add it to the world
    private void createStaticBody(PolygonMapObject polygonMapObject, boolean isSensor, String userData) {
        Body body = BodyHelperService.createBody(
                createPolygonShape(polygonMapObject),
                true,
                getWorld(),
                //gameScreen.getWorld(),
                Constants.CATEGORY_WORLD,
                Constants.MASK_WORLD,
                userData,
                isSensor);

        // Add the body to the map object so that we can access it later
        polygonMapObject.getProperties().put("body", body);
    }

    // Remove a static body from the world
    private void removeStaticBody(PolygonMapObject polygonMapObject) {
        Body body = (Body) polygonMapObject.getProperties().get("body");
        if (body != null) {
            getWorld().destroyBody(body);

            //gameScreen.getWorld().destroyBody(body);
        }
    }

    // Create a polygon shape
    static Shape createPolygonShape(PolygonMapObject polygonMapObject) {
        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; i++) {
            // Converting the vertices from pixels to meters ?
            Vector2 current = new Vector2(vertices[i * 2] / Constants.PPM, vertices[i * 2 + 1] / Constants.PPM);
            worldVertices[i] = current;
        }

        PolygonShape shape = new PolygonShape();
        shape.set(worldVertices);
        return shape;
    }

    // Create a map object for each tile in the map (these will be polygonmapobjects)
    private void createMapObjectsForAllTiles () {
        // Iterate through each layer of the map
        for (MapLayer layer : tiledMap.getLayers()) {
            if (layer instanceof TiledMapTileLayer) {
                TiledMapTileLayer tiledLayer = (TiledMapTileLayer) layer;
                // Iterate through each cell of the layer
                for (int y = 0; y < tiledLayer.getHeight(); y++) {
                    for (int x = 0; x < tiledLayer.getWidth(); x++) {
                        // Get the cell in the layer at the given coordinates
                        Cell cell = tiledLayer.getCell(x, y);
                        if (cell != null) {
                            // Get the tile ID of the cell
                            int tileId = cell.getTile().getId();
                            // Get the tile type based on the tile ID
                            TileType tileType = TileType.getTileTypeWithId(tileId);
                            if (tileType != null) {
                                // Create a polygon map object for the tile
                                createPolygonMapObject(x, y, tileType, tiledMap, tiledLayer);
                            }
                        }
                    }
                }
            }
        }
    }

    // Create a polygon map object
    private PolygonMapObject createPolygonMapObject(int x, int y, TileType tileType, TiledMap tiledMap, TiledMapTileLayer tiledLayer) {
        MapLayer objectLayer = tiledMap.getLayers().get("collisions");

        int tileWidth = tiledLayer.getTileWidth();
        int tileHeight = tiledLayer.getTileHeight();

        float[] vertices = {
                x * tileWidth, y * tileHeight,
                (x + 1) * tileWidth, y * tileHeight,
                (x + 1) * tileWidth, (y + 1) * tileHeight,
                x * tileWidth, (y + 1) * tileHeight
        };
        // Saving the coordinates of the tile/polygon for when we need to remove it!
        String coordinatesTile = (x * tileWidth) + ", " + (y * tileHeight);

        // Get the map objects of the object layer
        MapObjects objects = objectLayer.getObjects();

        PolygonMapObject polygon = new PolygonMapObject(vertices);
        polygon.setName(coordinatesTile);
        polygon.getProperties().put("id", tileType.getId());
        polygon.getProperties().put("collidable", tileType.isCollidable());

        // Add the polygon to the map objects
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
        // Fetching the coordinates of the tile
        String coordinatesTile = x * Constants.TILE_SIZE + ", " + y * Constants.TILE_SIZE;
        // Get the map object at the given coordinates
        MapObject mapObject = objects.get(coordinatesTile);
        if (mapObject instanceof PolygonMapObject) {
            PolygonMapObject polygon = (PolygonMapObject) mapObject;
            removeStaticBody(polygon);
            int objectId = (int) polygon.getProperties().get("id");
            // Remove the mapObject from objectlayer
            objects.remove(objectId);
        }
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
        TiledMapTileLayer tiledLayer = (TiledMapTileLayer) tiledMap.getLayers().get("mineable");
        TileType tileType = TileType.getTileTypeWithId(tileId);
        if (tileType != null) {
            PolygonMapObject polygon = createPolygonMapObject(x, y, tileType, tiledMap, tiledLayer);
            createStaticBody(polygon,
                    !polygon.getProperties().get("collidable", Boolean.class), polygon.getName());
        }
    }

    public void removeBlock(int x, int y, TiledMap tiledMap) {
        // check if there is a block to remove at the given coordinates
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("mineable");
        if (layer.getCell(x, y) == null) {
            return;
        }
        removeMapObject(x, y, tiledMap);
        removeTile(x, y, tiledMap);
    }

    public void addBlock(int x, int y, TileType tileType, TiledMap tiledMap) {
        // check if there is already a block at the coordinates
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("mineable");
        if (layer.getCell(x, y) != null) {
            return;
        }

        addMapObject(x, y, tileType.getId(), tiledMap);
        addTile(x, y, tileType, tiledMap);
    }

    public TiledMap getTiledMap() {
        return tiledMap;
    }

}
