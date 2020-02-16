package tetruino.display;

import tetruino.InputHandler;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.VolatileImage;

public class RenderPanel extends JPanel implements Runnable
{
	// Constants
	private static final String UpString = "Up\n(W)";
	private static final String DownString = "Down\n(S)";
	private static final String LeftString = "Left\n(A)";
	private static final String RightString = "Right\n(D)";
	
	protected static final Color Background = new Color(10, 10, 10);
	protected static final Color BackgroundUnPressed = new Color(119, 119, 119);
	protected static final Color BackgroundPressed = new Color(151, 0, 0);
	
	protected static final Font TextFont = new Font("Open Sans", Font.BOLD, 36);
	protected static final Color TextUnPressed = Color.BLACK;
	protected static final Color TextPressed = Color.WHITE;
	
	// Objects
	protected InputHandler inputHandler;
	
	public RenderPanel(@NotNull InputHandler inputHandler)
	{
		this.inputHandler = inputHandler;
	}
	
	public void run()
	{
		// Create initial buffer
		VolatileImage drawBuffer = createVolatileImage(getWidth(), getHeight());
		
		//noinspection InfiniteLoopStatement
		while(true)
		{
			/*
				This rendering method is real nasty.
				I wouldn't suggest messing with it unless you 100% absolutely have to.
				
				If you're going in there, good luck.
			 */
			do
			{
				// Positions
				final int width = getWidth();
				final int height = getHeight();
				
				final int halfWidth = width / 2;
				final int halfHeight = height / 2;
				
				final int thirdWidth = width / 3;
				
				
				// Validate the buffer
				if
				(
					width != drawBuffer.getWidth()
				||	height != drawBuffer.getHeight()
				||	drawBuffer.validate(getGraphicsConfiguration()) == VolatileImage.IMAGE_INCOMPATIBLE
				) drawBuffer = createVolatileImage(width, height);
				
				// Draw to offscreen buffer
				Graphics2D g2d = drawBuffer.createGraphics();
				g2d.setFont(TextFont);
				
				
				// Background
				g2d.setColor(Background);
				g2d.fillRect(0, 0, thirdWidth, halfHeight);
				g2d.fillRect(thirdWidth * 2, 0, thirdWidth, halfHeight);
				
				
				// Up
				setBackgroundColour(g2d, inputHandler.up());
				g2d.fillRect(thirdWidth, 0, thirdWidth, halfHeight);
				
				final Rectangle2D UpStringBounds = g2d.getFontMetrics().getStringBounds(UpString, g2d);
				setTextColour(g2d, inputHandler.up());
				g2d.drawString
				(
					UpString,
					halfWidth - ((int) UpStringBounds.getWidth() / 2),
					halfHeight / 2
				);
				
				
				// Left
				final int bottomTextHeight = halfHeight + (halfHeight / 2);
				
				setBackgroundColour(g2d, inputHandler.left());
				g2d.fillRect(0, halfHeight, thirdWidth, halfHeight);
				
				final Rectangle2D LeftStringBounds = g2d.getFontMetrics().getStringBounds(LeftString, g2d);
				setTextColour(g2d, inputHandler.left());
				g2d.drawString
				(
					LeftString,
					(thirdWidth / 2) - ((int) LeftStringBounds.getWidth() / 2),
					bottomTextHeight
				);
				
				
				// Down
				setBackgroundColour(g2d, inputHandler.down());
				g2d.fillRect(thirdWidth, halfHeight, thirdWidth, halfHeight);
				
				final Rectangle2D DownStringBounds = g2d.getFontMetrics().getStringBounds(DownString, g2d);
				setTextColour(g2d, inputHandler.down());
				g2d.drawString
				(
					DownString,
					halfWidth - ((int) DownStringBounds.getWidth() / 2),
					bottomTextHeight
				);
				
				
				// Right
				setBackgroundColour(g2d, inputHandler.right());
				g2d.fillRect(thirdWidth * 2, halfHeight, thirdWidth, halfHeight);
				
				final Rectangle2D RightStringBounds = g2d.getFontMetrics().getStringBounds(RightString, g2d);
				setTextColour(g2d, inputHandler.right());
				g2d.drawString
				(
					RightString,
					(thirdWidth * 2 + thirdWidth / 2) - ((int) RightStringBounds.getWidth() / 2),
					bottomTextHeight
				);
				
				
				// Draw offscreen to actual screen
				g2d.dispose();
				g2d = (Graphics2D) getGraphics();
				g2d.drawImage(drawBuffer, 0, 0, null);
				g2d.dispose();
			}
			while(drawBuffer.contentsLost());
		}
	}
	
	private static void setBackgroundColour(@NotNull Graphics2D g2d, boolean pressed)
	{
		g2d.setColor(pressed ? BackgroundPressed : BackgroundUnPressed);
	}
	
	private static void setTextColour(@NotNull Graphics2D g2d, boolean pressed)
	{
		g2d.setColor(pressed ? TextPressed : TextUnPressed);
	}
}
