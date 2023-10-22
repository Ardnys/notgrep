package searchengine;

public class FileScanner {
    private final String contents;
    private WordList words;
    private int current = 0;
    public FileScanner(String contents) {
        this.contents = contents;
    }

    public void scan() {
        char c = advance();

    }

    private char advance() {
        return contents.charAt(current++);
    }
    private char peek() {
        if (current >= contents.length()) return '\0';
        return contents.charAt(current);
    }
    private boolean separator() {
        // TODO peeks and advances to match a separator. returns true if hashes match the separator
        return false;
    }


}
