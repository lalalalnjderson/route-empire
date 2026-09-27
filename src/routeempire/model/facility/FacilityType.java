package routeempire.model.facility;

public enum FacilityType{
    IRON_ORE_MINE("Iron ore mine"),
    STEEL_MILL("Steel mill"),
    FARM("Farm"),
    PRODUCTION_FOREST("Production forest"),
    FACTORY("Factory"),
    SAWMILL("Sawmill");

    private final String displayName;

    FacilityType(String displayName){
        this.displayName = displayName;
    }

    public String getName(){
        return displayName;
    }
}