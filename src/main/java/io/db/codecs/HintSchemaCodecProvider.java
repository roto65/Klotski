package io.db.codecs;

import io.schemas.HintSchema;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

public class HintSchemaCodecProvider implements CodecProvider {

    @Override
    @SuppressWarnings("unchecked")
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        if (clazz == HintSchema.class) {
            return (Codec<T>) new HintSchemaCodec(registry);
        }
        return null;
    }


}
