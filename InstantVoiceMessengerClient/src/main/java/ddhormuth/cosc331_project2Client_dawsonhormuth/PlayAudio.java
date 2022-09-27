
package ddhormuth.cosc331_project2Client_dawsonhormuth;

import java.io.ByteArrayOutputStream;
import java.util.Queue;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

/**
 *
 * @author Dawson Hormuth
 */
public class PlayAudio extends Thread {
    
    private AudioFormat format;
    public boolean isRunning;
    Queue<byte[]> packetQueue;
    ByteArrayOutputStream arrOut;
    
    public PlayAudio(Queue<byte[]> packetQueue){
        this.packetQueue = packetQueue;
        isRunning = true;
        format = new AudioFormat(16000, 16, 1, true, true);
        arrOut = new ByteArrayOutputStream();
    }
    
    @Override
    public void run(){
        
        byte[] buff;
        while(isRunning){
            
            if(packetQueue.size() >= 20){
                for(int i = 0; i < 20; i++){
                    arrOut.writeBytes(packetQueue.remove());
                }
            }
            buff = arrOut.toByteArray();
            arrOut.reset();
            
            
            try {
            //gets audio clip we are going to use
            Clip clip = AudioSystem.getClip();
            clip.open(format, buff, 0, buff.length);
            clip.start();
            
        } catch (LineUnavailableException ex) {

        }
        }
        
    }
    
    public void end(){
        this.isRunning = false;
    }
}
