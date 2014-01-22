package team009;

public class BehaviorConstants {

    // Chase Strategies.
    public static final boolean CHASE_ENABLED = true;
    public static final int CHASE_STRATEGY_MINIMUM = 100;
    public static final int CHASE_STRATEGY_MAP_MINIMUM = 1200;
    public static final boolean CHASE_CAN_CANCEL_FOR_HUNT = false;
    public static final int CHASE_MINIMUM_ROUND_NUMBER = 0;
    public static final int CHASE_REQUIRED_SOLDIER_COUNT = 1;

    // Cheese // TODO: very strict.  Only works on 2 levels. neighbors and rushlane
    public static final boolean CHEESE_ENABLED = true;
    public static final int CHEESE_MILK_MINIMUM = 600;
    public static final int CHEESE_HQ_ENEMY_MINIMUM_DISTANCE = 900;

    // Map Sizing
    public static final int MAP_LARGE_MINIMUM_AREA = 2000;
    public static final int MAP_MEDIUM_MINIMUM_AREA = 1200;

    // HQ Constants
    public static final int HQ_REQUIRED_SOLDIER_COUNT_FOR_ATTACK = 4;
    public static final int HQ_REQUIRED_SOLDIER_COUNT_FOR_GROUP_ATTACK = 6;
    public static final int HQ_SOLDIER_COM_MAX = 35;
    public static final int HQ_SMALL_MAP_ONE_BASE_ROUND_NUMBER = 500;

    // Milk Selection picking
    public static final int MILK_INFO_STRAT_CORNERS_AND_SIDES = 0;
    public static final int MILK_INFO_STRAT_CORNERS = 1;
    public static final int MILK_INFO_STRAT = MILK_INFO_STRAT_CORNERS;

    // Soldier Coms
    public static final double ATTACK_SOLDIER_MULTIPLIER = (5.0);
    public static final int ATTACK_SOLDIER_MAX = 15;

    // Sound tower
    public static final int NOISE_TOWER_REDUCTION = 2;
    public static final boolean NOISE_TOWER_ENABLE_WITH_PASTURE = false;
    public static final boolean NOISE_TOWER_FIRST = false;
}
