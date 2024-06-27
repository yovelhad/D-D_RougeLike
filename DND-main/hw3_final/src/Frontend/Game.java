package Frontend;
import Backend.Tiles.Enemy;
import Backend.Tiles.Player;
import Backend.Tiles.Position;
import Backend.Tiles.Tile;
import Callbacks.MessageCallBack;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private final Board board;
    private final Player player;
    private final ArrayList<Enemy> enemies;
    protected MessageCallBack messageCallBack;
    public Game(Board b)
    {
        this.board = b;
        this.player = b.getPlayer();
        this.enemies = b.getEnemies();
        this.messageCallBack = System.out::println;
    }
    public void init() {
        while (!enemies.isEmpty()) {
            board.generateTiles();
            board.printBoard();
            messageCallBack.send(player.Description());
            char playerMove = getInput();
            Position playerPosition = null;
            while(playerPosition == null){
                playerPosition = player.moveTo(playerMove);
            }
            if (playerPosition.X() == -1) {
                Enemy enemyToRemove = player.cast(enemies);
                if(enemyToRemove!=null) {
                    enemies.remove(enemyToRemove);
                }
            }
            else {
                Tile tile = board.getTile(playerPosition);
                tile.unitVisit(player);
            }
            for (Enemy enemy : enemies) {
                Position enemyPos = enemy.move(player.getPosition());
                if (enemyPos.X() == -1) {
                    enemy.visit(player);
                }
                else{
                    Tile tile = board.getTile(enemyPos);
                    tile.unitVisit(enemy);

                }
            }
            board.tick();
        }
    }

    private char getInput() {
        Scanner scanner = new Scanner(System.in);
        char[] validChars = new char[] {'q', 'w', 'e', 'a', 's', 'd'};
        Character input = null;

        while (input == null) {
            try {
                String received = scanner.nextLine();
                input = received.trim().charAt(0);
                for (char c : validChars) {
                    if (input == c) {
                        return input;
                    }
                }
                input = null;
                System.out.println("wrong input, please choose a valid key");
            } catch (StringIndexOutOfBoundsException e) { input = null;}
        }
        return input;
    }
}