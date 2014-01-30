package team009.hq.robot;

import battlecode.common.*;
import team009.BehaviorConstants;
import team009.RobotInformation;
import team009.bt.Node;
import team009.hq.HQPreprocessor;
import team009.hq.bt.selectors.QualifierSelector;
import team009.communication.bt.HQCom;
import team009.utils.MilkInformation;

public class Qualifier extends HQPreprocessor {
    // Default behavior is one base and both groups defend it
    // If both groups get big, the second can break away and hunt.
    public boolean huddle = false;
    public boolean oneBase = false;
    public boolean soundTower = false;
    public boolean hunt = false;
    public boolean surround = false;
    public int groupToSpawn = 0;

    public Qualifier(RobotController rc, RobotInformation info) {
        super(rc, info);
        treeRoot = getTreeRoot();
        comRoot = new HQCom(this);
    }

    @Override
    protected Node getTreeRoot() {
        return new QualifierSelector(this);
    }
}

