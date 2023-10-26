package searchengine;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class WordListTest {
    WordList list;

    void setupListToTest() {
        if (list == null) {
            list = new WordList();
            list.append(new WordListNode("science", "tech"));
            list.append(new WordListNode("computer", "tech"));
            list.append(new WordListNode("software", "tech"));
            list.append(new WordListNode("information", "tech"));
            list.append(new WordListNode("information", "language"));
            list.append(new WordListNode("comma", "language"));
            list.append(new WordListNode("vision", "language"));
        }
    }

    @Test
    void insertWords() {
        setupListToTest();
        assertEquals("head->comma->computer->information->science->software->vision->end", list.toString());
    }
    @Test
    void searchOneKeywordInOneDoc() {
        setupListToTest();
        assertEquals(Set.of("tech"), list.search(List.of("computer")));
    }

    @Test
    void searchMultipleKeywordsInSameDoc() {
        setupListToTest();
        assertEquals(Set.of("tech"), list.search(List.of("computer", "science")));
    }

    @Test
    void searchOneKeywordInMultipleDocs() {
        setupListToTest();
        assertEquals(Set.of("tech", "language"), list.search(List.of("information")));
    }

    @Test
    void searchMultipleKeywordsInMultipleDocs() {
        setupListToTest();
        assertEquals(Set.of(), list.search(List.of("computer", "guitar")));
    }

    @Test
    void removeOneDocument() {
        setupListToTest();
        list.remove("tech");
        assertEquals(Set.of("language"), list.search(List.of("information")));
    }

    @Test
    void unsearchDocumentsOneExclusion() {
        setupListToTest();
        assertEquals(Set.of("tech"), list.search(List.of("science", "!comma")));
    }
    @Test
    void unsearchDocumentsWordAppearsInBoth() {
        setupListToTest();
        assertEquals(Set.of("tech"), list.search(List.of("science", "information")));
    }
    @Test
    void searchThatAppearsAndDoesNot() {
        WordList list = new WordList();
        list.append(new WordListNode("science", "tech"));
        list.append(new WordListNode("computer", "tech"));
        list.append(new WordListNode("software", "tech"));
        list.append(new WordListNode("information", "tech"));
        list.append(new WordListNode("information", "language"));
        list.append(new WordListNode("comma", "language"));
        list.append(new WordListNode("vision", "language"));
        list.append(new WordListNode("potato", "food"));
        assertEquals(Set.of("tech"), list.search(List.of("science", "!potato", "software")));
    }
    @Test
    void clearListNullRoot() {
        setupListToTest();
        list.clearList();
        assertNull(list.root);
    }
}