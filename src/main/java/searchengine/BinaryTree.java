package searchengine;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class BinaryTree implements EngineableStructure{
    public BinaryTreeNode root = null;
    public BinaryTree() {}

    @Override
    public void append(String word, String document) {
        root = insert(root, new BinaryTreeNode(word, document));
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
        // this implementation is the same. what can i do about it?
        // TODO maybe extract same implementation
        Set<String> docs = new HashSet<>();
        Set<String> excludedDocs = new HashSet<>();

        for (int i = 0; i < keywords.size(); i++) {
            String word = keywords.get(i);
            if (word.trim().charAt(0) == '!') {
                Set<String> keywordDocs = search(root, word.substring(1));
                excludedDocs.addAll(keywordDocs);
                continue;
            }
            Set<String> keywordDocs = search(root, word.trim());
            if (i == 0) {
                docs.addAll(keywordDocs);
                continue;
            }
            docs.retainAll(keywordDocs);
        }
        docs.removeAll(excludedDocs);
        return docs;
    }


    private Set<String> search(BinaryTreeNode node, String word) {
        if (node == null) return new HashSet<>();
        if (node.getWord().compareTo(word) < 0) {
            return search(node.left, word);
        } else if (node.getWord().compareTo(word) > 0) {
            return search(node.right, word);
        } else {
            return node.getDocuments();
        }
    }

    @Override
    public void remove(String document) {
        remove(root, document);
    }
    private void remove(BinaryTreeNode node, String document) {
        if (node == null) return;
        node.getDocuments().remove(document);
        remove(node.left, document);
        remove(node.right, document);
    }

    @Override
    public void reset() {
        if (root != null) {
            root = null;
            // chop chop GC, work is waiting
        }
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
        return levelOrder.toString().trim();
    }

}
