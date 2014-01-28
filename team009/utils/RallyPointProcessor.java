package team009.utils;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.RobotInformation;
import team009.hq.HQPreprocessor;

public class RallyPointProcessor {
    public HQPreprocessor hq;
    public boolean finished = false;
    public MapLocation rallyPoint;
    public MapLocation center;


    public RobotInformation info;

    public RallyPointProcessor(HQPreprocessor hq) {
        this.hq = hq;
        this.info = hq.info;
    }

    public void calc() throws GameActionException {
        int halfWidth = info.width / 2;
        center = new MapLocation(halfWidth, info.height / 2);
        MapLocation hq = info.hq;
        Direction dir = info.enemyDir;
        int[][] map = this.hq.map.map;
        int width = info.width;
        int height = info.height;

        for (int i = 0; i < 8; i++) {
            boolean found = true;
            MapLocation curr = hq;
            for (int j = 0; j < 3; j++) {
                curr = curr.add(dir);
                if (curr.x < 0 || curr.x > width || curr.y < 0 || curr.y > height && map[curr.x][curr.y] == 1) {
                    found = false;
                    break;
                }
            }

            if (found) {
                rallyPoint = curr;
                return;
            }
            dir = dir.rotateRight();
        }

        Direction toHome = center.directionTo(info.hq);
        MapLocation curr = center;
        for (int i = 0; i < halfWidth; i++) {
            curr = curr.add(toHome);
            if (map[curr.x][curr.y] == 0) {
                rallyPoint = curr;
                break;
            }
        }

        finished = true;
    }
}
