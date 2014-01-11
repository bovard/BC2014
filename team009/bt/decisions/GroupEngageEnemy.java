package team009.bt.decisions;

import battlecode.common.GameActionException;
import team009.bt.behaviors.EngageEnemy;
import team009.bt.behaviors.MoveToLocation;
import team009.robot.soldier.BaseSoldier;

public class GroupEngageEnemy extends Selector {
    BaseSoldier gs;
    MoveToLocation location;

    public GroupEngageEnemy(BaseSoldier robot) {
        super(robot);
        gs = robot;
        location = new MoveToLocation(robot);

        children.add(new EngageEnemy(robot));
        children.add(location);
    }

    @Override
    public boolean pre() throws GameActionException {
        if (gs.decoder != null) {
            rc.setIndicatorString(1, "Check it: " + gs.seesEnemy + " || (" + (gs.decoder != null) + " && " + gs.decoder.command + " == " + BaseSoldier.ATTACK + "))");
        }
        if (gs.decoder != null && gs.decoder.command == BaseSoldier.ATTACK) {
            location.setDestination(gs.decoder.location);
            return true;
        }

        return gs.seesEnemy;
    }

    @Override
    public boolean post() throws GameActionException {
        return false;
    }

    @Override
    public void reset() throws GameActionException {
    }

    @Override
    public boolean run() throws GameActionException {
        // Communication can disable active state of robot.
        if (rc.isActive()) {
            super.run();
        }
        return true;
    }
}

