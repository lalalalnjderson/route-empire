package routeempire.model.bridge;
import java.util.ArrayList;
import java.util.List;
import routeempire.model.map.Position;

public class Bridge{
    private final BridgeType type;
    private final Position start;
    private final Position end;
    private final List<Position> spannedTiles;

    public Bridge(BridgeType type, Position start, Position end,
                    List<Position> spannedTiles){
        this.type = type;
        this.start = start;
        this.end = end;
        this.spannedTiles = new ArrayList<>(spannedTiles);
    }

    public BridgeType getType(){
        return type;
    }

    public Position getStart(){
        return start;
    }

    public Position getEnd(){
        return end;
    }

    public List<Position> getSpannedTiles(){
        return new ArrayList<>(spannedTiles);
    }

    public int getSpeedLimit(){
        return type.getSpeedLimit();
    }

    public int getLength(){
        return spannedTiles.size();
    }
}