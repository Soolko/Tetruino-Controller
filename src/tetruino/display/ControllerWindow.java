package tetruino.display;

import tetruino.InputHandler;

import javax.swing.*;
import java.awt.*;

public class ControllerWindow extends JFrame implements Runnable
{
	public final RenderPanel panel;
	
	public ControllerWindow(InputHandler inputHandler)
	{
		// Core
		setTitle("Tetruino Controller");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Sizing
		setMinimumSize(new Dimension(640, 480));
		setSize(1280, 720);
		setResizable(true);
		
		// Add render panel
		panel = new RenderPanel(inputHandler);
		add(panel);
		pack();
		
		// Listener
		addKeyListener(inputHandler);
		
		// Init
		setVisible(true);
	}
	
	public void run() { panel.run(); }
}
