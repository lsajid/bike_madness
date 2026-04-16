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
	
	ArrayList<Rect> levelObjects = new ArrayList <Rect>();;
	
	public Level(String levelName) {
		this.levelName = levelName;
		this.img = Toolkit.getDefaultToolkit().getImage("levels/" + levelName +"/"+ levelName + ".png");
		
		load();
	}
	
	
	public void load(){
		Game.player.x = 0;
		Game.player.y = 873-Game.player.height;
		File file = new File("levels/" + levelName + "/Rectangles.txt");
		
		LevelLoader levelLoader = new LevelLoader();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            
            // readLine() returns null when it reaches the end of the file
            while ((line = br.readLine()) != null) {
            	int [] objInfo = levelLoader.getObjInfo(line);
            	
            	// 0 Means rectangle
            	if (objInfo[0] == 0) {
            		Rect r = new Rect(objInfo[1],objInfo[2],objInfo[3],objInfo[4]);
            		levelObjects.add(r);
            	}
            	
            	// System.out.println(line);
            	
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
	}
	public void draw(Graphics g) {
		 g.drawImage(img, 0, 0,Game.Width,Game.Height,null);
		 for (int i = 0; i < levelObjects.size(); i++) {
			 levelObjects.get(i).draw(g);
		 }
		 
	}
}
