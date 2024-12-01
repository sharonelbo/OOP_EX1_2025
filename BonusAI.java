import java.util.Comparator;
import java.util.List;

/**
 * Bonus section: represents AI player that will beat the Random AI with a ratio of at least 10:1
 * Extending class AIPlayer and Implementing Comparator
 */
public class BonusAI extends AIPlayer implements Comparator<Position> {
    private PlayableLogic gameStatus;
    private boolean stop = false;
    public BonusAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        this.gameStatus = gameStatus;
        List<Position> list = gameStatus.ValidMoves();
        Position bestPosition = list.get(0);
        for(Position position : list) {
            if (!stop){
                if(compare(position, bestPosition) > 0) {
                    bestPosition = position;
                }
            }
        }
        Move bestMove;
        if(Math.abs(bestPosition.col()-3) < 3 && Math.abs(bestPosition.row()-3) < 3 && this.getNumber_of_unflippedable() > 0) {
            bestMove = new Move(bestPosition, new UnflippableDisc(this), this);
        }
        else {
            bestMove = new Move(bestPosition, new SimpleDisc(this), this);
        }
        return bestMove;
    }

    @Override
    public int compare(Position position, Position bestPosition) {
        if (gameStatus.getDiscAtPosition(new Position(7,7)) != null && gameStatus.getDiscAtPosition(new Position(7,7)).getOwner() == this){
            for (Position p : position.getNeighbors()) {
                if (p.equals(position)) {
                    stop = true;
                    return 1;
                }
            }
        }
        if (gameStatus.getDiscAtPosition(new Position(0,7)) != null && gameStatus.getDiscAtPosition(new Position(0,7)).getOwner() == this){
            for (Position p : position.getNeighbors()) {
                if (p.equals(position)) {
                    stop = true;
                    return 1;
                }
            }
        }
        if (gameStatus.getDiscAtPosition(new Position(7,0)) != null && gameStatus.getDiscAtPosition(new Position(7,0)).getOwner() == this){
            for (Position p : position.getNeighbors()) {
                if (p.equals(position)) {
                    stop = true;
                    return 1;
                }
            }
        }
        if (gameStatus.getDiscAtPosition(new Position(0,0)) != null && gameStatus.getDiscAtPosition(new Position(0,0)).getOwner() == this){
            for (Position p : position.getNeighbors()) {
                if (p.equals(position)) {
                    stop = true;
                    return 1;
                }
            }
        }
        if ((position.row() == 7) && (position.col() == 7)) {
            return 1;
        }
        if ((position.row() == 0) && (position.col() == 0)) {
            return 1;
        }
        if ((position.row() == 0) && (position.col() == 7)) {
            return 1;
        }
        if ((position.row() == 7) && (position.col() == 0)) {
            return 1;
        }
        if (bestPosition.row() == 0 && bestPosition.col() == 0) {
            for (Position p : position.getNeighbors()) {
                if (p.equals(position)) {
                    return 1;
                }
            }
            return 0;
        }
        if (bestPosition.row() == 7 && bestPosition.col() == 7) {
            for (Position p : position.getNeighbors()) {
                if (p.equals(position)) {
                    return 1;
                }
            }
            return 0;
        }
        if (bestPosition.row() == 7 && bestPosition.col() == 0) {
            for (Position p : position.getNeighbors()) {
                if (p.equals(position)) {
                    return 1;
                }
            }
            return 0;
        }
        if (bestPosition.row() == 0 && bestPosition.col() == 7) {
            for (Position p : position.getNeighbors()) {
                if (p.equals(position)) {
                    return 1;
                }
            }
            return 0;
        }
        if (gameStatus.countFlips(position) > gameStatus.countFlips(bestPosition)) {
            return 1;
        }
        return 0;
    }
}
