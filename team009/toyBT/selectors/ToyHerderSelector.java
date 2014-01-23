package team009.toyBT.selectors;

import battlecode.common.GameActionException;
import team009.bt.Node;
import team009.bt.decisions.Decision;
import team009.robot.soldier.ToySoldier;
import team009.toyBT.behaviors.ToyHerdReplace;
import team009.toyBT.behaviors.ToyHerdSoundCapture;

public class ToyHerderSelector extends Decision {
    protected Node heard;
    protected Node replace;
    protected Node sound;

    public ToyHerderSelector(ToySoldier robot) {
        super(robot);
        heard = new ToyHerdSequence(robot);
        replace = new ToyHerdReplace(robot);
        sound = new ToyHerdSoundCapture(robot);
    }

    @Override
    public boolean pre() throws GameActionException {
        // no prereqs
        return true;
    }

    @Override
    public boolean post() throws GameActionException {
        // never finishes
        return false;
    }

    @Override
    public void reset() throws GameActionException {
        // nothing to reset
    }

    @Override
    public boolean run() throws GameActionException {

        // if our pastr has been killed, replace it!
        if (sound.pre()) {
            return sound.run();
        } else if (replace.pre()) {
            return replace.run();
        }

        // Otherwise pick a direction from the pasture, go till you can't go anymore (or max distance)
        // then turn around and move back
        return heard.run();

    }
}
