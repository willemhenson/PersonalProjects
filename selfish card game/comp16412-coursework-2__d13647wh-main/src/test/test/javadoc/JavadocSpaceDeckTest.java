package test.javadoc;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("javadoc")
@Tag("SpaceDeck")
public class JavadocSpaceDeckTest {

    String FQCN = "selfish.deck.SpaceDeck";

    @Test
    public void testAllMembersDocumented() {
        assertTrue(JavadocMemberHelper.allMembersAreDocumented(FQCN));
    }

    @Test
    public void testConstructorDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "SpaceDeck\\s*\\(.*");
    }

    @Test
    public void testAsteroidFieldDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "String\\s*ASTEROID_FIELD");
    }

    @Test
    public void testBlankSpaceDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "String\\s*BLANK_SPACE");
    }

    @Test
    public void testCosmicRadiationDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "String\\s*COSMIC_RADIATION");
    }

    @Test
    public void testGravitationalAnomalyDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "String\\s*GRAVITATIONAL_ANOMALY");
    }

    @Test
    public void testHyperspaceDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "String\\s*HYPERSPACE");
    }

    @Test
    public void testMeteoroidDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "String\\s*METEOROID");
    }

    @Test
    public void testMysteriousNebulaDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "String\\s*MYSTERIOUS_NEBULA");
    }

    @Test
    public void testSolarFlareDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "String\\s*SOLAR_FLARE");
    }

    @Test
    public void testUsefulJunkDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "String\\s*USEFUL_JUNK");
    }

    @Test
    public void testWormholeDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "String\\s*WORMHOLE");
    }

}