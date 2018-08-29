package ir.arcinc.tls.ReliableChannel;

import ir.arcinc.tls.Commons.AbstractReceiver;
import ir.arcinc.tls.Commons.AbstractSender;
import ir.arcinc.tls.Commons.UnreliableChannel;

import java.net.URLEncoder;

/**
 * Created by tahae on 4/18/2016.
 */

public class Receiver extends AbstractReceiver {
    public Receiver(UnreliableChannel channel, AbstractSender sender) {
        super(channel,sender);
    }

    /**
     *
     * @param data data received from channel;
     *
     *  You can use method send(byte[]) to send a data to channel
     *  Or use sendToApplication(byte[]) to send data to application
     */

    @Override
    public void receive(byte[] data) {

        byte sum=0;
        for (int i = 0; i < data.length-4; i++) {
            sum+=data[i];
        }

        /////////////////////////////////////////////////NAK////////////////////////////////////////////////

        byte[] nak = new byte[1];
        nak[0]=0;

        System.out.println("sum"+sum);
        if(sum!=-1)
        {
            nak[0]=1;
            send(nak);
        }

        byte[] finalData = new byte[data.length-5];
        for (int i = 0; i <finalData.length ; i++) {
            finalData[i]=data[i];
        }
        if(nak[0]!=1)
        sendToApplication(finalData);
    }
}
