package routeempire.model.facility;
import routeempire.model.CargoType;
import routeempire.model.map.Position;
import java.util.ArrayList;
import java.util.List;

public class IronOreMine extends PrimaryFacility{
    private static final int PRODUCTION_RATE = 20;

    public IronOreMine(String name, List<Position> occupiedTiles){
        super(name, FacilityType.IRON_ORE_MINE, occupiedTiles);
    }

    @Override
    public List<CargoType> getProducedCargo(){
        List<CargoType> produced = new ArrayList<>();
        produced.add(CargoType.IRON_ORE);
        return produced;
    }

    @Override
    public int getProductionRate(){
        return PRODUCTION_RATE;
    }
}