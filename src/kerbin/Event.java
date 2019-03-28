package kerbin;
//Синглтон-сообщение для вывода в ХУДе, генерируются действиями ИИ нпс и игрока, имеют срок жизни, цвет и приоритет (больше - важнее)
import java.awt.Color;

public class Event {
    private static Event instance;

    public String getMsg() { return msg; }

    public int getPriority() { return priority; }

    public int getLifetime() { return lifetime; }

    public Color getColor() { return color; }

    public void setMsg(String msg) { this.msg = msg; }

    public void setPriority(int priority) { this.priority = priority; }

    public void decreaseLifetime() { this.lifetime-=1; }

    public void setColor(Color color) { this.color = color; }

    private String msg;
    private int priority;
    private int lifetime;
    private Color color;

    private Event () {};

    public void init(String msg, int priority, int lifetime, Color color)
    {
        this.msg = msg;
        this.priority = priority;
        this.lifetime = lifetime;
        this.color = color;
    }

    public static Event getInstance() {
        if (instance == null) {
            instance = new Event();
        }
        return instance;
    }
}