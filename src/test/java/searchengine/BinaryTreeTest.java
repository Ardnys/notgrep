package searchengine;

import org.junit.jupiter.api.Test;

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
}