/**
 * Represents a default Disc known as SimpleDisc
 * Implements Disc interface
 */
public class SimpleDisc implements Disc {

    private Player owner;
    private final String type;

    // Constructor
    public SimpleDisc(Player owner) {
        this.owner = owner;
        this.type = "⬤";
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
