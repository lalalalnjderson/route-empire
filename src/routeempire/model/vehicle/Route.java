package routeempire.model.vehicle;
import java.util.ArrayList;
import java.util.List;

public class Route{
    private final List<RouteStop> stops;

    public Route(){
        this.stops = new ArrayList<>();
    }

    public void addStop(RouteStop stop){
        stops.add(stop);
    }

    public void removeStop(int index){
        if (index >= 0 && index < stops.size()){
            stops.remove(index);
        }
    }

    public RouteStop getStop(int index){
        return stops.get(index);
    }

    // next stop in circular route A -> B -> C -> A
    public int getNextIndex(int currentIndex){
        if (stops.isEmpty()){
            return -1;
        }
        return (currentIndex + 1) % stops.size();
    }

    public int getStopCount(){
        return stops.size();
    }

    public boolean isEmpty(){
        return stops.isEmpty();
    }

    public List<RouteStop> getStops(){
        return new ArrayList<>(stops);
    }
}