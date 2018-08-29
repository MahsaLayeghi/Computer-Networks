package ir.arcinc.tls.Commons;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by tahae on 4/18/2016.
 */
public class UnreliableChannel extends Observable{

    private static Random randomizer = new Random();

    private double lossRate;
    private double errorRate;
    private double lateRate;

    private List<byte[]> lateQueue = new ArrayList<>();

    public UnreliableChannel(double lossRate, double errorRate, double lateRate) {
        this.lossRate = lossRate;
        this.errorRate = errorRate;
        this.lateRate = lateRate;
    }

    public boolean send(byte[] data){
        byte[] corruptdata = data.clone();
        if (randomizer.nextDouble() < lateRate){
            lateQueue.add(data);
            return false;
        }
        if (randomizer.nextDouble() < lossRate)
            return false;
        if (randomizer.nextDouble() * lateQueue.size() > 1.0){
            int getIndex = (int) (randomizer.nextDouble()*lateQueue.size());
            byte[] getData = lateQueue.get(getIndex);
            lateQueue.remove(getIndex);
            this.setChanged();
            this.notifyObservers(getData);
        }

        for (int j=0; j < corruptdata.length; j++)
            if (randomizer.nextDouble() < errorRate)
                corruptdata[j] += randomizer.nextInt(256);
        this.setChanged();
        this.notifyObservers(corruptdata);
        return true;
    }
}