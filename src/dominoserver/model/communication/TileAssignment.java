package dominoserver.model.communication;

public class TileAssignment {

    private int[][] tiles;
    private int[] indices;

    public TileAssignment(int[][] tiles, int[] indices) {
        this.tiles = tiles;
        this.indices = indices;
    }
    
    public int[] getIndices() {
        return indices;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }

    public int[][] getTiles() {
        return tiles;
    }

    public void setTiles(int[][] tiles) {
        this.tiles = tiles;
    }
    
}
