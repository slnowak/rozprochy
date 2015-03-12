import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by novy on 12.03.15.
 */
public class Main {

    private static final int MAXIMUM_NICK_LENGTH = 6;

    public static void main(String[] args) throws Exception {

        validateArgsLength(args.length);

        final InetAddress multicastAddress = InetAddress.getByName(args[0]);
        validateAddress(multicastAddress);

        final int portNumber = Integer.parseInt(args[1]);

        final String nick = args[2];
        validateNick(nick);

        final MulticastSocket socket = new MulticastSocket(portNumber);
        socket.joinGroup(multicastAddress);
        socket.setLoopbackMode(false);


        final Thread chatReaderThread = createChatReaderThread(nick, socket);
        final Thread chatWriterThread = createChatWriterThread(multicastAddress, portNumber, nick, socket);

        chatReaderThread.start();
        chatWriterThread.start();

    }

    private static void validateArgsLength(int args) {
        if (args < 3) {
            System.out.println("Usage: java -jar xxx.jar <multicast address> <port> <nick>");
            System.exit(-1);
        }
    }

    private static void validateAddress(InetAddress multicastAddress) {
        if (!multicastAddress.isMulticastAddress()) {
            System.out.println("Given IP address is not multicast type!");
            System.exit(-1);
        }
    }

    private static void validateNick(String nick) {
        if (nick.length() > MAXIMUM_NICK_LENGTH) {
            System.out.println("Nick too long. Maximum allowed: 5.");
            System.exit(-1);
        }
    }

    private static Thread createChatWriterThread(InetAddress multicastAddress, int portNumber, String nick, MulticastSocket socket) {
        return new Thread(
                new ChatWriter(
                        nick,
                        socket,
                        multicastAddress,
                        portNumber,
                        new BufferedReader(new InputStreamReader(System.in)),
                        new OutputStreamWriter(System.out)
                )
        );
    }

    private static Thread createChatReaderThread(String nick, MulticastSocket socket) {
        return new Thread(
                new ChatReader(
                        nick,
                        socket,
                        new OutputStreamWriter(System.out)
                )
        );
    }

}
