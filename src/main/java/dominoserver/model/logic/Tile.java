package dominoserver.model.logic;

import java.awt.Dimension;
import java.awt.Point;

public class Tile {
    
    private int[] halves;
    private Point location;
    private TileOrientation orientation;

    public Tile(int s0, int s1) {
        halves = new int[2];
        halves[0] = s0;
        halves[1] = s1;
        location = new Point();
        orientation = TileOrientation.NORTH;
    }
   
    public boolean has(int n) {
        return (halves[0] == n) || (halves[1] == n);
    }
    
    public boolean isDouble() {
        return (halves[0] == halves[1]);
    }
    
    public int getHalf(int n) {
        return halves[n];
    }

    public int[] getHalves() {
        return halves;
    }

    public void setHalves(int[] halves) {
        this.halves = halves;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public TileOrientation getOrientation() {
        return orientation;
    }

    public void setOrientation(TileOrientation orientation) {
        this.orientation = orientation;
    }
    
    public Dimension size() {
        switch (orientation) {
            case EAST:
            case WEST:
                return new Dimension(4, 2);
            case NORTH:
            case SOUTH:
                return new Dimension(2, 4);
            default: return null;
        }
    }
    
}
