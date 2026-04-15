import java.awt.*;
import java.awt.geom.AffineTransform;

public class Player extends Rect{
	
	Image img;
	Double max_speed = 10.0;
	Double speed = 0.0;
	Double acceleration = (double) (max_speed/60);
	Double brakeForce = (double) (max_speed/30);
	Double resistance = .05;
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
	}
	public void draw(Graphics g) {
		
		g.drawImage(animations[action].nextImage(),x,y,width,height,null);
		
	
		super.draw(g);
	}
	
}
