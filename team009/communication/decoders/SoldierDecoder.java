package team009.communication.decoders;

// TODO
import battlecode.common.MapLocation;
import team009.communication.decoders.CommunicationDecoder;

public class SoldierDecoder extends CommunicationDecoder {
	public int soldierType;
	public int group;
    public int comChannel;
	public MapLocation loc;

    /**
     * Creates a soldier type that is focused around a map location.  This is either a farmer or a pastr.
     * @param loc
     */
	public SoldierDecoder(int soldierType, MapLocation loc) {
        this.soldierType = soldierType;
        this.group = 0;
        this.comChannel = 0;
		this.loc = loc;
	}

    /**
     * Creates a soldier type that is focused around a map location.  This is either a farmer or a pastr.
     * @param loc
     */
    public SoldierDecoder(int soldierType, int group, MapLocation loc) {
        this.soldierType = soldierType;
        this.loc = loc;
        this.group = group;
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
		return "Soldier Decoder: " + loc + " of " + soldierType + " : " + group;
	}

	@Override
	public int getData() {
		int data = soldierType * SOLDIER_TYPE_MULTIPLIER + group * GROUP_MULTIPLIER +
                (loc == null ? 0 : MapDecoder.getDataFromLocation(loc));

		return data;
	}

	//The soldier type multiplier
    private static final int GROUP_MULTIPLIER = MapDecoder.COMMAND_MULTIPLIER; // 10,000
	private static final int SOLDIER_TYPE_MULTIPLIER = GROUP_MULTIPLIER * 10; // 100,000
    private static final int SOLDIER_COM_CHANNEL = SOLDIER_TYPE_MULTIPLIER * 10; // 1,000,000 : Room for 2400's
}
