public class UnflippableDisc implements Disc {
    private final Player owner;
    private final String type;

    public UnflippableDisc(Player owner) {
        this.owner = owner;
        this.type = "⭕";
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public void setOwner(Player player) {
        return;
    }

    @Override
    public String getType() {
        return type;
    }
}
