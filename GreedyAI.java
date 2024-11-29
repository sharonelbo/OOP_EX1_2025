import java.util.Comparator;
import java.util.List;

/**
 * Represents and AI player that always chooses the move that will flip the maximum amount of Discs
 * Extending class AIPlayer and Implementing Comparator
 */
public class GreedyAI extends AIPlayer implements Comparator<Position> {

    public GreedyAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    /**
     * The function will choose the move that will flip the maximum amount of Discs
     *  if there is more than one move, the move with the rightmost Disc will be chosen
     *  if there is han one move, the move with the bottommost Disc will be chosen
     */
    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        List<Position> validMoves = gameStatus.ValidMoves();
        // if there is no valid move return null
        if (validMoves == null || validMoves.isEmpty()) {
            return null;
        }
        // assume that placing the Disc at the first position will flip the maximum amount of Discs
        Position bestPosition = validMoves.get(0);
        for (int i = 1; i < validMoves.size(); i++) {
            Position tempPosition = validMoves.get(i);
            // comparing the amount of flips using countFlips
            if (gameStatus.countFlips(tempPosition) > gameStatus.countFlips(bestPosition)) {
                bestPosition = tempPosition;
            } else if (gameStatus.countFlips(tempPosition) == gameStatus.countFlips(bestPosition)) {
                // got 2 positions that will flip the maximum amount of Discs, using the comparator to determine which one to pick
                if (compare(tempPosition, bestPosition) > 0) {
                    bestPosition = tempPosition;
                }
            }
        }
        return new Move(bestPosition, new SimpleDisc(this), this);
    }

    /**
     * The function that "implements" the comparator, using it to compare 2 positions with equal amounts of flips
     *  according to the conditions
     */
    @Override
    public int compare(Position position, Position bestPosition) {
        // Compare by column first
        if (position.col() > bestPosition.col()) {
            return 1;
        } else if (position.col() < bestPosition.col()) {
            return -1;
        }

        // If columns are the same, compare by row
        if (position.row() > bestPosition.row()) {
            return 1;
        } else if (position.row() < bestPosition.row()) {
            return -1;
        }

        // Both positions are equal
        return 0;
    }
}
