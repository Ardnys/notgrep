package searchengine;


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
        int wordComparison;
        while (current != null) {
            wordComparison = node.getWord().compareTo(current.getWord());
            if (wordComparison == 0) return;
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
                    // insert at the end
                    current.next = node;
                    return;
                }
            } else {
                root = node;
                node.next = current;
                return;
            }
            current = current.next;
        }
    }

    public void printList() {
        WordListNode current = root;
        System.out.print("head->");
        while (current != null) {
            System.out.printf("%s->", current.getWord());
            current = current.next;
        }
        System.out.print("end");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("head->");
        WordListNode current = root;
        while (current != null) {
            sb.append(current.getWord());
            sb.append("->");
            current = current.next;
        }
        sb.append("end");
        return sb.toString();
    }
}
