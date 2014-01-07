package team009.bt.decisions;
import battlecode.common.GameActionException;
import team009.bt.Node;
import team009.communication.Communicator;
import team009.communication.SoldierDecoder;
import team009.robot.TeamRobot;

public class SoldierTypeDecision extends Decision {

    private Node soldier = null;
    private SoldierDecoder decoder = null;

    public SoldierTypeDecision(TeamRobot robot) {
        super(robot);
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
        if (decoder == null) {
            decoder = Communicator.ReadNewSoldier(rc);
        }
        if (rc.isActive()) {
            // select the soldier from the com
            if (decoder.soldierType == SOLDIER_TYPE_DUMB) {
                children.get(SOLDIER_TYPE_DUMB).run();
            }
        }
        return true;
    }

    public static int SOLDIER_TYPE_DUMB = 0;
}
