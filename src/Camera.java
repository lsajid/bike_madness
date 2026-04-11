
public class Camera {
	static int x;
	
	public Camera(int x, int y) {
		this.x = x;
	}
	
	public static void moveLeft(int dx)
	{
		x -= dx;
	}
	
	public static void moveRight(int dx)
	{
		x += dx;
	}
	

}
