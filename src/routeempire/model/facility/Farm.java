package routeempire.model.facility;
import java.util.ArrayList;
import java.util.List;
import routeempire.model.CargoType;
import routeempire.model.map.Position;

public class Farm extends PrimaryFacility{
    private static final int PRODUCTION_RATE = 15;
    public Farm(String name, List<Position> occupiedTiles){
        super(name, FacilityType.FARM, occupiedTiles);
    }

    @Override
    public List<CargoType> getProducedCargo(){
        List<CargoType> produced = new ArrayList<>();
        produced.add(CargoType.GRAIN);
        produced.add(CargoType.LIVESTOCK);
        return produced;
    }

    @Override
    public int getProductionRate(){
        return PRODUCTION_RATE;
    }
}