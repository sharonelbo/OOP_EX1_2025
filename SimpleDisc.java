public class SimpleDisc implements Disc {

    private Player owner;
    private final String type;

    public SimpleDisc(Player owner) {
        this.owner = owner;
        this.type = "⬤";
    }
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
