package test.javadoc;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("javadoc")
@Tag("GameEngine")
public class JavadocGameEngineTest {

    String FQCN = "selfish.GameEngine";

    @Test
    public void testAllMembersDocumented() {
        assertTrue(JavadocMemberHelper.allMembersAreDocumented(FQCN));
    }

    @Test
    public void testConstructorDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "GameEngine\\s*\\(.*");
    }

    @Test
    public void testAddPlayerDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "int\\s*addPlayer\\s*\\(.*");
    }

    @Test
    public void testEndTurnDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "int\\s*endTurn\\s*\\(.*");
    }

    @Test
    public void testGameOverDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "boolean\\s*gameOver\\s*\\(.*");
    }

    @Test
    public void testGetAllPlayersDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "List\\s*<\\s*Astronaut\\s*>\\s*getAllPlayers\\s*\\(.*");
    }

    @Test
    public void testGetCurrentPlayerDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "Astronaut\\s*getCurrentPlayer\\s*\\(.*");
    }

    @Test
    public void testGetFullPlayerCountDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "int\\s*getFullPlayerCount\\s*\\(.*");
    }

    @Test
    public void testGetGameDeckDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "GameDeck\\s*getGameDeck\\s*\\(.*");
    }

    @Test
    public void testGetGameDiscardDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "GameDeck\\s*getGameDiscard\\s*\\(.*");
    }

    @Test
    public void testGetSpaceDeckDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "SpaceDeck\\s*getSpaceDeck\\s*\\(.*");
    }

    @Test
    public void testGetWinnerDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "Astronaut\\s*getWinner\\s*\\(.*");
    }

    @Test
    public void testGetSpaceDiscardDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "SpaceDeck\\s*getSpaceDiscard\\s*\\(.*");
    }

    @Test
    public void testKillPlayeDocumentedr() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "void\\s*killPlayer\\s*\\(.*");
    }

    @Test
    public void testLoadStateDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "GameEngine\\s*loadState\\s*\\(.*");
    }

    @Test
    public void testMergeDecksDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "void\\s*mergeDecks\\s*\\(.*");
    }

    @Test
    public void testSaveStateDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "void\\s*saveState\\s*\\(.*");
    }

    @Test
    public void testSplitOxygenDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "Oxygen\\s*\\[\\s*\\]\\s*splitOxygen\\s*\\(.*");
    }

    @Test
    public void testStartGameDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "void\\s*startGame\\s*\\(.*");
    }

    @Test
    public void testStartTurnDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "void\\s*startTurn\\s*\\(.*");
    }

    @Test
    public void testTravelDocumented() {
        JavadocMemberHelper._testmemberIsDocumented(FQCN, "Card\\s*travel\\s*\\(.*");
    }

}