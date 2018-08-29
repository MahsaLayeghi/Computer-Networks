package ir.arcinc.tls.Commons;

import java.util.*;

/**
 * Created by tahae on 4/18/2016.
 */
public class ReceiverApplication extends Observable implements Observer {
    private List<byte[]> data;
    private List<byte[]> receivedData = new LinkedList<>();
    private int iterator = 0;

    public ReceiverApplication(List<byte[]> data) {
        this.data = data;
    }

    @Override
    public void update(Observable o, Object arg) {
        receivedData.add((byte[]) arg);
    }

    private boolean match(byte[] a, byte[] b){
        if (a.length != b.length) {
            return false;
        }

        for (int i = 0; i < a.length; i++){
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    private boolean haveMatch(int i){
        for (byte[] aReceivedData : receivedData) {
            if (match(data.get(i), aReceivedData)) {
                return true;
            }
        }
        return false;
    }

    public double getJaccardCoefficient(){
        int match = 0;
        for (int i = 0; i < data.size(); i++){
            if (haveMatch(i)){
                match++;
//                System.out.println(i + ": match");
            }
            else {
//                System.out.println(i + ": mismatch");
            }
        }

        return ((double)(match)/(receivedData.size() + data.size() - match));
    }
}
