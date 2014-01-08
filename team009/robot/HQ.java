package team009.robot;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.decisions.HQSelector;
import team009.bt.decisions.SoldierSelector;
import team009.communication.Communicator;
import team009.communication.SoldierCountDecoder;

public class HQ extends TeamRobot {

    // This will adjust to how many soldiers we have.
    private int maxSoldiers;
    SoldierCountDecoder[] soldierCounts;

    public HQ(RobotController rc, RobotInformation info) {
        super(rc, info);

        maxSoldiers = SoldierSelector.SOLDIER_COUNT * SoldierSelector.MAX_GROUP_COUNT;
        soldierCounts = new SoldierCountDecoder[maxSoldiers];
    }

    @Override
    protected Node getTreeRoot() {
        Node root = new HQSelector(this);

        return root;
    }

    @Override
    public void environmentCheck() throws GameActionException {
        super.environmentCheck();

        if (Communicator.ReadRound()) {
            int groupCount = SoldierSelector.MAX_GROUP_COUNT;

            // TODO: $DEBUG$
            String soldierString = "";
            for (int i = 0; i < maxSoldiers; i++) {

                int group = i % groupCount;
                int type = i / groupCount;
                soldierCounts[i] = Communicator.ReadTypeAndGroup(rc, type, group);
                Communicator.ClearChannel(rc, type, group);

                if (group == 0) {
                    soldierString += "Type: " + soldierCounts[i].soldierType + " : Count: " + soldierCounts[i].count + " ";
                }
            }

            rc.setIndicatorString(1, soldierString);
        }
    }
}
