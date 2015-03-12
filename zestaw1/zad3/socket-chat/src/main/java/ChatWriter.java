import message.Message;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by novy on 12.03.15.
 */
public class ChatWriter extends ImmediateWriteTrait implements Runnable {

    private static final String QUIT_COMMAND = "/quit";
    private static final String CONTENT_TOO_SHORT_MESSAGE = "Message too long!";
    private static final int CONTENT_THRESHOLD = 20;

    private final String nick;
    private final DatagramSocket socket;
    private final InetAddress address;
    private final int port;

    private final BufferedReader reader;
    public final OutputStreamWriter writer;

    public ChatWriter(String nick, DatagramSocket socket, InetAddress address, int port, BufferedReader reader, OutputStreamWriter writer) {
        super(writer);
        this.nick = nick;
        this.socket = socket;
        this.address = address;
        this.port = port;

        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public void run() {
        try {
            String messageContent = reader.readLine();

            while (!QUIT_COMMAND.equals(messageContent)) {
                if (messageContent.length() > CONTENT_THRESHOLD) {
                    writeLine(CONTENT_TOO_SHORT_MESSAGE);
                } else {
                    sendMessage(messageContent);
                }
                messageContent = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }

    private void sendMessage(String messageContent) throws IOException {
        final Message messageToSend = new Message(nick, messageContent);
        final byte[] rawMessage = messageToSend.toByteArray();
        socket.send(
                new DatagramPacket(rawMessage, rawMessage.length, address, port)
        );
    }
}
