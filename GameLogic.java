import java.util.ArrayList;
import java.util.List;

public class GameLogic implements PlayableLogic {
    public static final int BOARD_SIZE = 8;
    private Disc[][] board;
    private Player player1, player2;
    private boolean isFirstPlayerTurn;

    public GameLogic() {
        board = new Disc[BOARD_SIZE][BOARD_SIZE];
        initializeBoard();
    }

    @Override
    public boolean locate_disc(Position a, Disc disc) {
        int row = a.getRow();
        int col = a.getCol();
        return board[row][col] == disc;
    }

    @Override
    public Disc getDiscAtPosition(Position position) {
        int row = position.getRow();
        int col = position.getCol();
        return board[row][col];
    }

    @Override
    public int getBoardSize() {
        return BOARD_SIZE;
    }

    @Override
    public List<Position> ValidMoves() {
        List<Position> validMoves = new ArrayList<Position>();
        for (int row = 0; row < getBoardSize(); row++) {
            for (int col = 0; col < getBoardSize(); col++) {
                Position currentPosition = new Position(row, col);
                //still needs work
            }
        }
        return validMoves;
    }

    @Override
    public int countFlips(Position a) {
        return 0;
    }

    @Override
    public Player getFirstPlayer() {
        return player1;
    }

    @Override
    public Player getSecondPlayer() {
        return player2;
    }

    @Override
    public void setPlayers(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public boolean isFirstPlayerTurn() {
        return isFirstPlayerTurn;
    }

    @Override
    public boolean isGameFinished() {
        return false;
    }

    @Override
    public void reset() {
        initializeBoard();
    }

    private void initializeBoard() {
        for (int row = 0; row < BOARD_SIZE; row++)
            for (int col = 0; col < BOARD_SIZE; col++)
                board[row][col] = null;

        board[3][3] = new SimpleDisc(player1);
        board[4][4] = new SimpleDisc(player1);
        board[3][4] = new SimpleDisc(player2);
        board[4][3] = new SimpleDisc(player2);

        isFirstPlayerTurn = true;
    }

    @Override
    public void undoLastMove() {

    }
}
