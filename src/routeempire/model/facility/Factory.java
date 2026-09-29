package routeempire.model.facility;
import routeempire.model.CargoType;
import routeempire.model.map.Position;
import java.util.ArrayList;
import java.util.List;

public class Factory extends SecondaryFacility{
    private static final int CONVERSION_RATE = 2;
    
    public Factory(String name, List<Position> occupiedTiles){
        super(name, FacilityType.FACTORY, occupiedTiles, CONVERSION_RATE);
    }

    @Override
    public List<CargoType> getProducedCargo(){
        List<CargoType> produced = new ArrayList<>();
        produced.add(CargoType.GOODS);
        return produced;
    }

    @Override List<CargoType> getConsumedCargo(){
        List<CargoType> consumed = new ArrayList<>();
        consumed.add(CargoType.STEEL);
        consumed.add(CargoType.GRAIN);
        consumed.add(CargoType.LIVESTOCK);
        return consumed;
    }

    @Override
    public int getProductionRate(){
        return 0;
    }
}