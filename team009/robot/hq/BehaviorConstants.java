package team009.robot.hq;

/**
 * Created by mpaulson on 1/20/14.
 */
public class BehaviorConstants {

    // Chase Strategies.
    public static final boolean CHASE_ENABLED = false;
    public static final int CHASE_STRATEGY_MINIMUM = 100;
    public static final int CHASE_STRATEGY_MAP_MINIMUM = 1200;
    public static final boolean CHASE_CAN_CANCEL_FOR_HUNT = true;
    public static final int CHASE_MINIMUM_ROUND_NUMBER = 500;

    // Dark Horse // TODO: Currently impossible with this milk requirements
    public static final boolean DARK_HORSE_ENABLED = false;
    public static final int DARK_HORSE_MILK_MINIMUM = 100000;

    // Map Sizing
    public static final int LARGE_MAP_MINIMUM_AREA = 2000;
    public static final int MEDIUM_MAP_MINIMUM_AREA = 1200;

    // HQ Constants
    public static final int REQUIRED_SOLDIER_COUNT_FOR_ATTACK = 6;
    public static final int REQUIRED_SOLDIER_COUNT_FOR_GROUP_ATTACK = 8;
    public static final int REQUIRED_SOLDIER_COUNT_FOR_CHASE = 4;
}
