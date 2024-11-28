import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GameLogic implements PlayableLogic {

    public static final int BOARD_SIZE = 8;
    private Disc[][] board;
    private Player player1, player2;
    private boolean isFirstPlayerTurn;
    private Stack<Move> gameMoves = new Stack<>();
    private static final int[] DIRECTIONS = {-1, 0, 1};


    public GameLogic() {
        this.player1 = new HumanPlayer(true);
        this.player2 = new HumanPlayer(false);
        board = new Disc[BOARD_SIZE][BOARD_SIZE];
        initializeBoard();
    }

    public GameLogic(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        board = new Disc[BOARD_SIZE][BOARD_SIZE];
        initializeBoard();
    }

    @Override
    public boolean locate_disc(Position a, Disc disc) {
        if (getDiscAtPosition(a) != null || ValidMoves().isEmpty()) {
            return false;
        }

        Player currentPlayer = getCurrentPlayer();
        List<Position> flippedPositions = new ArrayList<>();

        for (int rowDirection : DIRECTIONS) {
            for (int colDirection : DIRECTIONS) {
                if (rowDirection == 0 && colDirection == 0) {
                    continue;
                }

                int countFlips = countFlips(a, currentPlayer, rowDirection, colDirection);
                if (countFlips > 0) {
                    flippedPositionsList(a, currentPlayer, rowDirection, colDirection, flippedPositions);
                }
            }
        }

        if (flippedPositions.isEmpty()) {
            return false;
        }

        board[a.row()][a.col()] = disc;
        System.out.println("Player " + getPlayerDigit(currentPlayer) + " placed a " + disc.getType() + " in " + a);

        for (Position tempPosition : flippedPositions) {
            getDiscAtPosition(tempPosition).setOwner(currentPlayer);
            System.out.println("Player " + getPlayerDigit(currentPlayer) + " flipped the " + getDiscAtPosition(tempPosition).getType() + " in " + tempPosition);
        }

        Move move = new Move(a, disc, currentPlayer, flippedPositions);
        gameMoves.add(move);
        isFirstPlayerTurn = !isFirstPlayerTurn;
        System.out.println();

        return true;
    }

    @Override
    public Disc getDiscAtPosition(Position position) {
        int row = position.row();
        int col = position.col();

        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
            return null;
        }
        return board[row][col];
    }

    @Override
    public int getBoardSize() {
        return BOARD_SIZE;
    }

    @Override
    public List<Position> ValidMoves() {
        Player currentPlayer = getCurrentPlayer();
        List<Position> validMovesList = new ArrayList<>();

        for (int row = 0; row < getBoardSize(); row++) {
            for (int col = 0; col < getBoardSize(); col++) {
                Position currentPosition = new Position(row, col);
                if (getDiscAtPosition(currentPosition) != null) {
                    continue;
                }

                for (int rowDirection : DIRECTIONS) {
                    for (int colDirection : DIRECTIONS) {
                        if (rowDirection == 0 && colDirection == 0) {
                            continue;
                        }
                        if (isValidMove(currentPosition, rowDirection, colDirection, currentPlayer)) {
                            validMovesList.add(currentPosition);
                            break;
                        }
                    }
                }
            }
        }

        return validMovesList;
    }

    @Override
    public int countFlips(Position a) {
        if (getDiscAtPosition(a) != null) {
            return 0;
        }

        Player currentPlayer = getCurrentPlayer();
        int flips = 0;

        for (int rowDirection : DIRECTIONS) {
            for (int colDirection : DIRECTIONS) {
                if (rowDirection == 0 && colDirection == 0) {
                    continue;
                }

                flips += countFlips(a, currentPlayer, rowDirection, colDirection);
            }
        }
        return flips;
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
        boolean playerTurn = isFirstPlayerTurn;

        isFirstPlayerTurn = true;
        boolean player1NoValidMoves = ValidMoves().isEmpty();

        isFirstPlayerTurn = false;
        boolean player2NoValidMoves = ValidMoves().isEmpty();

        isFirstPlayerTurn = playerTurn;
        if (player1NoValidMoves || player2NoValidMoves) {
            int player1Discs = 0;
            int player2Discs = 0;

            for (int row = 0; row < BOARD_SIZE; row++) {
                for (int col = 0; col < BOARD_SIZE; col++) {
                    Disc tempDisc = board[row][col];

                    if (tempDisc == null) {
                        continue;
                    }
                    if (tempDisc.getOwner().equals(player1)) {
                        player1Discs++;
                    }
                    if (tempDisc.getOwner().equals(player2)) {
                        player2Discs++;
                    }
                }
            }

            if (player1Discs > player2Discs) {
                String winner = "1";
                String loser = "2";
                System.out.println("Player " + winner + " wins with " + player1Discs + " discs! Player " + loser + " had " + player2Discs + " discs.");
                player1.wins++;
            } else if (player1Discs < player2Discs) {
                String winner = "2";
                String loser = "1";
                System.out.println("Player " + winner + " wins with " + player2Discs + " discs! Player " + loser + " had " + player1Discs + " discs.");
                player2.wins++;
            } else {
                System.out.println("Tie");
            }

            System.out.println();
            return true;
        }

        return false;
    }

    @Override
    public void reset() {
        initializeBoard();
    }

    @Override
    public void undoLastMove() {
        System.out.println("Undoing last move:");
        if (!gameMoves.isEmpty()) {
            Move lastMove = gameMoves.pop();
            Position position = lastMove.position();
            Disc disc = lastMove.disc();

            board[position.row()][position.col()] = null;
            System.out.println("\tUndo: removing " + disc.getType() + " from " + position);

            for (int i = 0; i < lastMove.getFlippedDiscsPositions().size(); i++) {
                Position tempPosition = lastMove.getFlippedDiscsPositions().get(i);
                getDiscAtPosition(tempPosition).setOwner(getCurrentPlayer());
                System.out.println("\tUndo: flipping back " + getDiscAtPosition(tempPosition).getType() + " in " + tempPosition);
            }

            isFirstPlayerTurn = !isFirstPlayerTurn;
        } else {
            System.out.println("\tNo previous move available to undo.");
        }
        System.out.println();
    }

    private void initializeBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                board[row][col] = null;
            }
        }

        board[3][3] = new SimpleDisc(player1);
        board[4][4] = new SimpleDisc(player1);
        board[3][4] = new SimpleDisc(player2);
        board[4][3] = new SimpleDisc(player2);

        isFirstPlayerTurn = true;
        gameMoves.clear();
    }

    private Player getCurrentPlayer() {
        if (isFirstPlayerTurn) {
            return player1;
        }
        return player2;
    }

    private void flippedPositionsList(Position position, Player currentPlayer, int rowDirection, int colDirection, List<Position> flippedPositions) {
        int row = position.row() + rowDirection;
        int col = position.col() + colDirection;

        while (row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE) {
            Position tempPosition = new Position(row, col);
            Disc discAtPosition = getDiscAtPosition(tempPosition);

            if (discAtPosition == null || discAtPosition.getOwner().equals(currentPlayer)) {
                break;
            }

            flippedPositions.add(tempPosition);
            row += rowDirection;
            col += colDirection;
        }
    }

    private int countFlips(Position position, Player currentPlayer, int rowDirection, int colDirection) {
        int row = position.row() + rowDirection;
        int col = position.col() + colDirection;
        int flips = 0;


        while (row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE) {
            Position tempPosition = new Position(row, col);
            Disc discAtPosition = getDiscAtPosition(tempPosition);

            if (discAtPosition == null) {
                return 0;
            } else if (discAtPosition.getOwner().equals(currentPlayer)) {
                return flips;
            } else {
                flips++;
                row += rowDirection;
                col += colDirection;
            }
        }

        return 0;
    }


    private String getPlayerDigit(Player player) {
        String playerDigit = "";
        if (player.equals(player1)) {
            playerDigit = "1";
        } else {
            playerDigit = "2";
        }
        return playerDigit;
    }

    private boolean isValidMove(Position position, int rowDirection, int colDirection, Player currentPlayer) {
        int row = position.row() + rowDirection;
        int col = position.col() + colDirection;
        boolean foundOpponent = false;

        while (row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE) {
            Disc discAtPosition = getDiscAtPosition(new Position(row, col));

            if (discAtPosition == null) {
                return false;
            } else if (discAtPosition.getOwner() == currentPlayer) {
                return foundOpponent;
            } else {
                foundOpponent = true;
            }

            row += rowDirection;
            col += colDirection;
        }

        return false;
    }

}
