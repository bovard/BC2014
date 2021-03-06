package team009.communication.bt.behaviors;

import battlecode.common.Clock;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import team009.communication.Communicator;
import team009.communication.decoders.SoldierCountDecoder;
import team009.communication.decoders.TwoWayDecoder;
import team009.robot.TeamRobot;
import team009.robot.soldier.ToySoldier;

public class SoldierReadCom extends ReadBehavior {
    ToySoldier soldier;
    static MapLocation zero = new MapLocation(0, 0);

    public SoldierReadCom(ToySoldier soldier) {
        super(soldier);
        this.soldier = soldier;
    }

    public boolean run() throws GameActionException {

        // Updates the decoder with any information.
        soldier.groupCommand = Communicator.ReadFromGroup(rc, soldier.group, Communicator.GROUP_SOLDIER_CHANEL);
        soldier.hqCommand = Communicator.ReadFromGroup(rc, soldier.group, Communicator.GROUP_HQ_CHANNEL);

        rc.setIndicatorString(0, "GroupCommand: " + soldier.groupCommand.toString());
        rc.setIndicatorString(1, "HQCommand: " + soldier.hqCommand.toString());
        //rc.setIndicatorString(2, "" + Clock.getRoundNum());

        if (soldier.groupCommand.command > 0 && !soldier.comLocation.equals(zero)) {
            soldier.comLocation = soldier.groupCommand.location;
            soldier.comCommand = soldier.groupCommand.command;
        } else if (soldier.hqCommand.command > 0) {
            soldier.comLocation = soldier.hqCommand.location;
            soldier.comCommand = soldier.hqCommand.command;
        } else {
            soldier.comCommand = 0;
        }

        // Gets soldier count in group
        SoldierCountDecoder dec = Communicator.ReadTypeAndGroup(rc, TeamRobot.SOLDIER_TYPE_TOY_SOLDIER, robot.group);
        soldier.myGroupCount = dec.count;
        soldier.groupCentroid = dec.centroid;

        if (soldier.locationRequested) {
            TwoWayDecoder twoWay = Communicator.ReadTwoWayCommunicate(rc, soldier.twoWayChannel);
            if (twoWay.command == TeamRobot.LOCATION_RESULT) {
                soldier.locationResult = twoWay.to;
            } else {
                soldier.locationResult = null;
            }
            soldier.locationRequested = false;
        } else {
            soldier.locationResult = null;
        }

        return true;
    }
}

