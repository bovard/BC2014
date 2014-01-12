package flow.communication;

// TODO
import battlecode.common.MapLocation;

public class SoldierDecoder extends CommunicationDecoder {
	public int soldierType;
	public int group;
	public MapLocation loc;

    /**
     * Creates a soldier type that is focused around a map location.  This is either a farmer or a pastr.
     * @param loc
     */
	public SoldierDecoder(int soldierType, MapLocation loc) {
        this.soldierType = soldierType;
        this.group = 0;
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
		soldierType = data / SOLDIER_TYPE_MULTIPLIER;
		group = (data % SOLDIER_TYPE_MULTIPLIER) / GROUP_MULTIPLIER;

		int restOfData = data % GROUP_MULTIPLIER;
		if (restOfData % GROUP_MULTIPLIER > 0) {
			loc = new MapLocation(restOfData / X_LOCATION_MULTIPLIER, restOfData % X_LOCATION_MULTIPLIER);
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
		int data = soldierType * SOLDIER_TYPE_MULTIPLIER + group * GROUP_MULTIPLIER;

		if (loc != null) {
			data += loc.x * X_LOCATION_MULTIPLIER;
			data += loc.y;
		}

		return data;
	}

	//The soldier type multiplier
	private static final int SOLDIER_TYPE_MULTIPLIER = 10000000;
	private static final int GROUP_MULTIPLIER = 1000000;
	private static final int X_LOCATION_MULTIPLIER = 1000;
}
