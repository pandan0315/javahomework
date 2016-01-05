package message;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;

/**
 *
 * 
 */
public class Message implements Serializable {
     private int attempts;
     private String currentWord;
     private String gameStatus;
     private int score;
    
    public String getCurrentWord(){
        return currentWord;
    }
    public int getAttempts(){
        return attempts;
    }
    public int getScore(){
        
      
        return score;
    }
    public String getStatus(){
 
        return gameStatus;
    }
    
     @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Attempts: " + this.attempts);
        builder.append("  Current Word: " + this.currentWord);
        builder.append("   GameStatus: " + this.gameStatus);
        builder.append("   Score: " + this.score);
        return builder.toString();
    }
    
    public Message(int attempts,String currentWord,int score,String gameStatus){
        this.attempts=attempts;
        this.currentWord=currentWord;
        this.score=score;
        this.gameStatus=gameStatus;
        
    }
    
}
