package team009.behavior.decisions;

import team009.behavior.Node;
import team009.robot.TeamRobot;

public abstract class Decision extends Node {
    // store off which of our child nodes we last ran
    protected Node lastRun;

    public Decision(TeamRobot robot) {
        super(robot);
    }
}
