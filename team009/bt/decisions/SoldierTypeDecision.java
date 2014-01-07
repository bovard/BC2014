package team009.bt.decisions;
import battlecode.common.Direction;
import battlecode.common.GameActionException;
import team009.bt.Node;
import team009.bt.behaviors.DumbSoldier;
import team009.communication.Communicator;
import team009.communication.SoldierDecoder;
import team009.robot.TeamRobot;

public class SoldierTypeDecision extends Decision {

    private Node soldier = null;

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
        // Spawn a guy at a random location
        if (rc.isActive()) {

            // select the soldier from the com
            if (soldier == null) {
                SoldierDecoder decoder = Communicator.ReadNewSoldier(rc);

                if (decoder.soldierType == SOLDIER_TYPE_DUMB) {
                    children.add(new DumbSoldier(robot, Direction.NORTH));
                }
            }

            // TODO: Is this right? because this does not feel right
            // TODO: This may be awkward because of the fact i don't want to initialize a bunch of nodes.
            children.get(0).run();
        }
        return true;
    }

    public static int SOLDIER_TYPE_DUMB = 0;
}
