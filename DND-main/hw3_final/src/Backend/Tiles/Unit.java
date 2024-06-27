package Backend.Tiles;

import Backend.InputProvider;
import Backend.Tiles.Units.Resource;
import Callbacks.DeathCallBack;
import Callbacks.MessageCallBack;

import java.util.Random;

public abstract class Unit extends Tile {

    protected DeathCallBack deathCallBack;
    protected MessageCallBack messageCallBack;
    protected String name;
    protected Resource health;
    protected int attack;
    protected int defense;
    private final InputProvider inputProvider = new InputProvider();

    protected Unit(char tile, String name, int healthCapacity, int attack, int defense) {
        super(tile);
        this.name = name;
        this.health = new Resource(healthCapacity, healthCapacity);
        this.attack = attack;
        this.defense = defense;
    }
    public void initialize(Position position, DeathCallBack deathCallBack, MessageCallBack messageCallback){
        this.deathCallBack = deathCallBack;
        this.messageCallBack = messageCallback;
        super.initialize(position);
    }
    public Position moveTo(char step){
        return inputProvider.producePosition(getPosition(), step);
    }
    public abstract void dies();
    protected void battle(Unit unit){
        battle(unit,0);
    }
    protected void battle(Unit unit, int seed)
    {
        Random randomGen;
        if(seed != 0){
            randomGen = new Random(seed);
        }
        else randomGen = new Random();
        messageCallBack.send("\n" + this.getName() + " engaged in combat with " + unit.getName());
        messageCallBack.send(description());
        messageCallBack.send(unit.description());
        int randAttack = randomGen.nextInt( attack + 1);
        int randDefence = randomGen.nextInt(unit.getDefense() + 1);
        messageCallBack.send(this.getName() + " rolled " + randAttack + " attack points ");
        messageCallBack.send(unit.getName() + " rolled " + randDefence + " defense points ");
        int damage = Math.max((randAttack-randDefence),0);
        messageCallBack.send(this.getName() + " took "  +damage+ " health points from " + unit.getName() + "\n");
        unit.health.reduce(damage);
    }
    protected void castAssist(Player player, Enemy enemy, int damage, String abilityName, int seed){
        Random randomGen;
        if(seed != 0){
            randomGen = new Random(seed);
        }
        else randomGen = new Random();
        messageCallBack.send(String.format("\n%s used the %s on %s", getName(), abilityName, enemy.getName()));
        messageCallBack.send(enemy.description());
        int randBlocked = randomGen.nextInt(enemy.getDefense() + 1);
        messageCallBack.send(String.format("%s's damage is %s attack points", abilityName, damage));
        messageCallBack.send(String.format("%s rolled %d defense points", enemy.getName(), randBlocked));
        int finalDamage = Math.max((damage-randBlocked ),0); // not negative
        messageCallBack.send(String.format("%s took %d health points from %s\n", getName(), finalDamage, enemy.getName()));
        enemy.health.reduce(finalDamage);
        if(enemy.isDead())
            player.onAbilityKill(enemy);
    }

    // Visitor pattern
    public void visit(Empty empty){
        swap(empty);
    }
    public abstract void visit(Player player);
    public abstract void visit(Enemy enemy);
    public void visit(Wall wall){ }

    protected void swap(Tile tile){
        Position pos = tile.getPosition();
        tile.setPosition(getPosition());
        setPosition(pos);
    }
    public boolean isDead() { return health.getCurrent() <= 0; }

    public int getDefense() {
        return defense;
    }

    public int getAttack() {
        return attack;
    }

    public Resource getHealth(){
        return health;
    }
    public String getName() {
        return name;
    }

    public abstract String description();
    public String describe() {
        return String.format("%s\t\tHealth: %s\t\tAttack: %d\t\tDefense: %d", getName(), getHealth().getCurrent(), getAttack(), getDefense());
    }

}

