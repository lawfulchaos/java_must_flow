package kerbin;
// Точка входа в программу, создание окна и обработка клавиатуры 

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import kerbin.screens.Screen;
import kerbin.screens.StartScreen;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

public class ApplicationMain extends JFrame implements KeyListener {

    private AsciiPanel terminal;
    private Screen screen;
    private static Clip clip;

    private ApplicationMain() {
        super();
        terminal = new AsciiPanel(90, 40, AsciiFont.CP437_9x16);
        add(terminal);
        pack();
        screen = new StartScreen(this);
        addKeyListener(this);
        repaint();

    }

    public static void main(String[] args) {
        playSound();
        ApplicationMain app = new ApplicationMain();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }

    public static void playSound(String name) {
        if (name.equals("death.wav"))
        {
            clip.close();
        }
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(name));
            Clip clip = AudioSystem.getClip();
            clip.open(inputStream);
            clip.loop(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private static void playSound() {
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("sound.wav"));
            clip = AudioSystem.getClip();
            clip.open(inputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    // Вывод игрового поля в приложение
    @Override
    public void repaint() {
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }

    // Реакция на нажатие/отпускание клавиши
    @Override
    public void keyPressed(KeyEvent e) {
        screen = screen.respondToUserInput(e);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
