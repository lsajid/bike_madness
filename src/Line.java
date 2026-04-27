import java.awt.*;

public class Line {
    double x1, y1, x2, y2;

    public Line(double startX, double startY, double endX, double endY) {
        this.x1 = startX;
        this.y1 = startY;
        this.x2 = endX;
        this.y2 = endY;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
    }
}
