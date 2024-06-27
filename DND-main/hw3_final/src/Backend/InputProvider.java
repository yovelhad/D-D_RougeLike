package Backend;

import Backend.Tiles.Position;

public class InputProvider {
    public InputProvider() { }
    public Position producePosition(Position currPos, char input)
    {
        return switch (input) {
            case 'w' -> new Position(currPos.X(), currPos.Y() - 1);
            case 'a' -> new Position(currPos.X() - 1, currPos.Y());
            case 's' -> new Position(currPos.X(), currPos.Y() + 1);
            case 'd' -> new Position(currPos.X() + 1, currPos.Y());
            case 'e' -> new Position(-1, -1);
            case 'q' -> currPos;
            default -> null;
        };
    }
}