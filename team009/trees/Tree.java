package team009.trees;

import battlecode.common.Clock;
import team009.behavior.behaviors.Behavior;
import team009.behavior.decisions.Decision;
import team009.behavior.Node;

import team009.robot.TeamRobot;


public abstract class Tree {

	public TeamRobot robot;
	public Node current;
	
	/**
	 * This is where you would build up the tree. 
	 */
	public Tree(TeamRobot robot) {
		this.robot = robot;
	}
	
	public void run() {
		boolean newB = true;
        boolean done = false;
		while (true) {
			int round = Clock.getRoundNum();
			
			try {
				
				// at the start of the round, update with an environment check
				robot.environmentCheck();
				
				// TODO: doesn't support a sequence
				// first check the pres and navigate to a Node that pre returns true
				if (newB) {
					if (current instanceof Behavior) {
						((Behavior) current).start();
					}
				}
				if (!current.pre()) {					
					if (current instanceof Behavior) {
						((Behavior)current).stop();
					}
					//cascade back up the tree
					while(!current.pre()) {
						current = current.parent;
					}
				}
				
				// if we are on a behavior Node 
				// if it's new call start
				// call run
				if (current instanceof Behavior) {
					done = ((Behavior)current).run();
                    // if the behavior says that it's done, go back to the parent to do the next thing
                    if (done) {
                        current = current.parent;
                        newB = true;
                        continue;
                    }
				}
				// if we are on a decision Node
				// call select and set the thing it returns to the current, then loop again (continue)
				else {
					current = ((Decision)current).select();
					newB = true;
					continue;
				}
				
				newB = false;
				
				
			} catch (Exception e) {
				e.printStackTrace();	
			}

			
			try {
				robot.load();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Load error: " );
				e.printStackTrace();
			}
			
			if (round == Clock.getRoundNum()) {
				robot.rc.yield();
				robot.rc.setIndicatorString(0, "-");
			}
			
		} // end while
	}
}
