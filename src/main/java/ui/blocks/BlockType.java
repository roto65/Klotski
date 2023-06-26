package ui.blocks;

/**
 * Provides objects that describe all the possible type of blocks
 */
public enum BlockType {

    /**
     * Represents the small block
     */
    SMALL (1, 1),

    /**
     * Represents the wide block, but laying down
     */
    WIDE_HORIZONTAL (2, 1),

    /**
     * Represents the wide block, but standing up
     */
    WIDE_VERTICAL (1, 2),

    /**
     * Represents the big block
     */
    LARGE (2, 2);

    /**
     * The width of the block (x-axis)
     */
    private final int width;

    /**
     * The height of the block (y-axis)
     */
    private final int height;

    /**
     * Constructor for the block types
     *
     * @param width the width of the block (x-axis)
     * @param height the height of the block (y-axis)
     */
    BlockType(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * @return the width of the block (x-axis)
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the height of the block (y-axis)
     */
    public int getHeight() {
        return height;
    }
}
