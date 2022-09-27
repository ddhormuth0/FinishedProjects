
package ddhormuth.cosc331_p1;


import java.io.IOException;
import java.net.*;
import java.util.Scanner;

/**
 * Sends messages 
 * @author Dawson Hormuth
 */
public class SendingThread extends Thread {
    
    
    final Object LOCK;
    private InetSocketAddress destinationSocket;
    private DatagramSocket localSocket;
    private boolean isFirst;
    private boolean isRunning;
    
    /**
     * creates our sending thread object
     * @param localSocket the socket we want to send from
     * @param destinationSocket the socket we want to send to
     */
    public SendingThread(DatagramSocket localSocket, InetSocketAddress destinationSocket){
        LOCK = new Object();
        this.localSocket = localSocket;
        this.destinationSocket = destinationSocket;
        isFirst = true;
        isRunning = true;
    }
    
    /**
     * Accessor
     * @return the value of our isRunning boolean
     */
    public boolean getIsRunning(){
        return this.isRunning;
    }
    
    /**
     * starts our sending thread that allows us to send multiple messages to our destination
     */
    @Override
    public void run(){
        Scanner input = new Scanner(System.in);
        String message;
       
        while(isRunning){
            //types a short field explaining what to do
            if(isFirst){
            isFirst = false;
            System.out.println("Type message you want to send: ");
            }
            message = input.nextLine();
            //if the user wants to end the prcess then we will send this to the destination host
            if(message.equals("end"))
                message = "Ending Message App, Server Host Closing";
               
            //converts our message into a byte array
            byte[] arr = stringToByte(message);
            //creates packet with message and destination socket
            DatagramPacket p1 = new DatagramPacket(arr, 1024, destinationSocket);
            try {  
                //sends packet to the destination
                this.localSocket.send(p1);
            } catch (IOException ex) {
                System.out.println("Error sending packet!!!");
            }
            //if our message is equal to the ending message then we will call this.end to end our thread
            if(message.equals("Ending Message App, Server Host Closing"))
                this.end();
      
        }
        
        input.close();
    }
    
    /**
     * Ends thread
     */
    public void end(){
        //sets threads loop to false
        isRunning = false;
        //unlocks the lock, this allowed our main program to stop and wait until both threads were finished, once they do finish 
        //it will unlock and end the receiving thread
        synchronized (this.LOCK){
            LOCK.notifyAll();
        }
    }
    
    /**
     * converts string to byte
     * @param message message you want to make into byte
     * @return byte array from the string
     */
    public byte[] stringToByte(String message){
        byte[] arr = new byte[1024];
        char[] cArr = message.toCharArray();
        for(int i = 0; i < cArr.length; i++){
            arr[i] = (byte)cArr[i];
        }
        return arr;
    }
}
