package team009;

import battlecode.common.Direction;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

public class MapUtils {

    public static Direction[] allDirs = Direction.values();

	/**
	 * Sorts the map locations in accending distance order
	 * @param locs
	 */
	public static void sort(MapLocation start, MapLocation[] locs, boolean ascending) {
		int[] dists = new int[locs.length];
		for (int i = 0, len = locs.length; i < len; i++) {
			dists[i] = locs[i].distanceSquaredTo(start);
		}

		sort(locs, dists, ascending);
	}

    public static Direction getRandomDir() {
        return allDirs[((int)(Math.random()*8))];
    }

    /**
     * Tells you if given location is on the Map
     */
    public static boolean isOnMap(MapLocation location, int width, int height) {
        if (location.x < 0 || location.x >= width) {
            return false;
        }
        if (location.y < 0 || location.y >= height) {
            return false;
        }
        return true;
    }

    /**
     * Will trim the value of the map loation to be within the map
     * @param location
     * @return
     */
    public static MapLocation trim(MapLocation location, RobotInformation info) {
        int x = location.x < 0 ? 0 : location.x;
        x = x > info.width ? info.width - 1 : x;
        int y = location.y < 0 ? 0 : location.y;
        y = y > info.height ? info.height - 1 : y;

        return new MapLocation(x, y);
    }

	/**
	 * Sorts the map in accending order.
	 * @param locs
	 * @param dists
	 */
	public static void sort(MapLocation[] locs, int[] dists, boolean ascending) {

		if (locs.length < 5) {
			int currentDist;
			MapLocation currentLoc;

			if (ascending) {

				//InsertionSort
				for (int i = 1, j, len = locs.length; i < len; i++) {
					currentDist = dists[i];
					currentLoc = locs[i];

					for (j = i; j > 0 && currentDist < dists[j - 1]; j--) {
						locs[j] = locs[j - 1];
						dists[j] = dists[j - 1];
					}

					dists[j] = currentDist;
					locs[j] = currentLoc;
				}
			} else {


				//InsertionSort
				for (int i = 1, j, len = locs.length; i < len; i++) {
					currentDist = dists[i];
					currentLoc = locs[i];

					for (j = i; j > 0 && currentDist > dists[j - 1]; j--) {
						locs[j] = locs[j - 1];
						dists[j] = dists[j - 1];
					}

					dists[j] = currentDist;
					locs[j] = currentLoc;
				}
			}
		} else {

			if (ascending) {
				//QuickSort
				_quicksort(dists, locs, 0, locs.length - 1);
			} else {
				_quicksort_descend(dists, locs, 0, locs.length - 1);
			}
		}
	}

	/**
	 * The quicksort algorithm uses recursion, maybe we can make it
	 * into while loops.
	 */
	private static void _quicksort(int[] locsDists, MapLocation[] locs, int low, int high) {
		int i = low, j = high;
		int pivot = locsDists[low + (high - low) / 2];

		while (i <= j) {

			while (locsDists[i] < pivot) {
				i++;
			}

			while (locsDists[j] > pivot) {
				j--;
			}

			if (i <= j) {
				int temp = locsDists[i];
				locsDists[i] = locsDists[j];
				locsDists[j] = temp;

				MapLocation t = locs[i];
				locs[i] = locs[j];
				locs[j] = t;

				i++;
				j--;
			}
		}

		if (low < j) {
			_quicksort(locsDists, locs, low, j);
		}
		if (i < high) {
			_quicksort(locsDists, locs, i, high);
		}
	}


	/**
	 * The quicksort algorithm uses recursion, maybe we can make it
	 * into while loops.
	 */
	private static void _quicksort_descend(int[] locsDists, MapLocation[] locs, int low, int high) {
		int i = low, j = high;
		int pivot = locsDists[low + (high - low) / 2];

		while (i <= j) {

			while (locsDists[i] > pivot) {
				i++;
			}

			while (locsDists[j] < pivot) {
				j--;
			}

			if (i <= j) {
				int temp = locsDists[i];
				locsDists[i] = locsDists[j];
				locsDists[j] = temp;

				MapLocation t = locs[i];
				locs[i] = locs[j];
				locs[j] = t;

				i++;
				j--;
			}
		}

		if (low < j) {
			_quicksort_descend(locsDists, locs, low, j);
		}
		if (i < high) {
			_quicksort_descend(locsDists, locs, i, high);
		}
	}

	/**
	 * Attempts to move in the direction with defusion.
	 * @param rc
	 * @param dir
	 * @return
	 */
	public static final int canMove(RobotController rc, Direction dir) {
		if (rc.canMove(dir)) {
			return CAN_MOVE;
		} else {
			return CANT_MOVE;
		}
	}

	public static final int CAN_MOVE = 0;
	public static final int CANT_MOVE = 1;
}
