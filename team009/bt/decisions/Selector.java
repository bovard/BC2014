package team009.bt.decisions;

import battlecode.common.GameActionException;
import team009.bt.Node;
import team009.robot.TeamRobot;

/**
 * Created by mpaulson on 1/6/14.
 */
public abstract class Selector extends Decision {

    public Selector(TeamRobot robot) {
        super(robot);
    }

    @Override
    public boolean run() throws GameActionException {
        for (Node current : children) {
            if (current.pre()) {
                if (current.post()) {
                    current.reset();
                }
                return current.run();
            }
        }

        return true;
    }
}
