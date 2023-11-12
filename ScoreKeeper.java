import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ScoreKeeper implements Serializable{
    private int highscore;
    private int score;
    public boolean lost = false;
    
    public ScoreKeeper(){
        this.score = 0;
        try{
            this.highscore = readHighscoreObjectFromFile();
        }catch (Exception e){
            this.highscore = 0;
        }
    }
    
    public void increment(int amount){
        checkHighscore();
        if(score+amount < 0){
            lost = true;
        }else{
            score+=amount;
        }
    }
    
    public int getScore(){
        return score;
    }
    
    public int getHighScore(){
        checkHighscore();
        return this.highscore;
    }
    
    public void checkHighscore(){
        if(score > highscore) {
            highscore = score;
            try{
                writeHighScoreObjectToFile();
            }catch (java.io.IOException ioe){
                ioe.printStackTrace();
            }
        }
        if(lost){
            
        }
    }
    
    private void writeHighScoreObjectToFile() throws java.io.IOException {
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("Highscore.obj"));
        os.writeObject(this);
        os.close();
    }
    
    public int readHighscoreObjectFromFile() throws ClassNotFoundException, java.io.IOException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("Highscore.obj"));
        ScoreKeeper highscoreObj = (ScoreKeeper)in.readObject();
        in.close();
        return highscoreObj.getHighScore();
    }
}