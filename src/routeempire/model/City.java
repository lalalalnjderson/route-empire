package routeempire.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import routeempire.model.map.GameMap;
import routeempire.model.map.Position;
import routeempire.model.map.Tile;
import routeempire.model.map.TileType;

public class City{
    private static final double DEMAND_FLUCTUATION = 0.10;
    private static final int EXPANSION_THRESHOLD = 500;
    private final String name;
    private int population;
    private int passengerDemandThisMonth;
    private int goodsDemandThisMonth;
    private int passengersDeliveredThisMonth;
    private int goodsDeliveredThisMonth;
    private double passengerSatisfaction; // calculated each month
    private double goodsSatisfaction;
    private int populationAtLastExpansion; // for growth tracking
    private final Random random;
    private int originX;
    private int originY;
    private int gridWidth;
    private int gridHeight;
    private int nextDirection;

    public City(String name, int population, int originX, int originY,
                int gridWidth, int gridHeight, GameMap map){
        this.name = name;
        this.population = population;
        this.random = new Random();
        this.populationAtLastExpansion = population;
        this.passengerSatisfaction = 0.0;
        this.goodsSatisfaction = 0.0;
        this.originX = originX;
        this.originY = originY;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.nextDirection = 0;
        placeTilesOnMap(map, originX, originY, gridWidth, gridHeight);
        recalculateDemand();
    }

    public int getPassengerDemand(){
        return passengerDemandThisMonth;
    }

    public int getGoodsDemand(){
        return goodsDemandThisMonth;
    }

    private void recalculateDemand(){
        double fluctuation = 1.0 + (random.nextDouble() * 2 - 1) * DEMAND_FLUCTUATION;
        passengerDemandThisMonth = Math.max(1, (int) (population / 10.0 * fluctuation));
        goodsDemandThisMonth = Math.max(1, (int)(population / 20.0 * fluctuation));
    }

    public void deliverCargo(CargoType type, int amount){
        if (type == CargoType.PASSENGERS){
            passengersDeliveredThisMonth += amount;
        }
        else if (type == CargoType.GOODS){
            goodsDeliveredThisMonth += amount;
        }
    }

    // called at the end of each month 
    public void monthlyUpdate(GameMap map){
        if (passengerDemandThisMonth > 0){
            passengerSatisfaction = Math.min(1.0, (double) passengersDeliveredThisMonth / passengerDemandThisMonth);
        }
        else{
            passengerSatisfaction = 0.0;
        }

        if (goodsDemandThisMonth > 0){
            goodsSatisfaction = Math.min(1.0, (double) goodsDeliveredThisMonth / goodsDemandThisMonth); 
        }
        else{
            goodsSatisfaction = 0.0;
        }

        double growthRate = getGrowthRate();
        int populationGain = (int) (population * growthRate);
        if (populationGain < 1){
            populationGain = 1;
        }
        population += populationGain;

        while (population - populationAtLastExpansion >= EXPANSION_THRESHOLD){
            tryExpand(map);
            populationAtLastExpansion += EXPANSION_THRESHOLD;
        }
        passengersDeliveredThisMonth = 0;
        goodsDeliveredThisMonth = 0; 
        recalculateDemand();
    }

    private double getGrowthRate(){
        double overall = getOverallSatisfaction();
        if (overall >= 0.8) return 0.05;
        if (overall >= 0.6) return 0.035;
        if (overall >= 0.3) return 0.02;
        if (overall >= 0.01) return 0.01;
        return 0.005;
    }

    public double getOverallSatisfaction(){
        return (passengerSatisfaction + goodsSatisfaction) / 2.0;
    }

    private void tryExpand(GameMap map){
        for (int i=0; i<4; i++){
            boolean success = expandInDirection(map, nextDirection);
            nextDirection = (nextDirection + 1) % 4;
            if (success) {
                return;
            }
        }
    }
    
    private boolean isRoadTile(int localX, int localY) {
        return (localY % 3 == 0) || (localX % 3 == 2);
    }

    private void placeTilesOnMap(GameMap map, int fromX, int fromY, int width, int height) {
        for (int y = fromY; y < fromY + height; y++) {
            for (int x = fromX; x < fromX + width; x++) {
                int localX = x - originX;
                int localY = y - originY;
 
                TileType type;
                if (isRoadTile(localX, localY)) {
                    type = TileType.CITY_ROAD;
                } else {
                    type = TileType.CITY_BUILDING;
                }
 
                Tile tile = map.getTile(x, y);
                if (tile != null) {
                    tile.setType(type);
                }
            }
        }
    }

    private boolean expandInDirection(GameMap map, int direction) {
        int newFromX, newFromY, newWidth, newHeight;
 
        switch (direction) {
            case 0: // North
                newFromX = originX;
                newFromY = originY - 3;
                newWidth = gridWidth;
                newHeight = 3;
                break;
            case 1: // East
                newFromX = originX + gridWidth;
                newFromY = originY;
                newWidth = 3;
                newHeight = gridHeight;
                break;
            case 2: // South
                newFromX = originX;
                newFromY = originY + gridHeight;
                newWidth = gridWidth;
                newHeight = 3;
                break;
            case 3: // West
                newFromX = originX - 3;
                newFromY = originY;
                newWidth = 3;
                newHeight = gridHeight;
                break;
            default:
                return false;
        }
 
        if (!canPlaceTiles(map, newFromX, newFromY, newWidth, newHeight)) {
            return false;
        }
        if (direction == 0) { // North
            originY -= 3;
            gridHeight += 3;
        } else if (direction == 1) { // East
            gridWidth += 3;
        } else if (direction == 2) { // South
            gridHeight += 3;
        } else if (direction == 3) { // West
            originX -= 3;
            gridWidth += 3;
        }
        placeTilesOnMap(map, newFromX, newFromY, newWidth, newHeight);
        return true;
    }
 
    private boolean canPlaceTiles(GameMap map, int fromX, int fromY, int width, int height) {
        for (int y = fromY; y < fromY + height; y++) {
            for (int x = fromX; x < fromX + width; x++) {
                if (!map.isInBounds(x, y)) {
                    return false;
                }
                Tile tile = map.getTile(x, y);
                if (tile == null) {
                    return false;
                }
                TileType type = tile.getType();
                if (type != TileType.EMPTY && type != TileType.VEGETATION) {
                    return false;
                }
            }
        }
        return true;
    }
 
    public boolean containsTile(Position pos) {
        int x = pos.getX();
        int y = pos.getY();
        return x >= originX && x < originX + gridWidth
                && y >= originY && y < originY + gridHeight;
    }
 
    public boolean isRoadAt(Position pos) {
        if (!containsTile(pos)) {
            return false;
        }
        int localX = pos.getX() - originX;
        int localY = pos.getY() - originY;
        return isRoadTile(localX, localY);
    }
 
    public List<Position> getAllTiles() {
        List<Position> tiles = new ArrayList<>();
        for (int y = originY; y < originY + gridHeight; y++) {
            for (int x = originX; x < originX + gridWidth; x++) {
                tiles.add(new Position(x, y));
            }
        }
        return tiles;
    }
 
    public List<Position> getRoadTiles() {
        List<Position> roads = new ArrayList<>();
        for (int y = originY; y < originY + gridHeight; y++) {
            for (int x = originX; x < originX + gridWidth; x++) {
                int localX = x - originX;
                int localY = y - originY;
                if (isRoadTile(localX, localY)) {
                    roads.add(new Position(x, y));
                }
            }
        }
        return roads;
    }
    public String getName() { return name; }
    public int getPopulation() { return population; }
    public int getOriginX() { return originX; }
    public int getOriginY() { return originY; }
    public int getGridWidth() { return gridWidth; }
    public int getGridHeight() { return gridHeight; }
    public double getPassengerSatisfaction() { return passengerSatisfaction; }
    public double getGoodsSatisfaction() { return goodsSatisfaction; }
}
