import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * Created by novy on 11.03.15.
 */
public class Server implements Runnable {

    private final PiDigitGenerator piDigitGenerator;
    private final ServerSocket socket;
    private final ExecutorService executorService;


    public Server(PiDigitGenerator piDigitGenerator, ServerSocket socket, ExecutorService executorService) {

        this.piDigitGenerator = piDigitGenerator;
        this.socket = socket;
        this.executorService = executorService;
    }

    @Override
    public void run() {

        while (true) {

            try {
                final Socket clientSocket = socket.accept();
                handle(clientSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handle(Socket clientSocket) {
        executorService.submit(
                new Worker(piDigitGenerator, clientSocket)
        );
    }
}
