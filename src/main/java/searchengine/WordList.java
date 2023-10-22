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
    public void printList() {
        WordListNode current = root;
        System.out.print("head->");
        while (current != null) {
            System.out.printf("%s->%n", current.getWord());
            current = current.next;
        }
        System.out.print("->end");
    }
}
