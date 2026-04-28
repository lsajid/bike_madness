
import java.awt.Graphics;

public class Rect implements Drawable {
    int x;
    int y;
    int width;
    int height;
    
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
		
		if(penetration < 10)
			
			r.x -= penetration + 1;
	}
	
	public void pushRight(Rect r)
	{
		double penetration = x + width - r.x;
		
		if(penetration < 10)
			
			r.x += penetration + 1;
	}
	
	public void pushes(Rect r)
	{
		pushRight(r);
		pushLeft(r);		
	}
	
	
	public void rideRamp(double lineX1, double lineY1, double lineX2, double lineY2) {
	// the parameters are the line coordinates
    
    double centerX = x + width / 2.0;// Calculates the center of the soldier

    //checks if the soldier is  above the ramp
    if (centerX >= Math.min(lineX1, lineX2) && centerX <= Math.max(lineX1, lineX2)) {
        
        double slope = (lineY2 - lineY1) / (lineX2 - lineX1);
        double rampY = slope * (centerX - lineX1) + lineY1;

        // Checks if we are currently touching or have passed through the ramp
        
       
        if (y + height >= rampY - 2 && height <= rampY + 2) {
            
            
            this.y = (int) (rampY - height);
            
            // Stops falling velocity of the sodier so it stops on the ramp
			// this.vx = 0;
			// this.vy = 0;
            
           
        }
    }
}

	public int getX() {return this.x;}

	public int getY() {return this.y;}
	
}
