package Backend.Tiles;

import Backend.Tiles.Units.Resource;

import java.util.ArrayList;

public abstract class Player extends Unit {
    protected static final int REQ_EXPERIENCE = 50;
    protected int experience;
    protected int playerLevel;

    protected Player(char tile, String name, int healthCapacity, int attack, int defense) {
        super(tile, name, healthCapacity, attack, defense);
        experience = 0;
        playerLevel = 1;
    }

    @Override
    public void visit(Enemy enemy) {
        battle(enemy);
        if(enemy.isDead())
            onKill(enemy);
    }
    @Override
    public void unitVisit(Unit unit) { unit.visit(this); }

    protected void levelUp(){
        experience = experience - 50*playerLevel;
        playerLevel+=1;
        health = new Resource(health.getMax() +10*playerLevel,health.getMax()+10*playerLevel);
        attack += 4*playerLevel;
        defense += playerLevel;
        messageCallBack = System.out::println;
        messageCallBack.send(name() +" reached level "  +getPlayerLevel() +": +" +10*playerLevel+ " Health Points, +"+(4 * playerLevel) + " Attack Points, +"+playerLevel +" Defence Points\n" );
    }

    public String Description(){
        return getName() + "\t  Health: " +getHealth().getCurrent() + "/" +getHealth().getMax()
                + "\t  Attack: " +getAttack()
                + "\t  Defense: " +getDefense()
                + "\t  Level: " +playerLevel
                + "\n  Experience: " + getExperience() + "\t" + getAbility();
    }

    public void onKill(Enemy enemy){
        swap(enemy);
        onAbilityKill(enemy);
    }

    public void onAbilityKill(Enemy enemy){
        messageCallBack.send(enemy.getName() + " died. " + name() + " gained " + enemy.getExperienceValue() + " experience points" +"\n");
        experience+=enemy.getExperienceValue();
        while(experience >= playerLevel*50 ) {
            levelUp();
        }
        enemy.dies();
    }
    protected String getAttackPoints() {
        return attack +"";
    }
    protected String getDefensePoints() {
        return defense +"";
    }
    protected String getExperience() {
        return this.experience + "/"+this.playerLevel*50;
    }
    protected abstract String getAbility();
    public int getPlayerLevel(){
        return playerLevel;
    }
    public String describe() {
        return String.format("%s\t\tLevel: %d\t\tExperience: %d/%d", super.describe(), playerLevel, experience, forLevelUp());
    }
    protected int forLevelUp(){
        return playerLevel*REQ_EXPERIENCE;
    }
    protected String name(){
        return name;
    }
    public abstract Enemy cast(ArrayList<Enemy> Enemies);
    public void visit(Player player){}
    public void dies() {
        deathCallBack.call();
    }
}
