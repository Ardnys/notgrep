package searchengine;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class WordListTest {
    NotTree list;

    void setupListToTest() {
        if (list == null) {
            list = new NotTree();
            Set<String> docs = new HashSet<>();
            docs.add("tech");
            docs.add("language");
            list.setAllDocs(docs);
            list.append("science", "tech");
            list.append("computer", "tech");
            list.append("software", "tech");
            list.append("information", "tech");
            list.append("information", "language");
            list.append("comma", "language");
            list.append("vision", "language");
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
    }@Test
    void searchOneExcludedKeywordInOneDoc() {
        setupListToTest();
        assertEquals(Set.of("tech"), list.search(List.of("!comma")));
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
        NotTree list = new NotTree();
        list.append("science", "tech");
        list.append("computer", "tech");
        list.append("software", "tech");
        list.append("information", "tech");
        list.append("information", "language");
        list.append("comma", "language");
        list.append("vision", "language");
        list.append("potato", "food");
        assertEquals(Set.of("tech"), list.search(List.of("science", "!potato", "software")));
    }
    @Test
    void clearListNullRoot() {
        setupListToTest();
        list.reset();
        assertNull(list.root);
    }
}