package team009.robot.soldier;

import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.decisions.HerderSelector;

public class Herder extends BaseSoldier {
    private MapLocation pastrLocation;

    public Herder(RobotController rc, RobotInformation info, MapLocation location) {
        super(rc, info);
        System.out.print("Init Herder with pastr location: ");
        System.out.println(location.toString());
        treeRoot = getTreeRoot();
    }

    @Override
    protected Node getTreeRoot() {
        return new HerderSelector(this, pastrLocation);
    }
}
