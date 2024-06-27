package Tests;

import Backend.*;
import Backend.Tiles.Empty;
import Backend.Tiles.Position;
import Backend.Tiles.Tile;
import Backend.Tiles.Wall;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TileTest {
    private Empty empty;
    private Tile wall;
    private Tile enemy;
    private Tile player;
    private char randomEnemy;
    private String randomPlayer;

    @BeforeEach // This function initializes all the player and enemies and their positions for the tests.
    public void init(){
        TileFactory factory = new TileFactory();
        empty = new Empty();
        wall = new Wall();
        char[] enemies = {'s','k','q','z','b','g','w','M','C','K','B','Q','D'};
        randomEnemy = enemies[new Random().nextInt(enemies.length)];
        enemy = factory.produceEnemy(randomEnemy);
        String[] players = {"1","2","3","4","5","6"};
        randomPlayer = players[new Random().nextInt(players.length)];
        player = factory.producePlayer(randomPlayer);

        player.setPosition(new Position(1,2));
        enemy.setPosition(new Position(3,4));
        wall.setPosition(new Position(5,6));
        empty.setPosition(new Position(12,24));

    }

    @Test
    void getTileChar() { // This test checks if the given char is equal to its player/enemy.
        assertEquals('@', player.getChar());
        assertEquals(randomEnemy, enemy.getChar());
        assertEquals('#', wall.getChar());
        assertEquals('.', empty.getChar());
    }

    @Test
    void getPosition() { // This test checks all the units positions
        Assertions.assertTrue(new Position(1,2).equals(player.getPosition()));
        Assertions.assertTrue(new Position(3,4).equals(enemy.getPosition()));
        Assertions.assertTrue(new Position(5,6).equals(wall.getPosition()));
        Assertions.assertTrue(new Position(12,24).equals(empty.getPosition()));
    }

    @Test
    void compareTo(){ // This test checks if the position comparing function works as needed.
        assertEquals(4,wall.compareTo(player));
        assertEquals(-2,player.compareTo(enemy));
        assertEquals(22,empty.compareTo(player));
    }
}