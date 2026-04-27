import java.io.File;


public class LevelManager {
int current;
Level [] levels;
public void loadLevels() {
	
	File dir = new File("levels/");
	File[] folders = dir.listFiles(File::isDirectory);
	int numLevel = folders.length;
	levels = new Level [numLevel];
	System.out.println("Number of levels: "+ folders.length);
	for (int i = 0 ;i < numLevel; i++) {
		levels[i] = new Level("level" + (i+1));
	}
}

public Level getCurrent() {
	return levels[current];
}

public void goNextLevel() {
	this.current = Math.min(current+1, levels.length-1);
	Game.player.x = getCurrent().xStart;
	Game.player.y = getCurrent().yStart;
	Game.currentLevel = getCurrent();
}

}
