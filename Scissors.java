import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class Scissors{
    private BufferedImage player;
    private boolean display;
    private int x, y; //coords of circle incasing the paper image
    private int width, height; //size of the circle incasing the paper image
    public Scissors(int x, int y, int width, int height){
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.display = false;
        try{
            player = ImageIO.read(new File("Scissors.png"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void draw(Graphics g){
        if(!display) return;
        g.fillOval(x,y,width,height);
        g.drawImage(player, x+67, y+45, null);
    }
    
    public boolean isDisplaying(){
        return display;
    }
    
    public void switchDisplay(){
        this.display = !this.display;
    }
}