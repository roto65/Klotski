package ui.blocks;

public enum BlockType {
    SMALL (1, 1),
    WIDE_HORIZONTAL (2, 1),
    WIDE_VERTICAL (1, 2),
    LARGE (2, 2);

    private final int width;
    private final int height;

    BlockType(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
