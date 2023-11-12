import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.Container;
import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.EventQueue;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class RPSMenu{
    private JButton button;
    
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable(){
            public void run(){
                new RPSMenu();
            }
        });
    }
    
    public RPSMenu(){
        JFrame f = new JFrame("Rock, Paper, or Scissors");
        JButton playButton = new JButton("Play");
        JButton quitButton = new JButton("Quit");
        JButton switchButton = new JButton("Play Audio");
        JButton switchButton2 = new JButton("Mute Audio");
        JPanel panel = new MenuPanel();
        
        playButton.setFocusable(false);
        quitButton.setFocusable(false);
        switchButton.setFocusable(false);
        switchButton2.setFocusable(false);
        
        f.getContentPane().setLayout(new BoxLayout(f.getContentPane(), BoxLayout.PAGE_AXIS));
        switchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        f.getContentPane().add(playButton);
        f.getContentPane().add(quitButton);
        f.getContentPane().add(switchButton);
        f.getContentPane().add(switchButton2);
        //addComponentsToPane(f.getContentPane());
        
        
        playButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                RPS g = new RPS();
            }
        });
        
        quitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        
        try{
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File("audio.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            switchButton.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                }
            });
            switchButton2.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    clip.stop();
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
        
        panel.add(playButton);
        panel.add(quitButton);
        panel.add(switchButton);
        panel.add(switchButton2);
        f.add(panel);
        f.setSize(400,400);
        f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
        //f.pack();
        f.setResizable(false);
        f.setVisible(true);
    }
    
    public class MenuPanel extends JPanel{
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(Color.CYAN);
            g.fillRect(0,0,108,400);
            g.setColor(Color.RED);
            g.fillRect(108,0,98,400);
            g.setColor(Color.BLUE);
            g.fillRect(206,0,188,400);
            
            g.setColor(Color.BLACK);
            g.fillRect(10,55,360,280);
            g.setColor(Color.YELLOW);
            g.fillRect(15,60,350,270);
            
            g.setFont(new Font("Comic Sans", Font.PLAIN, 30));
            g.setColor(Color.MAGENTA);
            String welcome = "Welcome to RPS!  ";
            
            String play = "Press 'Play' to start the game, click ";
            String quit = "'Quit' to exit the game.";
            String[] switchDisplay = {"'Switch Display' changes the game","to use realistic images or cartoon ", "images, default is cartoon style."};
            
            g.drawString(welcome,80,100);
            g.setFont(new Font("Comic Sans", Font.PLAIN, 20));
            g.setColor(Color.gray);
            g.drawString(play,20,150);
            g.drawString(quit,20,180);
            g.drawString(switchDisplay[0],20,220);
            g.drawString(switchDisplay[1],20,250);
            g.drawString(switchDisplay[2],20,280);
            
            g.setColor(Color.BLACK);
            g.fillPolygon(new int[]{0,10,0}, new int[]{0,0,10},3);
            g.fillPolygon(new int[]{390,400,400,390}, new int[]{0,0,10,0},4);
        }
    }
}