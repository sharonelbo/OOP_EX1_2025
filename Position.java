public class Position {
    private int row, col;
    private static final int BOARD_SIZE = 8;

    public Position(int row, int col) {
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
            throw new IllegalArgumentException("Invalid position: row and col must be between 0 and " + (BOARD_SIZE - 1));
        }
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }
}
