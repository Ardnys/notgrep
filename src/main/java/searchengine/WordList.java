package searchengine;

import java.util.List;

public class WordList {
    public WordListNode root;
    private WordListNode last;

    public WordList() {    }

    public void append(WordListNode node) {
        if (root == null) {
            root = node;
        } else {
            last.next = node;
        }
        last = node;
    }
}
