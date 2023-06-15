package main;

public final class Constants {

    public static final int TITLE_SIZE = 125;
    public static final int ROWS = 5;
    public static final int COLUMNS = 4;
    public static final int LEVELS = 420;

    public static final String DEFAULT_BLOCK_CONFIGURATION = "default";


    // level selector ui constants

    public static final int LEVEL_SELECTOR_GRID_ROWS = 4;
    public static final int LEVEL_SELECTOR_GRID_COLUMNS = 7;


    // database constants

    public static final String databaseName = "klotski";
    public static final String levelsCollection = "levels";
    public static final String hintCollection = "hints";


    // flags

    public static final boolean USE_LEGACY_SPRITES     = false;
    public static final boolean USE_DB_HINT_CASHING    = false;
    public static final boolean USE_SOLVER_DEBUG_PRINT = false;
}
