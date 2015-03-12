import message.InvalidChecksumException;
import message.Message;
import org.apache.commons.io.IOUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by novy on 12.03.15.
 */
public class ChatReader extends ImmediateWriteTrait implements Runnable {

    private static final int BUFFER_SIZE = 2048;
    private static final String INVALID_CHECKSUM_MESSAGE = "Got a message with invalid checksum!";

    private final String nick;
    private final DatagramSocket socket;
    private final OutputStreamWriter writer;


    public ChatReader(String nick, DatagramSocket socket, OutputStreamWriter writer) {
        super(writer);
        this.nick = nick;
        this.socket = socket;
        this.writer = writer;
    }

    @Override
    public void run() {

        while (!socket.isClosed()) {

            try {
                final DatagramPacket packet = waitForPacketToReceive();
                final Message message = Message.fromByteArray(packet.getData());

                if (!nick.equals(message.nick())) {
                    writeLine(message.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidChecksumException e) {
                //todo: jesus fucking christ...
                try {
                    writeLine(INVALID_CHECKSUM_MESSAGE);
                } catch (IOException inner) {
                    inner.printStackTrace();
                }
            }
        }
    }


    private DatagramPacket waitForPacketToReceive() throws IOException {
        final DatagramPacket emptyPacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
        socket.receive(emptyPacket);
        return emptyPacket;
    }
}