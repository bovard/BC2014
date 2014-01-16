package team009.toyBT.micro;

import battlecode.common.GameActionException;
import team009.bt.behaviors.Behavior;
import team009.robot.soldier.ToySoldier;

public class EnageLoneEnemyNoise extends Behavior {
    public EnageLoneEnemyNoise(ToySoldier robot) {
        super(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        return ((ToySoldier)robot).seesEnemyNoise;
    }

    @Override
    public boolean run() throws GameActionException {
        return false;
    }
}
