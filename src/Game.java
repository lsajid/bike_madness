
import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

@SuppressWarnings("removal")
public class Game extends Applet implements Runnable, KeyListener,MouseListener,MouseMotionListener{
	Image off_screen;
	Graphics off_screen_g ;
	Graphics g ; 
	// THe main method is already defined in applet but paint method is ran automatically
	
	Thread t;
	
	Rect dragRect = new Rect(0,0,0,0);
	
	static int Width = 1920;
	static int Height=  1080;
	
	LevelManager levelManager = new LevelManager();
	public static Level currentLevel;
	
	boolean pressingUP = false;
	boolean pressingDN = false;
	boolean pressingLT = false;
	boolean pressingRT = false;
	boolean pressingN = false;
	boolean pressingM = false;
	
	int mx;
	int my;
	
	static int physics_g = 1;
	
	static Player player = new Player(0,0);
	
	
	TitleScreen titleScreen = new TitleScreen("image/title.PNG", 800, 900);
	ImageLayer sky = new ImageLayer("image/title.PNG", 0,0, 800, 900);
	
	
	
	static final int TITLE = 0;
	static final int PLAYING = 1;
	static final int PAUSED = 2;

	int gameState = TITLE;
	
	Image buttonImage = Toolkit.getDefaultToolkit().getImage("image/play.PNG");;
	Rect playButton;
	
	
	// the operating system calls update which then calls paint
	public void update(Graphics g) {
		Width = getWidth();
		Height = getHeight();
		
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
//			titleScreen.draw(g, getWidth(), getHeight());  // scale to current screen size
		}
		else {
			
 
			currentLevel.draw(g);
			player.draw(g);
			dragRect.drawAbsolute(g);
			
			
		}
	}
	
	
	public void run() {
		//This is the game loop
		
		while(true) {
			//this code will execute 60 times a second
			
			if(this.gameState == PLAYING) {
			
			//find how the objects in the game will move
			if (pressingUP)	player.wheelie();
			if (pressingDN) player.wheelieEnd();
			if (pressingLT) player.moveLeft();
			if (pressingRT) player.moveRight();
			
			Camera.x = (int) (player.x + player.width / 2 - Game.Width * 0.20);
			Camera.y = player.y + player.height / 2 - Game.Height / 2;
			
			// for development purposes can skip levels
			if (pressingM) levelManager.goNextLevel();
			if(pressingN) levelManager.goBackLevel();
			
			player.update();
			
			// collision detection
			
			
			
			//check if the player is on any of the ground platforms
			boolean playerGrounded = false;
			
			
			for(int i =0; i<currentLevel.ground.size();i++ ) {
				Rect curr = currentLevel.ground.get(i);
				
				if(player.overlaps(curr)) {
					curr.pushes(player);
				}
				if(player.isOnTop(curr)) {
					playerGrounded = true;
					player.currPlatform = curr;
				}
				
			}
			
			player.grounded = playerGrounded;
			
			
			for (int i=0;i< currentLevel.ramps.size();i++) {
				Line ramp = currentLevel.ramps.get(i);
				player.rideRamp(ramp.x1, ramp.y1, ramp.x2, ramp.y2);
			}	

	
			if(player.isOnTop(currentLevel.finishLine)) {
				levelManager.goNextLevel();
			}
			}
			
			
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
		
		levelManager.loadLevels();
		currentLevel = levelManager.getCurrent();
//		buttonImage = Toolkit.getDefaultToolkit().getImage("play.PNG");
	}
	
	
	public void keyPressed(KeyEvent e) {
		// Moving objects here will by cause sync issues as it would bypass the gameloop
		int code = e.getKeyCode();
		
		if (code == KeyEvent.VK_UP) pressingUP = true;
		if (code == KeyEvent.VK_DOWN)pressingDN = true;
		if (code == KeyEvent.VK_LEFT)pressingLT = true;
		if (code == KeyEvent.VK_RIGHT) pressingRT = true;
		if (code == KeyEvent.VK_N) pressingN = true;
		if (code == KeyEvent.VK_M) pressingM = true;
		
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
		if (code == KeyEvent.VK_RIGHT) pressingRT = false;
		if (code == KeyEvent.VK_N) pressingN = false;
		if (code == KeyEvent.VK_M) pressingM = false;
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
	        gameState = PLAYING;
	    }
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		
		dragRect = new Rect(0,0,0,0);
	}


	@Override
	public void mouseDragged(MouseEvent e) {

		
		int nx = e.getX();
		int ny = e.getY();
		
		int w = nx-mx;
		int h= ny-my;
		 
		dragRect = new Rect(mx,my,w,h);
		
		System.out.println("Rectangle " + (dragRect.x+Camera.x) + " " + (dragRect.y+Camera.y) + " " + dragRect.width  + " " + dragRect.height  );
	
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
