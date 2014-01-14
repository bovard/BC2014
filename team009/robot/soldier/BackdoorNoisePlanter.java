package team009.robot.soldier;

import battlecode.common.MapLocation;
import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.utils.MapQuadrantUtils;
import team009.utils.SmartMapLocationArray;

public class BackdoorNoisePlanter extends BaseSoldier {

    public SmartMapLocationArray wayPoints = new SmartMapLocationArray();
    private int currWayPoint = -1;

    public BackdoorNoisePlanter(RobotController rc, RobotInformation info) {
        super(rc, info);
        MapQuadrantUtils.hq = info.hq;
        MapQuadrantUtils.enemyHq = info.enemyHq;
        MapQuadrantUtils.width = info.width;
        MapQuadrantUtils.height = info.height;
        _createWayPoints();
    }

    public MapLocation getNextWayPoint() {
        currWayPoint++;
        return wayPoints.arr[currWayPoint];

    }

    private void _createWayPoints() {
        // TODO: Replace with the MapQuadrant Utils one... :/
        //   0,0     0,width
        //	    2 | 1
        //      - . -
        //      3 | 4
        // height,0   height,width
        //  ( I know this is odd, don't want to turn it around in teh head)
        int quadHQ = MapQuadrantUtils.getMapQuadrant(info.hq.x, info.hq.y);
        System.out.println("Our hq is in quadrent " + quadHQ);
        int quadEnemyHQ = MapQuadrantUtils.getMapQuadrant(info.enemyHq.x, info.enemyHq.y);
        System.out.println("Enemy hq is in quadrent " + quadEnemyHQ);

        if (quadHQ == 1) {
            if (quadEnemyHQ == 1) {
                wayPoints.add(MapQuadrantUtils.getMapCornerForQuadrant(1));
            } else if (quadEnemyHQ == 2) {
                wayPoints.add(MapQuadrantUtils.getMapCornerForQuadrant(4));
                wayPoints.add(MapQuadrantUtils.getMapCornerForQuadrant(3));
                wayPoints.add(new MapLocation(0, info.enemyHq.y));
            } else if (quadEnemyHQ == 3) {
                wayPoints.add(MapQuadrantUtils.getMapCornerForQuadrant(1));
                wayPoints.add(MapQuadrantUtils.getMapCornerForQuadrant(2));
                wayPoints.add(new MapLocation(0, info.enemyHq.y));
            } else {
                wayPoints.add(MapQuadrantUtils.getMapCornerForQuadrant(2));
                wayPoints.add(MapQuadrantUtils.getMapCornerForQuadrant(3));
                wayPoints.add(new MapLocation(info.enemyHq.x, info.height - 1));
            }
        } else if (quadHQ == 2) {
            if (quadEnemyHQ == 1) {
                wayPoints.add(MapQuadrantUtils.getMapCornerForQuadrant(3));
                wayPoints.add(MapQuadrantUtils.getMapCornerForQuadrant(4));
                wayPoints.add(new MapLocation(info.width -1 , info.enemyHq.y));
            } else if (quadEnemyHQ == 2) {
                wayPoints.add(MapQuadrantUtils.getMapCornerForQuadrant(2));
            } else if (quadEnemyHQ == 3) {
                wayPoints.add(MapQuadrantUtils.getMapCornerForQuadrant(1));
                wayPoints.add(MapQuadrantUtils.getMapCornerForQuadrant(4));
                wayPoints.add(new MapLocation(info.enemyHq.x, info.height - 1));
            } else {
                wayPoints.add(MapQuadrantUtils.getMapCornerForQuadrant(2));
                wayPoints.add(MapQuadrantUtils.getMapCornerForQuadrant(1));
                wayPoints.add(new MapLocation(info.width - 1 , info.enemyHq.y));
            }
            //		2 | 1
            //      - . -
            //      3 | 4

        } else if (quadHQ == 3) {
            if (quadEnemyHQ == 1) {
                wayPoints.add(MapQuadrantUtils.getMapCornerForQuadrant(3));
                wayPoints.add(MapQuadrantUtils.getMapCornerForQuadrant(2));
                wayPoints.add(new MapLocation(info.enemyHq.x, 0));
            } else if (quadEnemyHQ == 2) {
                wayPoints.add(MapQuadrantUtils.getMapCornerForQuadrant(4));
                wayPoints.add(MapQuadrantUtils.getMapCornerForQuadrant(1));
                wayPoints.add(new MapLocation(info.enemyHq.x, 0));
            } else if (quadEnemyHQ == 3) {
                wayPoints.add(MapQuadrantUtils.getMapCornerForQuadrant(3));
            } else {
                wayPoints.add(MapQuadrantUtils.getMapCornerForQuadrant(2));
                wayPoints.add(MapQuadrantUtils.getMapCornerForQuadrant(1));
                wayPoints.add(new MapLocation(info.width - 1, info.enemyHq.y));
            }
        } else {
            if (quadEnemyHQ == 1) {
                wayPoints.add(MapQuadrantUtils.getMapCornerForQuadrant(3));
                wayPoints.add(MapQuadrantUtils.getMapCornerForQuadrant(2));
                wayPoints.add(new MapLocation(info.enemyHq.x, 0));
            } else if (quadEnemyHQ == 2) {
                wayPoints.add(MapQuadrantUtils.getMapCornerForQuadrant(4));
                wayPoints.add(MapQuadrantUtils.getMapCornerForQuadrant(3));
                wayPoints.add(new MapLocation(0, info.enemyHq.y));
            } else if (quadEnemyHQ == 3) {
                wayPoints.add(MapQuadrantUtils.getMapCornerForQuadrant(1));
                wayPoints.add(MapQuadrantUtils.getMapCornerForQuadrant(2));
                wayPoints.add(new MapLocation(0, info.enemyHq.y));
            } else {
                wayPoints.add(MapQuadrantUtils.getMapCornerForQuadrant(4));
            }
        }

        wayPoints.add(info.enemyHq);
        System.out.println("Now we have " + wayPoints.size() + " way Points");
        for (int i = 0; i < wayPoints.size(); i++) {
            System.out.println(wayPoints.get(i).toString());
        }
    }

    @Override
    protected Node getTreeRoot() {
        return null;
    }
}
