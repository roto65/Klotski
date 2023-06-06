package io.db.codecs;

import org.bson.BsonReader;
import org.bson.BsonType;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import java.awt.*;

public class PointCodec implements Codec<Point> {

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

    @Override
    public void encode(BsonWriter writer, Point value, EncoderContext encoderContext) {
        if (value == null) return;

        writer.writeStartDocument();

        writer.writeInt32("x", value.x);
        writer.writeInt32("y", value.y);

        writer.writeEndDocument();
    }

    @Override
    public Class<Point> getEncoderClass() {
        return Point.class;
    }
}