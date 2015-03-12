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

        final ServerSocket serverSocket = new ServerSocket(serverPort);
        final ExecutorService executorService = Executors.newFixedThreadPool(ConstantHolder.WORKERS);
        final Thread serverThread = new Thread(
                new Server(serverSocket, executorService)
        );

        serverThread.start();
    }

    private static int parsePortNumberFrom(String[] args) {
        if (args.length > 0 && NumberUtils.isNumber(args[0])) {
            return NumberUtils.createInteger(args[0]);
        }
        return ConstantHolder.PORT;
    }
}
