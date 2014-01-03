package team009.behavior.decorators;

import battlecode.common.GameActionException;
import team009.behavior.Node;
import team009.robot.TeamRobot;

/**
 * Created by bovardtiberi on 1/2/14.
 */
public abstract class Decorator extends Node {
    protected Node decorated;

    public Decorator(TeamRobot robot, Node decorated) {
        super(robot);
        this.decorated = decorated;
    }

    public boolean pre() throws GameActionException {
        return decorated.pre();
    }

}
