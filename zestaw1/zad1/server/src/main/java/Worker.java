import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * Created by novy on 11.03.15.
 */
public class Worker implements Runnable {

    private final PiDigitGenerator piDigitGenerator;
    private final Socket socket;
    private final LongExtractor extractor;

    public Worker(PiDigitGenerator piDigitGenerator, Socket socket) {
        this.piDigitGenerator = piDigitGenerator;
        this.socket = socket;

        this.extractor = new LongExtractor();
    }

    @Override
    public void run() {
        while (!socket.isClosed()) {
            try {

                final long extractedInput = extractDataFrom(
                        socket.getInputStream()
                );

                final int piDigit = generatePiDigit(extractedInput);

                sendResponse(piDigit);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private int generatePiDigit(long extractedInput) {
        return piDigitGenerator.generate(extractedInput);
    }

    private void sendResponse(int piDigit) throws IOException {
        final ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(piDigit);

        final OutputStream stream = socket.getOutputStream();
        stream.write(
                buffer.array()
        );
    }

    private long extractDataFrom(InputStream stream) throws IOException {
        return extractor.fromInputStream(stream);
    }
}
