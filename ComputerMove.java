import java.util.Random;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Font;
import java.awt.Color;
import java.awt.FontMetrics;

public class ComputerMove{
    private String move;
    private int compMove;
    private int x, y, width, height;
    private BufferedImage movePic;
    private Rock rock;
    private Paper paper;
    private Scissors scissors;
    public boolean won, tie, displaying;
    public ComputerMove(int x, int y, int width, int height){
        this.won = true;
        this.tie = true;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.displaying = false;
        initialize(x+133,y + 140);
    }
    
    public void initialize(int x, int y){
        this.rock = new Rock(x,y,width,height);
        this.paper = new Paper(x,y,width,height);
        this.scissors = new Scissors(x,y,width,height);
        rock.switchDisplay();
        paper.switchDisplay();
        scissors.switchDisplay();
    }
    
    public boolean computerWin(String player){      
        switch(compMove){
            case 1: //rock
               if(player.equalsIgnoreCase("Rock")){
                   return false;
               }else if(player.equalsIgnoreCase("Paper")){
                   return false;
               }else if(player.equalsIgnoreCase("Scissors")){
                   return true;
               }
               break;
            case 2: // paper
               if(player.equalsIgnoreCase("Rock")){
                   return true;
               }else if(player.equalsIgnoreCase("Paper")){
                   return false;
               }else if(player.equalsIgnoreCase("Scissors")){
                   return false;
               }
               break;
            case 3: //scissors
               if(player.equalsIgnoreCase("Rock")){
                    return false;
               }else if(player.equalsIgnoreCase("Paper")){
                    return true;
               }else if(player.equalsIgnoreCase("Scissors")){
                   return false;
               }
               break;
        }
        return false;
    }
    
    public void setDisplay(){
        this.displaying = !displaying;
    }
    
    public boolean isDisplaying(){
        return this.displaying;
    }
    
    public boolean isTied(String player){
        return move.equalsIgnoreCase(player);
    }
    
    public void randMove(){
        Random rand = new Random();
        compMove = rand.nextInt(3)+1;
        switch (compMove) {
            case 1: //rock
                    move = "Rock";
                    break;
            case 2: // paper
                    move = "Paper";
                    break;
            case 3: //scissors
                    move = "Scissors";
                    break;
        }
        try{
            movePic = ImageIO.read(new File(move+".png"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
     
    public void draw(Graphics g, String playerMove){
        if(!displaying) return;
        randMove();
        //sets position to the left
        initialize(x+133,y + 140);
        drawMoves(g,playerMove);
        //draw the computer move on the right
        initialize(x+1063,y + 140);
        drawMoves(g,move);
        
        drawResultPageText(g);
        
        //player wins if computer loses and isn't a tie
        if(!computerWin(playerMove) && !isTied(playerMove)){
            won = true;
            tie = false;
            drawPlayerWin(g);
        }
        //player loses if computer wins 
        if(computerWin(playerMove) && !isTied(playerMove)){
            won = false;
            tie = false;
            drawPlayerLoss(g);
        }
        //player ties if computer plays same move(computer not win and same move)
        if(isTied(playerMove)){
            won = false;
            tie = true;
            drawTie(g);
        }
    }

    public void drawMoves(Graphics g, String playerMove){
        switch (playerMove.toLowerCase()) {
            case "rock": //rock
                    rock.draw(g);
                    break;
            case "paper": // paper
                    paper.draw(g);
                    break;
            case "scissors": //scissors
                    scissors.draw(g);
                    break;
        }
    }
    
    
    public void drawResultPageText(Graphics g){
        Font font = new Font("Bebas Neue", Font.PLAIN, 50);
        FontMetrics text = g.getFontMetrics(font);
        
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString("VS", x+766,y+320+text.getHeight());
        
        Font font2 = new Font("Bebas Neue", Font.PLAIN, 16);
        FontMetrics text2= g.getFontMetrics(font2);
        g.setColor(Color.darkGray);
        g.setFont(font2);
        g.drawString("Click again to continue", x+724,y+720+text2.getHeight());
        
        g.setColor(Color.GREEN);
        g.setFont(font);
        g.drawString("COMPUTER MOVE", x+1050,y+640+text.getHeight());
        
        g.setColor(Color.BLUE);
        g.drawString("YOUR MOVE", x+170,y+640+text.getHeight());
    }
    //draw win
    public void drawPlayerWin(Graphics g){
        Font font = new Font("Bebas Neue", Font.PLAIN, 80);
        FontMetrics text = g.getFontMetrics(font);
        
        g.setColor(Color.RED);
        g.setFont(font);
        g.drawString("YOU WON!!", x+610,y+text.getHeight());
    }
    //draw lose 
    public void drawPlayerLoss(Graphics g){
        Font font = new Font("Bebas Neue", Font.PLAIN, 80);
        FontMetrics text = g.getFontMetrics(font);
        
        g.setColor(Color.RED);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 80));
        g.drawString("YOU LOST!", x+620,y+text.getHeight());
    }
    //draw tie 
    public void drawTie(Graphics g){
        Font font = new Font("Bebas Neue", Font.PLAIN, 80);
        FontMetrics text = g.getFontMetrics(font);
        
        g.setColor(Color.RED);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 80));
        g.drawString("YOU TIED!", x+630,y+text.getHeight());
    }
}