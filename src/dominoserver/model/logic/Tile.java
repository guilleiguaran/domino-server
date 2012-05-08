package dominoserver.model.logic;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;

public class Tile {
    
    private TileHalf[] halves;
    private Point2D location;
    private TileOrientation orientation;

    public Tile(int s0, int s1) {
        halves = new TileHalf[2];
        halves[0] = new TileHalf(s0, null, this);
        halves[1] = new TileHalf(s1, null, this);
        location = new Point();
        orientation = TileOrientation.NORTH;
    }
   
    public boolean has(int n) {
        return (halves[0].getValue() == n) || (halves[1].getValue() == n);
    }
    
    public boolean isDouble() {
        return (halves[0].getValue() == halves[1].getValue());
    }
    
    public TileHalf getHalf(int n) {
        return halves[n];
    }

    public TileHalf[] getHalves() {
        return halves;
    }

    public void setHalves(TileHalf[] halves) {
        this.halves = halves;
    }

    public Point2D getLocation() {
        return location;
    }

    public void setLocation(Point2D location) {
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
