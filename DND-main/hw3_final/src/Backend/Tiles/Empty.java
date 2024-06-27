package Backend.Tiles;

public class Empty extends Tile {

    public Empty() {
        super('.');
    }
    @Override
    public void unitVisit(Unit unit) {
        unit.visit(this);
    }
    @Override
    public void Tick() {
    }

}
