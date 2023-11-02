package searchengine;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class RedBlackTree implements EngineableStructure {
    public RedBlackTreeNode root = null;

    @Override
    public void append(String word, String document) {

    }

    @Override
    public Set<String> search(List<String> keywords) {
        return null;
    }

    @Override
    public void remove(String document) {
        remove(root, document);
    }
    private void remove(RedBlackTreeNode node, String document) {
        // TODO maybe faster and better traversals than recursive ones?
        if (node == null) return;
        node.getDocuments().remove(document);
        remove(node.left, document);
        remove(node.right, document);
    }

    @Override
    public void reset() {
        root = null;
    }
    @Override
    public String toString() {
        StringBuilder levelOrder = new StringBuilder();

        Queue<RedBlackTreeNode> queue = new ArrayDeque<>();
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
        return levelOrder.toString().trim();
    }
}
