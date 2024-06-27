package Backend.Tiles;
public abstract class Tile implements Comparable<Tile> {
    protected char tile;
    protected Position position;

    protected Tile(char tile){
        this.tile = tile;
    }

    public void initialize(Position position){
        this.position = position;
    }
    public abstract void Tick();
    public abstract void unitVisit(Unit unit);

    public char getChar() {return tile;}
    public void setChar(char tile) { this.tile = tile;}

    public Position getPosition() {
        return position;
    }
    public void setPosition(Position getPosition) {
        this.position = getPosition;
    }

    public int compareTo(Tile tile) {
        return getPosition().compareTo(tile.getPosition());
    }





}
