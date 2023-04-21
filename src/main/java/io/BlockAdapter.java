package io;

import com.google.gson.*;
import ui.blocks.Block;
import ui.blocks.LargeBlock;
import ui.blocks.SmallBlock;
import ui.blocks.WideBlock;

import java.lang.reflect.Type;

public class BlockAdapter implements JsonDeserializer<Block> {
    @Override
    public Block deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String blockType = json.getAsJsonObject().get("blockType").getAsString();
        return switch (blockType) {
            case "SMALL" -> context.deserialize(json, SmallBlock.class);
            case "WIDE_HORIZONTAL", "WIDE_VERTICAL" -> context.deserialize(json, WideBlock.class);
            case "LARGE" -> context.deserialize(json, LargeBlock.class);
            default -> throw new IllegalArgumentException("BlockType not valid");
        };

    }
}
