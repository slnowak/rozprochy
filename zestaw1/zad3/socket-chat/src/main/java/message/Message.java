package message;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.SerializationException;
import org.apache.commons.lang3.SerializationUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by novy on 12.03.15.
 */
public class Message {

    private static final int BUFFER_SIZE = 2048;
    private static final String DATE_FORMAT_PATTERN = "YYYY-MM-DD HH:mm:ss";

    private final int checkSum;
    private final MessageData messageData;

    private Message(MessageData messageData) {
        this.messageData = messageData;
        checkSum = messageData.hashCode();
    }

    public Message(String nick, String content, LocalDateTime dateTime) {
        this(
                new MessageData(nick, content, dateTime)
        );
    }

    public Message(String nick, String content) {
        this(nick, content, LocalDateTime.now());
    }

    public String nick() {
        return messageData.nick();
    }

    public String content() {
        return messageData.content();
    }

    public LocalDateTime dateTime() {
        return messageData.dateTime();
    }

    public int checkSum() {
        return checkSum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (checkSum != message.checkSum) return false;
        if (messageData != null ? !messageData.equals(message.messageData) : message.messageData != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = checkSum;
        result = 31 * result + (messageData != null ? messageData.hashCode() : 0);
        return result;
    }

    public static Message fromByteArray(byte[] bytes) {
        final DataInputStream dataInputStream = new DataInputStream(
                new ByteArrayInputStream(bytes)
        );

        int checkSum;
        MessageData deserializedMessageData;

        try {
            checkSum = dataInputStream.readInt();
            deserializedMessageData = SerializationUtils.deserialize(dataInputStream);
        } catch (IOException | SerializationException e) {
            throw new InvalidSerializedMessageException();
        }

        Preconditions.checkArgument(checkSum == deserializedMessageData.hashCode(), "Invalid checksum!");
        return new Message(deserializedMessageData);

    }

    public byte[] toByteArray() {
        final ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

        return buffer
                .putInt(checkSum)
                .put(SerializationUtils.serialize(messageData))
                .array();
    }

    @Override
    public String toString() {
        final DateTimeFormatter formatter = DateTimeFormat.forPattern(DATE_FORMAT_PATTERN);

        return formatter.print(dateTime()) + " " + "<" + nick() + ">" + " " + content();
    }
}
