package core;

import ui.blocks.Block;

import java.util.ArrayList;

public class BlockCollection {

    private String name = null;
    private ArrayList<Block> blocks;
    private int moves = 0;

    public BlockCollection() {

    }
    public BlockCollection(ArrayList<Block> blocks) {
        this.blocks = blocks;
    }

    public BlockCollection(ArrayList<Block> blocks, Move move) {
        this.blocks = blocks;
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    @Override
    public String toString() {
        return "BlockCollection{" +
                "blocks=" + blocks +
                '}';
    }
}
