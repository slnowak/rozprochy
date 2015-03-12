import org.apache.commons.lang3.math.NumberUtils;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by novy on 11.03.15.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        final int serverPort = parsePortNumberFrom(args);

        final PiDigitGenerator generator = new PiDigitGenerator(ConstantsHolder.CUTOFF_THRESHOLD);
        final ExecutorService executorService = Executors.newFixedThreadPool(ConstantsHolder.THREADS);
        final ServerSocket serverSocket = new ServerSocket(serverPort);

        new Thread(
                new Server(generator, serverSocket, executorService)
        ).start();
    }

    private static int parsePortNumberFrom(String[] args) {
        if (args.length > 0 && NumberUtils.isNumber(args[0])) {
            return NumberUtils.createInteger(args[0]);
        }
        return ConstantsHolder.PORT;
    }
}
