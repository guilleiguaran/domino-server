package dominoserver.model;

public class Tile {
    
    private int[] spots;

    public Tile(int[] spots) {
        this.spots = spots;
    }
    
    public Tile(int s1, int s2) {
        spots = new int[2];
        spots[0] = s1;
        spots[1] = s2;
    }

    public int[] getSpots() {
        return spots;
    }
    
    public boolean has(int n) {
        return (spots[0] == n) || (spots[1] == n);
    }
    
    public boolean isDouble() {
        return (spots[0] == spots[1]);
    }
       
}
