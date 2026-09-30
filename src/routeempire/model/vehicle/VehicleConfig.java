package routeempire.model.vehicle;
import routeempire.model.CargoType;

public class VehicleConfig{
    private final String name;
    private final CargoType cargoType;
    private final int speed;
    private final int capacity;
    private final int purchaseCost;
    private final int yearlyRunningCost;
    private final int maintenanceCost;

    public VehicleConfig(String name, CargoType cargoType, int speed,
                        int capacity, int purchaseCost, 
                        int yearlyRunningCost, int maintenanceCost){
        this.name = name;
        this.cargoType = cargoType;
        this.speed = speed;
        this.capacity = capacity;
        this.purchaseCost = purchaseCost;
        this.yearlyRunningCost = yearlyRunningCost;
        this.maintenanceCost = maintenanceCost;
    }

    public String getName(){ return name; }
    public CargoType getCargoType(){ return cargoType; }
    public int getSpeed(){ return speed; }
    public int getCapacity(){ return capacity; }
    public int getPurchaseCost(){ return purchaseCost; }
    public int getYearlyRunningCost(){ return yearlyRunningCost; }
    public int getMaintenanceCost(){ return maintenanceCost; }
}