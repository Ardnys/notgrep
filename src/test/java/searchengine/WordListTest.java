package searchengine;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordListTest {
    // TODO make this test class nicer

    @Test
    void insertBeginning() {
        WordList list = new WordList();
        list.append(new WordListNode("b", "hello"));
        list.append(new WordListNode("f", "hello"));
        list.append(new WordListNode("a", "hello"));
        assertEquals("head->a->b->f->end", list.toString());
    }

    @Test
    void insertMiddle() {
        WordList list = new WordList();
        list.append(new WordListNode("a", "hello"));
        list.append(new WordListNode("f", "hello"));
        list.append(new WordListNode("b", "hello"));
        list.append(new WordListNode("y", "hello"));
        list.append(new WordListNode("j", "hello"));
        assertEquals("head->a->b->f->j->y->end", list.toString());
    }

    @Test
    void insertWords() {
        WordList list = new WordList();
        list.append(new WordListNode("television", "tech"));
        list.append(new WordListNode("science", "tech"));
        list.append(new WordListNode("computer", "tech"));
        list.append(new WordListNode("zoom", "hello"));
        list.append(new WordListNode("apostrophe", "hello"));
        assertEquals("head->apostrophe->computer->science->television->zoom->end", list.toString());
    }

    @Test
    void insertDuplicate() {
        WordList list = new WordList();
        list.append(new WordListNode("a", "hello"));
        list.append(new WordListNode("f", "hello"));
        list.append(new WordListNode("b", "hello"));
        list.append(new WordListNode("f", "hello"));
        list.append(new WordListNode("a", "hello"));
        list.append(new WordListNode("j", "hello"));
        assertEquals("head->a->b->f->j->end", list.toString());
    }

    @Test
    void searchOneKeywordInOneDoc() {
        WordList list = new WordList();
        list.append(new WordListNode("television", "tech"));
        list.append(new WordListNode("science", "tech"));
        list.append(new WordListNode("computer", "tech"));
        list.append(new WordListNode("comma", "language"));
        list.append(new WordListNode("apostrophe", "language"));
        list.append(new WordListNode("software", "tech"));
        list.append(new WordListNode("information", "language"));
        list.append(new WordListNode("information", "tech"));
        list.append(new WordListNode("vision", "language"));
        assertEquals(Set.of("tech"), list.search(List.of("computer")));
    }

    @Test
    void searchMultipleKeywordsInSameDoc() {
        WordList list = new WordList();
        list.append(new WordListNode("television", "tech"));
        list.append(new WordListNode("science", "tech"));
        list.append(new WordListNode("computer", "tech"));
        list.append(new WordListNode("comma", "language"));
        list.append(new WordListNode("apostrophe", "language"));
        list.append(new WordListNode("software", "tech"));
        list.append(new WordListNode("information", "language"));
        list.append(new WordListNode("information", "tech"));
        list.append(new WordListNode("vision", "language"));
        assertEquals(Set.of("tech"), list.search(List.of("computer", "science", "television")));
    }

    @Test
    void searchOneKeywordInMultipleDocs() {
        WordList list = new WordList();
        list.append(new WordListNode("television", "tech"));
        list.append(new WordListNode("science", "tech"));
        list.append(new WordListNode("computer", "tech"));
        list.append(new WordListNode("comma", "language"));
        list.append(new WordListNode("apostrophe", "language"));
        list.append(new WordListNode("software", "tech"));
        list.append(new WordListNode("information", "language"));
        list.append(new WordListNode("information", "tech"));
        list.append(new WordListNode("vision", "language"));
        assertEquals(Set.of("tech", "language"), list.search(List.of("information")));
    }

    @Test
    void searchMultipleKeywordsInMultipleDocs() {
        WordList list = new WordList();
        list.append(new WordListNode("television", "tech"));
        list.append(new WordListNode("guitar", "music"));
        list.append(new WordListNode("waves", "music"));
        list.append(new WordListNode("meaning", "language"));
        list.append(new WordListNode("programming", "tech"));
        list.append(new WordListNode("science", "tech"));
        list.append(new WordListNode("computer", "tech"));
        list.append(new WordListNode("comma", "language"));
        list.append(new WordListNode("apostrophe", "language"));
        list.append(new WordListNode("software", "tech"));
        list.append(new WordListNode("information", "language"));
        list.append(new WordListNode("information", "tech"));
        list.append(new WordListNode("vision", "language"));
        assertEquals(Set.of(), list.search(List.of("computer", "guitar")));
    }

    @Test
    void removeOneDocument() {
        WordList list = new WordList();
        list.append(new WordListNode("science", "tech"));
        list.append(new WordListNode("computer", "tech"));
        list.append(new WordListNode("software", "tech"));
        list.append(new WordListNode("information", "tech"));
        list.append(new WordListNode("information", "language"));
        list.append(new WordListNode("comma", "language"));
        list.append(new WordListNode("vision", "language"));
        list.remove("tech");
        assertEquals(Set.of("language"), list.search(List.of("information")));
    }

    @Test
    void unsearchDocumentsOneExclusion() {
        WordList list = new WordList();
        list.append(new WordListNode("science", "tech"));
        list.append(new WordListNode("computer", "tech"));
        list.append(new WordListNode("software", "tech"));
        list.append(new WordListNode("information", "tech"));
        list.append(new WordListNode("information", "language"));
        list.append(new WordListNode("comma", "language"));
        list.append(new WordListNode("vision", "language"));
        assertEquals(Set.of("tech"), list.search(List.of("science", "!comma")));
    }
    @Test
    void unsearchDocumentsWordAppearsInBoth() {
        WordList list = new WordList();
        list.append(new WordListNode("science", "tech"));
        list.append(new WordListNode("computer", "tech"));
        list.append(new WordListNode("software", "tech"));
        list.append(new WordListNode("information", "tech"));
        list.append(new WordListNode("information", "language"));
        list.append(new WordListNode("comma", "language"));
        list.append(new WordListNode("vision", "language"));
        assertEquals(Set.of(), list.search(List.of("science", "!information")));
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
        assertEquals(Set.of("tech"), list.search(List.of("science", "!vision", "software")));
    }
}