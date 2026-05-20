import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import java.awt.*;
import java.awt.*;

public class ScoreScreen {
    static Image background = Toolkit.getDefaultToolkit().getImage("image/level_complete.png");

    static int btnW = 511;
    static int btnH = 208;
    static int gap = 40;

    static Button retryLevel = new Button("image/retry_button.png", 0, 0, btnW, btnH);
    static Button nextLevel  = new Button("image/next_level_button.png", 0, 0, btnW, btnH);
    
    static int FAILED = 0;
    static int PASSED = 1;
    static int state = 0;
    
    static String SuccessMessage = "Level Complete!";
    static String FailureMessage = "Level Failed :(";
    
    public static void draw(Graphics g) {
        g.drawImage(background, 0, 0, Game.Width, Game.Height, null);

        // Calculate positions at draw time, not class load time
        int startX = (Game.Width - (btnW * 2 + gap)) / 2;
        int btnY = (int)(Game.Height * 0.75); // 3/4 down the screen, adjust to taste

        retryLevel.x = startX;
        retryLevel.y = btnY;
        nextLevel.x  = startX + btnW + gap;
        nextLevel.y  = btnY;

        // Score text centered above buttons
        int minutes = (int) Game.currentLevel.seconds/ 60;
        int seconds = (int) Game.currentLevel.seconds % 60;
        String timeStr = String.format("Time: %02d:%02d", minutes, seconds);

        g.setFont(new Font("Arial", Font.BOLD, 72));
        FontMetrics fm = g.getFontMetrics();
        int textX = (Game.Width - fm.stringWidth(timeStr)) / 2;
        int textY = btnY - 60;

        g.setColor(Color.BLACK);
        g.drawString(timeStr, textX + 4, textY + 4);
        
        if(state == PASSED)  g.drawString(SuccessMessage,(Game.Width - fm.stringWidth(SuccessMessage)) / 2, 64 + fm.getHeight());
        else if(state == FAILED)  g.drawString(FailureMessage, (Game.Width - fm.stringWidth(FailureMessage)) / 2, 64 + fm.getHeight());
        
        g.setColor(Color.WHITE);
        g.drawString(timeStr, textX, textY);

        retryLevel.draw(g);
        if(state == PASSED) {
        nextLevel.draw(g);
        }
    }
}