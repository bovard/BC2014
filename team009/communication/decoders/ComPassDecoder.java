package team009.communication.decoders;

public class ComPassDecoder extends CommunicationDecoder {
    public int comChannel = 0;
    public ComPassDecoder(int data) {
        comChannel = data;
    }

    public int getData() {
        return comChannel;
    }
}
