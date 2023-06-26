package ui.blocks;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

/**
 * Defines a generic type of block and it's properties
 */
public abstract class Block {

    /**
     * The position of the block
     */
    protected Point pos;

    /**
     * The position of all the block "parts" relative to the top left position
     */
    protected transient ArrayList<Point> offsets;

    /**
     * The sprite of the block
     */
    protected transient Image sprite; //transient = non serializable

    /**
     * The type of the block
     */
    protected BlockType blockType;

    /**
     * Method that loads data in the offsets list
     */
    protected abstract void loadOffsets();

    /**
     * Method that loads the sprite data from the resources
     */
    protected abstract void loadSprite();

    /**
     * Method that defines custom ui for the block object
     *
     * @param g graphics object
     * @param observer receives information about the objects drawn
     */
    public abstract void draw(Graphics g, ImageObserver observer);

    /**
     * Method that changes the position of the block, moving it
     *
     * @param destination the position the block needs to be moved into
     */
    public abstract void move(Point destination);

    /**
     * @return the coordinates of the top left corner of the block
     */
    public Point getPos() {
        return pos;
    }

    /**
     * @return the type of the block
     */
    public BlockType getBlockType() {
        return blockType;
    }

    /**
     * Method that defines the operation that still needs to be done after the block has been deserialized from Json
     */
    public void postDeserializationProcess() {
        loadOffsets();
        loadSprite();
    }

    /**
     * Methods that calculates the position in the board of each section of the block
     *
     * @return a list that contains all the positions
     */
    public ArrayList<Point> getSectionsPos() {
        return new ArrayList<>() {
            {
                for (Point offset : offsets) {
                    add(new Point(pos.x + offset.x, pos.y + offset.y));
                }
            }
        };
    }

    /**
     * Methods that executes a deep copy of the block object
     *
     * @return a newly created object with the same data as this
     */
    public Block copy() {
        switch (blockType) {
            case SMALL -> {
                return new SmallBlock(pos.x, pos.y);
            }
            case WIDE_HORIZONTAL -> {
                return new WideBlock(pos.x, pos.y, BlockType.WIDE_HORIZONTAL);
            }
            case WIDE_VERTICAL -> {
                return new WideBlock(pos.x, pos.y, BlockType.WIDE_VERTICAL);
            }
            case LARGE -> {
                return new LargeBlock(pos.x, pos.y);
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * @param obj object that is compared
     * @return true if this matches obj
     */
    @Override
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public boolean equals(Object obj) {
        Block block = (Block) obj;

        return this.blockType == block.blockType && this.pos.x == block.pos.x && this.pos.y == block.pos.y;
    }
}
