
package ddhormuth.cosc331_p1clientside;

import java.net.*;
import java.nio.charset.*;

/**
 * Abstract class that both the server class and client class will use.
 * 
 * @author Dawson Hormuth
 */
public abstract class ClientServer {
    
    //fields
    private InetSocketAddress destinationSocketAddr;
    DatagramSocket localSocket;
    
    //accessors

    /**
     * Accessor
     * @return destination socket address
     */
    public InetSocketAddress getDestinationSocketAddr(){
        return this.destinationSocketAddr;
    }
    
    /**
     * Accessor
     * @return local ip address
     */
    public InetAddress getLocalIP(){
        return this.localSocket.getLocalAddress();
    }
    
    /**
     * Accessor 
     * @return destination ip address
     */
    public InetAddress getDestinationIP(){
        return this.destinationSocketAddr.getAddress();
    }
    
    /**
     * Accessor
     * @return local port
     */
    public int getLocalPort(){
        return this.localSocket.getLocalPort();
    }
    
    /**
     * Accessor
     * @return destination port
     */
    public int getDestinationPort(){
        return this.destinationSocketAddr.getPort();
    }
    
    /**
     * Mutator
     * @param destinationSocketAddr socket address of destination host
     */
    public void setDestinationSocket(InetSocketAddress destinationSocketAddr){
        this.destinationSocketAddr = destinationSocketAddr;
    }
    
    //methods   

    /**
     * Converts string to byte array
     * @param message message you want to convert to byte
     * @return the byte array of the message you converted
     */
    public byte[] stringToByte(String message){
        byte[] arr = new byte[1024];
        char[] cArr = message.toCharArray();
        for(int i = 0; i < cArr.length; i++){
            arr[i] = (byte)cArr[i];
        }
        return arr;
    }
    
    /**
     * Converts byte array to string
     * @param arr the array you want to convert to a string
     * @return the string that was converted from the byte array
     */
    public String byteToString(byte[] arr){
        Charset charset = StandardCharsets.US_ASCII;
        
        return new String(arr, charset);
    }
    
    
}
