/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientapp;

//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private BufferedReader in;
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
     * reverse server. This is done once, then the thread dies.
     */
    @Override
    public void run() {
        connect();
        callServer();
        disconnect();
    }

    /**
     * Connects to the server using the host name and port number specified in
     * the constructor.
     */
    void connect() {

        try {
            this.clientSocket = new Socket(host, port);

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream());
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
        //callServer(word);
    }

    void startgame() {
        strings.add("_start_");
        //callServer("_start_");
    }

    void callServer() {
        String result;
        //String attempts = null;
        try {
            while (true) {
                String msg = strings.take();
                out.println(msg);
                out.flush();

           // get result from server
                //byte[] fromServer = new byte[128];
                //int n = in.read(fromServer);
                result = in.readLine();
                result = result.substring(1, result.length() - 2);
                result = result.replace(',', ' ');

                gui.showCurrentResult(result);
                gui.showAttempts();
                gui.showStatusResult();
            }

        } catch (InterruptedException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
        }

        //gui.showGuessedWords();
        //gui.showCurrentResult();
    }
}
