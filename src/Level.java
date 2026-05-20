import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Utilities.LevelLoader;

public class Level {
	
	String levelName;
	Image img ;
	int xStart;
	int yStart;
	int minY;
	
	double seconds =0.0;
	int levelNumber;
	
	int Rectangle = 0;
	int Line = 1;
	int Ring =2;
	
	ArrayList<Drawable> levelObjects = new ArrayList <Drawable>();
	ArrayList<Line> ramps = new ArrayList <Line>();
	ArrayList<Rect> ground = new ArrayList<Rect>();
	
	static Color[] levelColor = new Color[] {new Color(105,190,255),new Color(13,13,51),new Color(105,190,255)};
	static Image Tutorial = Toolkit.getDefaultToolkit().getImage("image/Tutorial.png");
	
	Rect finishLine ;
	
	Laser laser;
	
	
	public Level(String levelName) {
		this.levelName = levelName;
		this.img = Toolkit.getDefaultToolkit().getImage("levels/" + levelName +"/"+ levelName + ".png");
		//laser
//		if(this.levelName.equals("level3")) {
//
//		    laser = new Laser(Camera.x-200, Camera.y, 50, 1000, 12,Laser.VERTICAL);
//		
//		}//laser
		load();
	}
	
	
	public void load(){

		File file = new File("levels/" + levelName + "/Rectangles.txt");
		
		LevelLoader levelLoader = new LevelLoader();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            
            // readLine() returns null when it reaches the end of the file
            while ((line = br.readLine()) != null) {
            	Object [] objInfo = levelLoader.getObjInfo(line);
            	
            	int objType = (int) objInfo[0];
            	int [] objParams = (int[]) objInfo[1];
            	
            	// 0 Means rectangle
            	if (objType == Rectangle) {
            		Rect r = new Rect(objParams);
            		r.fill = true;
            		levelObjects.add(r);
            		ground.add(r);
            		if(ground.size() == 1) {this.minY = r.y + r.height;}
            		else {
            			this.minY = Math.max(this.minY, r.y + r.height);
            		}
            		
            	
            	}
            	
            	else if (objType == Line) {
            		Line l = new Line(objParams);
            		levelObjects.add(l);
            		ramps.add(l);
            	}
            	else if (objType== Ring) {
            		Ring r = new Ring(objParams);
//            		levelObjects.add(r);
            		
            	}
       
            }
            // the player spawn in at the location of the first level Object
        	xStart = levelObjects.get(0).getX();
        	yStart = levelObjects.get(0).getY()-Game.player.height;
        	//The final object in the rect.txt must be a rectangle indicating the end of level
        	
        	this.finishLine= (Rect) levelObjects.get(levelObjects.size()-1);
        	this.finishLine.color = Color.RED;
        	
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
	}
	public void draw(Graphics g) {
		g.setColor(levelColor[this.levelNumber]);
		g.fillRect(0, 0, Game.Width, Game.Height);
		for(int i =0;i<20;i++) {
			g.drawImage(img, i*1920 -Camera.x, 0 -Camera.y,1920,1080,null);
			
		}
		g.setColor(Color.BLACK);
		 for (int i = 0; i < levelObjects.size(); i++) {
			 levelObjects.get(i).draw(g);
		 }
		 if(this.levelName.equals("level1")) {

			    laser = new Laser(Camera.x-200, Camera.y, 50, 1000, 12,Laser.VERTICAL);
			
			    g.drawImage(Tutorial,0-Camera.x,0,null);
			}
	}
}
