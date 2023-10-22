package searchengine;

import static searchengine.FileScanner.Mode.*;

public class FileScanner {
    enum Mode {
        TITLE,
        BODY
    }
    private final String contents;
    private final WordList words = new WordList();
    private int start = 0;
    private int current = 0;
    private String currentDocument;
    private Mode mode = TITLE;
    public FileScanner(String contents) {
        this.contents = contents;
    }

    public WordList scanFile() {
        while (current <= contents.length()) {
            start = current;
            scan();
        }
        return words;
    }

    private void scan() {
        char c = advance();

        // TODO a title is scanned until the separator is encountered
        // TODO body is scanned word by word
    }

    private void word() {
        while (Character.isAlphabetic(peek())) advance();

        String word = contents.substring(start, current);
    }
    private void separator() {
        switch (mode) {
            case TITLE: mode = BODY;
            case BODY: mode = TITLE;
        }

    }

    private char advance() {
        return contents.charAt(current++);
    }
    private char peek() {
        if (current >= contents.length()) return '\0';
        return contents.charAt(current);
    }
}
