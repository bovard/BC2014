package flow.bt.decorators;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import flow.bt.Node;
import flow.robot.TeamRobot;

/**
 * Created by bovardtiberi on 1/2/14.
 */
public class NRoundsRepeater extends Decorator {
    private int round;
    private int startingRound;

    public NRoundsRepeater(TeamRobot robot, Node decorated, int round) {
        super(robot, decorated);
        this.round = round;
    }

    public void reset() {
        this.startingRound = Clock.getBytecodeNum();
    }

    public boolean run() throws GameActionException {
        if (!this.pre()) {
            return false;
        }

        if (this.decorated.post()) {
            this.decorated.reset();
        }

        return this.decorated.run();
    }

    public boolean post() {
        return Clock.getRoundNum() == startingRound + round;
    }
}
