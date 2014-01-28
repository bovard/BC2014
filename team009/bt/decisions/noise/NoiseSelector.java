package team009.bt.decisions.noise;

import battlecode.common.GameActionException;
import team009.bt.behaviors.noise.CounterTower;
import team009.bt.behaviors.noise.SoundTowerBehavior;
import team009.bt.behaviors.noise.SoundTowerBehaviorBrent;
import team009.bt.decisions.Selector;
import team009.robot.NoiseTower;

public class NoiseSelector extends Selector {
    public NoiseSelector(NoiseTower robot) {
        super(robot);
        children.add(new CounterTower(robot));
        children.add(new SoundTowerBehaviorBrent(robot));
    }

    @Override
    public boolean pre() throws GameActionException {
        return true;
    }
}
