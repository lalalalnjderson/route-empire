package routeempire.model.map;

public enum TileType{
    EMPTY(true, false),
    VEGETATION(true, false),
    WATER(false, false),
    CITY_BUILDING(false, false),
    CITY_ROAD(false, true),
    PLAYER_ROAD(false, true),
    BRIDGE(false, true),
    STOP(false, true),
    GARAGE(false, false),
    FACILITY(false, false);

    private final boolean buildable;
    private final boolean passable;

    private TileType(boolean buildable, boolean passable) {
        this.buildable = buildable;
        this.passable = passable;
    }

    public boolean isBuildable(){
        return buildable;
    }

    public boolean isPassable(){
        return passable;
    }
    
}