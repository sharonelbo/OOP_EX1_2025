import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
/**
 * Represents the rules of the game, and the game board itself
 * Implementing PlayableLogic
 */
public class GameLogic implements PlayableLogic {

    public static final int BOARD_SIZE = 8;
    private final Disc[][] board;
    private Player player1, player2;
    private boolean isFirstPlayerTurn;
    private final Stack<Move> gameMoves = new Stack<>();
    private static final int[] DIRECTIONS = {-1, 0, 1};

// Constructors
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
        // there is already a Disc at position a or there aren't any possible moves
        if (getDiscAtPosition(a) != null || ValidMoves().isEmpty()) {
            return false;
        }

        Player currentPlayer = getCurrentPlayer();
        List<Position> flippedPositions = new ArrayList<>();

        // going through all 8 directions around position a while skipping direction (0,0) which is position a itself
        for (int rowDirection : DIRECTIONS) {
            for (int colDirection : DIRECTIONS) {
                if (rowDirection == 0 && colDirection == 0) {
                    continue;
                }

                // checking if any Discs can be flipped
                int countFlips = countFlips(a, currentPlayer, rowDirection, colDirection);
                if (countFlips > 0) {
                    // adding the positions of Discs that can be flipped to a flippedPositionsList
                    flippedPositionsList(a, currentPlayer, rowDirection, colDirection, flippedPositions);
                }
            }
        }
        // no Discs to flip, the move is invalid
        if (flippedPositions.isEmpty()) {
            return false;
        }

        // placing disc in position a
        board[a.row()][a.col()] = disc;
        System.out.println("Player " + getPlayerDigit(currentPlayer) + " placed a " + disc.getType() + " in " + a);

        // going through flippedPositions and flipping every Disc in each position
        for (Position tempPosition : flippedPositions) {
            // can't flip UnflippableDisc
            if (getDiscAtPosition(tempPosition) instanceof UnflippableDisc) {
                continue;
            }
            getDiscAtPosition(tempPosition).setOwner(currentPlayer);
            System.out.println("Player " + getPlayerDigit(currentPlayer) + " flipped the " + getDiscAtPosition(tempPosition).getType() + " in " + tempPosition);
        }

        // creating a new move, adding it to the stack and passing the turn
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

        // check that the position is valid
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

        // going through the entire board searching a valid position to place new Disc
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Position currentPosition = new Position(row, col);
                if (getDiscAtPosition(currentPosition) != null) {
                    continue;
                }

                // going through all 8 directions around currentPosition
                for (int rowDirection : DIRECTIONS) {
                    for (int colDirection : DIRECTIONS) {
                        if (rowDirection == 0 && colDirection == 0) {
                            continue;
                        }
                        // calling isValidMove to check if there is an opponent Disc to flip and if so add the position as a valid move
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
        // invalid position
        if (getDiscAtPosition(a) != null) {
            return 0;
        }

        Player currentPlayer = getCurrentPlayer();
        int flips = 0;

        // going through all 8 directions around position a
        for (int rowDirection : DIRECTIONS) {
            for (int colDirection : DIRECTIONS) {
                if (rowDirection == 0 && colDirection == 0) {
                    continue;
                }

                // checking how many possible flips there are
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

        // checking if player1 is out of valid moves
        isFirstPlayerTurn = true;
        boolean player1NoValidMoves = ValidMoves().isEmpty();

        // checking if player2 is out of valid moves
        isFirstPlayerTurn = false;
        boolean player2NoValidMoves = ValidMoves().isEmpty();

        isFirstPlayerTurn = playerTurn;
        // check if one of the players doesn't have a valid move, if not the game isn't over
        if (player1NoValidMoves || player2NoValidMoves) {
            int player1Discs = 0;
            int player2Discs = 0;

            // going through the board and counting the number of Discs each player have
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

            // player1 wins
            if (player1Discs > player2Discs) {
                String winner = "1";
                String loser = "2";
                System.out.println("Player " + winner + " wins with " + player1Discs + " discs! Player " + loser + " had " + player2Discs + " discs.");
                player1.addWin();

            }
            // player2 wins
            else if (player1Discs < player2Discs) {
                String winner = "2";
                String loser = "1";
                System.out.println("Player " + winner + " wins with " + player2Discs + " discs! Player " + loser + " had " + player1Discs + " discs.");
                player2.addWin();
            }
            // Tie
            else {
                System.out.println("Tie!");
            }

            System.out.println();
            return true;
        }

        return false;
    }

    @Override
    public void reset() {
        player1.reset_bombs_and_unflippedable();
        player2.reset_bombs_and_unflippedable();
        initializeBoard();
    }

    @Override
    public void undoLastMove() {
        System.out.println("Undoing last move:");
        // checking if it's the first turn
        if (!gameMoves.isEmpty()) {
            Move lastMove = gameMoves.pop();
            Position position = lastMove.position();
            Disc disc = lastMove.disc();
            // the current player was the opponent of the previous turn (the original owner of the Discs that got flipped)
            Player player = getCurrentPlayer();

            // if a BombDisc was placed in the previous turn, add back the BombDisc to the player that placed it
            if (disc instanceof BombDisc) {
                lastMove.player().number_of_bombs++;
            }
            // if an UnflippableDisc was placed in the previous turn, add back the UnflippableDisc to the player that placed it
            if (disc instanceof UnflippableDisc) {
                lastMove.player().number_of_unflippedable++;
            }

            // removing the placed Disc
            board[position.row()][position.col()] = null;
            System.out.println("\tUndo: removing " + disc.getType() + " from " + position);

            // going through getFlippedDiscsPositions and flipping back all the Discs
            for (int i = 0; i < lastMove.getFlippedDiscsPositions().size(); i++) {
                Position tempPosition = lastMove.getFlippedDiscsPositions().get(i);
                Disc tempDisc = getDiscAtPosition(tempPosition);
                // if tempDisc is an UnflippableDisc skip it
                if (!(tempDisc instanceof UnflippableDisc)) {
                    tempDisc.setOwner(player);
                    System.out.println("\tUndo: flipping back " + tempDisc.getType() + " in " + tempPosition);
                }
            }

            // passing the turn back
            isFirstPlayerTurn = !isFirstPlayerTurn;
        } else {
            System.out.println("\tNo previous move available to undo.");
        }
        System.out.println();
    }

    /**
     * Helper function that empty the board, placing starting Discs, setting the turn back to player1, and clears the stack gameMoves
     */
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

    /**
     * the function returns the player that it's his turn
     */
    private Player getCurrentPlayer() {
        if (isFirstPlayerTurn) {
            return player1;
        }
        return player2;
    }

    /**
     * The function returns a list with the position of all the Discs that need to flipped ta specific move
     */
    private void flippedPositionsList(Position position, Player currentPlayer, int rowDirection, int colDirection, List<Position> flippedPositions) {
        int row = position.row() + rowDirection;
        int col = position.col() + colDirection;

        // while the new position is valid (not outside the board)
        while (row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE) {
            Position tempPosition = new Position(row, col);
            Disc discAtPosition = getDiscAtPosition(tempPosition);

            // if there isn't a Disc in the posotion or the Disc belongs to the current player stop
            if (discAtPosition == null || discAtPosition.getOwner().equals(currentPlayer)) {
                break;
            }
            // if it is a SimpleDisc make sure it isn't already in flippedPositions if not add it
            else if (discAtPosition instanceof SimpleDisc) {
                if (!flippedPositions.contains(tempPosition)) {
                    flippedPositions.add(tempPosition);
                }
                row += rowDirection;
                col += colDirection;
            }
            // if it is an UnflippableDisc skip it
            else if (discAtPosition instanceof UnflippableDisc) {
                row += rowDirection;
                col += colDirection;
            }
            // it is a BombDiscExplosion make sure it isn't already in flippedPositions if not add it and call BombDiscExplosion
            else {
                if (!(flippedPositions.contains(tempPosition))) {
                    flippedPositions.add(tempPosition);
                    // BombDiscExplosion adding the additional Discs that flips around the BombDisc
                    BombDiscExplosion(tempPosition, currentPlayer, flippedPositions);
                }
                row += rowDirection;
                col += colDirection;
            }
        }
    }

    /**
     * The stores all the possible positions of Discs to flip and than returns the amount of positions
     */
    private int countFlips(Position position, Player currentPlayer, int rowDirection, int colDirection) {
        int row = position.row() + rowDirection;
        int col = position.col() + colDirection;
        List<Position> countedPositions = new ArrayList<>();

        // while the new position is valid (not outside the board)
        while (row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE) {
            Position tempPosition = new Position(row, col);
            Disc discAtPosition = getDiscAtPosition(tempPosition);

            // reached empty position
            if (discAtPosition == null) {
                return 0;
            }
            // reached current player Disc
            else if (discAtPosition.getOwner().equals(currentPlayer)) {
                return countedPositions.size();
            }
            // if it is a SimpleDisc make sure it isn't already in countedPositions if not add it
            else if (discAtPosition instanceof SimpleDisc) {
                if (!countedPositions.contains(tempPosition)) {
                    countedPositions.add(tempPosition);
                }
                row += rowDirection;
                col += colDirection;
            }
            // if it is a UnflippableDisc skip it
            else if (discAtPosition instanceof UnflippableDisc) {
                row += rowDirection;
                col += colDirection;
            }
            // it is a BombDisc make sure it isn't already in countedPositions if not add it and call BombDiscExplosion
            else {
                if (!countedPositions.contains(tempPosition)) {
                    countedPositions.add(tempPosition);
                }
                // BombDiscExplosion adding the additional Discs that flips around the BombDisc
                BombDiscExplosion(tempPosition, currentPlayer, countedPositions);
                row += rowDirection;
                col += colDirection;
            }
        }

        return countedPositions.size();
    }

    /**
     * gets a Player and returns its digit as a String
     */
    private String getPlayerDigit(Player player) {
        String playerDigit = "";
        if (player.equals(player1)) {
            playerDigit = "1";
        } else {
            playerDigit = "2";
        }
        return playerDigit;
    }

    /**
     * The function make sure that a certain move is valid and can flip at least one opponent's Disc in the way
     */
    private boolean isValidMove(Position position, int rowDirection, int colDirection, Player currentPlayer) {
        int row = position.row() + rowDirection;
        int col = position.col() + colDirection;
        boolean foundOpponent = false;

        // while the new position is valid (not outside the board)
        while (row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE) {
            Disc discAtPosition = getDiscAtPosition(new Position(row, col));

            // reached empty position
            if (discAtPosition == null) {
                return false;
            }
            // reached current player Disc
            else if (discAtPosition.getOwner() == currentPlayer) {
                return foundOpponent;
            }
            // reached an opponent UnflippableDisc skip it
            else if (discAtPosition.getOwner() != currentPlayer && discAtPosition instanceof UnflippableDisc) {
                row += rowDirection;
                col += colDirection;
            }
            // rached an opponent flippable Disc ie. SimpleDisc or BombDisc
            else {
                foundOpponent = true;
                row += rowDirection;
                col += colDirection;
            }
        }

        return false;
    }

    /**
     * The function is adding the positions of the additional Discs that flips around a BombDisc to an ArrayList
     */
    private void BombDiscExplosion(Position position, Player player, List<Position> flippedPositions) {
        // going through all 8 directions around currentPosition
        for (int rowDirection : DIRECTIONS) {
            for (int colDirection : DIRECTIONS) {
                if (rowDirection == 0 && colDirection == 0) {
                    continue;
                }

                int row = position.row() + rowDirection;
                int col = position.col() + colDirection;

                // if the new position is valid (not outside the board)
                if (row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE) {
                    Position tempPosition = new Position(row, col);
                    Disc tempDisc = getDiscAtPosition(tempPosition);
                    // tempDisc exist and it is an opponent Disc
                    if (tempDisc != null && !tempDisc.getOwner().equals(player)) {
                        //if it is a BombDisc make sure it isn't already in flippedPositions if not call BombDiscExplosion recursively
                        // and add it to flippedPositions
                        if (tempDisc instanceof BombDisc) {
                            if (!flippedPositions.contains(tempPosition)) {
                                BombDiscExplosion(tempPosition, player, flippedPositions);
                                flippedPositions.add(tempPosition);
                            }
                        }
                        //if it is a SimpleDisc make sure it isn't already in flippedPositions if not add it
                        else if (tempDisc instanceof SimpleDisc) {
                            if (!flippedPositions.contains(tempPosition)) {
                                flippedPositions.add(tempPosition);
                            }
                        }
                    }
                }
            }
        }
    }

}
