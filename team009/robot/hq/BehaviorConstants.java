package team009.robot.hq;

public class BehaviorConstants {

    // Chase Strategies.
    public static final boolean CHASE_ENABLED = true;
    public static final int CHASE_STRATEGY_MINIMUM = 100;
    public static final int CHASE_STRATEGY_MAP_MINIMUM = 1200;
    public static final boolean CHASE_CAN_CANCEL_FOR_HUNT = false;
    public static final int CHASE_MINIMUM_ROUND_NUMBER = 0;
    public static final int CHASE_REQUIRED_SOLDIER_COUNT = 1;

    // Dark Horse // TODO: Currently impossible with this milk requirements
    public static final boolean DARK_HORSE_ENABLED = true;
    public static final int DARK_HORSE_MILK_MINIMUM = 900;

    // Map Sizing
    public static final int LARGE_MAP_MINIMUM_AREA = 2000;
    public static final int MEDIUM_MAP_MINIMUM_AREA = 1200;

    // HQ Constants
    public static final int REQUIRED_SOLDIER_COUNT_FOR_ATTACK = 6;
    public static final int REQUIRED_SOLDIER_COUNT_FOR_GROUP_ATTACK = 8;

    // Milk Selection picking
    public static final int MILK_INFO_STRAT_CORNERS_AND_SIDES = 0;
    public static final int MILK_INFO_STRAT_CORNERS = 1;
    public static final int MILK_INFO_STRAT = MILK_INFO_STRAT_CORNERS;

    // Soldier Coms
    public static final double ATTACK_SOLDIER_MULTIPLIER = (4.0 / 3.0);
}
