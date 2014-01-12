package flow.robot;

import battlecode.common.RobotController;
import flow.RobotInformation;
import flow.bt.Node;
import flow.bt.behaviors.Pasture;

public class Pastr extends TeamRobot {
    public Pastr(RobotController rc, RobotInformation info) {
        super(rc, info);
        treeRoot = getTreeRoot();
    }

    @Override
    protected Node getTreeRoot() {
        return new Pasture(this);
    }
}
