package ir.arcinc.tls.Commons;

import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 * Created by tahae on 4/18/2016.
 */
public class SenderApplication {
    private AbstractSender sender;
    private List<byte[]> data;

    public SenderApplication(List<byte[]> data, AbstractSender sender) {
        this.sender = sender;
        this.data = data;
    }

    private Random randomizer = new Random();

    public void createData(){
        int numberOfPackets = randomizer.nextInt(5*1000);

        for (int i = 0; i < numberOfPackets; i++){
            byte[] packet = new byte[randomizer.nextInt(1400)];
            randomizer.nextBytes(packet);
            data.add(packet);
        }
        System.out.println(numberOfPackets + " packets created.");
    }

    public void sendData(){
        for (byte[] packet : data){
            sender.receiveFromApplication(packet);
        }
    }
}
