package routeempire.model;
import java.util.HashMap;
import java.util.Map;

public class CargoStorage{
    public static final int DEFAULT_CAPACITY = 200;
    private final Map<CargoType, Integer> stored;
    // meaning that we cannot reassign it: stored = new HashMap<>();
    // but we still can change entries and values
    private final int capacityPerType;

    public CargoStorage(int capacityPerType){
        this.stored = new HashMap<>();
        this.capacityPerType = capacityPerType;
    }

    public int getAmount(CargoType type){
        Integer amount = stored.get(type);
        if (amount == null){
            return 0;
        }
        return amount;
    }

    public int add(CargoType type, int amount){
        int current = getAmount(type);
        int spaceLeft = capacityPerType - current;
        int actuallyAdded = Math.min(amount, spaceLeft);

        if (actuallyAdded > 0){
            stored.put(type, current + actuallyAdded);
        }
        return actuallyAdded;
    }

    public int remove(CargoType type, int amount){
        int current = getAmount(type);
        int actuallyRemoved = Math.min(amount, current);
        
        if (actuallyRemoved > 0){
            int remaining = current - actuallyRemoved;
            if (remaining == 0){
                stored.remove(type);
            }
            else{
                stored.put(type, remaining);
            }
        }
        return actuallyRemoved;
    }

    public boolean isFull(CargoType type){
        return getAmount(type) >= capacityPerType;
    }

    public boolean isEmpty(){
        for (int amount : stored.values()){
            if (amount > 0){
                return false;
            }
        }
        return true;
    }

    public int getCapacity(){
        return capacityPerType;
    }

    public Map<CargoType, Integer> getAllStored(){
        return new HashMap<>(stored);
    }
}