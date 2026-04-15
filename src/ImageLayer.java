import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class ImageLayer {
	Image image;
	int x;
	int y;
	
	int w;
	int h;
	
	public ImageLayer(String fileName, int x, int y, int w, int h) {
		image = Toolkit.getDefaultToolkit().getImage(fileName);
		
		this.x = x;
		this.y = y;
		
		this.w = w;
		this.h = h;
	}
	
	public void draw(Graphics g) 
	{
		System.out.println("draw method for image layer");
		for(int i = 0; i < 20; i++) 
		{
			g.drawImage(image, x + w*i - (Camera.x), y, w, h, null);			
		}
	}
}
