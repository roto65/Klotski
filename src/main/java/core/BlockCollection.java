package core;

import ui.blocks.Block;

import java.util.ArrayList;

public class BlockCollection {

    private String name = null;
    private ArrayList<Block> blocks;
    private Move move = null;
    private int moves = 0;

    public BlockCollection() {

    }
    public BlockCollection(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }

    public BlockCollection(ArrayList<Block> blocks, Move move) {
        this.blocks = blocks;
        this.move = move;
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public Move getMove() {
        return move;
    }

    @Override
    public String toString() {
        return "BlockCollection{" +
                "blocks=" + blocks +
                '}';
    }
}
