package searchengine;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class NotTreeNode {
    private final String word;
    private final Set<String> documents = new HashSet<>();
    public NotTreeNode next;

    public NotTreeNode(String word, String document) {
        this.word = word;
        this.addDocument(document);
    }

    public void addDocument(String document) {
        documents.add(document);
    }

    @Override
    public String toString() {
        return "WordListNode{" +
                "next=" + next +
                ", documents=" + Arrays.toString(documents.toArray()) +
                '}';
    }

    public String getWord() {
        return word;
    }

    public Set<String> getDocuments() {
        return documents;
    }
}
