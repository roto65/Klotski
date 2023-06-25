package io.db.codecs;

import core.Move;
import io.schemas.HintSchema;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * Implements the Codec interface for the HintSchema object
 */
public class HintSchemaCodec implements Codec<HintSchema> {

    private final Codec<Move> moveCodec;
    private final Codec<String> stringCodec;

    /**
     * Constructor for the HintSchemaCodec
     *
     * @param registry the codec registry
     */
    public HintSchemaCodec(CodecRegistry registry) {
        moveCodec = registry.get(Move.class);
        stringCodec = registry.get(String.class);
    }

    /**
     * {@inheritDoc}
     *
     * @param reader         the BSON reader
     * @param decoderContext the decoder context
     * @return decoded HintSchema
     */
    @Override
    public HintSchema decode(BsonReader reader, DecoderContext decoderContext) {
        HintSchema hintSchema = new HintSchema();

        reader.readStartDocument();

        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {

            String fieldName = reader.readName();

            if (fieldName.equals("state")) {
                hintSchema.setState(stringCodec.decode(reader, decoderContext));
            } else if (fieldName.equals("bestMove")) {
                hintSchema.setBestMove(moveCodec.decode(reader, decoderContext));
            } else {
                reader.skipValue();
            }
        }
        reader.readEndDocument();

        return hintSchema;
    }

    /**
     * {@inheritDoc}
     *
     * @param writer the BSON writer to encode into
     * @param value the value to encode
     * @param encoderContext the encoder context
     */
    @Override
    public void encode(BsonWriter writer, HintSchema value, EncoderContext encoderContext) {
        writer.writeStartDocument();

        writer.writeString("state", value.getState());
        writer.writeName("bestMove");
        moveCodec.encode(writer, value.getBestMove(), encoderContext);

        writer.writeEndDocument();
    }

    /**
     * {@inheritDoc}
     *
     * @return class instance
     */
    @Override
    public Class<HintSchema> getEncoderClass() {
        return HintSchema.class;
    }
}
