import java.util.List;

public class GreedyAI extends AIPlayer {

    public GreedyAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }
    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        List<Position> validMoves = gameStatus.ValidMoves();
        if(validMoves == null || validMoves.isEmpty()) {
            return null;
        }

        Move move = null;
        return move;
    }
}
