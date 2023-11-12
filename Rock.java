import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class Rock{
    private BufferedImage player;
    private boolean display;
    private int x, y; //coords of circle incasing the rock image
    private int width, height; //size of the circle incasing the paper image
    public Rock(int x, int y, int width, int height){
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.display = false;
        try{
            player = ImageIO.read(new File("Rock.png"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void draw(Graphics g){
        if(!display) return;
        g.fillOval(x,y,width,height);
        g.drawImage(player, x+23, y+79, null);
    }
    
    public boolean isDisplaying(){
        return display;
    }
    
    public void switchDisplay(){
        this.display = !this.display;
    }
}