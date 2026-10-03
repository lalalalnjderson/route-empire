package routeempire.core;

import routeempire.model.City;
import routeempire.model.facility.*;
import routeempire.model.map.GameMap;
import routeempire.model.map.Position;
import routeempire.model.map.TileType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Creates the predefined starting map with cities, facilities,
 * water, and vegetation. Called once when starting a new game.
 */
public class MapBuilder {

    private static final int MAP_WIDTH = 60;
    private static final int MAP_HEIGHT = 40;

    /**
     * Builds the complete starting GameState with map, cities, and facilities.
     */
    public static GameState createNewGame() {
        GameMap map = new GameMap(MAP_WIDTH, MAP_HEIGHT);
        GameState state = new GameState(map);

        placeWater(map);
        placeVegetation(map);
        placeCities(state, map);
        placeFacilities(state, map);

        return state;
    }

    private static void placeWater(GameMap map) {
        // A river running roughly top to bottom in the middle of the map
        int riverX = 28;
        for (int y = 0; y < MAP_HEIGHT; y++) {
            // river wiggles a bit
            if (y % 7 < 3) {
                riverX = 28;
            } else {
                riverX = 29;
            }
            map.setTileType(new Position(riverX, y), TileType.WATER);
            map.setTileType(new Position(riverX + 1, y), TileType.WATER);
        }

        // A small lake on the right side
        for (int y = 5; y <= 8; y++) {
            for (int x = 45; x <= 48; x++) {
                map.setTileType(new Position(x, y), TileType.WATER);
            }
        }
    }

    private static void placeVegetation(GameMap map) {
        Random random = new Random(42); // fixed seed so map is same every time
        for (int y = 0; y < MAP_HEIGHT; y++) {
            for (int x = 0; x < MAP_WIDTH; x++) {
                Position pos = new Position(x, y);
                // only place on empty tiles, ~15% chance
                if (map.getTile(pos).getType() == TileType.EMPTY) {
                    if (random.nextDouble() < 0.15) {
                        map.setTileType(pos, TileType.VEGETATION);
                    }
                }
            }
        }
    }

    private static void placeCities(GameState state, GameMap map) {
        // City 1: large city on the left side
        City city1 = new City("Irondale", 1500, 5, 10, 8, 6, map);
        state.addCity(city1);

        // City 2: medium city on the right side
        City city2 = new City("Milltown", 1000, 40, 20, 5, 6, map);
        state.addCity(city2);

        // City 3: small city top-right
        City city3 = new City("Greenfield", 600, 50, 3, 5, 3, map);
        state.addCity(city3);
    }

    private static void placeFacilities(GameState state, GameMap map) {
        // Iron ore mine - left side, below city1
        placeFacility(state, map, new IronOreMine("Iron Mine", makeTiles(3, 25, 2, 2)));

        // Steel mill - center-left
        placeFacility(state, map, new SteelMill("Steel Mill", makeTiles(18, 15, 2, 2)));

        // Farm - top center
        placeFacility(state, map, new Farm("Green Farm", makeTiles(15, 3, 3, 2)));

        // Factory - center, between the two cities
        placeFacility(state, map, new Factory("Central Factory", makeTiles(20, 22, 3, 2)));

        // Production forest - right side
        placeFacility(state, map, new ProductionForest("Pine Forest", makeTiles(50, 30, 2, 2)));

        // Sawmill - right side, near forest
        placeFacility(state, map, new Sawmill("Sawmill", makeTiles(44, 32, 2, 2)));
    }

    /**
     * Places a facility on the map by setting its tiles to FACILITY type.
     */
    private static void placeFacility(GameState state, GameMap map, Facility facility) {
        for (Position pos : facility.getOccupiedTiles()) {
            map.setTileType(pos, TileType.FACILITY);
        }
        state.addFacility(facility);
    }

    /**
     * Creates a list of positions for a rectangular area.
     * Used to define which tiles a facility occupies.
     */
    private static List<Position> makeTiles(int startX, int startY, int width, int height) {
        List<Position> tiles = new ArrayList<>();
        for (int y = startY; y < startY + height; y++) {
            for (int x = startX; x < startX + width; x++) {
                tiles.add(new Position(x, y));
            }
        }
        return tiles;
    }
}