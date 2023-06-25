package io.db.codecs;

import core.Move;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * Implements the CodecProvider interface for the Move object
 */
public class MoveCodecProvider implements CodecProvider {

    /**
     * {@inheritDoc}
     *
     * @param clazz the Class for which to get a Codec
     * @param registry the registry to use for resolving dependent Codec instances
     * @return Codec for the Move object
     * @param <T>
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        if (clazz == Move.class) {
            return (Codec<T>) new MoveCodec(registry);
        }
        return null;
    }
}
