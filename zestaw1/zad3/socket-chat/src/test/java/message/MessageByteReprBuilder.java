package message;

import org.apache.commons.lang3.SerializationUtils;
import org.joda.time.LocalDateTime;

import java.nio.ByteBuffer;

/**
 * Created by novy on 12.03.15.
 */
public class MessageByteReprBuilder {

    private static final int BUFFER_SIZE = 2048;
    private final ByteBuffer buffer;

    private String nick = "nick";
    private String content = "content";
    private LocalDateTime dateTime = LocalDateTime.now();
    private int checkSum = 666;

    private MessageByteReprBuilder() {
        buffer = ByteBuffer.allocate(BUFFER_SIZE);
    }

    public static MessageByteReprBuilder newByteMessageRepresentation() {
        return new MessageByteReprBuilder();
    }

    public MessageByteReprBuilder withNick(String nick) {
        this.nick = nick;
        return this;
    }

    public MessageByteReprBuilder withContent(String content) {
        this.content = content;
        return this;
    }

    public MessageByteReprBuilder withDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public MessageByteReprBuilder withCheckSum(int checkSum) {
        this.checkSum = checkSum;
        return this;
    }

    public byte[] build() {
        return
                buffer
                        .putInt(checkSum)
                        .put(SerializationUtils.serialize(
                                new MessageData(nick, content, dateTime)

                        ))
                        .array();
    }
}
