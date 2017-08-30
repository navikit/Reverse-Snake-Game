import java.awt.*;
import javax.swing.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.io.*;

/**
 * An instance of this class is an animation
 * to which you can add moving objects (sprites).
 * The animation is divided into frames.
 * You set up each frame by adding, removing, or positioning sprites,
 * then call frameFinished to display the completed frame onscreen.
 */
public class Animation extends JFrame
{
	private static class Canvas extends JPanel
	{
		int xsize, ysize;
		Image bgimage;
		ArrayList<Sprite> sprites;
		
		public Canvas(int xsize, int ysize)
		{
			this.xsize = xsize;
			this.ysize = ysize;
			this.setPreferredSize(new Dimension(xsize, ysize));
			this.bgimage = null;
			this.sprites = null;
		}
		
		public void saveFrame(Image bgimage, ArrayList<Sprite> sprites)
		{
			synchronized (this)
			{
				this.bgimage = bgimage;
				this.sprites = new ArrayList<Sprite>(sprites.size());
				for (Sprite sprite : sprites)
				{
					this.sprites.add(new Sprite(sprite));
				}
			}
		}
		
		public void paint(Graphics g)
		{
			Image spriteimage;
			int spritexpos, spriteypos, spritexsize, spriteysize;
			
			super.paint(g);
			
			synchronized (this)
			{
				if (this.bgimage != null)
				{
					g.drawImage(this.bgimage, 0, 0, xsize, ysize, null);
				}
				
				if (this.sprites != null)
				{
					for (Sprite sprite : this.sprites)
					{
						spriteimage = sprite.getImage();
						spritexpos = sprite.getXposition();
						spriteypos = sprite.getYposition();
						spritexsize = sprite.getXsize();
						spriteysize = sprite.getYsize();
						
						g.drawImage(
							spriteimage,
							spritexpos,
							this.ysize - spriteypos - spriteysize,
							spritexsize,
							spriteysize,
							null);
					}
				}
			}
		}
	}

	private Canvas canvas;
	private Image bgimage;
	private ArrayList<Sprite> sprites;
	private long frameDelay;

	/**
	 * Create an animation window of the given size.
	 * @param xsize Horizontal size, in pixels.
	 * @param ysize Vertical size, in pixels.
	 */
	public Animation(int xsize, int ysize)
	{
		super("Animation");

		this.canvas = new Canvas(xsize, ysize);
		//this.canvas.setBackground(Color.WHITE);
		this.add(this.canvas);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.bgimage = null;
		this.sprites = new ArrayList<Sprite>();
		this.frameDelay = 1000L;
	}
	
	/**
	 * Set the frame rate (speed) of the animation.
	 * Higher numbers result in faster animations.
	 * @param framesPerSecond Frame rate, in frames per second (fps).
	 */
	public void setFrameRate(int framesPerSecond)
	{
		this.frameDelay = 1000L / framesPerSecond;
	}
	
	/**
	 * Set the image drawn in the background of the animation.
	 * GIF, PNG, and JPEG formats are supported.
	 * @param filename Name of file containing image data.
	 */
	public void setBackgroundImage(String filename)
	{
		try
		{
			this.bgimage = ImageIO.read(new File(filename));
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 * Add a sprite to the animation.
	 * The sprite will appear in the next frame.
	 * Sprites are drawn in the order in which they are added,
	 * so newer sprites will appear in front of older ones.
	 * @param sprite Sprite object to add to animation.
	 */
	public void addSprite(Sprite sprite)
	{
		if (sprite == null)
		{
			throw new NullPointerException();
		}
		
		if ( ! this.sprites.contains(sprite) )
		{
			this.sprites.add(sprite);
		}
	}
	
	/**
	 * Remove a sprite from the animation.
	 * The sprite will disappear in the next frame.
	 * @param sprite Sprite object to remove from animation.
	 */
	public void removeSprite(Sprite sprite)
	{
		this.sprites.remove(sprite);
	}
	
	/**
	 * Indicate that the arrangement of the next frame is complete.
	 * Any changes made to the sprites or background will be saved into an animation frame when this method is called.
	 */
	public void frameFinished()
	{
		this.canvas.saveFrame(this.bgimage, this.sprites);
		this.setVisible(true);
		this.repaint();
		
		try
		{
			Thread.sleep(this.frameDelay);
		}
		catch (InterruptedException e)
		{
			// will never happen
		}
	}
}
