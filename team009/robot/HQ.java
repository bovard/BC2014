package team009.robot;

import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.behaviors.HQBase;

public class HQ extends TeamRobot {

    public HQ(RobotController rc, RobotInformation info) {
        super(rc, info);
    }

    @Override
    protected Node getTreeRoot() {
        Node root = new HQBase(this);

        return root;
    }
}
