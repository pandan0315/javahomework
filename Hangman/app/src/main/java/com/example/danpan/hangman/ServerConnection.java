package com.example.danpan.hangman;

import java.io.DataInputStream;
import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.DataOutputStream;

import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import message.Message;

/**
 * Created by danpan on 26/12/15.
 */
public class ServerConnection{
    private String host;
    private int port;
    //private final LinkedBlockingQueue<String> strings = new LinkedBlockingQueue<>();
    private String action_msg;

    private String erro_msg=null;

    private String wordToShow;
    private int attempts;
    private String status;
    private int score;
   private ObjectInputStream in;

    //private PrintWriter out;
    //private DataInputStream in;
    private DataOutputStream out;
    private Socket clientSocket;
    private static ServerConnection conn_instance = null;
    private boolean isInitialized = false;

    public String getErro_msg(){return this.erro_msg;}

    public int getAttempts(){return this.attempts;}

    public String getResult(){return this.wordToShow;}

    public String getStatus(){return this.status;}

    public int getScore(){return this.score;}

    public static ServerConnection getInstance()
    {
        if(ServerConnection.conn_instance != null) {
            return ServerConnection.conn_instance;
        }
        else {
            ServerConnection.conn_instance = new ServerConnection();
            return ServerConnection.conn_instance;
        }
    }

    public void initialize(String host, int port) {
        this.host = host;
        this.port = port;
        //strings.add("_start_");
       // this.action_msg="start";
        this.isInitialized = true;
    }

    public boolean isInitialized() {
        return this.isInitialized;
    }

    private ServerConnection() {
        this.isInitialized = false;
    }





    /**
     * Connects to the server using the host name and port number specified in
     * the constructor.
     */
    void connect() {
        erro_msg=null;

        try {
            this.clientSocket = new Socket(host, port);


            //out = new PrintWriter(clientSocket.getOutputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());



        } catch (UnknownHostException e) {
            erro_msg = "UnknownHostException: " + e.toString();



        } catch (IOException e) {

            erro_msg = "IOException: " + e.toString();
        }catch (Exception e){
            erro_msg="Exception: "+e.toString();
        }

    }


    void guess(String word) {
        this.action_msg=word;

    }
   void startgame() {
     this.action_msg="start";

    }


    void callServer(){
        Object result=null;
        Message resMessage=new Message(10,"_,_,_,_,_,_,_,_,_,_",0,"GAME START!");




            String msg = this.action_msg;
        try {
            out.writeUTF(msg);
            out.flush();
            result = in.readObject();
        } catch (IOException e) {
            erro_msg = "IOException: " + e.toString();
        } catch (ClassNotFoundException e) {
            erro_msg = "ClassNotFoundException: " + e.toString();
        }


        //result = in.readLine();
            // result = result.substring(1, result.length() - 2);
            //  result = result.replace(',', ' ');



            if (result instanceof Message) {
                resMessage = (Message) result;
            }
            wordToShow = resMessage.getCurrentWord();
            wordToShow = wordToShow.substring(1, wordToShow.length() - 2);
            wordToShow = wordToShow.replace(',', ' ');

            attempts = resMessage.getAttempts();
            status = resMessage.getStatus();
            score=resMessage.getScore();








    }
}

