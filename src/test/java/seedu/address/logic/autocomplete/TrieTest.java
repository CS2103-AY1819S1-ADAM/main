package seedu.address.logic.autocomplete;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TrieTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void insert_duplicateWord_sameSize() {
        Trie trie = new Trie();
        assertEquals(trie.size(), 0);
        trie.insertWord("lastone");
        assertEquals(trie.size(), 1);
        trie.insertWord("lastone");
        assertEquals(trie.size(), 1);
    }

    @Test
    public void insertNullWord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        Trie trie = new Trie();
        trie.insertWord(null);
    }
}
