package routeempire.model.map;

public class Tile{
    private TileType type;
    private final Position position;
    
    public Tile(TileType type, Position position){
        this.type = type;
        this.position = position;
    }

    public TileType getType(){
        return type;
    }

    public void setType(TileType type){
        this.type = type;
    }

    public Position getPosition(){
        return position;
    }

    public boolean isBuildable(){
        return type.isBuildable();
    }

    public boolean isPassable(){
        return type.isPassable();
    }
}