
package ddhormuth.cosc331_p1clientside;

import java.net.*;
import java.util.Scanner;


/**
 * Client class that implements the client host app
 * @author Dawson Hormuth
 */
public class Client extends ClientServer {
    
    /**
     *Constuctor
     */
    public Client(){
        try{
            this.localSocket = new DatagramSocket();
        } catch(SocketException e){    
        }
    }
    
    /**
     * Runs the client host app, starts by obtaining ip and port number of destination
     * from user then allows us to send and receive messages
     */
    public void startProgram(){
        Scanner input = new Scanner(System.in);
        //get ip and port
        System.out.println("Please Enter IP Address Of Destination: ");
        String ip = input.next();
        System.out.println("Please Enter Port Of Destination: ");
        int port = input.nextInt();
        
        InetSocketAddress destination = null;
        //make destination socket using ip and port
        try {
            destination = new InetSocketAddress(InetAddress.getByName(ip), port);
        } catch (UnknownHostException ex) {
            
        }
        //set destination socket
        this.setDestinationSocket(destination);
        //create our sending and receiving threads that allow us to send and receive continously
        SendingThread sendThread = new SendingThread(this.localSocket, this.getDestinationSocketAddr());
        ReceivingThread receiveThread = new ReceivingThread(this.localSocket);
        //start the threads
        sendThread.start();
        receiveThread.start();
        //a lock that has our program wait until the threads are done executing
        synchronized(sendThread.LOCK){
            try{
                sendThread.LOCK.wait();
            } catch(InterruptedException e){
                
            }
    }
        //ends receiving thread, sending thread is ended inside of its thread
        receiveThread.end();
    }
}
