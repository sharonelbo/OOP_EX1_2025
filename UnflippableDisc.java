public class UnflippableDisc implements Disc {
    private final Player owner;
    private final String type;

    public UnflippableDisc(Player owner) {
        this.owner = owner;
        this.type = "⭕";
    }
    @Override
    public Player getOwner() {
        return this.owner;
    }

    @Override
    public void setOwner(Player player) {
    }

    @Override
    public String getType() {
        return this.type;
    }
}
