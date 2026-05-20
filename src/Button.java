import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Button extends Rect{
	Image image;
	boolean hover =false;
 public Button(String imagePath,int x, int y, int width, int height) {
		super(x, y, width, height);
		this.image = Toolkit.getDefaultToolkit().getImage(imagePath);
		
	}
 
 @Override
 public void draw(Graphics g) {
	 g.drawImage(image,x,y,width,height,null);
	 if(hover) {
	 g.drawRect(x-10,y-10,width+10,height+10);
	 }
 }
}
