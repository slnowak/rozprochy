import org.apache.commons.lang3.RandomUtils;

import java.io.ByteArrayInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by novy on 11.03.15.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        final ServerSocket serverSocket = new ServerSocket(ConstantHolder.PORT);
        final ExecutorService executorService = Executors.newFixedThreadPool(ConstantHolder.WORKERS);
        final Thread serverThread = new Thread(
                new Server(serverSocket, executorService)
        );

        serverThread.start();
    }


}
