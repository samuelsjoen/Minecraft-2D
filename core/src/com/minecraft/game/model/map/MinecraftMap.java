package com.minecraft.game.model.map;

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
import com.minecraft.game.utils.BodyHelperService;
import com.minecraft.game.utils.Constants;

public class MinecraftMap implements IMinecraftMap {

    private TiledMap tiledMap;
    private World world;
    private Player player;

    public MinecraftMap() {
        this.world = new World(new Vector2(0, -25f), false);
    }

    @Override
    public OrthogonalTiledMapRenderer setupMap(String mapPath) {
        tiledMap = MapLoader.loadTileMap(mapPath);
        createMapObjectsForAllTiles();
        parseMapObjects(tiledMap.getLayers().get("collisions").getObjects());
        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public void removeBlock(int x, int y) {
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("mineable");
        if (layer.getCell(x, y) == null) {
            return;
        }
        removeMapObject(x, y);
        removeTile(x, y);
    }

    @Override
    public void addBlock(int x, int y, TileType tileType) {
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("mineable");
        if (layer.getCell(x, y) != null) {
            return;
        }

        addMapObject(x, y, tileType.getId());
        addTile(x, y, tileType);
    }

    @Override
    public TiledMap getTiledMap() {
        return tiledMap;
    }

    private void createStaticBody(PolygonMapObject polygonMapObject, boolean isSensor, String userData) {
        Body body = BodyHelperService.createBody(
                createPolygonShape(polygonMapObject),
                true,
                getWorld(),
                Constants.CATEGORY_WORLD,
                Constants.MASK_WORLD,
                userData,
                isSensor);

        polygonMapObject.getProperties().put("body", body);
    }

    private void removeStaticBody(PolygonMapObject polygonMapObject) {
        Body body = (Body) polygonMapObject.getProperties().get("body");
        if (body != null) {
            getWorld().destroyBody(body);
        }
    }

    private void createMapObjectsForAllTiles () {
        for (MapLayer layer : tiledMap.getLayers()) {
            if (layer instanceof TiledMapTileLayer) {
                TiledMapTileLayer tiledLayer = (TiledMapTileLayer) layer;
                for (int y = 0; y < tiledLayer.getHeight(); y++) {
                    for (int x = 0; x < tiledLayer.getWidth(); x++) {
                        Cell cell = tiledLayer.getCell(x, y);
                        if (cell != null) {
                            int tileId = cell.getTile().getId();
                            TileType tileType = TileType.getTileTypeWithId(tileId);
                            if (tileType != null) {
                                createPolygonMapObject(x, y, tileType, tiledLayer);
                            }
                        }
                    }
                }
            }
        }
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

                if (rectangleName != null && rectangleName.equals("player")) { // Check if rectangleName is not null
                    Body body = BodyHelperService.createBody(
                            rectangle.getX() + rectangle.getWidth() / 2,
                            rectangle.getY() + rectangle.getHeight() / 2,
                            rectangle.getWidth(),
                            rectangle.getHeight(),
                            null,
                            false,
                            getWorld(),
                            Constants.CATEGORY_PLAYER,
                            Constants.MASK_PLAYER,
                            "player",
                            false);
                    // TODO: player should probably be initialized in minecraftmodel?
                    this.player = new Player(rectangle.getHeight(), rectangle.getWidth(), body);
                }
            }
        }
    }

    static Shape createPolygonShape(PolygonMapObject polygonMapObject) {
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

    private PolygonMapObject createPolygonMapObject(int x, int y, TileType tileType, TiledMapTileLayer tiledLayer) {
        MapLayer objectLayer = tiledMap.getLayers().get("collisions");

        int tileWidth = tiledLayer.getTileWidth();
        int tileHeight = tiledLayer.getTileHeight();

        float[] vertices = {
                x * tileWidth, y * tileHeight,
                (x + 1) * tileWidth, y * tileHeight,
                (x + 1) * tileWidth, (y + 1) * tileHeight,
                x * tileWidth, (y + 1) * tileHeight
        };

        String coordinatesTile = (x * tileWidth) + ", " + (y * tileHeight);

        MapObjects objects = objectLayer.getObjects();

        PolygonMapObject polygon = new PolygonMapObject(vertices);
        polygon.setName(coordinatesTile);
        polygon.getProperties().put("id", tileType.getId());
        polygon.getProperties().put("collidable", tileType.isCollidable());

        objects.add(polygon);

        return polygon;
    }

    private void addMapObject(int x, int y, int tileId) {
        TiledMapTileLayer tiledLayer = (TiledMapTileLayer) tiledMap.getLayers().get("mineable");
        TileType tileType = TileType.getTileTypeWithId(tileId);
        if (tileType != null) {
            PolygonMapObject polygon = createPolygonMapObject(x, y, tileType, tiledLayer);
            createStaticBody(polygon,
                    !polygon.getProperties().get("collidable", Boolean.class), polygon.getName());
        }
    }

    private void removeMapObject(int x, int y) {
        MapLayer objectLayer = tiledMap.getLayers().get("collisions");
        MapObjects objects = objectLayer.getObjects();
        String coordinatesTile = x * Constants.TILE_SIZE + ", " + y * Constants.TILE_SIZE;
        MapObject mapObject = objects.get(coordinatesTile);
        if (mapObject instanceof PolygonMapObject) {
            PolygonMapObject polygon = (PolygonMapObject) mapObject;
            removeStaticBody(polygon);
            objects.remove(mapObject);
        }
    }

    private void addTile(int x, int y, TileType tileType) {
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("mineable");
        TiledMapTileSets tileSets = tiledMap.getTileSets();
        TiledMapTile tile = tileSets.getTile(tileType.getId());
        Cell cell = new Cell();
        cell.setTile(tile);
        layer.setCell(x, y, cell);
    }

    private void removeTile(int x, int y) {
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("mineable");
        layer.setCell(x, y, null);
    }

    // Setter method for tiledMap to be used for testing
    public void setTiledMap(TiledMap tiledMap) {
        this.tiledMap = tiledMap;
    }
}