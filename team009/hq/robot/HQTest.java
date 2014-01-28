package team009.hq.robot;

import battlecode.common.Clock;
import battlecode.common.RobotController;
import team009.RobotInformation;
import team009.bt.Node;
import team009.hq.HQ;

public class HQTest extends HQ {
    public HQTest(RobotController rc, RobotInformation info) {
        super(rc, info);
        treeRoot = getTreeRoot();
    }

    @Override
    protected Node getTreeRoot() {
        return new team009.hq.bt.behaviors.HQTest(this);
    }



    /**
     * We should never return from this
     */
    public void run() {
        while (true) {
            System.out.println("My action delay is now: " + rc.getActionDelay());
            System.out.println("I'm active?" + rc.isActive());
            System.out.println("I'm constructing?" + rc.isConstructing());
            int round = Clock.getRoundNum();

            try {
                // at the start of the round, update with an environment check
                this.environmentCheck();

                // every turn we run the comRoot
                if (comRoot != null) {
                    comRoot.run();
                }

                // if we're active have the tree choose what to do
                if (rc.isActive()) {
                    treeRoot.run();
                }

                int i = 0;
                System.out.println("Bytecodes left: " + Clock.getBytecodesLeft());
                while (Clock.getBytecodesLeft() > 500) {
                    i++;
                }
                System.out.println(i);
                System.out.println("Bytecodes left: " + Clock.getBytecodesLeft());

            } catch (Exception e) {
                e.printStackTrace();
                String str = "Error: " + e.getMessage();
                rc.setIndicatorString(2, str);
            }


            // then postProcessing with whatever remains of our byte code
            try {
                this.postProcessing();
            } catch (Exception e) {
                System.out.println("Load error: " );
                e.printStackTrace();
            }

            // only yield if we're still on the same clock turn
            // if we aren't that means that we ended up skipping
            // our turn because we went too long
            System.out.println("I used " + (10000 - Clock.getBytecodesLeft()) + " bytecodes!");
            if (round == Clock.getRoundNum()) {
                this.rc.yield();
            } else {
                System.out.println("BYTECODE LIMIT EXCEEDED!");
            }
        }
    }

}
