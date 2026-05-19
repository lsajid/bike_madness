import java.io.File;


public class LevelManager {
int current;
Level [] levels;
public void loadLevels() {
	
	File dir = new File("levels/");
	File[] folders = dir.listFiles(File::isDirectory);
	int numLevel = folders.length;
	levels = new Level [numLevel];
	for (int i = 0 ;i < numLevel; i++) {
		levels[i] = new Level("level" + (i+1));
		levels[i].levelNumber=i;
	}
	
	Game.player.x = levels[0].xStart;
	Game.player.y = levels[0].yStart;
}

public Level getCurrent() {
	return levels[current];
}

public void goNextLevel() {
	this.current = Math.min(current+1, levels.length-1);
	Game.player.reset();
	Game.player.x = getCurrent().xStart;
	Game.player.y = getCurrent().yStart;
	Game.currentLevel = getCurrent();
}
public void goBackLevel() {
	this.current = Math.max(current-1, 0);
	Game.player.reset();
	Game.player.x = getCurrent().xStart;
	Game.player.y = getCurrent().yStart;
	Game.currentLevel = getCurrent();
}


}
