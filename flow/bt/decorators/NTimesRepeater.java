package flow.bt.decorators;

import battlecode.common.GameActionException;
import flow.bt.Node;
import flow.robot.TeamRobot;

/**
 * Created by bovardtiberi on 1/2/14.
 */
public class NTimesRepeater extends Decorator {

    // the number of times to repeat the decorated node
    private int repetitions;
    private int repeated = 0;

    public NTimesRepeater(TeamRobot robot, Node decorated, int repetitions) {
        super(robot, decorated);
        this.repetitions = repetitions;
    }

    public void reset() {
        this.repeated = 0;
    }

    public boolean post() {
        return this.repeated == this.repetitions;
    }

    public boolean run() throws GameActionException {
        if (!this.pre()) {
            return false;
        }

        if (this.decorated.post()) {
            this.repeated += 1;
            this.decorated.reset();
        }

        return this.decorated.run();
    }


}
