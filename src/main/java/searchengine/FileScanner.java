package searchengine;

import java.util.HashSet;
import java.util.Set;

import static searchengine.FileScanner.Mode.BODY;
import static searchengine.FileScanner.Mode.TITLE;

public class FileScanner {
    private final String contents;
    private final Set<String> allDocs = new HashSet<>();
    private EngineableStructure words = null;
    // I wanted this final but this works now. I don't like it
    private int start = 0;
    private int current = 0;
    private String currentDocument;
    private Mode mode = TITLE;

    public FileScanner(String contents, Flag flag) {
        this.contents = contents;
        switch (flag) {
            case BST -> words = new BinaryTree();
            case LL -> words = new NotTree();
        }
    }

    public EngineableStructure scanFile() {
        while (current < contents.length()) {
            start = current;
            scan();
        }
        words.setAllDocs(allDocs);
        return words;
    }

    private void scan() {
        char c = advance();


        if (mode == TITLE) {
            if (Character.isAlphabetic(c)) {
                title();
            } else if (c == '#') {
                separator();
            }
        } else {
            // mode is BODY
            if (Character.isAlphabetic(c)) {
                word();
            } else if (c == '#') {
                separator();
            }
        }
    }

    private void title() {
        while (!isBang(peek())) advance();

        currentDocument = contents.substring(start, current);
    }

    private void word() {
        while (Character.isAlphabetic(peek())) advance();

        var word = contents
                .substring(start, current)
                .toLowerCase();
//        System.out.println("word: " + word);
        words.append(word, currentDocument);
        allDocs.add(currentDocument);
    }

    private void separator() {
        switch (mode) {
            case TITLE -> mode = BODY;
            case BODY -> mode = TITLE;
        }
        while (isBang(peek())) advance();

    }

    private boolean isBang(char c) {
        return c == '#';
    }

    private char advance() {
        return contents.charAt(current++);
    }

    private char peek() {
        if (current >= contents.length()) return '\0';
        return contents.charAt(current);
    }

    enum Mode {
        TITLE,
        BODY
    }
}
