import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by novy on 12.03.15.
 */
public abstract class ImmediateWriteTrait {

    private final OutputStreamWriter writer;

    public ImmediateWriteTrait(OutputStreamWriter writer) {
        this.writer = writer;
    }

    protected void writeLine(String line) throws IOException {
        IOUtils.write(line, writer);
        writer.write("\n");
        writer.flush();
    }
}
