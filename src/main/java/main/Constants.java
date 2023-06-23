package main;

import java.awt.*;

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

    public static final String DATABASE_NAME     = "klotski";
    public static final String LEVELS_COLLECTION = "levels";
    public static final String HINT_COLLECTION   = "hints";


    // flags

    public static final boolean USE_LEGACY_SPRITES     = false;
    public static final boolean USE_DB_HINT_CASHING    = false;
    public static final boolean USE_SOLVER_DEBUG_PRINT = false;
    public static final boolean USE_BG_END_HIGHLIGHT   = false;

    // colors

    public static final Color COLOR_BACKGROUND      = new Color(69, 76, 90, 255);
    public static final Color COLOR_BOARD_BG_LIGHT  = new Color(38, 83,  75,  255);
    public static final Color COLOR_BOARD_BG_DARK   = new Color(35, 59,  59,  255);
    public static final Color COLOR_BOARD_END_LIGHT = new Color(96, 27,  39,  255);
    public static final Color COLOR_BOARD_END_DARK  = new Color(91, 14,  29,  255);

}
