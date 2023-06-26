package main;

import java.awt.*;

/**
 * Defines constants used inside the code base
 */
@SuppressWarnings("unused")
public final class Constants {

    /**
     * Defines the game of the name
     */
    public static final String GAME_NAME = "Klotski";

    /**
     * Defines a scaling factor used in all the ui elements
     */
    public static final int TITLE_SIZE = 125;

    /**
     * Defines the number of rows of the game field
     */
    public static final int ROWS = 5;

    /**
     * Defines the number of columns of the game field
     */
    public static final int COLUMNS = 4;

    /**
     * Defines the number of levels currently available in the database
     */
    public static final int LEVELS = 420;


    // built-in layouts

    /**
     * Defines the name of the default layout file
     */
    public static final String LAYOUT_DEFAULT    = "default";

    /**
     * Defines the name of the first test layout file
     */
    public static final String LAYOUT_TEST_LARGE = "largeTest";

    /**
     * Defines the name of the second test layout file
     */
    public static final String LAYOUT_TEST_WIDE  = "wideTest";

    /**
     * Defines the name of the third test layout file
     */
    public static final String LAYOUT_TEST_SMALL = "smallTest";


    // level selector ui constants

    /**
     * Defines the number of grid rows in the level selector dialog
     */
    public static final int LEVEL_SELECTOR_GRID_ROWS = 4;

    /**
     * Defines the number of grid columns in the level selector dialog
     */
    public static final int LEVEL_SELECTOR_GRID_COLUMNS = 7;


    // folders

    /**
     * Defines the name of the layout resource folder
     */
    public static final String FOLDER_LAYOUTS = "layout/";

    /**
     * Defines the name of the image resource folder
     */
    public static final String FOLDER_IMAGES  = "drawable/";

    /**
     * Defines the name of the font resource folder
     */
    public static final String FOLDER_FONTS   = "font/";


    // database constants

    /**
     * Defines the name of the database
     */
    public static final String DATABASE_NAME     = "klotski";

    /**
     * Defines the name of the level collection inside the database
     */
    public static final String LEVELS_COLLECTION = "levels";

    /**
     * Defines the name of the hint collection inside the database
     */
    public static final String HINT_COLLECTION   = "hints";


    // flags

    /**
     * Defines if the old sprites should be used
     */
    public static final boolean USE_LEGACY_SPRITES     = false;

    /**
     * Defines if the database caching for hints should be used
     */
    public static final boolean USE_DB_HINT_CASHING    = false;

    /**
     * Defines if the solver should print debug information
     */
    public static final boolean USE_SOLVER_DEBUG_PRINT = false;

    /**
     * Defines if the board background should highlight the end position of the big block
     */
    public static final boolean USE_BG_END_HIGHLIGHT   = false;

    /**
     * Defines if the post game dialog should display how far from par the player is
     */
    public static final boolean USE_SHAME_BRAKETS      = false;


    // colors

    /**
     * Defines the main background color
     */
    public static final Color COLOR_BACKGROUND      = new Color(69,  76,  90, 255);

    /**
     * Defines the board background color (light)
     */
    public static final Color COLOR_BOARD_BG_LIGHT  = new Color(38,  83,  75, 255);

    /**
     * Defines the board background color (dark)
     */
    public static final Color COLOR_BOARD_BG_DARK   = new Color(35,  59,  59, 255);

    /**
     * Defines the background color (light) for the end position of the big block
     */
    public static final Color COLOR_BOARD_END_LIGHT = new Color(96,  27,  39, 255);

    /**
     * Defines the background color (dark) for the end position of the big block
     */
    public static final Color COLOR_BOARD_END_DARK  = new Color(91,  14,  29, 255);

    /**
     * Defines the color of the active dot for the page counter
     */
    public static final Color COLOR_PC_ACTIVE       = new Color(143, 222, 93, 255);

    /**
     * Defines the color of the inactive dot for the page counter
     */
    public static final Color COLOR_PC_INACTIVE     = new Color(60, 163, 112, 255);

    /**
     * Deleted constructor
     * This class cannot be instanced
     */
    private Constants() {
    }
}
