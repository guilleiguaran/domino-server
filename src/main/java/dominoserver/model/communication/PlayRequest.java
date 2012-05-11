package dominoserver.model.communication;

import dominoserver.model.logic.TileOrientation;

public class PlayRequest {
    
    int tile_id;
    TileOrientation orientation;
    int x; 
    int y;

    public PlayRequest(int tile_id, TileOrientation orientation, int x, int y) {
        this.tile_id = tile_id;
        this.orientation = orientation;
        this.x = x;
        this.y = y;
    }

    public TileOrientation getOrientation() {
        return orientation;
    }

    public void setOrientation(TileOrientation orientation) {
        this.orientation = orientation;
    }

    public int getTile_id() {
        return tile_id;
    }

    public void setTile_id(int tile_id) {
        this.tile_id = tile_id;
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
