package Backend.Tiles;

public class Wall extends Tile {

    public Wall() {
        super('#');
    }
    @Override
    public void unitVisit(Unit unit) {
        unit.visit(this);
    }
    @Override
    public void Tick() {
    }
}
