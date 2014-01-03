package team009.behavior.decorators;

import battlecode.common.GameActionException;
import team009.behavior.Node;

/**
 * Created by bovardtiberi on 1/2/14.
 */
public class Repeater extends Decorator {

    // the number of times to repeat the decorated node
    private int repititions;
    private int repeated = 0;

    public Repeater(Node decorated, int repititions) {
        super(decorated);
        this.repititions = repititions;
    }

    public void reset() {
        this.repeated = 0;
    }

    public boolean post() {
        return this.repeated == this.repititions;
    }

    public boolean run() throws GameActionException {
        if (!this.pre()) {
            return false;
        }

        if (this.decorated.post()) {
            this.repeated += 1;
            this.decorated.reset();
        }

        return this.decorated.run();
    }


}
