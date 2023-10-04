package test.javadoc;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("javadoc")
@Tag("Oxygen")
public class JavadocOxygenTest {

    String FQCN = "selfish.deck.Oxygen";

    @Test
    public void testAllMembersDocumented() {
        assertTrue(JavadocMemberHelper.allMembersAreDocumented(FQCN));
    }

    @Test
    public void testConstructorDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "Oxygen\\s*\\(.*");
    }

    @Test
    public void testGetValueDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "int\\s*getValue\\s*\\(.*");
    }

    @Test
    public void testToStringDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "String\\s*toString\\s*\\(.*");
    }

}