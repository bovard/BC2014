package team009.communication;

public class SoldierCountDecoder extends CommunicationDecoder {
    public int soldierType;
    public int group;
    public int count;

    public SoldierCountDecoder(int soldierType, int group) {
        this.soldierType = soldierType;
        this.group = group;
        count = 0;
    }

    public SoldierCountDecoder(int data) {
        group = data / GROUP_MULTIPLIER;
        soldierType = (data % GROUP_MULTIPLIER) / SOLDIER_TYPE_MULTIPLIER;
        count = data / 25;
    }

    @Override
    public int getData() {
        return soldierType * SOLDIER_TYPE_MULTIPLIER + group * GROUP_MULTIPLIER + count;
    }

    public static final int GROUP_MULTIPLIER = 10000;
    public static final int SOLDIER_TYPE_MULTIPLIER = 100;
}
