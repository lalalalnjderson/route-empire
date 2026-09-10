package routeempire.model.map;
import java.util.ArrayList;
import java.util.List;

public class GameMap{
    private final Tile[][] tiles;
    private final int width;
    private final int height;

    public GameMap(int width, int height){
        this.width = width;
        this.height = height;
        this.tiles = new Tile[height][width];

        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                tiles[i][j] = new Tile(TileType.EMPTY, new Position(i, j));
            }
        }
    }

    public boolean isInBounds(int x, int y){
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public boolean isInBounds(Position pos){
        return isInBounds(pos.getX(), pos.getY());
    }

    public Tile getTile(int x, int y){
        if (!isInBounds(x, y)){
            return null;
        }
        return tiles[x][y];
    }

    public Tile getTile(Position pos){
        return getTile(pos.getX(), pos.getY());
    }

    public void setTileType(Position pos, TileType type){
        Tile tile = getTile(pos);
        if (tile != null){
            tile.setType(type);
        }
    }

    public List<Position> getAdjacentPositions(Position pos){
        List<Position> result = new ArrayList<>();
        int x = pos.getX();
        int y = pos.getY();

        if (isInBounds(x, y-1)){
            result.add(new Position(x, y-1));
        }
        if (isInBounds(x, y+1)){
            result.add(new Position(x, y+1));
        }
        if (isInBounds(x-1, y)){
            result.add(new Position(x-1, y));
        }
        if (isInBounds(x+1, y)){
            result.add(new Position(x+1, y));
        }
        return result;
    }

    public List<Tile> getAdjacentTiles(Position pos){
        List<Tile> result = new ArrayList<>();
        for (Position adjPos : getAdjacentPositions(pos)){
            Tile tile = getTile(adjPos);
            if (tile != null){
                result.add(tile);
            }
        }
        return result;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }
}