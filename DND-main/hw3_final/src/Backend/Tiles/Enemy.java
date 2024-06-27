package Backend.Tiles;
public abstract class Enemy extends Unit {
    private final int experienceValue;

    protected Enemy(char tile, String name, int healthCapacity, int attack, int defense, int experienceValue) {
        super(tile, name, healthCapacity, attack, defense);
        this.experienceValue=experienceValue;
    }
    @Override
    public void visit(Player player) {
        battle(player);
        if (player.isDead())
            player.dies();
    }
    public void dies() {
        deathCallBack.call();
    }
    @Override
    public void visit(Enemy enemy) {
    }
    public int getExperienceValue(){
        return experienceValue;
    }
    public void unitVisit(Unit unit) {
        unit.visit(this);
    }
    @Override
    public abstract String description();
    public abstract Position move(Position position);


}
