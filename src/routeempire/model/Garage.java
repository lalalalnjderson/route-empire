package routeempire.model;
import routeempire.model.map.Position;

public class Garage{
    private static int nextId = 1;
    private final int id;
    private final String name;
    private final Position position;

    public Garage(Position position){
        this.id = nextId++;
        this.name = "Garage #" + id;
        this.position = position;
    }
    
    public int getId(){ return id; }
    public String getName(){ return name; }
    public Position getPosition(){ return position; }
}