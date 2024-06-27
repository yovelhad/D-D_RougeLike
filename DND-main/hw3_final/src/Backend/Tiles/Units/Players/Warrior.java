package Backend.Tiles.Units.Players;

import Backend.Tiles.Enemy;
import Backend.Tiles.Player;
import Backend.Tiles.Units.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Warrior extends Player {
    private final Resource cooldown;

    public Warrior(char tile, String name, int healthCapacity, int attack, int defense, int abilityCooldown) {
        super(tile, name, healthCapacity, attack, defense);
        this.cooldown = new Resource(abilityCooldown, 0);
    }
    protected void levelUp(){
        cooldown.setCurrent(0);
        health.addToMax(5*playerLevel);
        attack += 2*playerLevel;
        defense += playerLevel;
        super.levelUp();
    }
    @Override
    public void Tick(){
        cooldown.reduce(1);
    }
    @Override
    public Enemy cast(ArrayList<Enemy> enemies) {
        return cast(enemies,0);
    }
    public Enemy cast(ArrayList<Enemy> enemies, int seed) {

        if(cooldown.getCurrent() == 0){
            cooldown.setCurrent(cooldown.getMax());
            health.add(getDefense() * 10);
            List<Enemy> enemiesInRange = enemies.stream().filter(t -> t.getPosition().range(getPosition()) < 3).toList();
            if (enemiesInRange.size() == 0) return null;
            int index = new Random().nextInt(enemiesInRange.size());
            Enemy theChosenOne = enemiesInRange.get(index);
            int damage = health.getMax() / 10;
            castAssist(this, theChosenOne, damage, "Avenger's Shield",0);
            if (theChosenOne.isDead()){
                onAbilityKill(theChosenOne);
            }
        }
        return null;

    }
    @Override
    protected String getAbility() {
        return " Avengers Shield! Cooldown: "+cooldown.getCurrent()+"/"+cooldown.getMax();
    }
    @Override
    public String description() {
        return name() + "  Health: " +getHealth().getCurrent() + "/" +getHealth().getMax()
                + "  Attack: " +getAttackPoints()
                + "  Defense: " +getDefensePoints()
                + "  Level: " + playerLevel
                + "  Experience: " + getExperience() + getAbility();
    }
}
