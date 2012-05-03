package dominoserver.model.communication;

public class PlayRequest {
    
    int tile_id;
    int place;

    public PlayRequest(int tile_id, int place) {
        this.tile_id = tile_id;
        this.place = place;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getTile_id() {
        return tile_id;
    }

    public void setTile_id(int tile_id) {
        this.tile_id = tile_id;
    }
    
}
