package routeempire.model.facility;
import java.util.ArrayList;
import java.util.List;
import routeempire.model.CargoType;
import routeempire.model.map.Position;

public class ProductionForest extends PrimaryFacility{
    private static final int PRODUCTION_RATE = 20;
    
    public ProductionForest(String name, List<Position> occupiedTiles){
        super(name, FacilityType.PRODUCTION_FOREST, occupiedTiles);
    }

    @Override
    public List<CargoType> getProducedCargo(){
        List<CargoType> produced = new ArrayList<>();
        produced.add(CargoType.WOOD);
        return produced;
    }

    @Override
    public int getProductionRate(){
        return PRODUCTION_RATE;
    }
}