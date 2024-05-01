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
import com.minecraft.game.utils.BodyHelperService;
import com.minecraft.game.utils.Constants;

/**
 * Represents a Minecraft map that implements the IMinecraftMap interface.
 * This class provides methods for interacting with the map, such as getting
 * cells, adding and removing blocks,
 * setting up the map, and accessing the world and player rectangle.
 */
public class MinecraftMap implements IMinecraftMap {

    protected TiledMap tiledMap;
    private World world;
    private Rectangle playerRectangle;

    /**
     * Constructs a new MinecraftMap object with a new world.
     * Rectangle representing the player is set to null.
     */
    public MinecraftMap() {
        this.world = new World(new Vector2(0, -25f), false);
        this.playerRectangle = null;
    }

    @Override
    public Cell getCell(int tileX, int tileY) {
        TiledMap tiledMap = getTiledMap();
        TiledMapTileLayer mineableLayer = (TiledMapTileLayer) tiledMap.getLayers().get("mineable");
        return mineableLayer.getCell(tileX, tileY);
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

    /**
     * Checks if a tile at the specified coordinates is mineable.
     *
     * @param tileX the x-coordinate of the tile
     * @param tileY the y-coordinate of the tile
     * @return true if the tile is mineable, false otherwise
     */
    public boolean isTileMineable(int tileX, int tileY) {
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("mineable");
        return layer.getCell(tileX, tileY) != null;
    }

    /**
     * Creates a static body for a given PolygonMapObject.
     *
     * @param polygonMapObject The PolygonMapObject to create the body for.
     * @param isSensor         Specifies whether the body is a sensor or not.
     * @param userData         The user data associated with the body.
     */
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

    /**
     * Removes the static body associated with the given PolygonMapObject.
     *
     * @param polygonMapObject the PolygonMapObject whose static body needs to be
     *                         removed
     */
    private void removeStaticBody(PolygonMapObject polygonMapObject) {
        Body body = (Body) polygonMapObject.getProperties().get("body");
        if (body != null) {
            getWorld().destroyBody(body);
        }
    }

    /**
     * Creates map objects for all tiles in the tiled map.
     * This method iterates through each layer in the tiled map and checks if it is
     * a TiledMapTileLayer.
     * For each tile in the layer, it retrieves the tile's ID and gets the
     * corresponding TileType.
     * If a valid TileType is found, it creates a polygon map object at the
     * specified coordinates.
     */
    protected void createMapObjectsForAllTiles() {
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

    /**
     * Parses the map objects and creates static bodies for polygons and assigns the
     * player rectangle.
     *
     * @param mapObjects the map objects to be parsed
     */
    protected void parseMapObjects(MapObjects mapObjects) {
        for (MapObject mapObject : mapObjects) {
            if (mapObject instanceof PolygonMapObject) {
                createStaticBody((PolygonMapObject) mapObject,
                        !mapObject.getProperties().get("collidable", Boolean.class), mapObject.getName());
            }

            if (mapObject instanceof RectangleMapObject) {
                Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();

                String rectangleName = mapObject.getName();

                if (rectangleName != null && rectangleName.equals("player")) { // Check if rectangleName is not null
                    this.playerRectangle = rectangle;
                }
            }
        }
    }

    /**
     * Returns the player rectangle.
     * 
     * @return Rectangle object representing the player.
     */
    public Rectangle getPlayerRectangle() {
        return this.playerRectangle;
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

    /**
     * Creates a PolygonMapObject representing a tile at the specified coordinates.
     * The method constructs a PolygonMapObject based on the provided coordinates,
     * tile type,
     * and TiledMapTileLayer, and adds it to the object layer of the TiledMap.
     *
     * @param x          The x-coordinate of the tile
     * @param y          The y-coordinate of the tile
     * @param tileType   The type of tile to create
     * @param tiledLayer The TiledMapTileLayer to add the PolygonMapObject to
     * @return The created PolygonMapObject
     */
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

    /**
     * Adds a map object to the Minecraft map object layer at the specified
     * coordinates.
     *
     * @param x      the x-coordinate of the map object
     * @param y      the y-coordinate of the map object
     * @param tileId the ID of the tile for the map object
     */
    private void addMapObject(int x, int y, int tileId) {
        TiledMapTileLayer tiledLayer = (TiledMapTileLayer) tiledMap.getLayers().get("mineable");
        TileType tileType = TileType.getTileTypeWithId(tileId);
        if (tileType != null) {
            PolygonMapObject polygon = createPolygonMapObject(x, y, tileType, tiledLayer);
            createStaticBody(polygon,
                    !polygon.getProperties().get("collidable", Boolean.class), polygon.getName());
        }
    }

    /**
     * Removes a map object from the object layer at the specified coordinates.
     *
     * @param x The x-coordinate of the map object.
     * @param y The y-coordinate of the map object.
     */
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

    /**
     * Adds a tile of the specified type at the given coordinates.
     *
     * @param x        the x-coordinate of the tile
     * @param y        the y-coordinate of the tile
     * @param tileType the type of the tile to be added
     */
    private void addTile(int x, int y, TileType tileType) {
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("mineable");
        TiledMapTileSets tileSets = tiledMap.getTileSets();
        TiledMapTile tile = tileSets.getTile(tileType.getId());
        Cell cell = new Cell();
        cell.setTile(tile);
        layer.setCell(x, y, cell);
    }

    /**
     * Removes a tile at the specified coordinates from the mineable layer of the
     * map.
     *
     * @param x the x-coordinate of the tile to remove
     * @param y the y-coordinate of the tile to remove
     */
    private void removeTile(int x, int y) {
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("mineable");
        layer.setCell(x, y, null);
    }
}