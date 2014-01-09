package team009.bt.behaviors;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotInfo;
import team009.combat.DumbCombat;
import team009.communication.Communicator;
import team009.communication.GroupCommandDecoder;
import team009.navigation.BugMove;
import team009.robot.soldier.BaseSoldier;

public class GroupEngageEnemy extends Behavior {
    BaseSoldier gs;
    BugMove move;
    public GroupEngageEnemy(BaseSoldier robot) {
        super(robot);
        gs = robot;
        move = new BugMove(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        boolean attack = false;
        if (!gs.seesEnemy && Communicator.ReadRound(gs.round)) {
            GroupCommandDecoder dec = Communicator.ReadFromGroup(rc, gs.group);

            if (dec.command == BaseSoldier.ATTACK) {
                attack = true;
                move.setDestination(dec.location);
            }
        }
        return gs.seesEnemy || attack;
    }

    @Override
    public boolean post() throws GameActionException {
        return false;
    }

    @Override
    public void reset() throws GameActionException {

    }

    @Override
    public boolean run() throws GameActionException {
        if (Communicator.WriteRound(gs.round) && gs.enemies.length > 0) {
            RobotInfo info = rc.senseRobotInfo(gs.enemies[0]);
            Communicator.WriteToGroup(rc, gs.group, BaseSoldier.ATTACK, info.location);
        }

        // Attack IF we see enemies, else move toward enemy call
        if (gs.enemies.length > 0) {
            DumbCombat.Attack(rc, gs.enemies, gs.currentLoc);
        } else {
            // we are in this state because of communicator.
            if (move.destination != null) {
                move.move();
            }
        }


        return true;
    }
}

