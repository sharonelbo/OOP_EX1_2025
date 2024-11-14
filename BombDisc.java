public class BombDisc implements Disc {
    private Player owner;
    private final String type;

    public BombDisc(Player owner) {
        this.owner = owner;
        this.type = "💣";
    }
    @Override
    public Player getOwner() {
        return this.owner;
    }

    @Override
    public void setOwner(Player player) {
        this.owner = player;
    }

    @Override
    public String getType() {
        return this.type;
    }
}
