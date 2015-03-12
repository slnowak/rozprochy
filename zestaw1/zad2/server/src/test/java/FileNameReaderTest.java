import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.assertj.core.api.Assertions.assertThat;

public class FileNameReaderTest {

    @Test
    public void shouldReadGivenNumberOfBytesAsFileNameGivenLengthInFirstByte() throws Exception {

        final String expectedFileName = "filename";
        final InputStream inputStream = prepareArrayByteInputStreamContaining(expectedFileName.length(), expectedFileName);

        final FileNameReader objectUnderTest = new FileNameReader();

        assertThat(objectUnderTest.readFrom(inputStream)).isEqualTo(expectedFileName);

    }

    @Test(expected = EOFException.class)
    public void shouldThrowAnExceptionGivenFileNameShorterThanLengthGivenInFirstByte() throws Exception {

        final InputStream inputStream = prepareArrayByteInputStreamContaining(100, "filename");

        final FileNameReader objectUnderTest = new FileNameReader();

        System.out.println(objectUnderTest.readFrom(inputStream));

    }

    private InputStream prepareArrayByteInputStreamContaining(int length, String filename) {

        ByteBuffer buffer = ByteBuffer.allocate(100);

        buffer
                .put((byte) length)
                .put(filename.getBytes())
                .put(RandomUtils.nextBytes(5));

        return new ByteArrayInputStream(
                buffer.array()
        );
    }
}