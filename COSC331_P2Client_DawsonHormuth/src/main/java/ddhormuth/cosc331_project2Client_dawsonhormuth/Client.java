
package ddhormuth.cosc331_project2Client_dawsonhormuth;

import java.net.*;
import java.util.Scanner;


/**
 *
 * @author dawso
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
        Mic sendThread = new Mic(this.getDestinationSocketAddr(), this.localSocket);
        ReceivingAudio receiveThread = new ReceivingAudio(this.localSocket);
        PlayAudio playThread = new PlayAudio(receiveThread.packetQueue);
        //start the threads
        sendThread.start();
        receiveThread.start();
        playThread.start();
        
        String message = "";
        while(!message.equals("end")){
            System.out.println("Type end to end program!");
            message = input.next();
        }
        
        //ends threads
        sendThread.end();
        receiveThread.end();
        playThread.end();
        
    }  
    
}

