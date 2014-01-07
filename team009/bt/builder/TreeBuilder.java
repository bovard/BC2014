package team009.bt.builder;

import team009.bt.Node;
import team009.bt.behaviors.EngageEnemy;
import team009.bt.behaviors.MoveRandom;
import team009.bt.decisions.DumbSoldierSelector;
import team009.bt.decisions.SoldierTypeDecision;
import team009.robot.GenericSoldier;
import team009.robot.TeamRobot;

public class TreeBuilder {

    public static Node getExampleTree(TeamRobot robot) {
        return null;
    }

    public static Node getSoldierTree(GenericSoldier robot) {
        Node root = new SoldierTypeDecision(robot);
        Node dumbSelect = new DumbSoldierSelector(robot);
        dumbSelect.addChild(new EngageEnemy(robot));
        dumbSelect.addChild(new MoveRandom(robot));
        root.addChild(dumbSelect);
        return root;
    }
}
