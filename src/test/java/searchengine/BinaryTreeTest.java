package searchengine;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BinaryTreeTest {
    BinaryTree tree;

    void setupTree() {
        if (tree == null) {
            tree = new BinaryTree();
            tree.append(new BinaryTreeNode("science", "cs"));
            tree.append(new BinaryTreeNode("computer", "cs"));
            tree.append(new BinaryTreeNode("software", "cs"));
            tree.append(new BinaryTreeNode("information", "cs"));
            tree.append(new BinaryTreeNode("information", "lang"));
            tree.append(new BinaryTreeNode("comma", "lang"));
            tree.append(new BinaryTreeNode("vision", "lang"));
            tree.append(new BinaryTreeNode("alphabet", "lang"));
        }
    }

    @Test
    void insertWords() {
        setupTree();
        assertEquals("science software computer vision information comma alphabet", tree.toString());
    }

    @Test
    void searchOneWord() {
        setupTree();
        assertEquals(Set.of("cs"), tree.search(List.of("science")));
    }
    @Test
    void searchTwoWord() {
        setupTree();
        assertEquals(Set.of("cs"), tree.search(List.of("science", "software")));
    }

    @Test
    void searchOneWordInTwoFiles() {
        setupTree();
        assertEquals(Set.of("cs", "lang"), tree.search(List.of("information")));
    }
    @Test
    void searchWordsInNoFiles() {
        setupTree();
        assertEquals(Set.of(), tree.search(List.of("heyvsaucemichaelhere")));
    }
    @Test
    void searchOneExcludeOne() {
        setupTree();
        assertEquals(Set.of("cs"), tree.search(List.of("science", "!")));
    }
}