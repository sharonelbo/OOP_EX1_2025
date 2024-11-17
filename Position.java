public class Position {
    public static final int boardSize = 8;
    public static Disc[][] board = new Disc[boardSize][boardSize];

    private int row, col;

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }
}
