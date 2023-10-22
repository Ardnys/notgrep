package searchengine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordListNode {
    public WordListNode next;
    private final String word;
    private final List<String> documents = new ArrayList<>();


    public WordListNode(String word, String document) {
        this.word = word;
        this.addDocument(document);
    }
    public void addDocument(String document) {
        documents.add(document);
    }
    public void printDocuments() {
        documents.forEach(System.out::println);
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
}
