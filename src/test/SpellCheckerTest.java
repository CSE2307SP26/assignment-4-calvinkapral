import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SpellCheckerTest {

    private SpellChecker sc;

    @BeforeEach
    void setUp() {
        sc = new SpellChecker();
    }

    // 1) A spellchecker should be able to tell us how many words it currently knows about
    @Test
    void reportsHowManyWordsItKnows() {
        assertEquals(0, sc.size());
        sc.addWord("cat");
        assertEquals(1, sc.size());
        sc.addWord("dog");
        assertEquals(2, sc.size());
    }

    // 2) The number of words should go up by one whenever a new word is added
    @Test
    void countIncreasesByOneWhenNewWordAdded() {
        int before = sc.size();
        sc.addWord("bank");
        assertEquals(before + 1, sc.size());
    }

    // 3) Adding a duplicate word should not change the number of words
    @Test
    void addingDuplicateWordDoesNotChangeCount() {
        sc.addWord("cat");
        int before = sc.size();
        sc.addWord("cat");
        assertEquals(before, sc.size());
    }

    // 4) Properly spelled word returns an indication it is properly spelled
    @Test
    void properlySpelledWordIsMarkedCorrect() {
        sc.addWord("bank");
        assertTrue(sc.isCorrect("bank"));
    }

    // 5) Improperly spelled word returns an indication it is improperly spelled
    @Test
    void improperlySpelledWordIsMarkedIncorrect() {
        sc.addWord("bank");
        assertFalse(sc.isCorrect("bamk"));
    }

    // 6) Ignore case when checking spelling
    @Test
    void checkingIgnoresCase() {
        sc.addWord("cat");
        assertTrue(sc.isCorrect("cat"));
        assertTrue(sc.isCorrect("CaT"));
        assertTrue(sc.isCorrect("CAT"));
        assertTrue(sc.isCorrect("cAt"));
    }

    // 7) Recommend alphabetically closest word (bamk -> bank)
    @Test
    void suggestReturnsAlphabeticallyClosestWord() {
        // Ensure "bank" is the closest alphabetical neighbor to "bamk"
        sc.addWord("banana");
        sc.addWord("bank");
        sc.addWord("bar");

        assertEquals("bank", sc.suggest("bamk"));
    }

    // 8) If already properly spelled, suggest returns original word
    @Test
    void suggestReturnsOriginalIfAlreadyCorrect() {
        sc.addWord("bank");
        assertEquals("bank", sc.suggest("bank"));
    }

    // 9) Extra feature: reject null inputs
    @Test
    void addWordRejectsNull() {
        assertThrows(IllegalArgumentException.class, () -> sc.addWord(null));
        assertThrows(IllegalArgumentException.class, () -> sc.isCorrect(null));
        assertThrows(IllegalArgumentException.class, () -> sc.suggest(null));
    }

    // 10) Extra feature: trims whitespace on add/check/suggest
    @Test
    void trimsWhitespaceOnInputs() {
        sc.addWord("  cat  ");
        assertEquals(1, sc.size());

        assertTrue(sc.isCorrect("cat"));
        assertTrue(sc.isCorrect("  cat"));
        assertTrue(sc.isCorrect("cat  "));
        assertTrue(sc.isCorrect("  cat  "));

        // If suggest is called on a correct word with whitespace, it should still return the original trimmed/correct form
        assertEquals("cat", sc.suggest("  cat  "));
    }
}
