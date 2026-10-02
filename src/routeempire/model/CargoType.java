package routeempire.model;

public enum CargoType{
    IRON_ORE("Iron ore", 3),
    STEEL("Steel", 6),
    GRAIN("Grain", 3),
    LIVESTOCK("Livestock", 4),
    WOOD("Wood", 3),
    GOODS("Goods", 8),
    PASSENGERS("Passengers", 2);

    private final String displayName;
    private final int baseRate;

    CargoType(String displayName, int baseRate){
        this.displayName = displayName;
        this.baseRate = baseRate;
    }

    public String getDisplayName(){
        return displayName;
    }

    public int getBaseRate(){
        return baseRate;
    }
}