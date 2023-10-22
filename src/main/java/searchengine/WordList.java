package searchengine;

import java.util.List;

public class WordList {
    public WordListNode root;

    public WordList() {    }

    public void append(WordListNode node) {
        // TODO this should be alphabetic
        if (root == null) {
            root = node;
        } else {
            insert(node);
        }
    }
    private void insert(WordListNode node) {
        WordListNode current = root;
        while (current != null) {
            int wordComparison = node.getWord().compareTo(current.getWord());
            if (wordComparison > 0) {
                if (current.next != null) {
                    if (node.getWord().compareTo(current.next.getWord()) < 0) {
                        // found the place
                        WordListNode hold = current.next;
                        current.next = node;
                        node.next = hold;
                        return;
                    }
                } else {
                    current.next = node;
                    return;
                }
            } else {
                // duplicate
                return;
            }
            current = current.next;
        }
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
