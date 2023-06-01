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

public class MoveCodec implements Codec<Move> {

    private final Codec<Point> pointCodec;

    public MoveCodec(CodecRegistry registry) {
        this.pointCodec = registry.get(Point.class);
    }

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

    @Override
    public Class<Move> getEncoderClass() {
        return Move.class;
    }
}
