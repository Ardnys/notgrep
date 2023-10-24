package searchengine;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordList {
    public WordListNode root;

    public WordList() {
    }

    public void append(WordListNode node) {
        if (root == null) {
            root = node;
        } else {
            insert(node);
        }
    }

    private void insert(WordListNode node) {
        var current = root;
        int wordComparison;
        while (current != null) {
            wordComparison = node.getWord().compareTo(current.getWord());
            if (wordComparison == 0) {
                var nodeDocs = node.getDocuments();
                for (String doc : nodeDocs) {
                    current.getDocuments().add(doc);
                }
                return;
            }
            if (wordComparison > 0) {
                if (current.next != null) {
                    if (node.getWord().compareTo(current.next.getWord()) < 0) {
                        // found the place
                        var hold = current.next;
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

    public Set<String> search(List<String> keywords) {
        Set<String> docs = new HashSet<>();

        for (int i = 0; i < keywords.size(); i++) {
            Set<String> keywordDocs = search(keywords.get(i));
            if (i == 0) {
                docs.addAll(keywordDocs);
                continue;
            }
            docs = intersection(keywordDocs, docs);
        }

        return docs;
    }

    private Set<String> intersection(Set<String> a, Set<String> b) {
        Set<String> large, small, inter = new HashSet<>();
        if (a.size() > b.size()) {
            large = a;
            small = b;
        } else {
            large = b;
            small = a;
        }
        for (String s :
                small) {
            if (large.contains(s)) {
                inter.add(s);
            }
        }
        return inter;
    }

    private Set<String> search(String keyword) {
        // TODO intersection not union
        var current = root;
        while (current != null) {
            if (current.getWord().equals(keyword)) {
                // found the word
                return current.getDocuments();
            }
            current = current.next;
        }
        return new HashSet<>();
    }

    public void remove(String document) {
        var current = root;
        while (current != null) {
            // TODO maybe add node deletion for one document
            current.getDocuments().remove(document);
            current = current.next;
        }
    }

    public void printList() {
        var current = root;
        System.out.print("head->");
        while (current != null) {
            System.out.printf("%s->", current.getWord());
            current = current.next;
        }
        System.out.print("end");
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append("head->");
        var current = root;
        while (current != null) {
            sb.append(current.getWord());
            sb.append("->");
            current = current.next;
        }
        sb.append("end");
        return sb.toString();
    }
}
