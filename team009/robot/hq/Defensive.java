package team009.robot.hq;

import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.decisions.communication.HQCom;
import team009.bt.decisions.hq.DefensiveSelector;

public class Defensive extends HQ {
    public Defensive(RobotController rc, RobotInformation info) {
        super(rc, info);
        treeRoot = getTreeRoot();
        comRoot = new HQCom(this);
    }

    @Override
    protected Node getTreeRoot() {
        return new DefensiveSelector(this);
    }
}
