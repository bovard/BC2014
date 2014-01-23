package team009.toyBT.behaviors;

import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotType;
import team009.bt.behaviors.Behavior;
import team009.navigation.BugMove;
import team009.robot.TeamRobot;
import team009.robot.soldier.ToySoldier;

public class ToyHerdSoundCapture extends Behavior {
    protected BugMove move;
    protected MapLocation soundLocation;
    protected ToySoldier soldier;

    public ToyHerdSoundCapture(ToySoldier robot) {
        super(robot);
        soldier = robot;
        move = new BugMove(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return soldier.comCommand == TeamRobot.CAPTURE_SOUND;
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
        if (robot.currentLoc.equals(soldier.comLocation)) {
            robot.rc.construct(RobotType.NOISETOWER);
        } else {
            if (soundLocation == null) {
                soundLocation = soldier.comLocation;
                move.setDestination(soundLocation);
            }
            move.move();
        }
        return true;
    }
}

