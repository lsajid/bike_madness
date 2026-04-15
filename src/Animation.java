import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Animation
{
    BufferedImage[] images;
    boolean loopable = true;
    boolean finished = false;

    int numFrames;
    int frameWidth;
    int frameHeight;

    int current = 0;
    int duration;
    int delay;
    String fileName;

    public Animation(String path, int numFrames, int frameWidth, int frameHeight, int duration)
    {
        this.fileName = path;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.duration = duration;
        delay = duration;
        this.numFrames = numFrames;
        images = new BufferedImage[numFrames];
        loadImage(path);

    }

    public Animation(String path, int numFrames, int frameWidth, int frameHeight, int duration,boolean loopable)
    {
        this(path,numFrames,frameWidth,frameHeight,duration);
        this.loopable = loopable;
    }

    public void loadImage(String path){
        try {
            BufferedImage image = ImageIO.read(new File(path));

            for(int i = 0; i < this.numFrames; i++) {
                // Keep Y at 0, only move X
                this.images[i] = image.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
            }

        } catch (IOException e) {
            //throw new IOException("Failed to load image from " + this.path);
            e.printStackTrace(); // This will tell you if the file actually exists
        }


    }

    public Image nextImage() {
        if (delay == 0) {
            current++;

            if (loopable) {
                current = current % numFrames;
            } else if (current >= numFrames) {
                current = numFrames -1 ;
                finished = true;
            }

            delay = duration;
        } else {
            delay--;
        }

        return images[current];
    }

    public boolean isFinished()
    {
        return finished;
    }

    public boolean finished(){
        return current >= numFrames-1 && delay == 0;
    }
    public Image stillImage()
    {
        return images[0];
    }

    public void reset(){
        this.current = 0;
        this.delay = duration;
        this.finished = false;
    }
}