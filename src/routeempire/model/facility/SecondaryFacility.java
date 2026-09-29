package routeempire.model.facility;
import routeempire.model.CargoStorage;
import routeempire.model.CargoType;
import routeempire.model.Stop;
import routeempire.model.map.Position;
import java.util.List;

public abstract class SecondaryFacility extends Facility{
    private final CargoStorage inputStorage;
    private final int conversionRate;

    protected SecondaryFacility(String name, FacilityType type, List<Postition> occupiedTiles, int conversionRate){
        super(name, facilityType, occupiedTiles);
        this.inputStorage = new CargoStorage();
        this.conversionRate = conversionRate;
    }

    public CargoStorage getInputStorage(){
        return inputStorage;
    }

    public int getConversionRate(){
        return conversionRate;
    }

    public void receiveInput(CargoType type, int amount){
        if (getConsumedCargo().contains(type)){
            inputStorage.add(type, amount);
        }
    }

    @Override
    public void update(List <Stop> linkedStops, int ticksPerMonth){
        if (linkedStops.isEmpty()){
            return;
        }
        for (CargoType inputCargo : getConsumedCargo()){
            int available = inputStorage.getAmount(inputCargo);
            if (available >= conversionRate){
                int batches = available / conversionRate;
                int consumed = batches * conversionRate;
                inputStorage.remove(inputCargo, consumed);
                for (CargoType outputCargo : getProducedCargo()){
                    distributeToStops(outputCargo, batches, linkedStops);
                }
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