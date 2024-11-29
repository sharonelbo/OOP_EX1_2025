/**
 * Represents a special type of Disc known as BombDisc that when flipped also flips the Discs surrounding it
 * Implements Disc interface
 */
public class BombDisc implements Disc {

    private Player owner;
    private final String type;

    public BombDisc(Player owner) {
        this.owner = owner;
        this.type = "💣";
    }

    //Getters and Setters
    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public void setOwner(Player player) {
        owner = player;
    }

    @Override
    public String getType() {
        return type;
    }
}
