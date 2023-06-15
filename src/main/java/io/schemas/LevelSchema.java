package io.schemas;

import ui.blocks.Block;

import java.util.ArrayList;

public class LevelSchema {

    @SuppressWarnings("FieldCanBeLocal")
    private int levelNumber;
    private ArrayList<Block> blocks;
    @SuppressWarnings("FieldCanBeLocal")
    private int minimumMoves;

    @SuppressWarnings("unused")
    public LevelSchema() {
    }

    public LevelSchema(int levelNumber, ArrayList<Block> blocks, int minimumMoves) {
        this.levelNumber = levelNumber;
        this.blocks = blocks;
        this.minimumMoves = minimumMoves;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public int getMinimumMoves() {
        return minimumMoves;
    }

    @Override
    public String toString() {
        return "BlockCollection{" +
                "blocks=" + blocks +
                '}';
    }
}
