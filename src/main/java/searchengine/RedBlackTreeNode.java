package searchengine;

import java.util.HashSet;
import java.util.Set;

public class RedBlackTreeNode {
    public RedBlackTreeNode left, right;
    public int color = 0;
    private final String word;
    private final Set<String> documents = new HashSet<>();
    /*
        0 -> red
        1 -> black
        this could be enum for readability
     */
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
