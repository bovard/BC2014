package team009.utils;

import battlecode.common.*;
import team009.RobotInformation;

/**
 * Created by mpaulson on 1/19/14.
 */
public class MilkInformation {

    // Controls
    RobotController rc;
    RobotInformation info;
    Box[] boxes;
    int i = 0;
    int j = 0;
    Box curr;
    boolean hasBasesSet = false;
    int nmeBase = -1, ourBase = -1;

    // The public information
    public double[][] milks;
    public Box[] targetBoxes = new Box[2];
    public boolean finished = false;

    /**
     * A milk information will parse out the map and determine the
     * "sweet" spots for milking
     */
    public MilkInformation(RobotController rc, RobotInformation info) {
        this.rc = rc;
        this.info = info;

        // Calculates the bounding boxes.
        // +--+------+--+
        // |1 |2     |3 |
        // +--+------+--+
        // |4 |5     |6 |
        // |  |      |  |
        // |  |      |  |
        // +--+------+--+
        // |7 |8     |9 |
        // +--+------+--+
        int width = info.width;
        int height = info.height;
        int width20 = info.width / 5;
        int height20 = info.height / 5;
        int width80 = width20 * 4;
        int height80 = height20 * 4;

        // The bounding boxes
        boxes = new Box[] {
            new Box(0, 0, width20, height20),
            new Box(width20, 0, width80, height20),
            new Box(width80, 0, width, height20),
            new Box(0, height20, width20, height80),
            new Box(width20, height20, width80, height80),
            new Box(width80, height20, width, height80),
            new Box(0, height80, width20, height),
            new Box(width20, height80, width80, height),
            new Box(width80, height80, width, height),
        };
        milks = rc.senseCowGrowth();
    }

    public boolean calc() throws GameActionException {

        if (finished) {
            return true;
        }

        // Bases have been set
        if (!hasBasesSet) {
            int x = info.hq.x;
            int y = info.hq.y;
            for (int i = 0; i < boxes.length; i++) {
                Box b = boxes[i];
                if (x >= b.x && x < b.x2 && y >= b.y && y < b.y2) {
                    ourBase = i;
                }
            }
            _setTargetBoxes();
            hasBasesSet = true;
        }
        int roundsToProcess = (GameConstants.BYTECODE_LIMIT - (Clock.getBytecodeNum())) / 55;
        double[][] milks = this.milks;
        int i = this.i, j = this.j;
        Box curr = this.curr;

        // Now its time to sum only the bases we need.
        int k = 0;
        while (k < roundsToProcess && i < curr.x2 - 1) {
            for (; i < curr.x2 - 1 && k < roundsToProcess; i++, k++) {
                double[] row = milks[i];
                for (j = curr.y == 0 ? 1 : curr.y; j < curr.y2 - 1; j++, k++) {
                    if (curr.bestSpot == null) {
                        curr.bestSpot = new MapLocation(i, j);
                        curr.bestMilk = milks[i][j];
                        continue;
                    }

                    double val = row[j - 1] + row[j] + row[j + 1] + milks[i - 1][j] + milks[i + 1][j];
                    if (val > curr.bestMilk) {
                        curr.bestSpot = new MapLocation(i, j);
                        curr.bestMilk = val;
                    }
                }
            }
        }

        if (i == curr.x2 - 1) {
            if (curr.next == null) {
                finished = true;
            } else {
                curr = curr.next;
                i = curr.x;
                j = curr.y;

                if (j == 0) {
                    j++;
                }
                if (i == 0) {
                    i++;
                }
            }
        }

        this.curr = curr;
        this.i = i;
        this.j = j;

        return finished;
    }

    private void _setTargetBoxes() {
        if (ourBase == 0) {
            // 3 - 6
            targetBoxes[0] = new Box(boxes[3].x, boxes[3].y, boxes[3].x2, boxes[6].y2);

            // 1 - 2
            targetBoxes[1] = new Box(boxes[1].x, boxes[1].y, boxes[2].x2, boxes[2].y2);
        } else if (ourBase == 1) {
            // 0 - 3
            targetBoxes[0] = new Box(boxes[0].x, boxes[0].y, boxes[0].x2, boxes[3].y2);

            // 2 - 5
            targetBoxes[1] = new Box(boxes[2].x, boxes[2].y, boxes[2].x2, boxes[5].y2);
        } else if (ourBase == 2) {
            // 0 - 1
            targetBoxes[0] = new Box(boxes[0].x, boxes[0].y, boxes[1].x2, boxes[1].y2);

            // 5 - 8
            targetBoxes[1] = new Box(boxes[5].x, boxes[5].y, boxes[5].x2, boxes[8].y2);
        } else if (ourBase == 3) {
            // 0 - 1
            targetBoxes[0] = new Box(boxes[0].x, boxes[0].y, boxes[1].x2, boxes[1].y2);

            // 6 - 7
            targetBoxes[1] = new Box(boxes[6].x, boxes[6].y, boxes[6].x2, boxes[7].y2);
        } else if (ourBase == 4) {
            if (info.hq.x > info.width / 2) {
                // 1 - 2
                targetBoxes[0] = new Box(boxes[1].x, boxes[1].y, boxes[2].x2, boxes[2].y2);

                // 7 - 8
                targetBoxes[1] = new Box(boxes[7].x, boxes[7].y, boxes[7].x2, boxes[8].y2);
            } else {
                // 0 - 1
                targetBoxes[0] = new Box(boxes[0].x, boxes[0].y, boxes[1].x2, boxes[1].y2);

                // 6 - 7
                targetBoxes[1] = new Box(boxes[6].x, boxes[6].y, boxes[6].x2, boxes[7].y2);
            }
        } else if (ourBase == 5) {
            // 1 - 2
            targetBoxes[0] = new Box(boxes[1].x, boxes[1].y, boxes[2].x2, boxes[2].y2);

            // 7 - 8
            targetBoxes[1] = new Box(boxes[7].x, boxes[7].y, boxes[7].x2, boxes[8].y2);
        } else if (ourBase == 6) {
            // 0 - 3
            targetBoxes[0] = new Box(boxes[0].x, boxes[0].y, boxes[0].x2, boxes[3].y2);

            // 7 - 8
            targetBoxes[1] = new Box(boxes[7].x, boxes[7].y, boxes[7].x2, boxes[8].y2);
        } else if (ourBase == 7) {
            // 3 - 6
            targetBoxes[0] = new Box(boxes[3].x, boxes[3].y, boxes[3].x2, boxes[6].y2);

            // 5 - 8
            targetBoxes[1] = new Box(boxes[5].x, boxes[5].y, boxes[5].x2, boxes[8].y2);
        } else {
            // 6 - 7
            targetBoxes[0] = new Box(boxes[6].x, boxes[6].y, boxes[6].x2, boxes[7].y2);

            // 2 - 5
            targetBoxes[1] = new Box(boxes[2].x, boxes[2].y, boxes[2].x2, boxes[5].y2);
        }

        curr = targetBoxes[0];
        curr.next = targetBoxes[1];
        i = curr.x;
        j = curr.y;

        if (j == 0) {
            j++;
        }
        if (i == 0) {
            i++;
        }
    }

    public class Box {
        Box(int x, int y, int x2, int y2) {
            this.x = x;
            this.y = y;
            this.x2 = x2;
            this.y2 = y2;
        }
        int x, y, x2, y2;
        double bestMilk = 0;
        Box next;

        public MapLocation bestSpot;
    }
}
