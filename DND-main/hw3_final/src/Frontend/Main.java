package Frontend;
import Backend.*;
import Backend.Tiles.Player;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.*;


public class Main {

    public static void main(String[] args) {
        startMenu();
        String number = choosePlayer();

        TileFactory tileFactory = new TileFactory();
        Player player = tileFactory.producePlayer(number);

        int level = 1;
        while (true)
        {
            Object [] levelFile = readLevelFromTxt(level, args);
            if (levelFile == null || levelFile[0].equals("gameOver")) {
                winScreen(); return;
            }
            else
            {
                level++;
                Board b = buildLevel(levelFile,player);
                Game game = new Game(b);
                game.init(); // Game starts.
                System.out.println("\n Level up! \n");
            }
        }
    }
    private static String choosePlayer(){
        Scanner scanner = new Scanner(System.in);
        String player;
        String choice = null;

        while(choice == null){
            try
            {
                player = scanner.nextLine();
                int chosen = Integer.parseInt(player);
                if(chosen <= 0 || chosen > 6)
                    System.out.println("You have entered a wrong input, please choose a number between 1 to 6.");
                else
                    choice = player;
            }
            catch (NumberFormatException ignored){ // Ignore non-integer inputs
                }
        }
        return choice;
    }
    public static Board buildLevel(Object[] levelFile, Player player)
    {
        Board board = new Board((int) levelFile[1], (int) levelFile[2]);
        board.buildTiles((String) levelFile[0],player);
        return board;
    }

    public static Object[] readLevelFromTxt(int level, String[] args)
    {
        int height = 0, width = 0;
        String wantedLevel = args[0];
        System.out.println("Level " + level);

        StringBuilder board = new StringBuilder();

        try {
            File myObj = new File(wantedLevel);
            File[] matchingFile = myObj.listFiles((dir, name) -> name.endsWith("txt"));
            assert matchingFile != null;
            List<File> levelFiles = Arrays.asList(matchingFile);

            if (level > levelFiles.size()) {
                winScreen();
                return null;
            }

            File levelFile = levelFiles.get(level - 1);
            Scanner myReader = new Scanner(levelFile);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine().trim();

                width = Math.max(width, data.length());
                height++;
                board.append(data);
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("Game Over!");
            board = new StringBuilder("gameOver");
        }
        return new Object[]{board.toString(), height, width};
    }
    public static void startMenu()
    {
        TileFactory tf = new TileFactory();
        tf.showPlayers();
    }
    public static void winScreen(){
        System.out.print("You Won !!");
    }
}
