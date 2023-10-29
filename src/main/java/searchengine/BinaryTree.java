package searchengine;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class BinaryTree implements EngineableStructure {
    public BinaryTreeNode root = null;
    public BinaryTree() {}

    @Override
    public void append(EngineableNode node) {
        root = insert(root, (BinaryTreeNode) node);
    }
    private BinaryTreeNode insert(BinaryTreeNode root, BinaryTreeNode node) {
        if (root == null) {
            root = node;
            return root;
        } else if (root.getWord().compareTo(node.getWord()) < 0) {
            // insert left
            root.left = insert(root.left, node);
        } else if (root.getWord().compareTo(node.getWord()) > 0) {
            // insert right
            root.right = insert(root.right, node);
        } else {
            // duplicate. add all documents
            root.getDocuments().addAll(node.getDocuments());
        }
        return root;
    }

    @Override
    public Set<String> search(List<String> keywords) {
        return null;
    }

    @Override
    public void remove(String document) {

    }

    @Override
    public void reset() {

    }

    @Override
    public String toString() {
        StringBuilder levelOrder = new StringBuilder();

        Queue<BinaryTreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            var node = queue.poll();
            levelOrder.append(node.getWord());
            levelOrder.append(' ');
            if (node.left != null)
                queue.add(node.left);
            if (node.right != null)
                queue.add(node.right);
        }
        return levelOrder.toString();
    }

}
