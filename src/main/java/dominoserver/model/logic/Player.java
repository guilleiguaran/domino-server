package dominoserver.model.logic;

import dominoserver.model.connection.SocketClientThread;
import java.util.HashMap;
import java.util.Map;

public class Player {
    
    private String username;
    private Map<Integer, Tile> tiles;
    private SocketClientThread socket;
    private boolean playing;
    private boolean waiting;

    public Player(String name) {
        this.username = name;
        tiles = new HashMap<>();
        playing = false;
    }

    public boolean isWaiting() {
        return waiting;
    }

    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }
       
    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public Map<Integer, Tile> getTiles() {
        return tiles;
    }

    public void setTiles(Map<Integer, Tile> tiles) {
        this.tiles = tiles;
    }
    
    public void addTile(Tile tile, int index) {
        tiles.put(index, tile);
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

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

}
