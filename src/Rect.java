
import java.awt.Color;
import java.awt.Graphics;

public class Rect implements Drawable {
    int x;
    int y;
    int width;
    int height;
    
    boolean fill =false;
    Color color = Color.BLACK;
    double oldY;
    
    double vx;
    double vy;

    public Rect(int x, int y, int width, int height) {
        this.oldY = y;
    	this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;
    }

    public Rect(int [] params) {
    	this.x = params[0];
        this.y = params[1];

        this.oldY = y;
        
        this.width = params[2];
        this.height = params[3];
    }
    public boolean overlaps(Rect r) {
        return (x <= r.x + r.width) &&
                (y <= r.y + r.height) &&

                (r.x <= x + width) &&
                (r.y <= y + height);
    }

    // Quicker Draw Method

    public void draw(Graphics g) {
    	g.setColor(color);
    	if (this.fill) {g.fillRect(x-Camera.x, y-Camera.y, width, height);}
    	else g.drawRect(x-Camera.x, y-Camera.y, width, height);
    }

    public void drawAbsolute(Graphics g) {
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
	
	public void pushLeft(Rect r)
	{
		double penetration = r.x + r.width - x;
		
		if(penetration < r.width/2)
			
			r.x -= penetration + 1;
	}
	
	public void pushRight(Rect r)
	{
		double penetration = x + width - r.x;
		
		if(penetration < r.width/2)
			
			r.x += penetration + 1;
	}
	
	public void pushUp(Rect r)
	{
		double penetration = r.y + r.height - y ;
		
		if(penetration < r.height/2)
			
			r.y -= penetration + 1;
	}
	
	public void pushDown(Rect r)
	{
		double penetration = y + height - r.y;
		
		if(penetration < r.height/2)
			
			r.y += penetration + 1;
	}
	
	
	public void pushes(Rect r)
	{
		pushDown(r);
		pushUp(r);
		pushRight(r);
		pushLeft(r);		
	}

	public boolean isOnTop(Rect other) {
	    return Math.abs((this.y + this.height) - other.y) <= 1 &&
	           this.x < other.x + other.width &&
	           this.x + this.width > other.x;
	}
	
	public int getX() {return this.x;}

	public int getY() {return this.y;}
	
}
