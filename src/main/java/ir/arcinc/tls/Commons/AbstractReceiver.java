package ir.arcinc.tls.Commons;

import ir.arcinc.tls.ReliableChannel.Receiver;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by tahae on 4/18/2016.
 */
public abstract class AbstractReceiver extends Observable implements Observer{
    private UnreliableChannel channel;
    private AbstractSender sender;

    public AbstractReceiver(UnreliableChannel channel, AbstractSender sender) {
        this.channel = channel;
        this.sender = sender;
    }

    public abstract void receive(byte[] data);

    protected final void send(byte[] data){
        if (!channel.send(data))
            sender.timeOut();
    }

    @Override
    public final void update(Observable o, Object arg) {
        this.receive((byte[]) arg);
    }

    public final void sendToApplication(byte[] data){
        this.setChanged();
        this.notifyObservers(data);
    }
}
