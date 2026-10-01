package routeempire.model.bridge;

public enum BridgeType{
    STONE("Stone bridge", 500, 4, 40),
    STEEL("Steel bridge", 1500, 10, 0);

    private final String displayName;
    private final int cost;
    private final int maxSpan;
    private final int speedLimit; // 0 = no limit

    BridgeType(String name, int cost, int maxSpan, int speedLimit){
        this.displayName = name;
        this.cost = cost;
        this.maxSpan = maxSpan;
        this.speedLimit = speedLimit;
    }

    public String getDisplayName(){
        return displayName;
    }

    public int getCost(){ return cost; }
    public int getMaxSpan(){ return maxSpan; }
    public int getSpeedLimit(){ return speedLimit; }

    public boolean hasSpeedLimit(){
        return speedLimit > 0;
    }
}