import java.awt.Graphics;

public class Ring implements Drawable{
int x;
int y;
final int width=64;
final int height=128;
final int innerHeightOffset=10;
final int innerWidthOffset=10;
Rect outerBox;
Rect innerBox;


public Ring(int[] params) {
	this.x = params[0];
	this.y=params[1];
	this.outerBox = new Rect(x,y,width,height);
	this.innerBox = new Rect(x + innerWidthOffset, y + innerHeightOffset,
            width - innerWidthOffset * 2, height - innerHeightOffset * 2);
}


@Override
public void draw(Graphics g) {
	// TODO Auto-generated method stub
	outerBox.draw(g);
	innerBox.draw(g);
}


@Override
public int getX() {
	// TODO Auto-generated method stub
	return x;
}


@Override
public int getY() {
	// TODO Auto-generated method stub
	return y;
}
}
