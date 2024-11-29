import java.util.List;
/**
 * Represents and AI player that always chooses random move
 * Extending class AIPlayer
 */
public class RandomAI extends AIPlayer {

    public RandomAI(boolean isPlayerOne) {
        super(isPlayerOne);
    }

    /**
     * The function will choose a random valid position and place there a random Disc type
     */
    @Override
    public Move makeMove(PlayableLogic gameStatus) {
        // choosing a random position out of the valid positions
        List<Position> validMoves = gameStatus.ValidMoves();
        int randomNumber = (int) (Math.random() * validMoves.size());
        // choosing a random type of Disc to place
        Position randomPosition = validMoves.get(randomNumber);
        int randomDisc = (int) (Math.random() * 3);

        // try to place a BombDisc if the player don't have any BombDiscs left place SimpleDisc instead
        if (randomDisc == 0) {
            if (this.getNumber_of_bombs() <= 0) {
                return new Move(randomPosition, new SimpleDisc(this), this);
            }
            this.reduce_bomb();
            return new Move(randomPosition, new BombDisc(this), this);

        }
        // try to place an UnflippableDisc if the player don't have any UnflippableDiscs left place SimpleDisc instead
        else if (randomDisc == 1) {
            if (this.getNumber_of_unflippedable() <= 0) {
                return new Move(randomPosition, new SimpleDisc(this), this);
            }
            this.reduce_unflippedable();
            return new Move(randomPosition, new UnflippableDisc(this), this);

        }
        // place a SimpleDisc
        else {
            return new Move(randomPosition, new SimpleDisc(this), this);
        }
    }
}
