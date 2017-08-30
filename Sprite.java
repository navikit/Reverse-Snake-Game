import java.awt.Image;
import javax.imageio.ImageIO;
import java.io.*;

/**
 * An instance of this class represents a movable image in the animation.
 */
public class Sprite
{
	private Image image;
	private int xsize, ysize;
	private int xposition, yposition;
	
	/**
	 * Create a sprite whose image is loaded from the file with the given name.
	 * GIF, PNG, and JPEG formats are supported.
	 * @param filename Name of file containing image data.
	 */
	public Sprite(String filename)
	{
		this.setImage(filename);
		this.xsize = this.image.getWidth(null);
		this.ysize = this.image.getHeight(null);
		this.xposition = 0;
		this.yposition = 0;
	}
	
	/**
	 * Create a copy of the given sprite.
	 * @param original Sprite to make a copy of.
	 */
	public Sprite(Sprite original)
	{
		this.image = original.image;
		this.xsize = original.xsize;
		this.ysize = original.ysize;
		this.xposition = original.xposition;
		this.yposition = original.yposition;
	}
	
	/**
	 * Obtain the current horizontal (x-direction) position of this sprite.
	 * (0, 0) are the coordinates of the lower left corner of the window.
	 * @return Horizontal position, in pixels.
	 */
	public int getXposition()
	{
		return this.xposition;
	}
	
	/**
	 * Obtain the current vertical (y-direction) position of this sprite.
	 * (0, 0) are the coordinates of the lower left corner of the window.
	 * @return Vertical position, in pixels.
	 */
	public int getYposition()
	{
		return this.yposition;
	}
	
	/**
	 * Change the position of this sprite.
	 * (0, 0) are the coordinates of the lower left corner of the window.
	 * Coordinates outside the window (negative or greater than its size) are allowed.
	 * @param xsize New horizontal position, in pixels.
	 * @param ysize New vertical position, in pixels.
	 */
	public void setPosition(int xposition, int yposition)
	{
		this.xposition = xposition;
		this.yposition = yposition;
	}

	/**
	 * Obtain the current horizontal (x-direction) size of this sprite.
	 * @return Horizontal size, in pixels.
	 */
	public int getXsize()
	{
		return this.xsize;
	}
	
	/**
	 * Obtain the current vertical (y-direction) size of this sprite.
	 * @return Vertical size, in pixels.
	 */
	public int getYsize()
	{
		return this.ysize;
	}
	
	/**
	 * Change the size of this sprite.
	 * The minimum size is one pixel in each dimension.
	 * @param xsize New horizontal size, in pixels.
	 * @param ysize New vertical size, in pixels.
	 */
	public void setSize(int xsize, int ysize)
	{
		this.xsize = Math.max(1, xsize);
		this.ysize = Math.max(1, ysize);
	}
	
	/**
	 * Change the image used by this sprite.
	 * Image is loaded from the file with the given name.
	 * GIF, PNG, and JPEG formats are supported.
	 * @param filename Name of file containing image data.
	 */
	public void setImage(String filename)
	{
		try
		{
			this.image = ImageIO.read(new File(filename));
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////
	// for use by Animation class only
	/////////////////////////////////////////////////////////////////////////////
	
	Image getImage()
	{
		return this.image;
	}
}
