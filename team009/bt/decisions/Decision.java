package team009.bt.decisions;

import team009.bt.Node;
import team009.robot.TeamRobot;

public abstract class Decision extends Node {
    // store off which of our child nodes we last ran
    protected Node lastRun;

    public Decision(TeamRobot robot) {
        super(robot);
    }
}
