package com.minecraft.game.utils;

import com.badlogic.gdx.maps.Map;
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
        tiledMap = new TmxMapLoader().load("assets/mapExample2-64.tmx");
        createMapObjects();
        parseMapObjects(tiledMap.getLayers().get("collisions").getObjects());
        //parseMapObjects(tiledMap.getLayers().get("liquid").get);
        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    private void parseMapObjects(MapObjects mapObjects) {
        for (MapObject mapObject : mapObjects) {

            if (mapObject instanceof PolygonMapObject) {
                createStaticBody((PolygonMapObject) mapObject);
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
                            gameScreen.getWorld());
                    gameScreen.setPlayer(new Player(rectangle.getHeight(), rectangle.getWidth(), body));
                }
            }
        }    
    }

    private void createStaticBody(PolygonMapObject polygonMapObject) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = gameScreen.getWorld().createBody(bodyDef);
        Shape shape = createPolygonShape(polygonMapObject);
        body.createFixture(shape, 1000);
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
			//System.out.println("Layer is: " + layer.getName());

		// Access the tile layers
        // we have two map layers, liquid and mineable. 
		//MapLayer layer = tiledMap.getLayers().get("mineable");

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
                            TileType  tileType = TileType.getTileTypeWithId(tileId);
                            if (tileType !=  null){

                                if (tileType.isCollidable()) {
                                    MapLayer objectLayer = tiledMap.getLayers().get("collisions");

                                    float[] vertices = {
                                        x * tiledLayer.getTileWidth(), y * tiledLayer.getTileHeight(),
                                        (x + 1) * tiledLayer.getTileWidth(), y * tiledLayer.getTileHeight(),
                                        (x + 1) * tiledLayer.getTileWidth(), (y + 1) * tiledLayer.getTileHeight(),
                                        x * tiledLayer.getTileWidth(), (y + 1) * tiledLayer.getTileHeight()
                                    };

                                    MapObjects objects =  objectLayer.getObjects();
                                    PolygonMapObject polygon =  new PolygonMapObject(vertices);                            
                                    
                                   // polygon.getPolygon().setVertices(vertices);
                                    objects.add(polygon);
                                    
                                }

                            }
                            //System.out.println("Tile at (" + x + ", " + y + ") has ID: " + tileId);
                        }
                    }
                }
            }}}
}
