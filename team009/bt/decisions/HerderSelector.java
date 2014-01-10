package team009.bt.decisions;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.bt.Node;
import team009.bt.behaviors.HerdReplace;
import team009.robot.soldier.Herder;

public class HerderSelector extends Decision {
    protected MapLocation pastureLocation;
    protected Node engage;
    protected Node heard;
    protected Node replace;

    public HerderSelector(Herder robot, MapLocation pastureLocation) {
        super(robot);
        this.pastureLocation = pastureLocation;
        engage = new GroupEngageEnemy(robot);
        heard = new HerdSequence(robot, pastureLocation);
        replace = new HerdReplace(robot, pastureLocation);
    }

    @Override
    public boolean pre() throws GameActionException {
        // no prereqs
        return true;
    }

    @Override
    public boolean post() throws GameActionException {
        // never finishes
        return false;
    }

    @Override
    public void reset() throws GameActionException {
        // nothing to reset
    }

    @Override
    public boolean run() throws GameActionException {
        // if there is an enemy, engage or run to HQ
        if (engage.pre()) {
            return engage.run();
        }

        // if our pastr has been killed, replace it!
        if (replace.pre()) {
            return replace.run();
        }

        // Otherwise pick a direction from the pasture, go till you can't go anymore (or max distance)
        // then turn around and move back
        return heard.run();

    }
}
