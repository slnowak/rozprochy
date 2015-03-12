package message;

import org.apache.commons.lang3.RandomUtils;
import org.joda.time.LocalDateTime;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MessageTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotBeAbleToCreateMessageWithNickLongerThan6Characters() throws Exception {

        new Message("1234567", "", LocalDateTime.now());

    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotBeAbleToCreateMessageWithContentLongerThan20Characters() throws Exception {

        new Message("nick", "012345678901234567890", LocalDateTime.now());

    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotBeAbleToCreateMessageWithEmptyNick() throws Exception {

        new Message("", "content", LocalDateTime.now());
    }

    @Test(expected = InvalidChecksumException.class)
    public void shouldNotBeAbleToDeserializeFromByteArrayWithInvalidChecksum() throws Exception {

        final String nick = "nick";
        final String content = "content";
        final LocalDateTime dateTime = LocalDateTime.now();
        final int fakeCheckSum = 666;

        final byte[] serializedMessage = MessageByteReprBuilder
                .newByteMessageRepresentation()
                .withNick(nick)
                .withContent(content)
                .withDateTime(dateTime)
                .withCheckSum(fakeCheckSum)
                .build();


        Message.fromByteArray(serializedMessage);
    }

    @Test
    public void shouldBeAbleToSerializeMessage() throws Exception {

        final Message message = new Message("nick", "content", LocalDateTime.now());
        final byte[] expectedBytes = MessageByteReprBuilder
                .newByteMessageRepresentation()
                .withNick(message.nick())
                .withContent(message.content())
                .withDateTime(message.dateTime())
                .withCheckSum(message.checkSum())
                .build();

        assertThat(expectedBytes).containsExactly(message.toByteArray());

    }

    @Test
    public void serializedMessageShouldBeEqualAfterDeserialization() throws Exception {

        final Message expectedMessage = new Message("nick", "content", LocalDateTime.now());
        final byte[] serializedMessage = MessageByteReprBuilder
                .newByteMessageRepresentation()
                .withNick(expectedMessage.nick())
                .withContent(expectedMessage.content())
                .withDateTime(expectedMessage.dateTime())
                .withCheckSum(expectedMessage.checkSum())
                .build();

        assertThat(expectedMessage).isEqualTo(Message.fromByteArray(serializedMessage));

    }

    @Test(expected = InvalidSerializedMessageException.class)
    public void shouldThrowInvalidSerializedMessageExceptionGivenRandomBytes() throws Exception {

        Message.fromByteArray(
                RandomUtils.nextBytes(5)
        );
    }

    @Test
    public void toStringShouldProduceNiceReadableString() throws Exception {

        final Message message = new Message("novy", "message", new LocalDateTime(2015, 1, 1, 13, 0, 0));
        final String expected = "2015-01-01 13:00:00 <novy> message";

        assertThat(message.toString()).isEqualTo(expected);


    }
}