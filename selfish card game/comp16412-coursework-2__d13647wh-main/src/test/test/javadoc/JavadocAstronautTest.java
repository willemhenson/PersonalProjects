package test.javadoc;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("javadoc")
@Tag("Astronaut")
public class JavadocAstronautTest {

    String FQCN = "selfish.Astronaut";

    @Test
    public void testAllMembersDocumented() {
        assertTrue(JavadocMemberHelper.allMembersAreDocumented(FQCN));
    }

    @Test
    public void testConstructorDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "Astronaut\\s*\\(.*");
    }

    @Test
    public void testAddToHandDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "void\\s*addToHand\\s*\\(.*");
    }

    @Test
    public void testAddToTrackDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "void\\s*addToTrack\\s*\\(.*");
    }

    @Test
    public void testBreatheDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "int\\s*breathe\\s*\\(.*");
    }

    @Test
    public void testDistanceFromShipDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "int\\s*distanceFromShip\\s*\\(.*");
    }

    @Test
    public void testGetActionsDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "List\\s*<\\s*Card\\s*>\\s*getActions\\s*\\(.*");
    }

    @Test
    public void testGetActionsStrDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "String\\s*getActionsStr\\s*\\(.*");
    }

    @Test
    public void testGetHandDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "List\\s*<\\s*Card\\s*>\\s*getHand\\s*\\(.*");
    }

    @Test
    public void testGetHandStrDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "String\\s*getHandStr\\s*\\(.*");
    }

    @Test
    public void testGetTrackDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "Collection\\s*<\\s*Card\\s*>\\s*getTrack\\s*\\(.*");
    }

    @Test
    public void testHackReturnsVoidDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "void\\s*hack\\s*\\(.*");
    }

    @Test
    public void testHackReturnsCardDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "Card\\s*hack\\s*\\(.*");
    }

    @Test
    public void testHasCardDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "int\\s*hasCard\\s*\\(.*");
    }

    @Test
    public void testHasMeltedEyeballsDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "boolean\\s*hasMeltedEyeballs\\s*\\(.*");
    }

    @Test
    public void testHasWonDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "boolean\\s*hasWon\\s*\\(.*");
    }

    @Test
    public void testIsAliveDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "boolean\\s*isAlive\\s*\\(.*");
    }

    @Test
    public void testLaserBlastDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "Card\\s*laserBlast\\s*\\(.*");
    }

    @Test
    public void testOxygenRemainingDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "int\\s*oxygenRemaining\\s*\\(.*");
    }

    @Test
    public void testPeekAtTrackDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "Card\\s*peekAtTrack\\s*\\(.*");
    }

    @Test
    public void testSiphonDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "Oxygen\\s*siphon\\s*\\(.*");
    }

    @Test
    public void testStealDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "Card\\s*steal\\s*\\(.*");
    }

    @Test
    public void testSwapTrackDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "void\\s*swapTrack\\s*\\(.*");
    }

    @Test
    public void testToStringDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "String\\s*toString\\s*\\(.*");
    }

}