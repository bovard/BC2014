package team009.robot;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.decisions.HQSelector;

public class HQ extends TeamRobot {

    class SoldierInfo {
        int group;
        int type;
        int count;
    }

    // This will adjust to how many soldiers we have.
    int soldierCount = 4;
    SoldierInfo[] soldierGroups;

    public HQ(RobotController rc, RobotInformation info) {
        super(rc, info);

        int maxSoldiers = soldierCount * RobotInformation.INFORMATION_ROUND_MOD;
        soldierGroups = new SoldierInfo[maxSoldiers];
        for (int i = 0; i < maxSoldiers; i++) {
            soldierGroups[i] = new SoldierInfo();
            soldierGroups[i].group = i % RobotInformation.INFORMATION_ROUND_MOD;
            soldierGroups[i].type = i / RobotInformation.INFORMATION_ROUND_MOD;
        }
    }

    @Override
    protected Node getTreeRoot() {
        Node root = new HQSelector(this);

        return root;
    }

    @Override
    public void environmentCheck() throws GameActionException {
        super.environmentCheck();

        if ((Clock.getRoundNum() - 1) % RobotInformation.INFORMATION_ROUND_MOD == 0) {

        }
    }
}
