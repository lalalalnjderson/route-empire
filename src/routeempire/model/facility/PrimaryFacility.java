package routeempire.model.facility;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import routeempire.model.CargoType;
import routeempire.model.Stop;
import routeempire.model.map.Position;

public abstract class PrimaryFacility extends Facility{
    private final Map<CargoType, Double> productionAccumulators;
    protected PrimaryFacility(String name, FacilityType type, List<Position> occupiedTiles){
        super(name, type, occupiedTiles);
        this.productionAccumulators = new HashMap<>();
    }

    @Override
    public List<CargoType> getConsumedCargo(){
        return new ArrayList<>();
    }

    @Override
    public void update(List<Stop> linkedStops, int ticksPerMonth){
        if (linkedStops.isEmpty()){
            return;
        }
        for (CargoType cargo : getProducedCargo()) {
            double acc = productionAccumulators.getOrDefault(cargo, 0.0);
            double productionPerTick = (double) getProductionRate() / ticksPerMonth;
            acc += productionPerTick;
            int wholeUnits = (int) acc;
            if (wholeUnits > 0) {
                acc -= wholeUnits;
                distributeToStops(cargo, wholeUnits, linkedStops);
            }
            productionAccumulators.put(cargo, acc);
        }
    }

    protected void distributeToStops(CargoType cargo, int amount, List<Stop> stops){
        int perStop = amount / stops.size();
        int remainder = amount % stops.size();

        for (int i=0; i<stops.size(); i++){
            int toAdd = perStop;
            if (i < remainder){
                toAdd += 1;
            }
            stops.get(i).addCargo(cargo, toAdd);
        }
    }
}