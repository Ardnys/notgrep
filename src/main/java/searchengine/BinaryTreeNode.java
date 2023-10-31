package searchengine;

import java.util.HashSet;
import java.util.Set;

public class BinaryTreeNode {
    public BinaryTreeNode left, right;
    private final String word;
    private final Set<String> documents = new HashSet<>();

    public BinaryTreeNode(String word, String document) {
        this.word = word;
        documents.add(document);
    }

    public String getWord() {
        return word;
    }

    public Set<String> getDocuments() {
        return documents;
    }
}
