package dominoserver.model.communication;

public class TileAssignment {

    private int[][] tiles;

    public TileAssignment(int[][] tiles) {
        this.tiles = tiles;
    }

    public int[][] getTiles() {
        return tiles;
    }
    
}
