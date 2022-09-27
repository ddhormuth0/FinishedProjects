
package ddhormuth.cosc331_p1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Thread to receive messages
 * @author Dawson Hormuth
 */
public class ReceivingThread extends Thread{
    private DatagramSocket localSocket;
    boolean isFirst;
    boolean isRunning;
    
    /**
     * Constructor for Receiving Thread
     * @param localSocket
     */
    public ReceivingThread(DatagramSocket localSocket){
        this.localSocket = localSocket;
        isRunning = true;
    }
    
    /**
     * runs the the receiving thread that allows us to receive multiple messages
     */
    @Override
    public void run(){
        //loops the receiving portion
        while(isRunning){
        //byte array packet
        byte[] bArr = new byte[1024];
        DatagramPacket p1 = new DatagramPacket(bArr, 1024);
        //tries to receive message
        try{
            this.localSocket.receive(p1);
            
            System.out.println("Recieved Message:\n" + byteToString(p1.getData()));
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
    
    /**
     * converts byte array to string
     * @param arr byte array you want to become string
     * @return string you converted from byte array
     */
    public String byteToString(byte[] arr){
        Charset charset = StandardCharsets.US_ASCII;
        
        return new String(arr, charset);
    }
    
}
