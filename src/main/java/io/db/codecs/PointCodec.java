package io.db.codecs;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.awt.*;

/**
 * Implements the Codec interface for the Point object
 */
public class PointCodec implements Codec<Point> {

    /**
     * {@inheritDoc}
     *
     * @param reader         the BSON reader
     * @param decoderContext the decoder context
     * @return decoded Point
     */
    @Override
    public Point decode(BsonReader reader, DecoderContext decoderContext) {
        Point point = new Point();

        reader.readStartDocument();

        while (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {

            String fieldName = reader.readName();

            if (fieldName.equals("x")) {
                point.x = reader.readInt32();
            } else if (fieldName.equals("y")) {
                point.y = reader.readInt32();
            }
        }
        reader.readEndDocument();

        return point;
    }

    /**
     * {@inheritDoc}
     *
     * @param writer the BSON writer to encode into
     * @param value the value to encode
     * @param encoderContext the encoder context
     */
    @Override
    public void encode(BsonWriter writer, Point value, EncoderContext encoderContext) {
        if (value == null) return;

        writer.writeStartDocument();

        writer.writeInt32("x", value.x);
        writer.writeInt32("y", value.y);

        writer.writeEndDocument();
    }

    /**
     * {@inheritDoc}
     *
     * @return class instance
     */
    @Override
    public Class<Point> getEncoderClass() {
        return Point.class;
    }
}