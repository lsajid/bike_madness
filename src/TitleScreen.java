
import java.awt.*;
public class TitleScreen {
	
	Image image;
	
	int w;
	int h;
	
	public TitleScreen(String fileName, int w, int h) {
		this.image = Toolkit.getDefaultToolkit().getImage(fileName);
		this.w = w;
		this.h = h;
		
	}
	
	public void draw(Graphics g, int w, int h) {
	    g.drawImage(image, 0, 0, w, h, null);
	}
	
}
