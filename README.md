This shall be our glorious entry this year!

Going for 1st place (or at least the finals again)!

# Instructions to install

1. Checkout the battlecode repo
    * ```git clone git@github.com:Cixelyn/bcode2013-scaffold.git```
    * this will change once they release the new specs (Jan 6th 2pm MST)
2. Remove the teams folder
    * ```rm -rf teams```
3. Clone this repo in the teams folder:
    * ```git clone git@bitbucket.org:bovard.tiberi/battlecode-2014.git teams```


# Behavior Tree

The behavior tree works a bit different this year. It starts from the root node every single time now!

### This means:
1. Since the pre of the parents is called before the pre of the children, they implicitly already have the pre!
2. No more !inCombat all over the place, just place a combat on the top level selector
3. We now support sequencers!
4. We've added a post() method to show if the behavior has completed. This should be called every time before calling the node
5. We've added a reset() which should be called when a node is completed or needs to be reset

See TeamRobot.run() to see how the trees work on action now!

