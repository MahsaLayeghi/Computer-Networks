package ir.arcinc.tls.ReliableChannel;

import ir.arcinc.tls.Commons.AbstractSender;
import ir.arcinc.tls.Commons.UnreliableChannel;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tahae on 4/18/2016.
 */
public class Sender extends AbstractSender {

    static int packetNumber =0;
    static ArrayList<byte[]> store = new ArrayList<byte[]>();
    Timer timer = new Timer();

    public Sender(UnreliableChannel channel) {
        super(channel);
    }
    int a0;
    int a1;
    int a2;
    int a3;
    /**
     *
     * @param data data received from application to be sent to another application
     * Use send(byte[]) to send a data to channel.
     */
    @Override
    public void receiveFromApplication(byte[] data) {

        ///////////////////////////////////////////////seq number///////////////////////////////////////////////////
        store.add(data);
        int packetCopy=packetNumber;
        System.out.println("packetnum"+packetNumber);

        a0=packetCopy%10;
        packetCopy/=10;
        a1=packetCopy%10;
        packetCopy/=10;
        a2=packetCopy%10;
        packetCopy/=10;
        a3=packetCopy%10;
        packetCopy/=10;


        packetNumber++;
        ////////////////////////////////////////////finding checksum//////////////////////////////////////////////

        byte sum = 0;
        for (int i = 0; i <data.length ; i++) {
            sum+=data[i];
        }
        byte checksum= (byte)~(sum);

        //////////////////////////////////append checksum and seqNum to the end of array////////////////////////////

        byte[]packet=new byte[data.length+5];

        for (int i = 0; i < data.length; i++) {
            packet[i]=data[i];
        }
        packet[packet.length-5]=checksum;
        packet[packet.length-4]=(byte)a3;
        packet[packet.length-3]=(byte)a2;
        packet[packet.length-2]=(byte)a1;
        packet[packet.length-1]=(byte)a0;

        System.out.println("a33:"+(byte)a3);
        System.out.println("a22:"+(byte)a2);
        System.out.println("a11:"+(byte)a1);
        System.out.println("a00:"+(byte)a0);

        send(packet);
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                timeOut();
//            }
//        },2);
    }

    @Override
    public void receive(byte[] data) {

        ////////////////////////////////////////calculating seq num/////////////////////////////////////////
        System.out.println("a3:"+a3);
        System.out.println("a2:"+a2);
        System.out.println("a1:"+a1);
        System.out.println("a0:"+a0);
        int index= 1000*a3+100*a2+10*a1+a0;
        System.out.println("index"+index);
        ////////////////////////////////////////////finding checksum//////////////////////////////////////////////
            byte sum = 0;
            byte[] resend = new byte[store.get(index).length];
            for (int i = 0; i < store.get(index).length; i++) {
                resend[i]=store.get(index)[i];
            }

            for (int i = 0; i <resend.length ; i++) {
                sum+=resend[i];
            }
            byte checksum= (byte)~(sum);

            //////////////////////////////////append checksum and seqNum to the end of array////////////////////////////

            byte[]packet=new byte[resend.length+5];

            for (int i = 0; i < resend.length; i++) {
                packet[i]=resend[i];
            }
            packet[packet.length-5]=checksum;
            packet[packet.length-4]=(byte)a3;
            packet[packet.length-3]=(byte)a2;
            packet[packet.length-2]=(byte)a1;
            packet[packet.length-1]=(byte)a0;

            send(packet);
    }

    @Override
    public void timeOut() {
    }
}
