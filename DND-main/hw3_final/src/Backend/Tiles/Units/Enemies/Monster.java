package Backend.Tiles.Units.Enemies;
import Backend.Tiles.Enemy;
import Backend.Tiles.Position;

import java.util.Random;

public class Monster extends Enemy {
    private final int visionRange;
    public Monster(char tile, String name, int healthCapacity, int attack, int defense,int experienceValue,int visionRange){
        super(tile,name,healthCapacity,attack,defense,experienceValue);
        this.visionRange = visionRange;

    }
    @Override
    public void Tick() {}
    @Override
    public Position move(Position position) {
        double range = position.range(position);
        return ((range < visionRange) ? moveToPlayer(position) : randomMove());
    }
    private Position moveToPlayer(Position playerPos) {
        int xDiff = position.X() - playerPos.X();
        int yDiff = position.Y() - playerPos.Y();
        char whereTo = ((Math.abs(xDiff) > Math.abs(yDiff)) ? ((xDiff > 0) ? 'a' : 'd') : ((yDiff > 0) ? 'w' : 's'));
        return moveTo(whereTo);
    }

    private Position randomMove() {
        char[] moves = {'w', 'a', 's', 'd'};
        int r = new Random().nextInt(moves.length);
        return moveTo(moves[r]);
    }

    public String description() {
        return getName() + "  Health: " +getHealth().getCurrent() + "/" +getHealth().getMax()
                + "  Attack: " + getAttackPoints()
                + "  Defense: " + getDefensePoints()
                + "  Experience Value: " + getExperience()
                + "  Vision Range: " + visionRange;
    }
    private String getExperience() {
        return ""+getExperienceValue();
    }
    private String getDefensePoints() {
        return defense +"";
    }
    private String getAttackPoints() {
        return attack +"";
    }

}