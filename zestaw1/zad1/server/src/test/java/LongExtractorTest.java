import org.junit.Test;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class LongExtractorTest {

    @Test
    public void shouldExtractLongGivenPrefixOf1AndByteInStream() throws Exception {

        final LongExtractor objectUnderTest = new LongExtractor();
        final InputStream stream = InputStreamBuilder
                .newInputStream()
                .withPrefix((byte) 1)
                .withByte((byte) 100)
                .withRandomBytesOfLength(20)
                .build();


        assertThat(objectUnderTest.fromInputStream(stream)).isEqualTo(100L);

    }

    @Test
    public void shouldExtractLongGivenPrefixOf2AndShortInStream() throws Exception {

        final LongExtractor objectUnderTest = new LongExtractor();
        final InputStream stream = InputStreamBuilder
                .newInputStream()
                .withPrefix((byte) 2)
                .withShort((short) 100)
                .withRandomBytesOfLength(20)
                .build();


        assertThat(objectUnderTest.fromInputStream(stream)).isEqualTo(100L);

    }

    @Test
    public void shouldExtractLongGivenPrefixOf4AndIntInStream() throws Exception {

        final LongExtractor objectUnderTest = new LongExtractor();
        final InputStream stream = InputStreamBuilder
                .newInputStream()
                .withPrefix((byte) 4)
                .withInt(100)
                .withRandomBytesOfLength(20)
                .build();


        assertThat(objectUnderTest.fromInputStream(stream)).isEqualTo(100L);

    }

    @Test
    public void shouldExtractLongGivenPrefixOf8AndLongInStream() throws Exception {

        final LongExtractor objectUnderTest = new LongExtractor();
        final InputStream stream = InputStreamBuilder
                .newInputStream()
                .withPrefix((byte) 8)
                .withLong(100L)
                .withRandomBytesOfLength(20)
                .build();


        assertThat(objectUnderTest.fromInputStream(stream)).isEqualTo(100L);

    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnExceptionGivenOtherPrefix() throws Exception {

        final LongExtractor objectUnderTest = new LongExtractor();
        final InputStream stream = InputStreamBuilder
                .newInputStream()
                .withPrefix((byte) 10)
                .build();

        objectUnderTest.fromInputStream(stream);

    }
}