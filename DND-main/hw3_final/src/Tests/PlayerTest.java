package Tests;

import Backend.*;
import Backend.Tiles.Enemy;
import Backend.Tiles.Player;
import Backend.Tiles.Position;
import Backend.Tiles.Units.Enemies.Monster;
import Backend.Tiles.Units.Enemies.Trap;
import Backend.Tiles.Units.Players.Mage;
import Backend.Tiles.Units.Players.Rogue;
import Backend.Tiles.Units.Players.Warrior;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Mage mage;
    private Rogue rouge;
    private Warrior warrior;
    private Monster monster;
    private Trap trap;
    private final TileFactory factory = new TileFactory();


    @BeforeEach
    public void init() { // This function initializes all the player and enemies and their positions for the tests.
        String[] warriors = {"1","2"};
        String[] mages = {"3","4"};
        String[] rogues = {"5","6"};
        warrior = (Warrior) factory.producePlayer(warriors[new Random().nextInt(2)]);
        mage = (Mage) factory.producePlayer(mages[new Random().nextInt(2)]);
        rouge = (Rogue) factory.producePlayer(rogues[new Random().nextInt(2)]);


        char[] enemies = {'s','k','q','z','b','g','w','M','C','K'};
        char rndMonster = enemies[new Random().nextInt(enemies.length)];
        monster = (Monster) factory.produceEnemy(rndMonster);

        char[] traps = {'B','Q','D'};
        char rndTrap = traps[new Random().nextInt(traps.length)];
        trap = (Trap) factory.produceEnemy(rndTrap);


        warrior.initialize(new Position(2,2), this::nothing,this::nothing);
        mage.initialize(new Position(2,2), this::nothing,this::nothing);
        rouge.initialize(new Position(2,2), this::nothing,this::nothing);
        monster.initialize(new Position(2,1), this::nothing,this::nothing);
        trap.initialize(new Position(1,2), this::nothing,this::nothing);
    }
    @Test // This test checks the cast ability of each player on random enemies.
    void cast() {
        Player[] players = {mage,warrior,rouge};
        for(Player p: players) {

            char[] letters = {'s', 'k', 'q', 'z', 'b', 'g', 'w', 'M', 'C', 'K'};
            char rndMonster = letters[new Random().nextInt(letters.length)];
            Monster farMonster = (Monster) factory.produceEnemy(rndMonster);
            farMonster.initialize(new Position(13, 5), this::nothing, this::nothing);

            ArrayList<Enemy> enemies = new ArrayList<>();
            enemies.add(farMonster);
            enemies.add(monster);
            int monsterBefore = monster.getHealth().getCurrent();
            int farMonsterBefore = farMonster.getHealth().getCurrent();

            p.cast(enemies);
            assertTrue(monster.getHealth().getCurrent() <= monsterBefore);
            assertEquals(farMonster.getHealth().getCurrent(), farMonsterBefore);
        }
    }
    @Test // This test checks the scenario of level up for each type of player.
    void levelUp() {
        Player[] players = {mage,warrior,rouge};
        for(Player p: players) {
            int attackBefore = p.getAttack();
            Enemy monster1 = new Monster('H', "EXP", 1, 0, 0, 96, 100);
            monster1.initialize(new Position(7, 8), this::nothing, this::nothing);
            while(!monster1.isDead())
                p.visit(monster1);
            assertTrue(attackBefore < p.getAttack());
            assertEquals(2, p.getPlayerLevel());
        }
    }
    @Test // This test checks if the player takes the position of the killed enemy.
    void onKill() {
        Player[] players = {mage,warrior,rouge};
        for(Player p: players) {
            trap.initialize(new Position(1,2), this::nothing,this::nothing);
            p.onKill(trap);
            assertTrue(new Position(1,2).equals(p.getPosition()));
        }
    }

    private void nothing(String s) {}
    private void nothing(){}
}