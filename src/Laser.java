import java.awt.Color;
import java.awt.Graphics;

public class Laser extends Rect {

    double speed;
    
    int startX;
    int startY;
    
    int orientation;
    
    static int HORIZONTAL = 0;
    static int VERTICAL =1;

    public Laser(int x, int y, int width, int height, double speed, int orientation) {
    	
        super(x, y, width, height);

        this.speed = speed;
        this.startX = x;
        this.startY = y;
        this.orientation = orientation;
    }

    public void update() {

    	
    	if(orientation == HORIZONTAL) {

        this.x -= speed;
        
        if (x + width < Camera.x) {
            reset();
        }
    	}
    	else {
    		this.x += speed;
    		 if (x + width > Game.player.x + Game.player.width ) {
    	            reset();
    	        }
    	}
    }
    
    public void reset() {
    	
        this.x = startX;
        this.y = startY;
    }
    

    public boolean hits(Player p) {
    	
        return this.overlaps(p);
    }

    @Override
    public void draw(Graphics g) {

        g.setColor(Color.RED);

        g.fillRect(x - Camera.x, y - Camera.y, width, height);
    }
}