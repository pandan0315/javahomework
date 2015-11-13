/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapp;

//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danpan
 */
class ConnectionHandler implements Runnable {

    private Socket clientSocket = null;
    private char[] word;
    private String pickedWord;
    private char[] currentWord;
    private char[] inputCharArray;

    public ConnectionHandler(Socket clientSocket) {

        this.clientSocket = clientSocket;

    }

    public String getPickedWord() {
        return pickedWord;
    }

    @Override
    public void run() {

        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream())) {

            while (true) {
                String msg = in.readLine();

                processMsg(msg);
                //Thread.sleep(5000);

                String outmsg = Arrays.toString(currentWord);
                out.println(outmsg);
                out.flush();

            }
        } catch (IOException ex) {
            Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        } //catch (InterruptedException ex) {
           // Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        //}

    }

    private void processMsg(String msgStr) {
        if (msgStr == null || msgStr.length() == 0) {
            return;
        }
        String startStr = "_start_";
        if (msgStr.equals(startStr)) {
            pickedWord = pickupWord();
            System.out.println(pickedWord);
            currentWord = new char[10];
            for (int i = 0; i < pickedWord.length(); i++) {
                currentWord[i] = '_';
            }

        } else {
            compareWord(msgStr, pickedWord);
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
