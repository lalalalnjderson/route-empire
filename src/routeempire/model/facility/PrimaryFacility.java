package routeempire.model.facility;
import routeempire.model.CargoType;
import routeempire.model.Stop;
import routeempire.model.map.Position;
import java.util.ArrayList;
import java.util.List;

public abstract class PrimaryFacility extends Facility{
    private double productionAccumulator;
    protected PrimaryFacility(String name, FacilityType type, List<Postition> occupiedTiles){
        super(name, type, occupiedTiles);
        this.productionAccumulator = 0.0;
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
        for (CargoType cargo : getProducedCargo){
            double productionPerTick = (double) getProductionRate() / ticksPerMonth;
            productionAccumulator += productionPerTick;
            int wholeUnits = (int) productionAccumulator;
            if (wholeUnits > 0){
                productionAccumulator -= wholeUnits;
                distributeToStops(cargo, wholeUnits, linkedStops);
            }
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