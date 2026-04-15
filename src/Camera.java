
public class Camera {
	static int x;
	
	public Camera(int x, int y) {
		this.x = x;
	}
	
	public static void moveLeft(int dx)
	{
		System.out.println("camera moving left ...");
		x -= dx;
	}
	
	public static void moveRight(int dx)
	{
		System.out.println("camera moving right ...");
		x += dx;
	}
	

}
