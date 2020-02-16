package tetruino;

import tetruino.display.ControllerWindow;

public class TetruinoController extends Thread
{
	public final ControllerWindow window;
	public final InputHandler inputHandler;
	
	public TetruinoController()
	{
		inputHandler = new InputHandler();
		window = new ControllerWindow(inputHandler);
	}
	
	@Override
	public void run() { window.run(); }
	
	public static void main(String[] args) throws InterruptedException
	{
		TetruinoController instance = new TetruinoController();
		instance.start();
		instance.join();
	}
}
