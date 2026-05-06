import java.awt.*;
import java.awt.geom.AffineTransform;

public class Player extends Rect{
	
	Image img;
	
	Double max_speed = 10.0;
	Double speed = 0.0;
	Double acceleration = (double) (max_speed/60);
	Double brakeForce = (double) (max_speed/30);
	Double resistance = .05;
	Boolean grounded= true;
	
	Animation[] animations;

	Animation wheelie = new Animation("image/wheelie.png",7,120,80,8,false);
	Animation idle = new Animation("image/motorcycle.png",1,120,80,8);
	Animation wheelie_end = new Animation("image/end_wheelie.png",7,120,80,8,false);
	
	
	int action = 0;
	
	int IDLE = 0 ; 
	int WHEELIE = 1;
	int WHEELIE_END = 2;
	
	public Player(int x, int y) {
		super(x, y, 120, 80);
		this.img = Toolkit.getDefaultToolkit().getImage("image/motorcycle.png");
		this.animations= new Animation[] {idle,wheelie,wheelie_end};
	}
	
	public void moveLeft() {
		if (speed > -max_speed) speed -= brakeForce;
	}
	
	public void moveRight() {
		if (speed < max_speed) speed += acceleration;
		
	}
	
	public void wheelie() {
		this.action = WHEELIE;
		wheelie_end.reset();
	}
	
	public void  wheelieEnd() {
		this.action = WHEELIE_END;
		wheelie.reset();
	}
	
	public void jump() {}
	
	public void update() {
		if (speed < 0)speed += resistance;
		else if (speed > 0) speed -= resistance;
		
		this.x += speed;
		
		if(!grounded) {
			this.y+=1;
		}
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
	
	public void draw(Graphics g) {
		
		g.drawImage(animations[action].nextImage(),x,y,width,height,null);
		
		super.draw(g);
	}
}
