
import java.applet.*;


import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("removal")
public class Game extends Applet implements Runnable, KeyListener,MouseListener,MouseMotionListener{
	Image off_screen;
	Graphics off_screen_g ;
	Graphics g ; 
	// THe main method is already defined in applet but paint method is ran automatically
	
	Thread t;
	Rect[] rectangles = new Rect[10];
	Rect r1 = new Rect(10,10,100,100);
	Rect r2 = new Rect(50,50,100,100);
	Rect r3 = new Rect(10,50,100,100);
	
	Rect dragRect = new Rect(0,0,0,0);
	boolean pressingUP = false;
	boolean pressingDN = false;
	boolean pressingLT = false;
	boolean pressingRT = false;
	
	int mx;
	int my;
	
	
	TitleScreen titleScreen = new TitleScreen("title.PNG", 800, 900);
	
	static final int TITLE = 0;
	static final int PLAYING = 1;
	static final int PAUSED = 2;

	int gameState = TITLE;
	
	Image buttonImage = Toolkit.getDefaultToolkit().getImage("play.PNG");;
	Rect playButton;
	
	

	
	// the operating sytem calls update which then calls paint
	public void update(Graphics g) {
		off_screen_g.clearRect(0, 0, 1920, 1080); // clear the off sceen image
		paint(off_screen_g); // paint
		g.drawImage(off_screen,0,0,null);
	}
	public void paint(Graphics g) {
		// (0,0) is the top left of the screen
		// Y increases as you move down and X increases as you go right
		
		this.g = g;
			
		// In Game.paint():
		if (gameState == TITLE) {
		    titleScreen.draw(g, getWidth(), getHeight());  // scale to current screen size
		    drawPlayButton(g); // draws the play button
		}
		else if (this.gameState == PAUSED) {
			titleScreen.draw(g, getWidth(), getHeight());  // scale to current screen size
		}
		else {
			if (r1.overlaps(r2) || r1.overlaps(r3)){
				g.setColor(Color.red);
			}
		
		r1.draw(g);
		g.setColor(Color.black);
		r2.draw(g);
		r3.draw(g);
		dragRect.draw(g);
		
		}
	}
	
	
	public void run() {
		//This is the game loop
		while(true) {
			
			if(this.gameState == PLAYING) {
			//this code will execute 60 times a second
			if (pressingUP)r1.moveBy(0, -3);
			if (pressingDN) r1.moveBy(0, 3);
			if (pressingLT) r1.moveBy(-3, 0);
			if (pressingRT) r1.moveBy(3, 0);
			//r1.moveBy(1,1);//we must find out what the user wants to do (get user input). 
			//r2.moveBy(5,0);
			}
			
			//r3.moveBy(0, -3);
			//find how the objects in the game will move
			// collision detection
			repaint();//tells OS window needs to be painted (this is the lowest priority item)
			
			
			try {
				Thread.sleep(16);// sleeps for a 60th of a second
			} catch (Exception e) {} 
		}
	}
	

	public void init(){
		this.requestFocus();
		this.addKeyListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		t = new Thread(this);
		t.start();
		
		off_screen = this.createImage(1920, 1080);
		off_screen_g = off_screen.getGraphics();
		
//		buttonImage = Toolkit.getDefaultToolkit().getImage("play.PNG");
	}
	
	
	public void keyPressed(KeyEvent e) {
		// Moving objects here will by cause sync issues as it would bypass the gameloop
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_UP) pressingUP = true;
		if (code == KeyEvent.VK_DOWN)pressingDN = true;
		if (code == KeyEvent.VK_LEFT)pressingLT = true;
		if (code == KeyEvent.VK_RIGHT) pressingRT = true;
		
		
		//to pause or resume game using space button for pause
		  if (code == KeyEvent.VK_SPACE) {
		        if (gameState == PLAYING) {
		            gameState = PAUSED;
		        }
		        
		        else if (gameState == PAUSED) {
		            gameState = PLAYING;
		        }
		    }
		
	}
	
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_UP) pressingUP = false;
		if (code == KeyEvent.VK_DOWN)pressingDN = false;
		if (code == KeyEvent.VK_LEFT)pressingLT = false;
		if (code == KeyEvent.VK_RIGHT) pressingRT = false;
	}
	public void keyTyped(KeyEvent e) {}


	@Override
	public void mouseClicked(MouseEvent e) {
		
		// if you move the mouse after the press it wont count as a click//
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		
		//mouse entered window
	}


	@Override
	public void mouseExited(MouseEvent e) {
	
		// mouse exited window
	}


	@Override
	public void mousePressed(MouseEvent e) {
		
		this.mx = e.getX();
		this.my = e.getY();


		    if (gameState == 0 && playButton.contains(mx, my)) {
		        gameState = PLAYING; //starts the game
		    }
	System.out.println("X: "+ Integer.toString(e.getX())+ " Y: " + Integer.toString(e.getY()));	
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		
		dragRect = new Rect(0,0,0,0);
	}


	@Override
	public void mouseDragged(MouseEvent e) {

		System.out.println("mouse Dragged");
		int nx = e.getX();
		int ny = e.getY();
		
		int w = nx-mx;
		int h= ny-my;
		 
		dragRect = new Rect(mx,my,w,h);
		
	
	}


	@Override
	public void mouseMoved(MouseEvent e) {
	
		
	}
	
	private void drawPlayButton(Graphics g) {

	    int buttonWidth = (int)(getWidth() * 0.3);
	    int buttonHeight = (int)(getHeight() * 0.15);

	    int x = (getWidth() - buttonWidth) / 2 ;
	    int y = (getHeight() - buttonHeight) / 2 + 120;

	    playButton = new Rect(x, y, buttonWidth, buttonHeight);

	    g.drawImage(buttonImage, x, y, buttonWidth, buttonHeight, null);
	}
	

}