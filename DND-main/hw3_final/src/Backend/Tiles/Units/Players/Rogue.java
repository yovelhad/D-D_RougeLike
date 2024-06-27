package Backend.Tiles.Units.Players;

import Backend.Tiles.Enemy;
import Backend.Tiles.Player;
import Backend.Tiles.Units.Resource;

import java.util.ArrayList;
import java.util.List;

public class Rogue extends Player {
    private final int cost;
    private final Resource energy;

    public Rogue(char tile, String name, int healthCapacity, int attack, int defense,
                 int cost) {
        super(tile, name, healthCapacity, attack, defense);
        this.cost = cost;
        this.energy = new Resource(100, 100);
    }
    @Override
    protected void levelUp() {
        energy.setCurrent(100);
        attack += 3*playerLevel;
        super.levelUp();
    }
    @Override
    public void Tick() {
        energy.add(10);
    }
    @Override
    public Enemy cast(ArrayList<Enemy> enemies) {
        return cast(enemies,0);
    }
    public Enemy cast(ArrayList<Enemy> enemies, int seed) {

        if(energy.getCurrent() >= cost){
            energy.reduce(cost);
            List<Enemy> enemiesInRange = enemies.stream().filter(t -> t.getPosition().range(getPosition()) < 2).toList();
            for(Enemy theChosenOne: enemiesInRange){
                castAssist(this, theChosenOne, attack,"Fan of Knives", seed);
            }
        }
        return null;
    }
    public String getAbility() {
        return "  Ability: Fan of Knives!  Energy: " + energy.getCurrent()+"/"+energy.getMax() +"   Ability cost: " +cost ;
    }
    public String description() {
        return name() + "  Health: " +getHealth().getCurrent() + "/" +getHealth().getMax()
                + "  Attack: " +getAttackPoints()
                + "  Defense: " +getDefensePoints()
                + "  Level: " +playerLevel
                + "  Experience: " + getExperience() + getAbility();
    }
}