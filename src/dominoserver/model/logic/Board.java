package dominoserver.model.logic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    public int width;
    public int height;
    private ArrayList<Tile> tiles;
    private int[][] hit_mask;
    ArrayList<Point> head;
    ArrayList<Point> tail;
    ArrayList<ArrayList<Point>> endings;

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
        head = new ArrayList<>();
        tail = new ArrayList<>();
        endings = new ArrayList<>();
        endings.add(head);
        endings.add(tail);
    }
    
    public boolean isEmpty() {
        return tiles.isEmpty();
    }

    public void addTile(Tile tile) {
        tiles.add(tile);
        int x = tile.getLocation().x;
        int y = tile.getLocation().y;
        int w = tile.size().width, h = tile.size().height;
        for (int i = x - w / 2; i <= x + w / 2 - 1; i++) {
            for (int j = y - h / 2; j <= y + h / 2 - 1; j++) {
                hit_mask[i][j] = tiles.indexOf(tile);
            }
        }
        if (!head.isEmpty() && !tail.isEmpty()) {
            ArrayList<Point> e = head;
            if ((playingBy(head, tile) && tail.isEmpty()) || (playingBy(tail, tile))) {
                e = tail;
            }
            int v = getValue(e.get(0).x, e.get(0).y, null);
            e.clear();
            for (int i = x - w / 2; i <= x + w / 2 - 1; i++) {
                for (int j = y - h / 2; j <= y + h / 2 - 1; j++) {
                    if ((getValue(i, j, null) != v) || tile.isDouble()) {
                        e.add(new Point(i, j));
                    }
                }
            }
        } else {
            for (int i = x - w / 2; i <= x + w / 2 - 1; i++) {
                for (int j = y - h / 2; j <= y + h / 2 - 1; j++) {
                    if (!tile.isDouble()) {
                        switch (tile.getOrientation()) {
                            case EAST:
                            case WEST:
                                (i < x ? head : tail).add(new Point(i, j));
                                break;
                            case NORTH:
                            case SOUTH:
                                (j < y ? head : tail).add(new Point(i, j));
                                break;
                        }                            
                    } else {
                        head.add(new Point(i, j));
                    }
                }
            }
        }
        System.out.println("head: " + Arrays.toString(head.toArray()));
        System.out.println("tail: " + Arrays.toString(tail.toArray()));
    }
    
    private boolean playingBy(ArrayList<Point> end, Tile tile) {
        int x = tile.getLocation().x;
        int y = tile.getLocation().y;
        int w = tile.size().width, h = tile.size().height;
        int c = 0;
        for (int i = x - w / 2; i <= x + w / 2 - 1; i++) {
            for (int j = y - h / 2; j <= y + h / 2 - 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        for (Point p : end) {
                            if ((p.distance(i + k, j + l) == 0) && (Math.abs(l) + Math.abs(k) != 2)) {
                                c += 1;
                                if (c == 2) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean canPlace(Tile tile) {
        int x = tile.getLocation().x;
        int y = tile.getLocation().y;
        int w = tile.size().width, h = tile.size().height;
        if ((x - w / 2 < 0) || (x + w / 2 > width) || (y - h / 2 < 0) || (y + h / 2 > height)) {
            System.out.println("cond1");
            return false;
        }
        for (int i = x - w / 2; i <= x + w / 2 - 1; i++) {
            for (int j = y - h / 2; j <= y + h / 2 - 1; j++) {
                if (hit_mask[i][j] != -1) {
                    System.out.println("cond2");
                    return false;
                }
                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        if ((getValue(i + k, j + l, null) != -1) && (getValue(i + k, j + l, null) != getValue(i, j, tile)) && (Math.abs(l) + Math.abs(k) != 2) && ((i + k < x - w / 2) || (i + k > x + w / 2 - 1) || (j + l < y - h / 2) || (j + l > y + h / 2 - 1))) {
                            System.out.println("cond3");
                            System.out.println(i + " " + j + " " +  k + " " + l);
                            System.out.println(getValue(i + k, j + l, null));
                            System.out.println(getValue(i, j, tile));
                            return false;
                        }
                    }
                }
            }
        }
        System.out.println("pbh? " + playingBy(head, tile));
        System.out.println("pbt? " + playingBy(tail, tile));
        if (!(playingBy(head, tile) ^ playingBy(tail, tile)) && !tiles.isEmpty()) {
            System.out.println("cond4");
            return false;
        }
        return true;
    }

    public int getValue(int x, int y, Tile tile) {
        if ((x < 0) || (y < 0) || (x >= width) || (y >= height)) {
            return -1;
        }
        if ((hit_mask[x][y] == -1) && (tile == null)) {
            return -1;
        } else {
            Tile t = (tile == null ? tiles.get(hit_mask[x][y]) : tile);
            switch (t.getOrientation()) {
                case WEST:
                    return t.getHalf((x < t.getLocation().x ? 0 : 1));
                case EAST:
                    return t.getHalf((x >= t.getLocation().x ? 0 : 1));
                case NORTH:
                    return t.getHalf((y < t.getLocation().y ? 0 : 1));
                case SOUTH:
                    return t.getHalf((y >= t.getLocation().y ? 0 : 1));
                default:
                    return -1;
            }
        }
    }
   
    @Override
    public String toString() {
        String ret = "";
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                ret += (hit_mask[j][i] == -1 ? "* " : getValue(j, i, null) + " ");
            }
            ret += "\n";
        }
        return ret;
    }
    
}
