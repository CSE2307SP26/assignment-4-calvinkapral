package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SpellCheckerTest {

	
	}
	@Test
	void testCreation(){

	
		//1. Construct Object
		SpellChecker checker = new SpellChecker();
	
		//2 Call the method being tested
		int words = checker.getNumberOfWords();
	
	
		//3 Use assertions to verify results
		assertEquals(0, words);
	}
	void testWordKnown(){
	 	assertEquals(0, checker.size());
        checker.addWord("cat");
        assertEquals(1, checker.size());
        checker.addWord("dog");
        assertEquals(2, checker.size());
	}
	
	void testIncreaseCount(){
		SpellChecker checker = new SpellChecker();
		int before = checker.size();
   	 	checker.addWord("bank");
   		assertEquals(before + 1, checker.size());
	
	}
	
	void testNewWordCountStaysSame(){
		SpellChecker checker = new SpellChecker();
		checker.addWord("cat");
		int before = checker.size();
		assertEquals(before, checker.size());
		
	}
	
	void testSpelling(){
		SpellChecker checker = new SpellChecker();
	  	checker.addWord("bank");
        assertTrue(checker.isCorrect("bank"));
	}
	
	 void improperlySpelledWordIsMarkedIncorrect() {
        checker.addWord("bank");
        assertFalse(checker.isCorrect("bamk"));
	}
	
	 void checkingIgnoresCase() {
        checker.addWord("cat");
        assertTrue(checker.isCorrect("cat"));
        assertTrue(checker.isCorrect("CaT"));
        assertTrue(checker.isCorrect("CAT"));
        assertTrue(checker.isCorrect("cAt"));
    }
     void suggestReturnsAlphabeticallyClosestWord() {
       
        checker.addWord("banana");
        checker.addWord("bank");
        checler.addWord("bar");

        assertEquals("bank", checker.suggest("bamk"));
    }
     void suggestReturnsOriginalIfAlreadyCorrect() {
        checker.addWord("bank");
        assertEquals("bank", checker.suggest("bank"));
    }
     void addWordRejectsNull() {
        assertThrows(IllegalArgumentException.class, () -> checker.addWord(null));
        assertThrows(IllegalArgumentException.class, () -> checker.isCorrect(null));
        assertThrows(IllegalArgumentException.class, () -> checker.suggest(null));
    }
	 void trimsWhitespaceOnInputs() {
        checker.addWord("  cat  ");
        assertEquals(1, checker.size());

        assertTrue(checker.isCorrect("cat"));
        assertTrue(checker.isCorrect("  cat"));
        assertTrue(checker.isCorrect("cat  "));
        assertTrue(checker.isCorrect("  cat  "));

        // If suggest is called on a correct word with whitespace, it should still return the original trimmed/correct form
        assertEquals("cat", checker.suggest("  cat  "));
}
