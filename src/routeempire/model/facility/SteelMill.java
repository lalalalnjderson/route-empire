package routeempire.model.facility;
import java.util.ArrayList;
import java.util.List;
import routeempire.model.CargoType;
import routeempire.model.map.Position;

public class SteelMill extends SecondaryFacility{
    private static final int CONVERSION_RATE = 2;
    
    public SteelMill(String name, List<Position> occupiedTiles){
        super(name, FacilityType.STEEL_MILL, occupiedTiles, CONVERSION_RATE);
    }

    @Override
    public List<CargoType> getProducedCargo(){
        List<CargoType> produced = new ArrayList<>();
        produced.add(CargoType.STEEL);
        return produced;
    }

    @Override 
    public List<CargoType> getConsumedCargo(){
        List<CargoType> consumed = new ArrayList<>();
        consumed.add(CargoType.IRON_ORE);
        return consumed;
    }

    @Override
    public int getProductionRate(){
        return 0;
    }
}