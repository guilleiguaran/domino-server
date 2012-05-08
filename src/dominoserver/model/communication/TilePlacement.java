package dominoserver.model.communication;

import dominoserver.model.logic.TileOrientation;

public class TilePlacement {

    int[] tile;
    int x;
    int y;
    TileOrientation orientation;

    public TilePlacement(int[] tile, int x, int y, TileOrientation orientation) {
        this.tile = tile;
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    public TileOrientation getOrientation() {
        return orientation;
    }

    public void setOrientation(TileOrientation orientation) {
        this.orientation = orientation;
    }

    public int[] getTile() {
        return tile;
    }

    public void setTile(int[] tile) {
        this.tile = tile;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
        
}
