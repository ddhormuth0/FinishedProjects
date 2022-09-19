package ddhormuth.cosc331_project2Server_dawsonhormuth;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

/**
 * Server application class that will be running on server side.
 *
 * @author Dawson Hormuth
 */
public class Server extends ClientServer {
    
    /**
     * Constructor with no parameters
     */
    public Server() {
        
    }
    
    /**
     * Lets the host receive one message and uses it to set destination socket
     */
    public void receiveMessage(){
        byte[] bArr = new byte[1200];
        DatagramPacket p1 = new DatagramPacket(bArr, 1200);
        try{
            this.localSocket.receive(p1);
        
            setDestinationSocket((InetSocketAddress) p1.getSocketAddress());
        } catch(IOException e){

        }     
    }

    /**
     * starts the program by setting up all necessary parts such as acquiring port number and waiting for client to send a message
     */
    public void startProgram(){
        //get server port
        Scanner input = new Scanner(System.in);
        System.out.println("Server Started!\nInput port you would like to use!(Must be 1024-65535)");
        int port = input.nextInt();
        //port must be in between 1024 and 65535
        while(port > 65535 || port < 1024){
            System.out.println("Invalid Port Number (Must be between 1024-65535):");
            port = input.nextInt();
        }
        //prints out port
        System.out.println("\nCreating socket with port:" + port);
        //creates datagram socket with specified port
        try {
            this.localSocket = new DatagramSocket(port);
        } catch (SocketException e) {

        }
        
        //attempts to print out ip and local port
        try {
            System.out.println("Server IP:" + InetAddress.getLocalHost().getHostAddress());
            System.out.println("Server Port:" + this.getLocalPort());
        } catch (UnknownHostException ex) {
            System.out.println("Error Finding IP!");
            System.exit(-1);
        }
        //wait for client to send a message so we can obtain thier ip and port
        System.out.println("Waiting for Client to send message...");
        //receives clients message and sets destination socket

        this.receiveMessage();
        
        //sending thread and receiving thread so we can send and receive messages continuously
        Mic sendThread = new Mic(this.getDestinationSocketAddr(), this.localSocket);
        ReceivingAudio receiveThread = new ReceivingAudio(this.localSocket);
        PlayAudio playThread = new PlayAudio(receiveThread.packetQueue);
        
        //starts threads
        sendThread.start();
        receiveThread.start();
        playThread.start();
        
        
        //locks the program in place until we want to stop the messages
        String message = "";
        while(!message.equals("end")){
            System.out.println("Type end to end program!");
            message = input.next();
        }
       
        //ends the receiving thread
        receiveThread.end();
        sendThread.end();
        playThread.end();
        input.close();
        
    }
}
