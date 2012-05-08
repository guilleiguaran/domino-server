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

    public void addTile(Tile tile) {
        tiles.add(tile);
        int x = (int) tile.getLocation().getX();
        int y = (int) tile.getLocation().getY();
        int w = tile.size().width, h = tile.size().height;
        for (int i = x - w / 2; i <= x + w / 2 - 1; i++) {
            for (int j = y - h / 2; j <= y + h / 2 - 1; j++) {
                hit_mask[i][j] = tiles.indexOf(tile);
            }
        }
    }

    public boolean canPlace(Tile tile) {
        int x = (int) tile.getLocation().getX();
        int y = (int) tile.getLocation().getY();
        int w = tile.size().width, h = tile.size().height;
        if ((x - w / 2 < 0) || (x + w / 2 > width) || (y - h / 2 < 0) || (y + h / 2 > height)) {
            return false;
        }
        for (int i = x - w / 2; i <= x + w / 2 - 1; i++) {
            for (int j = y - h / 2; j <= y + h / 2 - 1; j++) {
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
