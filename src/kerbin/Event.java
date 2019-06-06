package kerbin;
//Синглтон-сообщение для вывода в ХУДе, генерируются действиями ИИ нпс и игрока, имеют срок жизни, цвет и приоритет (больше - важнее)

import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Event implements Serializable {
    private static Event instance;
    private String msg;
    private int priority;
    private int lifetime;
    private Color color;
    // Журнал сообщений
    private Map<String, Color> msgs = new HashMap<>();

    private Event() {
    }

    public static Event getInstance() {
        if (instance == null) {
            instance = new Event();
        }
        return instance;
    }

    public String getMsg() {
        return msg;
    }

    public int getPriority() {
        return priority;
    }

    public int getLifetime() {
        return lifetime;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void decreaseLifetime() {
        this.lifetime -= 1;
    }

    public void init(String msg, int priority, int lifetime, Color color) {
        this.msg = msg;
        this.priority = priority;
        this.lifetime = lifetime;
        this.color = color;
        msgs.put(msg, color);
    }
}