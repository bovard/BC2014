package team009.robot;

import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.bt.behaviors.pasture.Pasture;
import team009.communication.Communicator;
import team009.communication.bt.BuildingCom;
import team009.communication.bt.behaviors.PastrWrite;

public class Pastr extends TeamRobot {
    public Pastr(RobotController rc, RobotInformation info) {
        super(rc, info);
        treeRoot = getTreeRoot();
        comRoot = new BuildingCom(this, new PastrWrite(this));

        try {
            twoWayChannel = Communicator.ReadPassComChannel(rc, false);
        } catch (Exception e) {
            System.out.println("OMG!  We could nto read our pass channel");
        }
    }

    @Override
    protected Node getTreeRoot() {
        return new Pasture(this);
    }
}
