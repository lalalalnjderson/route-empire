package routeempire.model.vehicle;
import routeempire.model.CargoType;
import routeempire.model.Garage;
import routeempire.model.map.Position;
import java.util.ArrayList;
import java.util.List;

public class Vehicle{
    private static int nextId = 1;
    private final int id;
    private final String name;

    private final VehicleConfig config;
    private VehicleState state;
    private Position currentTile;
    private Position nextTile;
    private double movementProgress;
    private List<Position> currentPath;
    private int currentPathIndex;
    private Route route;
    private int currentRouteIndex;

    private int cargoAmount;
    private Position cargoOriginStop; // where was cargo loaded -> for distance calc
    private Garage assignedGarage;
    private int ageInMonths;
    private int distanceSinceLastMaintenance;

    public Vehicle(VehicleConfig config, Garage assignedGarage){
        this.id = nextId++;
        this.config = config;
        this.name = config.getName() + " #" + id;
        this.state = VehicleState.IN_GARAGE;
        this.currentTile = assignedGarage.getPosition();
        this.nextTile = null;
        this.movementProgress = 0.0;
        this.currentPath = new ArrayList<>();
        this.currentPathIndex = 0;
        this.cargoAmount = 0;
        this.cargoOriginStop = null;
        this.route = null;
        this.currentRouteIndex = 0;
        this.assignedGarage = assignedGarage;
        this.ageInMonths = 0;
        this.distanceSinceLastMaintenance = 0;
    }

    public int getId(){ return id; }
    public String getName() {return name; }
    public VehicleConfig getConfig(){ return config; }
    public CargoType getCargoType(){ return config.getCargoType(); }
    public VehicleState getState() { return state; }
    public Position getCurrentTile() { return currentTile; }
    public Position getNextTile() { return nextTile; }
    public double getMovementProgress() { return movementProgress; }
    public int getCargoAmount() { return cargoAmount; }
    public Position getCargoOriginStop() { return cargoOriginStop; }
    public Route getRoute() { return route; }
    public int getCurrentRouteIndex() { return currentRouteIndex; }
    public Garage getAssignedGarage() { return assignedGarage; }
    public int getAgeInMonths() { return ageInMonths; }
    public int getDistanceSinceLastMaintenance() { return distanceSinceLastMaintenance; }
    public List<Position> getCurrentPath() { return currentPath; }
    public int getCurrentPathIndex() { return currentPathIndex; }

    public void setState(VehicleState state){ this.state = state; }
    public void setCurrentTile(Position pos) { this.currentTile = pos; }
    public void setNextTile(Position pos) { this.nextTile = pos; }
    public void setMovementProgress(double progress) { this.movementProgress = progress; }
    public void setRoute(Route route) { this.route = route; }
    public void setCurrentRouteIndex(int index) { this.currentRouteIndex = index; }

    public void setPath(List<Position> path){
        this.currentPath = new ArrayList<>(path);
        this.currentPathIndex = 0;
    }

    public void advancePathIndex(){
        this.currentPathIndex++;
    }

    public void advanceRouteIndex(){
        if (route != null){
            this.currentRouteIndex = route.getNextIndex(currentRouteIndex);
        }
    }

    public void loadCargo(int amount, Position originStop){
        this.cargoAmount = Math.min(amount, config.getCapacity());
        this.cargoOriginStop = originStop;
    }

    // important: but what if there is not enough space on the stop to accept all cargo?
    public int unloadCargo(){
        int unloaded = cargoAmount;
        cargoAmount = 0;
        return unloaded;
    }

    public void incrementDistance(){
        this.distanceSinceLastMaintenance++;
    }

    public void resetMaintenance(){
        this.distanceSinceLastMaintenance = 0;
    }

    public void incrementAge(){
        this.ageInMonths++;
    }

    public int getMaintenanceThreshold(){
        int base = 500;
        int minimum = 150;
        int yearsOld = ageInMonths / 12;
        int reduction = yearsOld * 35;
        return Math.max(minimum, base - reduction);
    }

    public boolean needsMaintenance(){
        return distanceSinceLastMaintenance >= getMaintenanceThreshold();
    }

    // returns [x, y] as doubles for sub-tile positioning and smooth rendering
    public double[] getRenderPosition(){
        if (nextTile == null || state != VehicleState.TRAVELING){
            return new double[]{currentTile.getX(), currentTile.getY()};
        }
        double x = currentTile.getX() + (nextTile.getX() - currentTile.getX()) * movementProgress;
        double y = currentTile.getY() + (nextTile.getY() - currentTile.getY()) * movementProgress;
        return new double[]{x, y};
    }
}