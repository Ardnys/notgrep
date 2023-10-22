package searchengine;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordListTest {

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
        list.append(new WordListNode("television", "hello"));
        list.append(new WordListNode("science", "hello"));
        list.append(new WordListNode("computer", "hello"));
        list.append(new WordListNode("zoom", "hello"));
        list.append(new WordListNode("apostrophe", "hello"));
        assertEquals("head->apostrophe->computer->science->television->zoom->end", list.toString());
    }
}