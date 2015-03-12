import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by novy on 11.03.15.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        final PiDigitGenerator generator = new PiDigitGenerator(ConstantsHolder.CUTOFF_THRESHOLD);
        final ExecutorService executorService = Executors.newFixedThreadPool(ConstantsHolder.THREADS);
        final ServerSocket serverSocket = new ServerSocket(ConstantsHolder.PORT);

        new Thread(
                new Server(generator, serverSocket, executorService)
        ).start();
    }
}
