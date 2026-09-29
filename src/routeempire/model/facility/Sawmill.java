package routeempire.model.facility;
import routeempire.model.CargoType;
import routeempire.model.map.Position;
import java.util.ArrayList;
import java.util.List;

public class Sawmill extends SecondaryFacility{
    private static final int CONVERSION_RATE = 2;
    
    public Sawmill(String name, List<Position> occupiedTiles){
        super(name, FacilityType.SAWMILL, occupiedTiles, CONVERSION_RATE);
    }

    @Override
    public List<CargoType> getProducedCargo(){
        List<CargoType> produced = new ArrayList<>();
        produced.add(CargoType.GOODS);
        return produced;
    }

    @Override List<CargoType> getConsumedCargo(){
        List<CargoType> consumed = new ArrayList<>();
        consumed.add(CargoType.WOOD);
        return consumed;
    }

    @Override
    public int getProductionRate(){
        return 0;
    }
}