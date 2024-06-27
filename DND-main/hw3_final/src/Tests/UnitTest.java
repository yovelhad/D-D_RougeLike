package Tests;

import Backend.*;
import Backend.Tiles.Position;
import Backend.Tiles.Unit;
import Backend.Tiles.Units.Enemies.Monster;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class UnitTest {
    private Unit player;
    private Unit monster;
    private Unit trap;
    private final TileFactory factory = new TileFactory();

    @BeforeEach
    public void init(){ // This function initializes all the player and enemies and their positions for the tests.
        String[] players = {"1","2","3","4","5","6"};
        String randomPlayer = players[new Random().nextInt(players.length)];
        player = factory.producePlayer(randomPlayer);
        player.setPosition(new Position(1,2));

        char[] enemies = {'s','k','q','z','b','g','w','M','C','K'};
        char randomEnemy = enemies[new Random().nextInt(enemies.length)];
        monster = factory.produceEnemy(randomEnemy);
        monster.setPosition(new Position(2,3));

        char[] traps = {'B','Q','D'};
        char randomTrap = traps[new Random().nextInt(traps.length)];
        trap = factory.produceEnemy(randomTrap);
        trap.setPosition(new Position(3,3));

    }

    @Test
    void moveTo() {
        // This test checks if the movement system works as needed.
        Assertions.assertTrue(new Position(1,1).equals(player.moveTo('w')));
        Assertions.assertTrue(new Position(2,4).equals(monster.moveTo('s')));
        Assertions.assertTrue(new Position(3,3).equals(monster.moveTo('d')));
        Assertions.assertTrue(new Position(0,2).equals(player.moveTo('a')));
        Assertions.assertTrue(new Position(-1,-1).equals(player.moveTo('e')));
        Assertions.assertTrue(new Position(3,3).equals(trap.moveTo('q')));
    }

    @Test
    void isDead() {
        assertFalse(monster.isDead());
        Unit deadMonster = new Monster('P',"DeadMonster",0,999,999,0,3);
        Assertions.assertTrue(deadMonster.isDead());
        assertEquals(player.isDead(),trap.isDead());
    }

    @Test
    void getAttack() {
        Unit currentTrap = trap;
        int attackTrap;
        char trapChar = currentTrap.getChar();
        if(trapChar == 'B')
            attackTrap = 1;
        else if (trapChar == 'Q')
            attackTrap = 50;
        else
            attackTrap = 100;
        assertEquals(attackTrap, currentTrap.getAttack());
        Assertions.assertTrue(player.getAttack() >=5 && player.getAttack() <= 40);
    }

    @Test
    void description() {
        Unit newMonster = factory.produceEnemy(monster.getChar());
        assertEquals(newMonster.description(), monster.description());
    }
}