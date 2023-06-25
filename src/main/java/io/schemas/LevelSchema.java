package io.schemas;

import core.Move;
import ui.blocks.Block;

import java.util.ArrayList;

/**
 * Defines a schema for the level collection inside the database
 */
public class LevelSchema {

    /**
     * The number of the level, works as a primary key
     */
    private Integer levelNumber;

    /**
     * The disposition of the blocks at the start of the level
     */
    private ArrayList<Block> blocks;

    /**
     * The minimum moves that the player can do to win in the level
     */
    private Integer minimumMoves;

    /**
     * The moves the player made before the schema is generated.
     * Usually empty list in the database.
     */
    private ArrayList<Move> moves;

    /**
     * The index of the move list that helps to keep track of undos the player has done
     */
    private Integer iteratorIndex;



    /**
     * Empty constructor, called in parser
     */
    @SuppressWarnings("unused")
    public LevelSchema() {
    }

    /**
     * Constructor used to create objects by the database adapter
     *
     * @param levelNumber the number of the level, works as a primary key
     * @param blocks the disposition of the blocks at the start of the level
     * @param minimumMoves the minimum moves that the player can do to win in the level
     */
    public LevelSchema(Integer levelNumber, ArrayList<Block> blocks, Integer minimumMoves) {
        this.levelNumber = levelNumber;
        this.blocks = blocks;
        this.minimumMoves = minimumMoves;
    }

    /**
     * Constructor used to create objects by the board after it has been modified by the player
     *
     * @param levelNumber the number of the level, works as a primary key
     * @param blocks the disposition of the blocks at the start of the level
     * @param minimumMoves the minimum moves that the player can do to win in the level
     * @param moves the moves the player made before the schema is generated
     * @param iteratorIndex the index of the move list that helps to keep track of undos the player has done
     */
    public LevelSchema(Integer levelNumber, ArrayList<Block> blocks, Integer minimumMoves, ArrayList<Move> moves, Integer iteratorIndex) {
        this.levelNumber = levelNumber;
        this.blocks = blocks;
        this.minimumMoves = minimumMoves;
        this.moves = moves;
        this.iteratorIndex = iteratorIndex;
    }

    /**
     * @return the number of the level, works as a primary key
     */
    public int getLevelNumber() {
        return levelNumber;
    }

    /**
     * @return the disposition of the blocks at the start of the level
     */
    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    /**
     * @return the minimum moves that the player can do to win in the level
     */
    public int getMinimumMoves() {
        return minimumMoves;
    }

    /**
     * @return the moves the player made before the schema is generated
     */
    public ArrayList<Move> getMoves() {
        return moves;
    }

    /**
     * @return the index of the move list that helps to keep track of undos the player has done
     */
    public int getIteratorIndex() {
        return iteratorIndex;
    }
}
