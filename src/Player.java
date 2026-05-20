import java.awt.*;
import java.awt.geom.AffineTransform;

public class Player extends Rect{
	
	Image img;
	double a;
	Double max_speed = 60.0;
	Double speed = 0.0;

	Double vy=0.0;
	Double vx=0.0;
	
	Double acceleration = (double) (max_speed/60);
	Double brakeForce = (double) (max_speed/30);
	
	Double resistance = .05;
	Double gravity = .25; 
	Boolean grounded= true;
	Boolean onRamp = false;
	
	Animation[] animations;

	Animation wheelie = new Animation("image/wheelie.png",7,120,80,8,false);
	Animation idle = new Animation("image/motorcycle.png",1,120,80,8);
	Animation wheelie_end = new Animation("image/end_wheelie.png",7,120,80,8,false);
	
	
	int action = 0;
	
	int IDLE = 0 ; 
	int WHEELIE = 1;
	int WHEELIE_END = 2;
	
	Rect currPlatform;
	
	public Player(int x, int y) {
		super(x, y, 120, 80);
		this.img = Toolkit.getDefaultToolkit().getImage("image/motorcycle.png");
		this.animations= new Animation[] {idle,wheelie,wheelie_end};
	}
	
	public void moveLeft() {
		if (vx > -max_speed) vx -= brakeForce;
		
	}
	
	public void moveRight() {
		if (vx < max_speed) vx += acceleration;
		
	}

public void forceLeft() {
	this.x -= 10;
	
}
	
public void forceRight() {
	this.x += 10;
	
}
public void forceUp() {
	this.y -= 10;
	
}
public void forceDown() {
	this.y += 10;
	
}

	public void wheelie() {
		if(!grounded) {
		this.a += .1;
		}
	}
	
	public void  wheelieEnd() {
		if(!grounded) {
		this.a-=.1;}
	}
	
	public void jump() {}
	
	public void update() {
		this.onRamp = false;
		
		if (vx < 0)vx += resistance;
		else if (vx > 0) vx -= resistance;
		
		if(!grounded) {
			this.vy += (double) Game.physics_g;
		}
		else {
			 vy = 0.0; 
			 this.a = Math.max(0, a-.05);
		}
		
		this.x += vx;
		this.y+=vy;
		
	}
	
	
	public void rideRamp(double lineX1, double lineY1, double lineX2, double lineY2) {
		    double centerX = x + width / 2.0;

		    if (centerX >= Math.min(lineX1, lineX2) && centerX <= Math.max(lineX1, lineX2)) {
		        double slope = (lineY2 - lineY1) / (lineX2 - lineX1);
		        double rampY = slope * (centerX - lineX1) + lineY1;

		        // FIX: was "height <= rampY + 2" — should be y + height
		        if (y + height >= rampY - 4) {
		            // Calculate ramp angle from horizontal
		            double dx = lineX2 - lineX1;
		            double dy = lineY2 - lineY1;
		            double targetAngle = Math.atan2(dy, dx); // angle of ramp surface

		            // Smooth angle interpolation instead of snapping
		            this.a += (targetAngle - this.a) * 0.25;

		            // FIX: only redirect velocity ONCE when landing on ramp,
		            // not every frame (which kills speed). Instead, project
		            // velocity onto the ramp direction.
		            double speed = Math.sqrt(vx * vx + vy * vy);
		            double rampDirX = Math.cos(targetAngle);
		            double rampDirY = Math.sin(targetAngle);

		            // Dot product: how much velocity is already along the ramp
		            double dot = vx * rampDirX + vy * rampDirY;

		            // Blend current velocity toward ramp-aligned velocity smoothly
		            double blend = 0.3; // higher = snappier, lower = floatier
		            vx += (dot * rampDirX - vx) * blend;
		            vy += (dot * rampDirY - vy) * blend;

		            // Surface lock: keep player sitting on ramp surface
		            this.y = (int)(rampY - height);
		            this.grounded = true;
		            this.onRamp = true;
		        }
		    }
		}


public void reset() {
	this.a =0.0;
	this.vy = 0.0;
	this.vx = 0.0;
	this.currPlatform = null;
}
public void draw(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;

    // Save the original transform
    AffineTransform oldTransform = g2d.getTransform();

    // Calculate center of player in screen space
    int screenX = x - Camera.x;
    int screenY = y - Camera.y;
    int cx = screenX + width / 2;
    int cy = screenY + height / 2;

    // Rotate around the center of the player
    g2d.rotate(this.a, cx, cy);

    // Draw the animation frame as normal
    g2d.drawImage(animations[action].nextImage(), screenX, screenY, width, height, null);

    // Restore transform so nothing else is affected
    g2d.setTransform(oldTransform);

    super.draw(g);
}
}
