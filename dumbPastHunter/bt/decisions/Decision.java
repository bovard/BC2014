package dumbPastHunter.bt.decisions;

import dumbPastHunter.bt.Node;
import dumbPastHunter.robot.TeamRobot;

public abstract class Decision extends Node {
    // store off which of our child nodes we last ran
    protected int lastRun;

    public Decision(TeamRobot robot) {
        super(robot);
    }
}
