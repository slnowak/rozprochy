import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.Socket;

/**
 * Created by novy on 11.03.15.
 */
public class Worker implements Runnable {

    private final Socket socket;
    private final FileNameReader fileNameReader;


    public Worker(Socket socket) {
        this.socket = socket;

        this.fileNameReader = new FileNameReader();
    }

    @Override
    public void run() {

        try {
            final InputStream inputStream = socket.getInputStream();

            final String fileName = fileNameFrom(inputStream);
            final FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            copyDataBetween(inputStream, fileOutputStream);

            fileOutputStream.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void copyDataBetween(InputStream inputStream, OutputStream outputStream) throws IOException {
        IOUtils.copy(inputStream, outputStream);
    }

    private String fileNameFrom(InputStream inputStream) throws IOException {
        return fileNameReader.readFrom(inputStream);
    }
}
