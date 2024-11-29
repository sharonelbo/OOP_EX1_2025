/**
 * Represents a special type of Disc known as UnflippableDisc that can't be flipped
 * Implements Disc interface
 */
public class UnflippableDisc implements Disc {
    private Player owner;
    private final String type;

    // Constructor
    public UnflippableDisc(Player owner) {
        this.owner = owner;
        this.type = "⭕";
    }

    // Getters and Setter
    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public void setOwner(Player player) {
        // doesn't change the original owner
        this.owner = player;
    }

    @Override
    public String getType() {
        return type;
    }
}
