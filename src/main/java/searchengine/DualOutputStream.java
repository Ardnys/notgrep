package searchengine;

import java.io.IOException;
import java.io.OutputStream;

public class DualOutputStream extends OutputStream {

    private final OutputStream standard;
    private final OutputStream file;

    public DualOutputStream(OutputStream standard, OutputStream file) {
        this.standard = standard;
        this.file = file;
    }

    @Override
    public void write(int b) throws IOException {
        standard.write(b);
        file.write(b);
    }
}
