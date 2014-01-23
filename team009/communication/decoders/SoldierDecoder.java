package team009.communication.decoders;

// TODO
import battlecode.common.MapLocation;

public class SoldierDecoder extends CommunicationDecoder {
	public int soldierType;
	public int group;
    public int comChannel;
	public MapLocation loc;

    /**
     * Creates a soldier type that is focused around a map location.  This is either a farmer or a pastr.
     * @param loc
     */
    public SoldierDecoder(int soldierType, int group, int comChannel, MapLocation loc) {
        this.soldierType = soldierType;
        this.loc = loc;
        this.group = group;
        this.comChannel = comChannel;
    }

    /**
     * Resets data from the data coming in
     */
	public SoldierDecoder(int data) {
        comChannel = data / SOLDIER_COM_CHANNEL;
		soldierType = (data % SOLDIER_COM_CHANNEL) / SOLDIER_TYPE_MULTIPLIER;
		group = (data % SOLDIER_TYPE_MULTIPLIER) / GROUP_MULTIPLIER;

		int restOfData = data % GROUP_MULTIPLIER;
		if (restOfData % GROUP_MULTIPLIER > 0) {
			loc = MapDecoder.getLocationFromData(restOfData);
		}
	}

	/**
	 * Strinifies this
	 */
	@Override
	public String toString() {
		return "Soldier Decoder: " + loc + " of " + soldierType + " : " + group + " : " + comChannel;
	}

	@Override
	public int getData() {
		int data = comChannel * SOLDIER_COM_CHANNEL + soldierType * SOLDIER_TYPE_MULTIPLIER + group * GROUP_MULTIPLIER +
                (loc == null ? 0 : MapDecoder.getDataFromLocation(loc));

		return data;
	}

	//The soldier type multiplier
    private static final int GROUP_MULTIPLIER = MapDecoder.COMMAND_MULTIPLIER; // 10,000
	private static final int SOLDIER_TYPE_MULTIPLIER = GROUP_MULTIPLIER * 10; // 100,000
    private static final int SOLDIER_COM_CHANNEL = SOLDIER_TYPE_MULTIPLIER * 10; // 1,000,000 : Room for 2400's
}
