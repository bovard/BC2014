package team009.communication;

import team009.robot.TeamRobot;
import team009.hq.HQSpawner;

public class TeamMemoryManager {
    public static int PASTURES_KILLED = 0;
    public static int ROUND = 1;

    protected TeamRobot robot;

    public int pasturesKilled = 0;

    public TeamMemoryManager(TeamRobot robot) {
        this.robot = robot;
    }


    public void writeMemeory() {
        robot.rc.setTeamMemory(ROUND, robot.round);
    }

    public void markPastureKilled() {
        // TODO: not sure what the best way to do this is, shoud the pastures
        // call this when they die or the HQ keep track of them and update it?
        pasturesKilled++;
        robot.rc.setTeamMemory(PASTURES_KILLED, pasturesKilled);
    }

    public static int getHQStrategy() {
        // Memory defaults to 0
        // TODO: Use this
        /*
        long [] memory = robot.rc.getTeamMemory();
        if (memory[PASTURES_KILLED] > 5) {
            return HQSpawner.DEFENSIVE_PASTURE;
        } else if (memory[ROUND] != 0 && memory[ROUND] < 1000) {
            return HQSpawner.PASTURE_HUNTING;
        } else {
            //return HQSpawner.BALANCED;
            return HQSpawner.PASTURE_HUNTING;
        }
        */
        return HQSpawner.BALANCED;
    }

}
