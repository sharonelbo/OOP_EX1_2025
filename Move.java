import java.util.ArrayList;
import java.util.List;
/**
 * Represents a move of placing a Disc at the board
 */
public class Move {
    private Position position;
    private Disc disc;
    private Player player;
    private List<Position> flippedDiscsPositions = new ArrayList<>();

    // Constructors
    public Move(Position position, Disc disc, Player player) {
        this.position = position;
        this.disc = disc;
        this.player = player;
        flippedDiscsPositions = new ArrayList<Position>();
    }

    public Move(Position position, Disc disc, Player player, List<Position> positions) {
        this.position = position;
        this.disc = disc;
        this.player = player;
        flippedDiscsPositions.addAll(positions);
    }

    // Getters
    public Position position() {
        return position;
    }

    public Disc disc() {
        return disc;
    }

    public Player player() {
        return player;
    }

    public List<Position> getFlippedDiscsPositions() {
        return flippedDiscsPositions;
    }
}
