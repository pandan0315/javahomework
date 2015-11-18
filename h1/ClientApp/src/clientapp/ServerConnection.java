/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp;



import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.Message;
/**
 *
 * @author danpan
 */
class ServerConnection implements Runnable {

    private String host;
    private int port;
    private final LinkedBlockingQueue<String> strings
            = new LinkedBlockingQueue<>();
    private ClientJFrame gui;
   
    private ObjectInputStream in;
    
    private PrintWriter out;
    private Socket clientSocket;

    public ServerConnection(ClientJFrame gui, String host, int port) {
        this.host = host;
        this.port = port;
        this.gui = gui;

    }

    /**
     * The run method of the communication thread. First connects to the server
     * using the host name and port number specified in the constructor. Second
     * waits to receive a letter or a word from the gui and sends that to the
     * server.
     */
    @Override
    public void run() {
        connect();
        try {
            callServer();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        disconnect();
    }

    /**
     * Connects to the server using the host name and port number specified in
     * the constructor.
     */
    void connect() {

        try {
            this.clientSocket = new Socket(host, port);

          
            out = new PrintWriter(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            
            gui.connected();

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + host + ".");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: "
                    + host + ".");
            System.exit(1);
        }

    }
    
    private void disconnect() {
        try {
           this.clientSocket.close();
        } catch (IOException e) {
        }
    }

    void guess(String word) {
        strings.add(word);
       
    }

    void startgame() {
        strings.add("_start_");
        
    }

    void callServer() throws ClassNotFoundException {
       Object result;
       Message resMessage=new Message(10,"_,_,_,_,_,_,_,_,_,_",0,"GAME START!");
       
        try {
            while (true) {
                String msg = strings.take();
                out.println(msg);
                out.flush();

          
                //result = in.readLine();
               // result = result.substring(1, result.length() - 2);
              //  result = result.replace(',', ' ');
                
                result=in.readObject();
               
               if (result instanceof Message){
                   resMessage=(Message)result;
            }
                String wordToShow=resMessage.getCurrentWord();
                wordToShow = wordToShow.substring(1, wordToShow.length() - 2);
                wordToShow = wordToShow.replace(',', ' ');
                gui.showCurrentResult(wordToShow);
                gui.showAttempts(resMessage.getAttempts());
                gui.showStatusResult(resMessage.getStatus());
                gui.showScoreResult(resMessage.getScore());
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
        }

       
    }
}
