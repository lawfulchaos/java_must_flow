package kerbin;
//Сообщения для вывода в ХУДе, генерируются действиями ИИ нпс и игрока, имеют срок жизни, цвет и приоритет (больше - важнее)
import java.awt.Color;

public class Event {
    public String msg;
    public int lifetime;
    public int priority;
    public Color color;
    public Event(String msg, int lifetime, Color color, int priority)
    {
        this.msg = msg;
        this.lifetime = lifetime;
        this.color = color;
        this.priority = priority;
    }

}
