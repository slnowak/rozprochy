import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * Created by novy on 11.03.15.
 */
public class Server implements Runnable {

    private final ServerSocket serverSocket;
    private final ExecutorService executorService;

    public Server(ServerSocket serverSocket, ExecutorService executorService) {
        this.serverSocket = serverSocket;
        this.executorService = executorService;
    }

    @Override
    public void run() {

        while (true) {
            try {

                final Socket clientSocket = serverSocket.accept();
                handle(clientSocket);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void handle(Socket clientSocket) {
        executorService.submit(
                new Worker(clientSocket)
        );
    }
}
