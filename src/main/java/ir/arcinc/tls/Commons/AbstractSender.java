package ir.arcinc.tls.Commons;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tahae on 4/18/2016.
 */
public abstract class AbstractSender implements Observer{
    private UnreliableChannel channel;

    public AbstractSender(UnreliableChannel channel) {
        this.channel = channel;
    }

    public abstract void receiveFromApplication(byte[] data);
    public abstract void receive(byte[] data);
    public abstract void timeOut();

    protected final void send(byte[] data){
        if(!channel.send(data))
            timeOut();
    }

    @Override
    public final void update(Observable o, Object arg) {
        this.receive((byte[]) arg);
    }
}
