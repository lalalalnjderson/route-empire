package routeempire.model;
import routeempire.model.map.Position;

public class Stop{
    private static int nextId = 1;
    private final int id;
    private final String name;
    private final Position position;
    private final CargoStorage storage;

    public Stop(Position position){
        this.id = nextId++; 
        this.name = "Stop #" + id;
        this.position = position;
        this.storage = new CargoStorage();
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public Position getPosition(){
        return position;
    }

    public CargoStorage getStorage(){
        return storage;
    }

    public int addCargo(CargoType type, int amount){
        return storage.add(type, amount);
    }

    public int removeCargo(CargoType type, int amount){
        return storage.remove(type, amount);
    }

    public int getAmount(CargoType type){
        return storage.getAmount(type);
    }

    public boolean isFull(CargoType type){
        return storage.isFull(type);
    }
}