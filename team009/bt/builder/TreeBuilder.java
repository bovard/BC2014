package team009.bt.builder;

import team009.bt.Node;
import team009.bt.behaviors.DumbSoldier;
import team009.bt.decisions.SoldierTypeDecision;
import team009.robot.TeamRobot;

public class TreeBuilder {

    public static Node getExampleTree(TeamRobot robot) {
        return null;
    }

    public static Node getSoldierTree(TeamRobot robot) {
        Node root = new SoldierTypeDecision(robot);
        root.addChild(new DumbSoldier(robot));
        return root;
    }
}
