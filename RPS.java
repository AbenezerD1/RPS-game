import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.event.MouseMotionListener;
import java.awt.FontMetrics;

/**
 * The game of Rock, Paper, and Scissors played via a GUI
 */
public class RPS extends JFrame{
    private final int FRAME_WIDTH = 1800, FRAME_HEIGHT = 900;
    private final int playAreaX = FRAME_WIDTH/20, playAreaY = FRAME_HEIGHT/10; //rectangle where the game is drawn
    private boolean startGame = false, clicked = false; //checks if game has started. checks if player has clicked
    private String playerMove; //keeps track of the players move
    
    private Color backgroundColor = Color.YELLOW;
    private RPSPane panel; 
    private ComputerMove m;
    private ScoreKeeper score;
    private JFrame f;
    private JLabel scoreLabel;
    private JButton resetButton;
    private JLabel highscoreLabel;
    /**
     * Runs the game
     */
    public static void main(String[] args){
        RPS p = new RPS();
    }
    
    /**
     * Sets the diemsnsions, style, and elements in the GUI
     *      PRECONDITION: none
     */
    public RPS(){
        f = new JFrame("ROCK PAPER SCISSORS");
        //calls to draw the game with XY inseted by the playArea and width & height of game similarly
        panel = new RPSPane(playAreaX, playAreaY, FRAME_WIDTH-playAreaX, FRAME_HEIGHT-playAreaY);
        panel.addMouseListener(new Mouse()); 
        
        scoreLabel = new JLabel("Score:  "+0);
        scoreLabel.setFont(new Font("Anton", Font.PLAIN, 30));
        panel.add(scoreLabel, BorderLayout.NORTH);
        
        highscoreLabel = new JLabel("Highscore:  "+score.getHighScore());
        highscoreLabel.setHorizontalAlignment(HEIGHT);
        highscoreLabel.setFont(new Font("Anton", Font.PLAIN, 30));
        panel.add(highscoreLabel, BorderLayout.SOUTH);
        
        f.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        f.setResizable(false);
        f.add(panel);
        f.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        f.setVisible(true);
    }
    
    
    /**
     * Controls the games output and displays the different plays
     */
    public class RPSPane extends JPanel{
        //x and y represent the playArea coords and width and height to play area width and height
        private int x, y, width,  height;
        private Rock r;
        private Paper p;
        private Scissors s;
        
        /**
         * Stores the location and dimension information of the panel
         * 
         *      PRECONDITION: coordniates and dimensions aren't negative
         *      POSTCONDITION: collects and stores data in fields
         */
        public RPSPane(int x, int y, int width,int height){
            if(x<0||y<0||width<0||height<0) {
                System.err.println("Invalid panel dimensions or coordinates");
                System.exit(0);
            }
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            initialize();
        }
        
        /**
         * initializes the rock, paper, and scissors with the appropriate dimensions
         * 
         *      PRECONDITION: Frame width and height is 1800px by 900px
         *      POSTCONDITION: switches the plays to be ready to be displayed
         */
        private void initialize(){
            if(startGame)return;
            
            r = new Rock(x+133,y + 140, FRAME_WIDTH/4-25,FRAME_WIDTH/4-25); //hard coded locations 
            p = new Paper(x+598,y + 140,FRAME_WIDTH/4-25,FRAME_WIDTH/4-25);
            s = new Scissors(x+1063,y + 140, FRAME_WIDTH/4-25,FRAME_WIDTH/4-25);
            m = new ComputerMove(x,y, FRAME_WIDTH/4-25,FRAME_WIDTH/4-25);
            score = new ScoreKeeper();
            
            r.switchDisplay();
            p.switchDisplay();
            s.switchDisplay();
        }
        
        /**
         * draws all the graphics for the game
         * 
         *      PRECONDITION: none
         *      POSTCONDITION: draws elements
         */
        @Override 
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            panel.setBackground(backgroundColor);
            scoreLabel.setText("Score:  " + score.getScore());
            highscoreLabel.setText("Highscore:  "+score.getHighScore());
            if(score.lost) {
                g.setColor(Color.RED);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
                g.drawString("GAME OVER", FRAME_WIDTH/3-27,FRAME_HEIGHT/2-37);
                return;
            }
            
            drawIntroductions(g);
            drawGame(g);
            g.setColor(Color.lightGray);
            drawComputerMove(g);
        }
        
        /**
         * draws the welcome screen of the game
         * 
         *      PRECONDITION: game has not started
         *      POSTCONDITION: draws the introductions
         */
        private void drawIntroductions(Graphics g){
            if(startGame) return;
            g.setColor(new Color(0,74,73));
            Font font = new Font("Bebas Neue", Font.BOLD, 60);
            FontMetrics text = g.getFontMetrics(font);
            g.setFont(font);
            g.drawString("WELCOME TO ROCK, PAPER, AND SCISSORS", x+153,y+148+text.getHeight());
            
            g.setColor(new Color(255,87,87));
            g.setFont(new Font("Bebas Neue", Font.PLAIN, 30));
            g.drawString("RULES: Rock beats Scissors, paper beats rock, and scissors beats paper. Every win gets you a point, goal is to beat", x+60,y+446);
            g.drawString("the highscore. If you lose with zero points the game is over.", x+196,y+510);
            
        }
        
        /**
         * draws the choice page where the player selects their move
         * 
         *      PRECONDITION: game has started and computer move isn't being shown
         *      POSTCONDITION: draws the choice page
         */
        private void drawGame(Graphics g){
            if(m.isDisplaying()) return;
            if(!startGame)return;
            
            g.setColor(Color.darkGray);
            Font font = new Font("Bebas Neue", Font.PLAIN, 60);
            FontMetrics text = g.getFontMetrics(font);
            g.setFont(font);
            g.drawString("CHOOSE EITHER ROCK, PAPER, OR SCISSORS", x+115,y+text.getHeight());
            g.setColor(Color.lightGray);
            
            r.draw(g);
            p.draw(g);
            s.draw(g);
        }
        
        /**
         * draws the results page where the player finds out the result of the game
         * 
         *      PRECONDITION: game has started and the player has clicked on the screen(in the choice page)
         *      POSTCONDITION: draws the result page
         */
        private void drawComputerMove(Graphics g){
            if(!startGame)return;
            if(!clicked) return;
            m.draw(g,playerMove); //sent the x & y coords of the play area
            m.setDisplay(); //turns of the result page
            clicked = false; //set the clicked false to make it change scrrens
        }
    }
    /**
     * keeps track of user's mouse
     * 
     * Contains methods: mouseReleased(MouseEvent me)
     */
    class Mouse implements MouseListener{
        public void mouseExited(MouseEvent me){}
        public void mouseEntered(MouseEvent me){}
        /**
         * updates player's move based on where the player clicks on the page
         * 
         *      PRECONDITION: user clicked in the panel
         *      POSTCONDITION: updates player move or starts game if it hasn't started
         */
        public void mouseReleased(MouseEvent me){
            if(containedRock(me.getX(),me.getY()) && startGame){
                playerMove = "Rock";
                clicked = true;
                if(!m.isDisplaying())checkAndUpdateScore();
            }else if(containedPaper(me.getX(),me.getY())&& startGame){
                playerMove = "Paper";
                clicked = true;
                if(!m.isDisplaying())checkAndUpdateScore();
            }else if(containedScissors(me.getX(),me.getY()) && startGame){
                playerMove = "Scissors";
                clicked = true;
                if(!m.isDisplaying())checkAndUpdateScore();
            }
            
            if(!startGame) startGame = true; //starts the game
            panel.repaint();
        }
        public void mousePressed(MouseEvent me){}
        public void mouseClicked(MouseEvent me){}
    }
    
    /**
     * looks at who won and updates the game
     * 
     *      PRECONDITION: user has clicked on the choice page
     *      POSTCONDITION: updates score
     */
    private void checkAndUpdateScore(){
        if(!clicked)return;
        if(m.won && !m.tie) score.increment(1);
        if(!m.won && !m.tie) score.increment(-1);
    }
    
    /**
     * checks if the user has clicked rock
     * 
     *      PRECONDITION: none
     *      POSTCONDITION: true or not if they clicked on the rock or the circle incompassing it
     */
    private boolean containedRock(int x, int y){
        int rockAreaX = playAreaX + 133, rockAreaY = playAreaY + 140, diameter = FRAME_HEIGHT/2-25;
        return x>=rockAreaX && x<=rockAreaX + diameter && y>=rockAreaY && y<=rockAreaY+(diameter);
    }
    /**
     * checks if the user has clicked paper
     * 
     *      PRECONDITION: none
     *      POSTCONDITION: true or not if they clicked on the paper or the circle incompassing it
     */
    private boolean containedPaper(int x, int y){
        int paperAreaX = playAreaX + 598, paperAreaY = playAreaY + 140, diameter = FRAME_HEIGHT/2-25;
        return x>=paperAreaX && x<=paperAreaX+diameter && y>=paperAreaY && y<=paperAreaY+(diameter);
    }
    /**
     * checks if the user has clicked scissors
     * 
     *      PRECONDITION: none
     *      POSTCONDITION: true or not if they clicked on the scissors or the circle incompassing it
     */
    private boolean containedScissors(int x, int y){
        int scissorsAreaX = playAreaX + 1063, scissorsAreaY = playAreaY + 140, diameter = FRAME_HEIGHT/2-25;
        return x>=scissorsAreaX && x<=scissorsAreaX+diameter && y>=scissorsAreaY && y<=scissorsAreaY+(diameter);
    }
}
