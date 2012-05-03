package dominoserver.model.logic;

import java.util.ArrayList;

public class Board {

    public int width;
    public int height;
    private ArrayList<Tile> tiles;
    private int[][] hit_mask;
    private TileHalf head;
    private TileHalf tail;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        tiles = new ArrayList<>();
        hit_mask = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                hit_mask[i][j] = -1;
            }
        }
    }
    
    public boolean isEmpty() {
        return tiles.isEmpty();
    }

    public void addTile(Tile tile, int place) {
        tiles.add(tile);
        if (isEmpty()) {
            head = tile.getHalf(0);
            tail = tile.getHalf(1);
        } else {
            if (place == 0) {
                if (head.matches(tile.getHalf(0))) {
                    
                } else if (head.matches(tile.getHalf(1))) {
                    
                }
            } else {
                if (tail.matches(tile.getHalf(0))) {
                    
                } else if (tail.matches(tile.getHalf(1))) {
                    
                }
            }
        }
        int x = (int) tile.getLocation().getX();
        int y = (int) tile.getLocation().getY();
        int w = 0, h = 0;
        switch (tile.getOrientation()) {
            case EAST:
            case WEST:
                w = 4;
                h = 2;
                break;
            case NORTH:
            case SOUTH:
                w = 2;
                h = 4;
                break;
        }
        for (int i = x - w / 2; i <= x + w / 2 - 1; i++) {
            for (int j = y - h / 2; j <= y + h / 2 - 1; j++) {
                hit_mask[i][j] = tiles.indexOf(tile);
            }
        }
    }

    public boolean canPlace(Tile tile) {
        int x = (int) tile.getLocation().getX();
        int y = (int) tile.getLocation().getY();
        int w = 0, h = 0;
        switch (tile.getOrientation()) {
            case EAST:
            case WEST:
                w = 4;
                h = 2;
                break;
            case NORTH:
            case SOUTH:
                w = 2;
                h = 4;
                break;
        }
        if ((x - w < 0) || (x + w > width) || (y - h < 0) || (y + h > height)) {
            return false;
        }
        for (int i = x - w / 2; i <= x + w / 2; i++) {
            for (int j = y - h / 2; j <= y + h / 2; j++) {
                if (hit_mask[i][j] != -1) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getValue(int x, int y) {
        if (hit_mask[x][y] != -1) {
            Tile tile = tiles.get(hit_mask[x][y]);
            switch (tile.getOrientation()) {
                case EAST:
                    return tile.getHalf((x < tile.getLocation().getX() ?  0 : 1)).getValue();
                case WEST:
                    return tile.getHalf((x >= tile.getLocation().getX() ?  0 : 1)).getValue();
                case NORTH:
                    return tile.getHalf((y < tile.getLocation().getY() ?  0 : 1)).getValue();
                case SOUTH:
                    return tile.getHalf((y >= tile.getLocation().getY() ?  0 : 1)).getValue();
                default:
                    return -1;
            }
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        String ret = "";
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (hit_mask[j][i] == -1) {
                    ret += "* ";
                } else {
                    ret += getValue(j, i) + " ";
                }
            }
            ret += "\n";
        }
        return ret;
    }
}
