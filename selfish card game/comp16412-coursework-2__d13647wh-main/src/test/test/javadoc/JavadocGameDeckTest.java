package test.javadoc;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("javadoc")
@Tag("GameDeck")
public class JavadocGameDeckTest {

    String FQCN = "selfish.deck.GameDeck";

    @Test
    public void testAllMembersDocumented() {
        assertTrue(JavadocMemberHelper.allMembersAreDocumented(FQCN));
    }

    @Test
    public void testConstructorDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "GameDeck\\s*\\(.*");
    }

    @Test
    public void testHackSuitDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "String\\s*HACK_SUIT");
    }

    @Test
    public void testHoleInSuitDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "String\\s*HOLE_IN_SUIT");
    }

    @Test
    public void testLaserBlastDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "String\\s*LASER_BLAST");
    }

    @Test
    public void testOxygenDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "String\\s*OXYGEN_1");
    }

    @Test
    public void testOxygenOneDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "String\\s*OXYGEN_2");
    }

    @Test
    public void testOxygenTwoDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "String\\s*OXYGEN_SIPHON");
    }

    @Test
    public void testOxygenSiphonDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "String\\s*OXYGEN");
    }

    @Test
    public void testRocketBoosterDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "String\\s*ROCKET_BOOSTER");
    }

    @Test
    public void testShieldDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "String\\s*SHIELD");
    }

    @Test
    public void testTetherDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "String\\s*TETHER");
    }

    @Test
    public void testTractorBeamDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "String\\s*TRACTOR_BEAM");
    }

    @Test
    public void testDrawOxygenDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "Oxygen\\s*drawOxygen\\s*\\(.*");
    }

    @Test
    public void testSplitOxygenDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "Oxygen\\s*\\[\\s*\\]\\s*splitOxygen\\s*\\(.*");
    }

}