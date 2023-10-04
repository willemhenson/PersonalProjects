package test.javadoc;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("javadoc")
@Tag("Deck")
public class JavadocDeckTest {

    String FQCN = "selfish.deck.Deck";

    @Test
    public void testAllMembersDocumented() {
        assertTrue(JavadocMemberHelper.allMembersAreDocumented(FQCN));
    }

    @Test
    public void testConstructorDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "Deck\\s*\\(.*");
    }

    @Test
    public void testLoadCardsDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, ".*List\\s*<\\s*Card\\s*>\\s*loadCards\\s*\\(.*");
    }

    @Test
    public void testStringToCardsDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "Card\\s*\\[\\s*\\]\\s*stringToCards\\s*\\(.*");
    }

    @Test
    public void testAddDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "int\\s*add\\s*\\(.*");
    }

    @Test
    public void testDrawDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "Card\\s*draw\\s*\\(.*");
    }

    @Test
    public void testRemoveDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "void\\s*remove\\s*\\(.*");
    }

    @Test
    public void testShuffleDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "void\\s*shuffle\\s*\\(.*");
    }

    @Test
    public void testSizeDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "int\\s*size\\s*\\(.*");
    }

}