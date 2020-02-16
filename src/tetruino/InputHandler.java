package tetruino;

import org.jetbrains.annotations.NotNull;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static java.awt.event.KeyEvent.*;

public class InputHandler implements KeyListener
{
	public boolean[] keys = new boolean[65535];
	
	public boolean up() { return keys[VK_UP] || keys[VK_W]; }
	public boolean down() { return keys[VK_DOWN] || keys[VK_S]; }
	public boolean left() { return keys[VK_LEFT] || keys[VK_A]; }
	public boolean right() { return keys[VK_RIGHT] || keys[VK_D]; }
	
	public void keyPressed(@NotNull KeyEvent e) { keys[e.getKeyCode()] = true; }
	public void keyReleased(@NotNull KeyEvent e) { keys[e.getKeyCode()] = false; }
	public void keyTyped(KeyEvent e) {}
}
