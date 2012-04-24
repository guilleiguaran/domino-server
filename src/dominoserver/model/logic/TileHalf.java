package dominoserver.model.logic;

public class TileHalf {
    
    private int value;
    private TileHalf link;
    private Tile parent;

    public TileHalf(int value, TileHalf link, Tile parent) {
        this.value = value;
        this.link = link;
        this.parent = parent;
    }

    public TileHalf getLink() {
        return link;
    }

    public int getValue() {
        return value;
    }
    
    public boolean matches(TileHalf th) {
        return this.value == th.value;
    }

    public Tile getParent() {
        return parent;
    }
    
}
