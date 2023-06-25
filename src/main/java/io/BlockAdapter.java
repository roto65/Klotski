package io;

import com.google.gson.*;
import ui.blocks.*;

import java.lang.reflect.Type;

/**
 * Implements the JsonDeserializer interface for the abstract class Block
 */
public class BlockAdapter implements JsonDeserializer<Block> {

    /**
     * {@inheritDoc}
     *
     * @param json The Json data being deserialized
     * @param typeOfT The type of the Object to deserialize to
     * @param context The context when the method is called
     * @return deserialized Block
     * @throws JsonParseException
     */
    @Override
    public Block deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        BlockType blockType = BlockType.valueOf(json.getAsJsonObject().get("blockType").getAsString());

        Block block = switch (blockType) {
            case SMALL -> context.deserialize(json, SmallBlock.class);
            case WIDE_HORIZONTAL, WIDE_VERTICAL -> context.deserialize(json, WideBlock.class);
            case LARGE -> context.deserialize(json, LargeBlock.class);
        };

        block.postDeserializationProcess();

        return block;
    }
}
