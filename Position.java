/**
 * Represents a position (cell) on the game board
 */
public class Position {
    private int row, col;
    private static final int BOARD_SIZE = 8;

    public Position(int row, int col) {
        // check that the position is valid
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
            throw new IllegalArgumentException("Invalid position: row and col must be between 0 and " + (BOARD_SIZE - 1));
        }
        this.row = row;
        this.col = col;
    }
    // Getters
    public int row() {
        return this.row;
    }

    public int col() {
        return this.col;
    }

    @Override
    public String toString() {
        return "(" + row() + ", " + col() + ")";
    }

    /**
     * The function is used to compare 2 position and determine if both are representing the same cell in the board
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position position)) return false;

        if (row != position.row) return false;
        return col == position.col;
    }
}
