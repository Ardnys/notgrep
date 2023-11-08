package searchengine;

import java.io.IOException;
import java.io.OutputStream;

/*
THIS IS EXPERIMENTAL ONLY. MAY BREAK SOME STUFF IDK
basically writes to both stdout and a file when you System.out.print
 */

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
