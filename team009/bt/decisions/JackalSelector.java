package team009.bt.decisions;

import battlecode.common.GameActionException;
import team009.bt.behaviors.EngageEnemy;
import team009.robot.soldier.Jackal;

public class JackalSelector extends Selector {

    public JackalSelector(Jackal robot) {
        super(robot);
        addChild(new LowHPSelector(robot));
        addChild(new EngageEnemy(robot));
        //addChild(new DefendHQ(robot));
        //addChild(new GroupHuntPasture());

    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }

    @Override
    public boolean post() throws GameActionException {
        return false;
    }

    @Override
    public void reset() throws GameActionException {

    }
}
