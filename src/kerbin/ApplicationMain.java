package kerbin;
// Точка входа в программу, создание окна и обработка клавиатуры 
import javax.swing.JFrame;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import kerbin.screens.Screen;
import kerbin.screens.StartScreen;

public class ApplicationMain extends JFrame implements KeyListener {
	
	private AsciiPanel terminal;
	private Screen screen;
	
	public ApplicationMain(){
		super();
		terminal = new AsciiPanel(90,40, AsciiFont.CP437_9x16);
		add(terminal);
		pack();
		screen = new StartScreen();
		addKeyListener(this);
		repaint();
		
	}
// Вывод игрового поля в приложение
	@Override
	public void repaint(){
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
	public void keyReleased(KeyEvent e) { }

	@Override
	public void keyTyped(KeyEvent e) { }
	
	public static void main(String[] args) {
		ApplicationMain app = new ApplicationMain();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}
}
