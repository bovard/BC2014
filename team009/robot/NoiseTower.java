package team009.robot;

import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.decisions.noise.NoiseSelector;
import team009.communication.Communicator;
import team009.communication.bt.BuildingCom;
import team009.communication.bt.behaviors.NoiseTowerWrite;

public class NoiseTower extends TeamRobot {
    public NoiseTower(RobotController rc, RobotInformation info) {
        super(rc, info);
        treeRoot = getTreeRoot();
        comRoot = new BuildingCom(this, new NoiseTowerWrite(this));

        try {
            twoWayChannel = Communicator.ReadPassComChannel(rc, true);
        } catch (Exception e) {
            System.out.println("OMG!  We could nto read our pass channel");
        }
    }

    @Override
    protected Node getTreeRoot() {
        return new NoiseSelector(this);
    }
}
