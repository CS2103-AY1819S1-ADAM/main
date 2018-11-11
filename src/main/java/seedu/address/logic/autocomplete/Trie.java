package seedu.address.logic.autocomplete;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * Trie data structure for word auto-complete
 */
public class Trie {

    private Node root;
    private int size = 0;

    /**
     * Represents a node of trie
     */
    private class Node {
        private HashMap<Character, Node> children = new HashMap<>();
        private boolean isCompleteWord = false;
    }

    /**
     * Creates a trie
     */
    public Trie() {
        root = new Node();
    }

    /**
     * Inserts a word into Trie
     */
    public void insertWord(String word) {
        requireNonNull(word);
        insert(root, word);
    }

    /**
     * Recursively insert to insert part of key into Trie
     */
    private void insert(Node currentNode, String key) {
        if (!key.isEmpty()) {
            if (!currentNode.children.containsKey(key.charAt(0))) {
                currentNode.children.put(key.charAt(0), new Node());
            }
            insert(currentNode.children.get(key.charAt(0)), key.substring(1));
        } else {
            if (!currentNode.isCompleteWord) {
                size++;
            }
            currentNode.isCompleteWord = true;
        }
    }

    /**
     * Auto-complete Strings
     * Returns an ArrayList of strings of auto-completed words with given prefixes
     */
    public List<String> autoComplete(String prefix) {
        List<String> result = new ArrayList<>();
        if (search(root, prefix) == null) {
            return result;
        }
        for (String s : getAllPostFix(search(root, prefix))) {
            result.add(prefix + s);
        }
        return result;
    }

    /**
     * Recursive search for end node
     */
    private Node search(Node currentNode, String key) {
        if (!key.isEmpty() && currentNode != null) {
            return search(currentNode.children.get(key.charAt(0)), key.substring(1));
        } else {
            return currentNode;
        }
    }

    /**
     * Returns ArrayList of all postfix from node
     */
    private List<String> getAllPostFix(Node node) {
        ArrayList<String> listOfPostFix = new ArrayList<>();
        return getAllPostFix(node, "", null, listOfPostFix);
    }

    /**
     * Recursive method to get all postfix string
     */
    private List<String> getAllPostFix(Node node, String s, Character next, List<String> listOfPostFix) {
        if (next != null) {
            s += next;
        }
        for (Entry<Character, Node> entry : node.children.entrySet()) {
            listOfPostFix = getAllPostFix(entry.getValue(), s, entry.getKey(), listOfPostFix);
        }
        if (node.isCompleteWord) {
            listOfPostFix.add(s);
        }
        return listOfPostFix;
    }

    /**
     * Returns size of Trie
     */
    public int size() {
        return size;
    }
}
