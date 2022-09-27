
package ddhormuth.cosc331_project2Client_dawsonhormuth;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Thread to receive messages
 * @author Dawson Hormuth
 */
public class ReceivingAudio extends Thread{
    private final DatagramSocket localSocket;
    boolean isFirst;
    boolean isRunning;
    Queue<byte[]> packetQueue;
    
    /**
     * Constructor for Receiving Thread
     * @param localSocket
     */
    public ReceivingAudio(DatagramSocket localSocket){
        this.localSocket = localSocket;
        isRunning = true;
        packetQueue = new LinkedList<>();
        
    }
    
    /**
     * runs the the receiving thread that allows us to receive multiple audio messages
     */
    @Override
    public void run(){
        //loops the receiving portion
        while(isRunning){
        //byte array packet
        byte[] bArr = new byte[1200];
        DatagramPacket p1 = new DatagramPacket(bArr, bArr.length);
        //tries to receive message
        try{
            this.localSocket.receive(p1);
            packetQueue.add(p1.getData());
            
        } catch(IOException e){

        }
        
        
        
        
        
        }
    }
    
    /**
     * ends the thread process and closes the socket
     */
    public void end(){
        isRunning = false;
        localSocket.close();
        
    }
    
}
