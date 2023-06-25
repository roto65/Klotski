package io.db.codecs;

import io.schemas.HintSchema;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * Implements the CodecProvider for the HintSchema object
 */
public class HintSchemaCodecProvider implements CodecProvider {

    /**
     * {@inheritDoc}
     *
     * @param clazz the Class for which to get a Codec
     * @param registry the registry to use for resolving dependent Codec instances
     * @return Codec for the HintSchema object
     * @param <T>
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        if (clazz == HintSchema.class) {
            return (Codec<T>) new HintSchemaCodec(registry);
        }
        return null;
    }


}
