package Backend.Tiles;

public class Position {
    protected int x;
    protected int y;
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int X(){
        return x;
    }
    public int Y(){
        return y;
    }
    public double range(Position position){return Math.sqrt(Math.pow((X() - position.X()),2) + Math.pow((Y() - position.Y()),2));}
    public boolean equals(Position other) {
        return y == other.Y() && x == other.X();
    }
    public int compareTo(Position other) {
        if (Y() != other.Y())
            return Y() - other.Y();
        return X() - other.X();
    }
    public String toString(){
        return "("+X()+","+ Y()+")";
    }
}
