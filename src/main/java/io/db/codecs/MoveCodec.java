package io.db.codecs;

import core.Move;
import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

import java.awt.*;

/**
 * Implements the Codec interface for the Move object
 */
public class MoveCodec implements Codec<Move> {

    private final Codec<Point> pointCodec;

    /**
     * Constructor for the MoveCodec
     *
     * @param registry the codec registry
     */
    public MoveCodec(CodecRegistry registry) {
        this.pointCodec = registry.get(Point.class);
    }

    /**
     * {@inheritDoc}
     *
     * @param reader         the BSON reader
     * @param decoderContext the decoder context
     * @return decoded Move
     */
    @Override
    public Move decode(BsonReader reader, DecoderContext decoderContext) {
        Move move = new Move();

        reader.readStartDocument();

        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {

            String fieldName = reader.readName();

            if (fieldName.equals("startPos")) {
                move.setStartPos(pointCodec.decode(reader, decoderContext));
            } else if (fieldName.equals("endPos")) {
                move.setEndPos(pointCodec.decode(reader, decoderContext));
            }
        }
        reader.readEndDocument();

        return move;
    }

    /**
     * {@inheritDoc}
     *
     * @param writer the BSON writer to encode into
     * @param value the value to encode
     * @param encoderContext the encoder context
     */
    @Override
    public void encode(BsonWriter writer, Move value, EncoderContext encoderContext) {
        if (value == null) return;

        writer.writeStartDocument();

        writer.writeName("startPos");
        pointCodec.encode(writer, value.getStartPos(), encoderContext);
        writer.writeName("endPos");
        pointCodec.encode(writer, value.getEndPos(), encoderContext);

        writer.writeEndDocument();
    }

    /**
     * {@inheritDoc}
     *
     * @return class instance
     */
    @Override
    public Class<Move> getEncoderClass() {
        return Move.class;
    }
}
