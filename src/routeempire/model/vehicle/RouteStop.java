package routeempire.model.vehicle;
import routeempire.model.Stop;

public class RouteStop{
    private Stop stop;
    private boolean loadCargo;
    private boolean unloadCargo;

    public RouteStop(Stop stop, boolean loadCargo, boolean unloadCargo){
        this.stop = stop;
        this.loadCargo = loadCargo;
        this.unloadCargo = unloadCargo;
    }

    public Stop getStop(){ return stop; }
    public void setStop(Stop stop){
        this.stop = stop;
    }

    public boolean shouldLoad(){ return loadCargo; }
    public void setLoadCargo(boolean loadCargo){
        this.loadCargo = loadCargo;
    }

    public boolean shouldUnload(){ return unloadCargo; }
    public void setUnloadCargo(boolean unloadCargo){
        this.unloadCargo = unloadCargo;
    }
}