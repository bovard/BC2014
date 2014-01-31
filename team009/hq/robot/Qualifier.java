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
    // If both groups get big, the second can break away and huntZero.
    // group zero behaviors
    public boolean huddleZero = false;
    public boolean baseZero = false;
    public boolean huntZero = false;
    public boolean surroundZero = false;

    // group one behaviors
    public boolean huntComOne = false;
    public boolean baseOne = false;
    public boolean huddleOne = false;

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

