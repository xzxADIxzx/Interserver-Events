package interserver.net;

import arc.net.FrameworkMessage;
import arc.net.FrameworkMessage.*;
import arc.net.NetSerializer;
import arc.util.Log;
import mindustry.io.JsonIO;

import java.nio.ByteBuffer;

public class EventSerializer implements NetSerializer {

    @Override
    public void write(ByteBuffer buffer, Object object) {
        if (object instanceof FrameworkMessage message) {
            buffer.put((byte) 0);
            writeFramework(buffer, message);
        } else {
            buffer.put((byte) 1);
            writeString(buffer, object.getClass().getName());
            writeString(buffer, JsonIO.write(object));
        }
    }

    @Override
    public Object read(ByteBuffer buffer) {
        byte id = buffer.get();
        if (id == 0) return readFramework(buffer);

        String className = readString(buffer);
        try {
            Class<?> type = Class.forName(className);
            return JsonIO.read(type, className);
        } catch (Throwable ignored) {
            Log.err("Couldn`t find the event class received from another server. Class name is @", className, ignored);
            return null;
        }
    }

    public void writeFramework(ByteBuffer buffer, FrameworkMessage message) {
        if (message instanceof Ping || message instanceof DiscoverHost) buffer.put((byte) 0); // unused
        else if (message instanceof RegisterTCP reg) buffer.put((byte) 1).putInt(reg.connectionID);
        else if (message instanceof RegisterUDP reg) buffer.put((byte) 2).putInt(reg.connectionID);
        else if (message instanceof KeepAlive) buffer.put((byte) 3);
    }

    public FrameworkMessage readFramework(ByteBuffer buffer) {
        byte id = buffer.get();
        if (id == 1)
            return new RegisterTCP() {{
                connectionID = buffer.getInt();
            }};
        if (id == 2)
            return new RegisterUDP() {{
                connectionID = buffer.getInt();
            }};
        if (id == 3) return FrameworkMessage.keepAlive;
        throw new RuntimeException("Unknown framework message!");
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
