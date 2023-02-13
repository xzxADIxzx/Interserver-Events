package interserver.net;

import arc.net.NetSerializer;
import arc.util.Log;
import mindustry.io.JsonIO;

import java.nio.ByteBuffer;

public class EventSerializer implements NetSerializer {

    @Override
    public void write(ByteBuffer buffer, Object object) {
        writeString(buffer, object.getClass().getName());
        writeString(buffer, JsonIO.write(object));
    }

    @Override
    public Object read(ByteBuffer buffer) {
        String className = readString(buffer);
        try {
            Class<?> type = Class.forName(className);
            return JsonIO.read(type, className);
        } catch (Throwable ignored) {
            Log.err("Couldn`t find the event class received from another server. Class name is @", className, ignored);
            return null;
        }
    }

    public static void writeString(ByteBuffer buffer, String message) {
        if (message == null) message = "null";
        buffer.putInt(message.length());

        for (char chara : message.toCharArray())
            buffer.putChar(chara);
    }

    public static String readString(ByteBuffer buffer) {
        int length = buffer.getInt();
        StringBuilder builder = new StringBuilder(length);

        for (int i = 0; i < length; i++)
            builder.append(buffer.getChar());

        return builder.toString();
    }
}
