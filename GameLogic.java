import java.util.List;

public class GameLogic implements PlayableLogic {

    @Override
    public boolean locate_disc(Position a, Disc disc) {
        int row = a.getRow();
        int col = a.getCol();
        return Position.board[row][col] == disc;
    }

    @Override
    public Disc getDiscAtPosition(Position position) {
        int row = position.getRow();
        int col = position.getCol();
        return Position.board[row][col];
    }

    @Override
    public int getBoardSize() {
        return Position.boardSize;
    }

    @Override
    public List<Position> ValidMoves() {
        return null;
    }

    @Override
    public int countFlips(Position a) {
        return 0;
    }

    @Override
    public Player getFirstPlayer() {
        return null;
    }

    @Override
    public Player getSecondPlayer() {
        return null;
    }

    @Override
    public void setPlayers(Player player1, Player player2) {

    }

    @Override
    public boolean isFirstPlayerTurn() {
        return false;
    }

    @Override
    public boolean isGameFinished() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void undoLastMove() {

    }
}
