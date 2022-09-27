package ddhormuth.cosc331_project2Server_dawsonhormuth;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;

/**
 *
 * @author Dawson Hormuth
 */
public class Mic extends Thread {

    private AudioFormat format;
    private DataLine.Info info;
    private TargetDataLine microphone;
    private boolean isRecording;
    private final InetSocketAddress DESTINATION_SOCKET;
    private final DatagramSocket LOCAL_SOCKET;;

    public Mic(InetSocketAddress destination, DatagramSocket socket) {  
        
        try {
            //items required for recording audio
            //the audio format we will be encoding in
            format = new AudioFormat(16000, 16, 1, true, true);
            //the dataline with the format and class we are using
            info = new DataLine.Info(TargetDataLine.class, format);
            //the microphone where we will get our audio from
            microphone = (TargetDataLine) AudioSystem.getLine(info);

        } catch (LineUnavailableException e) {
            
        }
        //items required for sending our packet
        this.LOCAL_SOCKET = socket;
        this.DESTINATION_SOCKET = destination;
        isRecording = true;
        
    }

    @Override
    public void run() {

        try {
            //opens and starts mic
            this.microphone.open(this.format);
            microphone.start();

        } catch (LineUnavailableException ex) {
            Logger.getLogger(Mic.class.getName()).log(Level.SEVERE, null, ex);
        }

        byte[] buff = new byte[1200];

        while (isRecording) {
            //gets audio info
            microphone.read(buff, 0, buff.length);
            //sends audio info
            DatagramPacket p1 = new DatagramPacket(buff, buff.length, DESTINATION_SOCKET);
            try {  
                //sends packet to the destination
                this.LOCAL_SOCKET.send(p1);
            } catch (IOException ex) {
                System.out.println("Error sending packet!!!");
            }
        }
    }
    
    public void end(){
        //sets threads loop to false
        isRecording = false;
        microphone.close();
    }
}
