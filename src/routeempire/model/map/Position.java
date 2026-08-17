package routeempire.model.map;

public class Position{
    private final int x;
    private final int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public double distanceTo(Position other){
        int dx = this.x - other.x;
        int dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public int manhattanDistance(Position other){
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position other = (Position) o;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode(){
        return 31 * x + y;
    }

    @Override
    public String toString(){
        return "(" + x + ", " + y + ")";
    }
}