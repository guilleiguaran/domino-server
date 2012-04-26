package dominoserver.model.logic;

import dominoserver.model.connection.SocketClientThread;
import java.util.ArrayList;

public class Player {
    
    private String username;
    private ArrayList<Tile> tiles;
    private SocketClientThread socket;

    public Player(String name) {
        this.username = name;
        tiles = new ArrayList<>();
    }
        
    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
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

    public SocketClientThread getSocket() {
        return socket;
    }

    public void setSocket(SocketClientThread socket) {
        this.socket = socket;
    }
    
}
