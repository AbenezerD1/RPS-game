import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class Paper{
    private BufferedImage player;
    private boolean display;
    private int x, y; //coords of circle incasing the paper image
    private int width, height; //size of the circle incasing the paper image
    public Paper(int x, int y, int width, int height){
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.display = false;
        try{
            player = ImageIO.read(new File("Paper.png"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void draw(Graphics g){
        if(!display) return;
        g.fillOval(x,y,width,height);
        g.drawImage(player, x+70, y+63, null);
    }
    
    public boolean isDisplaying(){
        return display;
    }
    
    public void switchDisplay(){
        this.display = !this.display;
    }
}