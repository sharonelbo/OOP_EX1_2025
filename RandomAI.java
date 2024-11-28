import java.util.List;

public class RandomAI extends AIPlayer {

    public RandomAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        List<Position> validMoves = gameStatus.ValidMoves();
        int randomNumber = (int) (Math.random() * validMoves.size());
        Position randomPosition = validMoves.get(randomNumber);
        int randomDisc = (int) (Math.random() * 3);

        if (randomDisc == 0) {
            if (this.getNumber_of_bombs() <= 0) {
                return new Move(randomPosition, new SimpleDisc(this), this);
            }
            this.reduce_bomb();
            return new Move(randomPosition, new BombDisc(this), this);

        } else if (randomDisc == 1) {
            if (this.getNumber_of_unflippedable() <= 0) {
                return new Move(randomPosition, new SimpleDisc(this), this);
            }
            this.reduce_unflippedable();
            return new Move(randomPosition, new UnflippableDisc(this), this);

        } else {
            return new Move(randomPosition, new SimpleDisc(this), this);
        }
    }
}
