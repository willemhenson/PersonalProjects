package test.javadoc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("javadoc")
public class JavadocClassTest {

	public static HashMap<String, List<String>> getClassDocs(String fqcn) {
        HashMap<String, List<String>> jDoc = null;
        try {
            jDoc = JavadocHelper.extractClassSignaturesAndDocs(fqcn, false);
        } catch (IOException e) {
            return null;
        }
        return jDoc;
    }

    public static boolean allClassesAreDocumented(HashMap<String, List<String>> jDoc) {
        for (String sig: jDoc.keySet()) {
            if (jDoc.get(sig).size() == 0) return false;
            int linesBeforeTags = 0;
            boolean foundAt = false;
            boolean foundAuthor = false;
            boolean foundVersion = false;
            for (String line: jDoc.get(sig)) {
                line = JavadocHelper.stripCommentSignifier(line);
                if (line.startsWith("@")) foundAt = true;
                if (!foundAt && line.length() > 0) ++linesBeforeTags;
                if (line.startsWith("@author ") && line.length() > "@author ".length()) foundAuthor = true;
                if (line.startsWith("@version ") && line.length() > "@version ".length()) foundVersion = true;
            }
            if (!foundAuthor || !foundVersion) return false;
            if (linesBeforeTags < 1) return false;
        }
        return true;
    }

    public void _testclassIsDocumented(String fqcn) {
        HashMap<String, List<String>> jDoc = getClassDocs(fqcn);
        assertNotNull(jDoc);

        // Check the expected class is documented
        String[] parts = fqcn.split("\\."); 
        String key = null;
        for (String sig: jDoc.keySet()) {
            if (sig.matches("class\\s*" + parts[parts.length-1] + ".*")) {
                key = sig;
                break;
            }
        }
        assertNotNull(key);
        assertTrue(jDoc.get(key).size() > 0);

        // Check all public/protected classes in this file are documented
        assertTrue(allClassesAreDocumented(jDoc));
	}

    @Test
    public void testAstronautIsDocumented() {
        _testclassIsDocumented("selfish.Astronaut");
    }

    @Test
    public void testCardIsDocumented() {
        _testclassIsDocumented("selfish.deck.Card");
    }

    @Test
    public void testDeckIsDocumented() {
        _testclassIsDocumented("selfish.deck.Deck");
    }

    @Test
    public void testGameDeckIsDocumented() {
        _testclassIsDocumented("selfish.deck.GameDeck");
    }

    @Test
    public void testGameEngineIsDocumented() {
        _testclassIsDocumented("selfish.GameEngine");
    }

    @Test
    public void testGameExceptionIsDocumented() {
        _testclassIsDocumented("selfish.GameException");
    }

    @Test
    public void testOxygenIsDocumented() {
        _testclassIsDocumented("selfish.deck.Oxygen");
    }

    @Test
    public void testSpaceDeckIsDocumented() {
        _testclassIsDocumented("selfish.deck.SpaceDeck");
    }

}