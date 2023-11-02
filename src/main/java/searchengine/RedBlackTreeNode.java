package searchengine;

import java.util.HashSet;
import java.util.Set;

public class RedBlackTreeNode {
    public RedBlackTreeNode parent;
    public RedBlackTreeNode left, right;
    public Color color = Color.RED;
    private final String word;
    private final Set<String> documents = new HashSet<>();

    public RedBlackTreeNode(String word, String document) {
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
