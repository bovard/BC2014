package team009.robot;

import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.behaviors.DumbSoldier;
import team009.bt.decisions.SoldierTypeDecision;

/**
 * Created by bovardtiberi on 1/6/14.
 */
public class GenericSoldier extends TeamRobot {
    public GenericSoldier(RobotController rc, RobotInformation info) {
        super(rc, info);
    }

    @Override
    protected Node getTreeRoot() {
        Node root = new SoldierTypeDecision(this);
        root.addChild(new DumbSoldier(this));

        return root;
    }
}
