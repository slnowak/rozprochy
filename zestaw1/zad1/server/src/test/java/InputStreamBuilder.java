import org.apache.commons.lang3.RandomUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Created by novy on 11.03.15.
 */
public class InputStreamBuilder {

    private final ByteBuffer byteBuffer = ByteBuffer.allocate(256);

    private InputStreamBuilder() {
    }

    public InputStreamBuilder withByte(byte number) {
        byteBuffer.put(number);
        return this;
    }

    public InputStreamBuilder withPrefix(byte prefix) {
        return withByte(prefix);
    }

    public InputStreamBuilder withShort(short number) {
        byteBuffer.putShort(number);
        return this;
    }

    public InputStreamBuilder withInt(int number) {
        byteBuffer.putInt(number);
        return this;
    }

    public InputStreamBuilder withLong(long number) {
        byteBuffer.putLong(number);
        return this;
    }

    public InputStreamBuilder withRandomBytesOfLength(int n) {
        byteBuffer.put(RandomUtils.nextBytes(n));
        return this;
    }

    public static InputStreamBuilder newInputStream() {
        return new InputStreamBuilder();
    }

    public InputStream build() {
        return new ByteArrayInputStream(
                byteBuffer.array()
        );
    }
}
