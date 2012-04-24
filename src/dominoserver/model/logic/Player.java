package dominoserver.model.logic;

import java.util.ArrayList;

public class Player {
    
    private String name;
    private ArrayList<Tile> tiles;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }
    
    public void addTile(Tile tile) {
        tiles.add(tile);
    }
    
    public Tile popTile(int index) {
        return tiles.remove(index);
    }
        
}
