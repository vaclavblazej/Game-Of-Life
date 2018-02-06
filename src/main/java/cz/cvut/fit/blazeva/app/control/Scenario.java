package cz.cvut.fit.blazeva.app.control;

import java.util.Random;

public class Scenario {

    public enum TileTypes {
        OCCUPIED, EMPTY;
    }

    public int size = 40;

    public TileTypes[][] map, off, tmp;

    public Scenario() {
        map = new TileTypes[size][size];
        off = new TileTypes[size][size];
        clear(map);
        clear(off);
    }

    private void clear(TileTypes[][] arr) {
        final Random random = new Random();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                arr[i][j] = (random.nextInt() % 100) > 50 ? TileTypes.EMPTY : TileTypes.OCCUPIED;
            }
        }
    }

    private void swapMaps() {
        tmp = map;
        map = off;
        off = tmp;
    }

    public void toggle(int bx, int by) {
        if (bx >= 0 && bx < size && by >= 0 && by < size) {
            map[bx][by] = TileTypes.OCCUPIED;
        }
    }

    private int dx[] = {1, 1, 1, 0, 0, -1, -1, -1};
    private int dy[] = {-1, 0, 1, -1, 1, -1, 0, 1};

    public void update() {
        clear(off);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int count = 0;
                for (int k = 0; k < dx.length; k++) {
                    int x = j + dx[k];
                    int y = i + dy[k];
                    if (x < 0 || x >= size) continue;
                    if (y < 0 || y >= size) continue;
                    if (map[y][x] == TileTypes.OCCUPIED) {
                        count++;
                    }
                }
                if (map[i][j] == TileTypes.OCCUPIED) {
                    off[i][j] = count >= 2 && count <= 3 ? TileTypes.OCCUPIED : TileTypes.EMPTY;
                } else {
                    off[i][j] = count == 3 ? TileTypes.OCCUPIED : TileTypes.EMPTY;
                }
            }
        }
        swapMaps();
    }

}
