package ir.arcinc.tls.Commons;

import ir.arcinc.tls.ReliableChannel.Receiver;
import ir.arcinc.tls.ReliableChannel.Sender;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tahae on 4/18/2016.
 */
public class Initializer {
    public Initializer(){
        double lossRate = 0.01;
        double errorRate = 0.0005;
        double lateRate = 0.05;

        UnreliableChannel sendChannel = new UnreliableChannel(lossRate, errorRate, lateRate);
        UnreliableChannel receiveChannel = new UnreliableChannel(lossRate, errorRate, lateRate);

        Sender sender = new Sender(sendChannel);
        receiveChannel.addObserver(sender);

        Receiver receiver = new Receiver(receiveChannel,sender);
        sendChannel.addObserver(receiver);

        List<byte[]> data = new LinkedList<>();

        SenderApplication senderApplication = new SenderApplication(data,sender);
        ReceiverApplication receiverApplication = new ReceiverApplication(data);
        receiver.addObserver(receiverApplication);

        senderApplication.createData();
        senderApplication.sendData();

        System.out.println("Calculating jaccard coefficient...");
        double matchRate = receiverApplication.getJaccardCoefficient();
        System.out.println("Matched: " + String.format("%2.2f",matchRate*100) + "%");
    }
}
