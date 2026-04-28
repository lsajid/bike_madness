import java.awt.*;

public class Line implements Drawable{
    double x1, y1, x2, y2;

    public Line(double startX, double startY, double endX, double endY) {
        this.x1 = startX;
        this.y1 = startY;
        this.x2 = endX;
        this.y2 = endY;
    }

    public Line(int[] params) {
		// TODO Auto-generated constructor stub
        this.x1 = params[0];
        this.y1 = params[1];
        this.x2 = params[2];
        this.y2 = params[3];
    }

	public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
    }

	public int getX() {return (int) this.x1;}

	public int getY() {return (int) this.y1;}
}
