/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverapp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author danpan
 */
public class ServerApp {
    
    private ServerSocket serverSocket;
    private  static final String USAGE = "java ServerApp [port]";
    private boolean listening = true;
    /**
     * @throws java.io.IOException
     */ 
    public ServerApp()throws IOException {
        this(1234);
        
        
    }
    public ServerApp(int port)throws IOException {
        serverSocket = new ServerSocket(port);
        
        
    }
    public void server(){
        try {
             // serverSocket.setSoTimeout(10000);
            while (listening) {
                Socket clientSocket = serverSocket.accept();
                //System.out.println(clientSocket.getInetAddress());
                //System.out.println(clientSocket.getPort());
               new Thread(new ConnectionHandler(clientSocket)).start();   
              
            }
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerApp.class.getName()).log(Level.SEVERE, null, ex);
        } 

    } 
    
    
    public static void main(String[] args) throws IOException{
        
         // TODO code application logic here
        
        if (args.length > 0 &&(args[0].equalsIgnoreCase("-h") || args[0].equalsIgnoreCase("-help"))) {
			System.out.println(USAGE);
			System.exit(1);
		}
    
        ServerApp server = null;
        String portNoString = null;
        try{
            if (args.length>0){
                portNoString=args[0];
                int port =Integer.parseInt(portNoString);
                server = new ServerApp(port);
            }else{
                server = new ServerApp();
            }
            
        }catch(NumberFormatException e ){
         
           System.err.println("Invalid port number: "+portNoString+".");
           System.exit(1);
        }
         server.server();
         
         
    }
       
}
        
    

    
