package team009.toyBT.behaviors;

import team009.bt.behaviors.Behavior;
import battlecode.common.GameActionException;
import team009.robot.TeamRobot;
import team009.robot.soldier.ToySoldier;

public class GroupDestruct extends Behavior {
    ToySoldier soldier;

    public GroupDestruct(ToySoldier soldier) {
        super(soldier);
        this.soldier = soldier;
    }

    @Override
    public boolean pre() throws GameActionException {
        return soldier.comCommand == TeamRobot.DESTRUCT;
    }

    public boolean run() throws GameActionException {
        rc.selfDestruct();
        return true;
    }
}

