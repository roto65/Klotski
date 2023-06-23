package io.schemas;

import core.Move;
import ui.blocks.Block;

import java.util.ArrayList;

public class LevelSchema {

    private Integer levelNumber;
    private ArrayList<Block> blocks;
    private Integer minimumMoves;
    private ArrayList<Move> moves;
    private Integer iteratorIndex;


    @SuppressWarnings("unused")
    public LevelSchema() {
    }

    public LevelSchema(Integer levelNumber, ArrayList<Block> blocks, Integer minimumMoves) {
        this.levelNumber = levelNumber;
        this.blocks = blocks;
        this.minimumMoves = minimumMoves;
    }

    public LevelSchema(Integer levelNumber, ArrayList<Block> blocks, Integer minimumMoves, ArrayList<Move> moves, Integer iteratorIndex) {
        this.levelNumber = levelNumber;
        this.blocks = blocks;
        this.minimumMoves = minimumMoves;
        this.moves = moves;
        this.iteratorIndex = iteratorIndex;
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

    public ArrayList<Move> getMoves() {
        return moves;
    }

    public int getIteratorIndex() {
        return iteratorIndex;
    }

    @Override
    public String toString() {
        return "BlockCollection{" +
                "blocks=" + blocks +
                '}';
    }
}
