package src;
import java.awt.Graphics;
public class Rect {
    int x;
    int y;
    int width;
    int height;

    public Rect(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;
    }


    public boolean overlaps(Rect r) {
        return (x <= r.x + r.width) &&
                (y <= r.y + r.height) &&

                (r.x <= x + width) &&
                (r.y <= y + height);
    }

    // Quicker Draw Method

    public void draw(Graphics g) {
        g.drawRect(x, y, width, height);
    }

    public void moveBy(int dx, int dy) {
        x += dx;
        y += dy;
    }
    
	public boolean contains(int mx, int my) {
		return (mx > x)      && 
			   (my > y)      && 
			   (mx < x + this.width)  && 
			   (my < y + this.height);
	}
}
