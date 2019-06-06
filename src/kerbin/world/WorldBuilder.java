package kerbin.world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class WorldBuilder {
    private int ROOM_MAX_SIZE;
    private int ROOM_MIN_SIZE;
    private int MAX_ROOMS;
    private int width;
    private int height;
    private TileFactory tileFactory;
    private Tile[][] tiles;

    public WorldBuilder(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
        this.tileFactory = new TileFactory();
        for (Tile[] tileset : tiles) Arrays.fill(tileset, tileFactory.newWall());
        ROOM_MAX_SIZE = 12;
        ROOM_MIN_SIZE = 5;
        MAX_ROOMS = 35;
    }

    public World build() {
        this.makeMap();
        return new World(tiles);
    }

    public World buildBossLevel() {
        this.makeBossLevel();
        return new World(tiles);
    }

    private void create_room(Rect room) {
        for (int x = room.x1 + 1; x < room.x2; x++) {
            for (int y = room.y1 + 1; y < room.y2; y++) {
                tiles[x][y] = tileFactory.newFloor();
            }

        }
    }

    private void create_h_tunnel(int x1, int x2, int y) {
        for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
            tiles[x][y] = tileFactory.newFloor();
        }
    }

    private void create_v_tunnel(int y1, int y2, int x) {
        for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
            tiles[x][y] = tileFactory.newFloor();
        }
    }

    private void add_teleport(boolean check) {
        if (check) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);

            while (!(tiles[x][y].glyph() == (char) 250) || tiles[x][y].item != null || (tiles[x][y].glyph() == '#')) {
                x = (int) (Math.random() * width);
                y = (int) (Math.random() * height);
            }

            tiles[x][y] = tileFactory.newTeleport();
        }

    }

    private void add_stairs(int[] coords) {
        for (int i = -1; i < 2; i++) {
            if (tiles[coords[0]][coords[1] + i].item == null) {
                tiles[coords[0]][coords[1] + i] = tileFactory.newStairs();
                break;
            }
        }
    }

    private void makeMap() {
        List<Rect> rooms = new ArrayList<>();
        int num_rooms = 0;
        boolean check_teleport = true;
        for (int i = 0; i < MAX_ROOMS; i++) {
            int w = ThreadLocalRandom.current().nextInt(ROOM_MIN_SIZE, ROOM_MAX_SIZE + 1);
            int h = ThreadLocalRandom.current().nextInt(ROOM_MIN_SIZE, ROOM_MAX_SIZE + 1);
            int x = ThreadLocalRandom.current().nextInt(0, 88);
            int y = ThreadLocalRandom.current().nextInt(0, 30);

            Rect new_room = new Rect(x, y, w, h);

            boolean failed = false;
            for (Rect other_room : rooms) {
                if (new_room.intersect(other_room)) {
                    failed = true;
                    break;
                }
            }
            if (!failed) {
                create_room(new_room);

                add_teleport(check_teleport);
                check_teleport = false;

                int[] center = new_room.center();
                if (num_rooms > 0) {
                    int[] prev_center = rooms.get(num_rooms - 1).center();
                    if (ThreadLocalRandom.current().nextInt(0, 1) > 0) {
                        create_h_tunnel(prev_center[0], center[0], prev_center[1]);
                        create_v_tunnel(prev_center[1], center[1], center[0]);
                    } else {
                        create_v_tunnel(prev_center[1], center[1], prev_center[0]);
                        create_h_tunnel(prev_center[0], center[0], center[1]);
                    }

                }
                rooms.add(new_room);
                num_rooms += 1;
            }

        }
        add_stairs(rooms.get(rooms.size() - 2).center());
    }

    //Уровень босса, две комнаты, в одной спавнится игрок, в другой босс
    private void makeBossLevel() {
        Rect playerRoom = new Rect(41, 3, 6, 6);
        Rect bossRoom = new Rect(30, 20, 30, 30);
        create_room(playerRoom);
        create_room(bossRoom);
        create_v_tunnel(playerRoom.center()[1], bossRoom.center()[1], playerRoom.center()[0]);
        add_stairs(bossRoom.center());
    }
}
