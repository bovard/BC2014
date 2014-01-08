package team009.bt.decisions;
import battlecode.common.GameActionException;
import team009.bt.Node;
import team009.bt.behaviors.Pasture;
import team009.communication.Communicator;
import team009.communication.SoldierDecoder;
import team009.robot.GenericSoldier;

public class SoldierSelector extends Decision {

    private Node soldier = null;
    private SoldierDecoder decoder = null;

    public SoldierSelector(GenericSoldier robot) {
        super(robot);

        try {
            decoder = Communicator.ReadNewSoldier(rc);
            int type = decoder.soldierType;
            robot.group = decoder.group;
            robot.type = type;

            if (type == SOLDIER_TYPE_DUMB) {
                soldier = new DumbSoldierSelector(robot);
            } else if (type == SOLDIER_TYPE_PASTURE_CAPTURER) {
                soldier = new PastureSelector(robot, decoder.loc);
            } else if (type == SOLDIER_TYPE_HEADER) {
                soldier = new HerderSelector(robot, decoder.loc);
            } else if (type == SOLDIER_TYPE_PASTURE) {
                soldier = new Pasture(robot);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean pre() throws GameActionException {
        // There are never any preconditions that mean we shouldn't enter this state
        return true;
    }

    @Override
    public boolean post() throws GameActionException {
        // This state never completes
        return false;
    }

    @Override
    public void reset() throws GameActionException {
        // There is no state here so nothing to reset!

    }

    @Override
    public boolean run() throws GameActionException {
        if (rc.isActive()) {
            soldier.run();
        }
        return true;
    }

    public static int SOLDIER_TYPE_DUMB = 0;
    public static int SOLDIER_TYPE_PASTURE_CAPTURER = 1;
    public static int SOLDIER_TYPE_PASTURE = 2;
    public static int SOLDIER_TYPE_HEADER = 3;

    public static int SOLDIER_COUNT = 4;
}
