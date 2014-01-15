package team009.bt.decisions.soldier;

import battlecode.common.GameActionException;
import team009.bt.behaviors.soldier.CongragateInHQCorner;
import team009.bt.behaviors.soldier.EngageEnemy;
import team009.bt.behaviors.soldier.EscapeHQ;
import team009.bt.behaviors.soldier.JackalPastrHunt;
import team009.bt.decisions.Selector;
import team009.robot.soldier.Jackal;

public class JackalSelector extends Selector {

    public JackalSelector(Jackal robot) {
        super(robot);
        addChild(new EscapeHQ(robot));
        addChild(new EngageEnemy(robot));
        addChild(new JackalPastrHunt(robot));
        addChild(new CongragateInHQCorner(robot));

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
