package Backend.Tiles.Units.Players;

import Backend.Tiles.Enemy;
import Backend.Tiles.Player;
import Backend.Tiles.Units.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mage extends Player {
    private final Resource mana;
    private final int cost;
    private int spellPower;
    private final int hitsCount;
    private final int abilityRange;

    public Mage(char tile, String name, int healthCapacity, int attack, int defense,
                int manaPool, int cost, int spellPower, int hitsCount, int abilityRange) {
        super(tile, name, healthCapacity, attack, defense);
        this.mana = new Resource(manaPool, manaPool/4);
        this.cost = cost;
        this.spellPower = spellPower;
        this.hitsCount = hitsCount;
        this.abilityRange = abilityRange;
    }
    @Override
    protected void levelUp() {
        mana.addToMax(25*playerLevel);
        mana.add(mana.getMax()/4.0);
        spellPower += 10*playerLevel;
        super.levelUp();
    }
    @Override
    public void Tick() {
        mana.add(playerLevel);
    }
    @Override
    public Enemy cast(ArrayList<Enemy> Enemies) {
        return cast(Enemies,0);
    }
    @Override
    protected String getAbility() {
        return "  Ability: Blizzard!   Mana pool: " +mana.getCurrent()+"/"+mana.getMax() + "   Mana cost: " + cost
                + "   Spell power:" + spellPower + "   Hits count: " + hitsCount + "   Ability range: " + abilityRange ;
    }
    public Enemy cast(ArrayList<Enemy> Enemies, int seed) {
        if(mana.getCurrent() >= cost){
            mana.reduce(cost);
            int hits = 0;
            List<Enemy> selectedEnemies = Enemies.stream().filter(t -> (t.getPosition().range(getPosition()) < abilityRange)).toList();

            while((hits < hitsCount) & !selectedEnemies.isEmpty()){
                int index = new Random().nextInt(selectedEnemies.size());
                Enemy chosen = selectedEnemies.get(index);
                if(chosen != null)
                    castAssist(this,chosen,spellPower,"Blizzard", seed);
                selectedEnemies = Enemies.stream().filter(t -> (t.getPosition().range(getPosition()) < abilityRange)).toList();
                hits++;
            }
        }
        return null;
    }

    public String description() {
        return name() + "  Health: " +getHealth().getCurrent() + "/" +getHealth().getMax()
                + "  Attack: " +getAttackPoints()
                + "  Defense: " +getDefensePoints()
                + "  Level: " + playerLevel
                + "  Experience: " + getExperience() + getAbility();
    }
}