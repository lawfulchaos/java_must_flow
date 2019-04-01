package kerbin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class WorldBuilder {
    int ROOM_MAX_SIZE = 10;
    int ROOM_MIN_SIZE = 6;
    int MAX_ROOMS = 20;
    private int width;
    private int height;
    TileFactory tileFactory;
    public Tile[][] tiles;

    public WorldBuilder(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
        this.tileFactory = new TileFactory();
        for (Tile[] tileset: tiles) Arrays.fill(tileset, tileFactory.newWall());
    }

    public World build() {
        this.makeMap();
        return new World(tiles);
    }

    public void create_room(Rect room) {
        for (int x = room.x1 + 1; x < room.x2; x++) {
            for (int y = room.y1 + 1; y < room.y2; y++) {
                tiles[x][y] = tileFactory.newFloor();
            }

        }
    }

    public void create_h_tunnel(int x1, int x2, int y) {
        for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
            tiles[x][y] = tileFactory.newFloor();
        }
    }

    public void create_v_tunnel(int y1, int y2, int x) {
        for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
            tiles[x][y] = tileFactory.newFloor();
        }
    }

    public void add_teleport(boolean check){
        if(check == true) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);

            while (!tiles[x][y].isGround()) {
                x = (int) (Math.random() * width);
                y = (int) (Math.random() * height);
            }

            tiles[x][y] = tileFactory.newTeleport();
        }

    }

    public void makeMap() {
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
            if (!failed)
            {
                create_room(new_room);

                add_teleport(check_teleport);
                check_teleport=false;

                int[] center = new_room.center();
                if (num_rooms>0) {
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
    }
}