package Backend.Tiles.Units.Enemies;

import Backend.Tiles.Enemy;
import Backend.Tiles.Position;

public class Trap extends Enemy {
    private final int visibilityTime;
    private final int invisibilityTime;
    private int ticksCount;
    private boolean isVisible;

    public Trap(char tile, String name, int healthCapacity, int attack, int defense, int experienceValue, int invisibilityTime, int visibilityTime) {
        super(tile, name, healthCapacity, attack, defense, experienceValue);
        this.visibilityTime=visibilityTime;
        this.invisibilityTime=invisibilityTime;
        isVisible = true;
        ticksCount=0;
    }

    public void Tick(){
    }
    @Override
    public Position move(Position position) {
        isVisible = (ticksCount < visibilityTime);
        if (ticksCount == (visibilityTime + invisibilityTime))
            ticksCount=0;
        else
            ticksCount++;

        if(this.position.range(position) < 2){
            return new Position(-1,-1);
        }
        return getPosition();
    }

    @Override
    public String description() {
        return getName() + "  Health:" +getHealth().getCurrent() + "/" +getHealth().getMax()
                + "  Attack:" +getAttack()
                + "  Defense:" +getDefense()
                + "  Experience Value:" + getExperienceValue();
    }
    public char getChar(){
        return isVisible ? super.getChar() : '.';
    }
}
