package flow.postProcessing;

import battlecode.common.RobotController;

public abstract class BasePostProcessor {
    protected RobotController rc;
    // if we're done processing
    protected boolean done;

    public BasePostProcessor(RobotController rc) {
        this.rc = rc;
    }

    /**
     * Should calculate things, pausing before roundLimit is met
     * @param roundLimit should stop before roundLimit is met
     * @return if it's done processing
     */
    public abstract boolean calculate(int roundLimit);

    public boolean isDone() {
        return done;
    }

}
