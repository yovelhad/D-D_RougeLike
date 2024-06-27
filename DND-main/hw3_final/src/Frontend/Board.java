package Frontend;

import Backend.*;
import Backend.Tiles.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Board {
    public List<Tile> tiles = new ArrayList<>();
    private final TileFactory tileFactory = new TileFactory();
    private Player player;
    private final ArrayList<Enemy> enemies = new ArrayList<>();
    private final int width;
    private final int height;

    public Board(int height, int width){
        this.width = width;
        this.height = height;
    }
    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }
    public Player getPlayer() {
        return this.player;
    }
    public void buildTiles(String boardsChars, Player player)
    {
        this.player = player;
        int index = 0;
        for (char tile : boardsChars.toCharArray())
        {
            Position tilePosition = new Position(index%width, index/width);
            switch (tile) {
                case '.' -> {
                    Empty empty = new Empty();
                    empty.initialize(tilePosition);
                    tiles.add(empty);
                }
                case '#' -> {
                    Wall wall = new Wall();
                    wall.initialize(tilePosition);
                    tiles.add(wall);
                }
                case '@' -> {
                    player.initialize(tilePosition, this::gameOver, this::print);
                    tiles.add(player);
                }
                default -> {
                    Enemy enemy = tileFactory.produceEnemy(tile);
                    enemy.initialize(tilePosition, () -> remove(enemy), this::print);
                    tiles.add(enemy);
                    enemies.add(enemy);
                }
            }
            index++;
        }
    }

    private void gameOver(){
        player.setChar('X');
        printBoard();
        System.out.println("YOU LOST :(");
        System.exit(0);
    }
    public void remove(Enemy enemy) {
        enemies.remove(enemy);
        tiles.remove(enemy);
        Empty empty = new Empty();
        tiles.add(empty);
        empty.initialize(enemy.getPosition());
    }
    public void printBoard()
    {
        StringBuilder board = new StringBuilder();
        for(Tile t: tiles){
            Position pos = t.getPosition();
            board.append(t.getChar());
            if(pos.X() == width-1)
                board.append("\n");
        }
        System.out.println(board);
    }
    public Tile getTile(Position p){
        for(Tile t: tiles){
            if(t.getPosition().equals(p))
                return t;
        }
        return tiles.get(width*p.Y() + p.X());
    }
    public void tick(){
        tiles.forEach(Tile::Tick);
    }
    private void print(String message){
        System.out.println(message);
    }
    public void generateTiles() {
        tiles = tiles.stream().sorted().collect(Collectors.toList());}




}