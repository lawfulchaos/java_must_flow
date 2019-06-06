package kerbin.world;

//Прямоугольник, вспомогательный класс для генерации мира
class Rect {
    int x1, y1, x2, y2;

    Rect(int x, int y, int w, int h) {
        x1 = x;
        y1 = y;
        x2 = x + w;
        if (x2 > 89) x2 = 89;
        y2 = y + h;
        if (y2 > 31) y2 = 31;
    }

    //Находит центр комнаты
    int[] center() {
        int center_x = (x1 + x2) / 2;
        int center_y = (y1 + y2) / 2;
        return new int[]{center_x, center_y};
    }

    //Проверяет на пересечение две комнаты
    boolean intersect(Rect other) {
        return ((x1 <= other.x2) && (x2 >= other.x1) && (y1 <= other.y2) && (y2 >= other.y1));
    }

}
