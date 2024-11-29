import java.util.Comparator;
import java.util.List;

public class GreedyAI extends AIPlayer implements Comparator<Position> {

    public GreedyAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        List<Position> validMoves = gameStatus.ValidMoves();
        if (validMoves == null || validMoves.isEmpty()) {
            return null;
        }
        Position bestPosition = validMoves.get(0);
        for (int i = 1; i < validMoves.size(); i++) {
            Position tempPosition = validMoves.get(i);
            if (gameStatus.countFlips(tempPosition) > gameStatus.countFlips(bestPosition)) {
                bestPosition = tempPosition;
            } else if (gameStatus.countFlips(tempPosition) == gameStatus.countFlips(bestPosition)) {
                if (compare(tempPosition, bestPosition) > 0) {
                    bestPosition = tempPosition;
                }
            }
        }
        return new Move(bestPosition, new SimpleDisc(this), this);
    }

    @Override
    public int compare(Position position, Position bestPosition) {
        if (position.equals(bestPosition)) {
            return 0;
        }
        if (position.col() > bestPosition.col()) {
            return 1;
        } else if (position.col() == bestPosition.col()) {
            if (position.row() > bestPosition.row()) {
                return 1;
            }
            return -1;
        }
        return 0;
    }
}
