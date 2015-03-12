import org.apache.commons.io.IOUtils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by novy on 11.03.15.
 */
class FileNameReader {

    public String readFrom(InputStream inputStream)throws IOException {
        final DataInputStream dataInputStream = new DataInputStream(inputStream);

        final int fileNameLength = dataInputStream.readByte();
        final byte[] fileNameRawData = new byte[fileNameLength];
        dataInputStream.readFully(fileNameRawData, 0, fileNameLength);
        return IOUtils.toString(fileNameRawData, "UTF-8");
    }
}
