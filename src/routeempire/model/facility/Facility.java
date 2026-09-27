package routeempire.model.facility;
import routeempire.model.CargoType;
import routeempire.model.Stop;
import routeempire.model.map.Position;
import java.util.ArrayList;
import java.util.List;

public abstract class Facility{
    private final String name;
    private final FacilityType type;
    private final List<Position> occupiedTiles;

    protected Facility(String name, FacilityType type, List<Postition> occupiedTiles){
        this.name = name;
        this.type = type;
        this.occupiedTiles = new ArrayList<>(occupiedTiles);
    }

    public String getName(){
        return name;
    }

    public FacilityType getType(){
        return type;
    }

    public List<Position> getOccupiedTiles(){
        return new ArrayList<>(occupiedTiles);
    }

    public abstract List<CargoType> getProducedCargo();
    public abstract List<CargoType> getConsumedCargo();
    public abstract int getProductionRate();
    public abstract void update(List<Stop> linkedStops, int ticksPerMonth);
}