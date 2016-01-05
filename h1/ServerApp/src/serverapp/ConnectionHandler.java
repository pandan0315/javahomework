/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapp;

//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
import message.Message;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;


       

/**
 *
 * \
 */
class ConnectionHandler implements Runnable {

    private Socket clientSocket = null;
    private char[] word;
    private String pickedWord;
    private char[] currentWord;
    private char[] inputCharArray;
    private int attempts=10;
    private final ArrayList<String> guessedWord = new ArrayList<String>();
    private boolean isGuessedWord = false;
    private String outMsg;
    private int score=0;
    private String gameStatus="GAME START!";
    
    
    public ConnectionHandler(Socket clientSocket) {

        this.clientSocket = clientSocket;

    }

    public String getPickedWord() {
        return pickedWord;
    }
    
    
    @Override
    public void run() {

        try (   ObjectOutputStream out= new ObjectOutputStream(clientSocket.getOutputStream());
                //BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
                DataInputStream in = new DataInputStream(clientSocket.getInputStream());
               
               ) {

            while (true) {
                String previousOutMsg=Arrays.toString(currentWord);
                
                String msg = in.readUTF();
                 processMsg(msg);
               
                
               
             
                if(attempts>=0&&!outMsg.contains("_")){
                    gameStatus="YOU WIN!";
                   score+=1; 
                }
                else if(attempts==0){
                    gameStatus="YOU LOSE!";
                    if(score>0){
                        score-=1;
                    }
                }
            //  Thread.sleep(5000);
                Message retMessage = new Message(attempts,outMsg,score,gameStatus);
                System.out.println(retMessage);
                out.writeObject(retMessage);
                out.flush();

            }
        } catch (IOException ex) {
            Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    } 

    private void processMsg(String msgStr) {
         String previousOutMsg= Arrays.toString(currentWord);
        if (msgStr == null || msgStr.length() == 0) {
            return;
        }
        String startStr = "start";
        if (msgStr.equals(startStr)) {
            
           this.guessedWord.clear();
           attempts=10;
           this.gameStatus="GAME START!";
            pickedWord = pickupWord();
            System.out.println(pickedWord);
            currentWord = new char[10];
            for (int i = 0; i < pickedWord.length(); i++) {
                currentWord[i] = '_';
            }

        } else {
            this.isGuessedWord=this.guessedWord.contains(msgStr);
            if (!guessedWord.contains(msgStr)) {
            guessedWord.add(msgStr);
            
        }
             
            compareWord(msgStr, pickedWord);
            
       
        }
        
        outMsg=Arrays.toString(currentWord);
        
       // if(previousOutMsg.equals(outMsg)&&!this.isGuessedWord&&!this.guessedWord.isEmpty()){
         //   this.attempts-=1;
        //}
        if(previousOutMsg.equals(outMsg)){
            this.attempts-=1;
        }
        
    }

    private String pickupWord() {
        return WordReader.getWord();
    }

    private void compareWord(String msgStr, String newWord) {
        inputCharArray = msgStr.toCharArray();
        word = newWord.toCharArray();
        if (inputCharArray.length == 1) {
            // content is just one character
            for (int i = 0; i < word.length; i++) {
                if (inputCharArray[0] == word[i]) {
                    // Change the current word's space into corresponding character         
                    currentWord[i] = inputCharArray[0];

                }
            }
        } else {
        // content is a word
            // it is demanded that length of the word that client inputs equals the length of the correct word
            if (inputCharArray.length == word.length) {
                // check if the word that client inputs is just the correct word, the two words are identical when the value is 0         
                if (0 == msgStr.compareTo(new String(word))) {
                    currentWord = word;

                }
            }
        }

    }
}

