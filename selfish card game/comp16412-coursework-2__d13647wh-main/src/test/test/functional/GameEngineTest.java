package test.functional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import selfish.Astronaut;
import selfish.GameEngine;
import selfish.deck.Card;
import selfish.deck.Oxygen;
import selfish.deck.Deck;
import selfish.deck.GameDeck;
import selfish.deck.SpaceDeck;

@Tag("functional")
@Tag("GameEngine")
public class GameEngineTest {
	static GameEngine engine;

	public void addPlayers(int players) {
		switch (players) {
			case 5:
			    engine.addPlayer("Tingting");
			case 4:
		        engine.addPlayer("Zahra");
			case 3:
		        engine.addPlayer("Terry");
			case 2:
			    engine.addPlayer("Markel");
			case 1:
    			engine.addPlayer("Sarah");		    
		}
	}

	@BeforeEach
	public void setup() throws Exception {
		engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
	}

	@Test
	public void testAstronautBreatheLastResultsInNewCorpse() throws IllegalAccessException, NoSuchFieldException {
		Field corpsesField = GameEngine.class.getDeclaredField("corpses");
		corpsesField.setAccessible(true);
		Astronaut astronaut = new Astronaut("Fyodor Yurchikhin", engine);
		List<Card> corpses = (List<Card>)corpsesField.get(engine);
		int oldCorpses = corpses.size();
		astronaut.addToHand(new Oxygen(1));
		astronaut.breathe();
		assertEquals(oldCorpses+1, corpses.size());
		assertTrue(corpses.contains(astronaut));
	}

	@Test
	public void testAstronautHackCardParamLastOxygenResultsInNewCorpse() throws IllegalAccessException, NoSuchFieldException {
		Field corpsesField = GameEngine.class.getDeclaredField("corpses");
		corpsesField.setAccessible(true);
		Astronaut astronaut = new Astronaut("Leonid Kadeniuk", engine);
		Oxygen o2 = new Oxygen(2);
		astronaut.addToHand(o2);
		astronaut.addToHand(new Card("Laser blast", "Pick another player and knock them back 1 space."));
		astronaut.addToHand(new Card("Hole in suit", "Punch a hole in a rival's suit."));

		List<Card> corpses = (List<Card>)corpsesField.get(engine);
		int oldCorpses = corpses.size();

		astronaut.hack(o2);
		assertEquals(oldCorpses+1, corpses.size());
		assertTrue(corpses.contains(astronaut));
	}

	@Test
	public void testAstronautHackStringParamLastOxygenResultsInNewCorpse() throws IllegalAccessException, NoSuchFieldException {
		Field corpsesField = GameEngine.class.getDeclaredField("corpses");
		corpsesField.setAccessible(true);
		Astronaut astronaut = new Astronaut("Leonid Kadeniuk", engine);
		astronaut.addToHand(new Oxygen(2));
		Card shield = new Card("Shield", "Block an attack from another astronaut.");

		List<Card> corpses = (List<Card>)corpsesField.get(engine);
		int oldCorpses = corpses.size();

		astronaut.hack("Oxygen(2)");
		assertEquals(oldCorpses+1, corpses.size());
		assertTrue(corpses.contains(astronaut));
	}

	@Test
	public void testAstronautSiphonLastResultsInNewCorpse() throws IllegalAccessException, NoSuchFieldException {
		Field corpsesField = GameEngine.class.getDeclaredField("corpses");
		corpsesField.setAccessible(true);
		Astronaut astronaut = new Astronaut("Fyodor Yurchikhin", engine);
		List<Card> corpses = (List<Card>)corpsesField.get(engine);
		int oldCorpses = corpses.size();
		astronaut.addToHand(new Oxygen(1));
		astronaut.siphon();
		assertEquals(oldCorpses+1, corpses.size());
		assertTrue(corpses.contains(astronaut));
	}


	@Test
	public void testAstronautStealLastCardResultsInNewCorpse() throws IllegalAccessException, NoSuchFieldException {
		Field corpsesField = GameEngine.class.getDeclaredField("corpses");
		corpsesField.setAccessible(true);
		Astronaut astronaut = new Astronaut("Anousheh Ansari", engine);
		List<Card> corpses = (List<Card>)corpsesField.get(engine);
		int oldCorpses = corpses.size();
		astronaut.addToHand(new Oxygen(2));
		astronaut.steal();
		assertEquals(oldCorpses+1, corpses.size());
		assertTrue(corpses.contains(astronaut));
	}

	@Test
	public void testConstructor() throws IllegalAccessException, NoSuchFieldException {
		Field activePlayersField = GameEngine.class.getDeclaredField("activePlayers");
		Field corpsesField = GameEngine.class.getDeclaredField("corpses");
		Field currentPlayerField = GameEngine.class.getDeclaredField("currentPlayer");
		Field gameDeckField = GameEngine.class.getDeclaredField("gameDeck");
		Field gameDiscardField = GameEngine.class.getDeclaredField("gameDiscard");
		Field hasStartedField = GameEngine.class.getDeclaredField("hasStarted");
		Field randomField = GameEngine.class.getDeclaredField("random");
		Field spaceDeckField = GameEngine.class.getDeclaredField("spaceDeck");
		Field spaceDiscardField = GameEngine.class.getDeclaredField("spaceDiscard");
		activePlayersField.setAccessible(true);
		corpsesField.setAccessible(true);
		currentPlayerField.setAccessible(true);
		gameDeckField.setAccessible(true);
		gameDiscardField.setAccessible(true);
		hasStartedField.setAccessible(true);
		randomField.setAccessible(true);
		spaceDeckField.setAccessible(true);
		spaceDiscardField.setAccessible(true);
		assertEquals(0, ((Collection<Card>)activePlayersField.get(engine)).size());
		assertEquals(0, ((Collection<Card>)corpsesField.get(engine)).size());
		assertNull(currentPlayerField.get(engine));
		assertEquals(78, ((GameDeck)gameDeckField.get(engine)).size());
		assertEquals(0, ((GameDeck)gameDiscardField.get(engine)).size());
		assertEquals(42, ((SpaceDeck)spaceDeckField.get(engine)).size());
		assertEquals(0, ((SpaceDeck)spaceDiscardField.get(engine)).size());
		Random rand = new Random(16412);
		for (int i=0; i<42+78-2; ++i) rand.nextInt(); // To account for calls used in deck shuffling.
		assertEquals(rand.nextLong(), ((Random)randomField.get(engine)).nextLong());
		assertEquals(Boolean.FALSE, hasStartedField.get(engine));
	}

	@Test
	public void testConstructorShuffle16412() throws IllegalAccessException, NoSuchFieldException {
		Field cardsField = Deck.class.getDeclaredField("cards");
		Field gameDeckField = GameEngine.class.getDeclaredField("gameDeck");
		Field spaceDeckField = GameEngine.class.getDeclaredField("spaceDeck");
		cardsField.setAccessible(true);
		gameDeckField.setAccessible(true);
		spaceDeckField.setAccessible(true);
		List<Card> gameDeckCards = (List<Card>)cardsField.get((GameDeck)gameDeckField.get(engine));
		List<Card> spaceDeckCards = (List<Card>)cardsField.get((SpaceDeck)spaceDeckField.get(engine));

		assertEquals("Hole in suit", gameDeckCards.get(71).toString());
		assertEquals("Blank space", spaceDeckCards.get(41).toString());
		assertEquals("Oxygen(1)", gameDeckCards.get(61).toString());
		assertEquals("Gravitational anomaly", spaceDeckCards.get(31).toString());
		assertEquals("Oxygen(2)", gameDeckCards.get(51).toString());
		assertEquals("Mysterious nebula", spaceDeckCards.get(21).toString());
		assertEquals("Oxygen(1)", gameDeckCards.get(41).toString());
		assertEquals("Blank space", spaceDeckCards.get(11).toString());
		assertEquals("Oxygen(1)", gameDeckCards.get(31).toString());
		assertEquals("Useful junk", spaceDeckCards.get(1).toString());
	}

	@Test
	public void testAddPlayerFive() {
		addPlayers(5);
	}

	@Test
	public void testAddPlayerOne() {
		addPlayers(1);
	}

	@Test
	public void testEndTurnUpdatesPlayerAttributes() throws IllegalAccessException, NoSuchFieldException {
		addPlayers(2);
		engine.startGame();
		engine.startTurn();
		engine.endTurn();

		Field activePlayersField = GameEngine.class.getDeclaredField("activePlayers");
		Field currentPlayerField = GameEngine.class.getDeclaredField("currentPlayer");
		activePlayersField.setAccessible(true);
		currentPlayerField.setAccessible(true);
		Collection<Astronaut> activePlayers = (Collection<Astronaut>)activePlayersField.get(engine);
		Astronaut currentPlayer = (Astronaut)currentPlayerField.get(engine);

		assertNull(currentPlayer);
		assertEquals(2, activePlayers.size());
	}

	@Test
	public void testEndTurnUpdatesPlayerAttributesCorrectlyWhenCurrentPlayerDied() throws IllegalAccessException, NoSuchFieldException {
		addPlayers(2);
		engine.startGame();
		engine.startTurn();
		while (engine.getCurrentPlayer().breathe() > 0);
		engine.endTurn();

		Field activePlayersField = GameEngine.class.getDeclaredField("activePlayers");
		Field currentPlayerField = GameEngine.class.getDeclaredField("currentPlayer");
		activePlayersField.setAccessible(true);
		currentPlayerField.setAccessible(true);
		Collection<Astronaut> activePlayers = (Collection<Astronaut>)activePlayersField.get(engine);
		Astronaut currentPlayer = (Astronaut)currentPlayerField.get(engine);

		assertNull(currentPlayer);
		assertEquals(1, activePlayers.size());
	}

	@Test
	public void testGameOverTrueWhenPlayerAtShip() throws IllegalAccessException, NoSuchFieldException {
		Field currentPlayerField = GameEngine.class.getDeclaredField("currentPlayer");
		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		currentPlayerField.setAccessible(true);
		oxygensField.setAccessible(true);

		addPlayers(3);
		engine.startGame();

		for (int i=0; i<16; ++i) {
    		engine.startTurn();
		    Astronaut currentPlayer = (Astronaut)currentPlayerField.get(engine);
		    List<Oxygen> oxygens = (List<Oxygen>)oxygensField.get(currentPlayer);
		    while (oxygens.size() < 5) oxygens.add(new Oxygen(2));
			if (i<5 || i%3 == 0) engine.travel(currentPlayer);
		    engine.endTurn();
		}
		assertTrue(engine.gameOver());
	}

	@Test
	public void testGameOverTrueWhenLastPlayerAtShip() throws IllegalAccessException, NoSuchFieldException {
		Field currentPlayerField = GameEngine.class.getDeclaredField("currentPlayer");
		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		currentPlayerField.setAccessible(true);
		oxygensField.setAccessible(true);

		addPlayers(3);
		engine.startGame();

		for (int i=0; i<13; ++i) {
    		engine.startTurn();
		    Astronaut currentPlayer = (Astronaut)currentPlayerField.get(engine);
		    List<Oxygen> oxygens = (List<Oxygen>)oxygensField.get(currentPlayer);
			while ((i==7 || i==8) && currentPlayer.breathe() > 0);
			while (i%3 == 0 && oxygens.size() < 5) oxygens.add(new Oxygen(2));
			if (i<5 || i>8) engine.travel(currentPlayer);
		    engine.endTurn();
		}
		assertTrue(engine.gameOver());
	}

	@Test
	public void testGameOverTrueWhenLastPlayerAtShipHasDied() throws IllegalAccessException, NoSuchFieldException {
		Field currentPlayerField = GameEngine.class.getDeclaredField("currentPlayer");
		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		currentPlayerField.setAccessible(true);
		oxygensField.setAccessible(true);

		addPlayers(3);
		engine.startGame();

		for (int i=0; i<12; ++i) {
    		engine.startTurn();
		    Astronaut currentPlayer = (Astronaut)currentPlayerField.get(engine);
		    List<Oxygen> oxygens = (List<Oxygen>)oxygensField.get(currentPlayer);
			while ((i==7 || i==8) && currentPlayer.breathe() > 0);
			while (i%3 == 0 && oxygens.size() < 5) oxygens.add(new Oxygen(2));
			if (i<5 || i>8) engine.travel(currentPlayer);
		    engine.endTurn();
		}

		engine.startTurn();
		Astronaut currentPlayer = (Astronaut)currentPlayerField.get(engine);
		while (currentPlayer.breathe() > 2); engine.travel(currentPlayer);
		assertTrue(engine.gameOver());
	}

	@Test
	public void testGameOverFalseWhenPlayerAtShipHasDied() throws IllegalAccessException, NoSuchFieldException {
		Field currentPlayerField = GameEngine.class.getDeclaredField("currentPlayer");
		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		currentPlayerField.setAccessible(true);
		oxygensField.setAccessible(true);

		addPlayers(3);
		engine.startGame();

		for (int i=0; i<15; ++i) {
    		engine.startTurn();
		    Astronaut currentPlayer = (Astronaut)currentPlayerField.get(engine);
		    List<Oxygen> oxygens = (List<Oxygen>)oxygensField.get(currentPlayer);
		    while (oxygens.size() < 5) oxygens.add(new Oxygen(2));
			if (i<5 || i%3 == 0) engine.travel(currentPlayer);
			if (i == 14) while (currentPlayer.breathe() > 0);
		    engine.endTurn();
		}

		assertFalse(engine.gameOver());
	}

	@Test
	public void testGameOverFalseWhenPlayersAtStart() {
		addPlayers(3);
		engine.startGame();
		engine.startTurn();
		assertFalse(engine.gameOver());
	}

	@Test
	public void testGameOverTrueWhenPlayersDead() throws IllegalAccessException, NoSuchFieldException {
		Field currentPlayerField = GameEngine.class.getDeclaredField("currentPlayer");
		currentPlayerField.setAccessible(true);

		addPlayers(3);
		engine.startGame();
		for (int i=0; i<3; ++i) {
		    engine.startTurn();
		    Astronaut currentPlayer = (Astronaut)currentPlayerField.get(engine);
		    while (currentPlayer.breathe() > 0);
		    engine.endTurn();
		}
		assertTrue(engine.gameOver());
	}

	@Test
	public void testGameOverFalseWhenPlayersAdrift() throws IllegalAccessException, NoSuchFieldException {
		Field currentPlayerField = GameEngine.class.getDeclaredField("currentPlayer");
		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		currentPlayerField.setAccessible(true);
		oxygensField.setAccessible(true);

		addPlayers(3);
		engine.startGame();

		for (int i=0; i<15; ++i) {
    		engine.startTurn();
		    Astronaut currentPlayer = (Astronaut)currentPlayerField.get(engine);
		    List<Oxygen> oxygens = (List<Oxygen>)oxygensField.get(currentPlayer);
		    while (oxygens.size() < 5) oxygens.add(new Oxygen(2));
			if (i<5 || i%3 == 0) engine.travel(currentPlayer);
		    engine.endTurn();
		}
		assertFalse(engine.gameOver());
	}

	@Test
	public void testGameOverFalseWhenLastPlayerAdrift() throws IllegalAccessException, NoSuchFieldException {
		Field currentPlayerField = GameEngine.class.getDeclaredField("currentPlayer");
		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		currentPlayerField.setAccessible(true);
		oxygensField.setAccessible(true);

		addPlayers(3);
		engine.startGame();

		for (int i=0; i<12; ++i) {
    		engine.startTurn();
		    Astronaut currentPlayer = (Astronaut)currentPlayerField.get(engine);
		    List<Oxygen> oxygens = (List<Oxygen>)oxygensField.get(currentPlayer);
			while ((i==7 || i==8) && currentPlayer.breathe() > 0);
			while (i%3 == 0 && oxygens.size() < 5) oxygens.add(new Oxygen(2));
			if (i<5 || i>8) engine.travel(currentPlayer);
		    engine.endTurn();
		}
		assertFalse(engine.gameOver());
	}

	@Test
	public void testGetAllPlayers() throws IllegalAccessException, NoSuchFieldException {
		addPlayers(3);
		engine.startGame();

		Field activePlayersField = GameEngine.class.getDeclaredField("activePlayers");
		Field corpsesField = GameEngine.class.getDeclaredField("corpses");
		Field currentPlayerField = GameEngine.class.getDeclaredField("currentPlayer");
		activePlayersField.setAccessible(true);
		corpsesField.setAccessible(true);
		currentPlayerField.setAccessible(true);
		Collection<Astronaut> activePlayers = (Collection<Astronaut>)activePlayersField.get(engine);
		Collection<Astronaut> corpses = (Collection<Astronaut>)corpsesField.get(engine);
		Astronaut currentPlayer = (Astronaut)currentPlayerField.get(engine);
        List<Astronaut> expected = new ArrayList<Astronaut>(activePlayers); expected.addAll(corpses);
        if (currentPlayer != null && !expected.contains(currentPlayer)) expected.add(currentPlayer);
		expected.sort(null);

		List<Astronaut> actual = engine.getAllPlayers();
		actual.sort(null);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetAllPlayersMidTurn() throws IllegalAccessException, NoSuchFieldException {
		addPlayers(3);
		engine.startGame();
		engine.startTurn();

		Field activePlayersField = GameEngine.class.getDeclaredField("activePlayers");
		Field corpsesField = GameEngine.class.getDeclaredField("corpses");
		Field currentPlayerField = GameEngine.class.getDeclaredField("currentPlayer");
		activePlayersField.setAccessible(true);
		corpsesField.setAccessible(true);
		currentPlayerField.setAccessible(true);
		Collection<Astronaut> activePlayers = (Collection<Astronaut>)activePlayersField.get(engine);
		Collection<Astronaut> corpses = (Collection<Astronaut>)corpsesField.get(engine);
		Astronaut currentPlayer = (Astronaut)currentPlayerField.get(engine);
        List<Astronaut> expected = new ArrayList<Astronaut>(activePlayers); expected.addAll(corpses);
        if (currentPlayer != null && !expected.contains(currentPlayer)) expected.add(currentPlayer);
		expected.sort(null);

		List<Astronaut> actual = engine.getAllPlayers();
		actual.sort(null);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetAllPlayersMidTurnWhenCurrentPlayerIsCorpse() throws IllegalAccessException, NoSuchFieldException {
		addPlayers(3);
		engine.startGame();
		engine.startTurn();
		while (engine.getCurrentPlayer().breathe() > 0);

		Field activePlayersField = GameEngine.class.getDeclaredField("activePlayers");
		Field corpsesField = GameEngine.class.getDeclaredField("corpses");
		Field currentPlayerField = GameEngine.class.getDeclaredField("currentPlayer");
		activePlayersField.setAccessible(true);
		corpsesField.setAccessible(true);
		currentPlayerField.setAccessible(true);
		Collection<Astronaut> activePlayers = (Collection<Astronaut>)activePlayersField.get(engine);
		Collection<Astronaut> corpses = (Collection<Astronaut>)corpsesField.get(engine);
		Astronaut currentPlayer = (Astronaut)currentPlayerField.get(engine);
        List<Astronaut> expected = new ArrayList<Astronaut>(activePlayers); expected.addAll(corpses);
        if (currentPlayer != null && !expected.contains(currentPlayer)) expected.add(currentPlayer);
		expected.sort(null);

		List<Astronaut> actual = engine.getAllPlayers();
		actual.sort(null);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetAllPlayersWithCorpses() throws IllegalAccessException, NoSuchFieldException {
		addPlayers(3);
		engine.startGame();
		engine.startTurn();
		while (engine.getCurrentPlayer().breathe() > 0);
		engine.endTurn();

		Field activePlayersField = GameEngine.class.getDeclaredField("activePlayers");
		Field corpsesField = GameEngine.class.getDeclaredField("corpses");
		Field currentPlayerField = GameEngine.class.getDeclaredField("currentPlayer");
		activePlayersField.setAccessible(true);
		corpsesField.setAccessible(true);
		currentPlayerField.setAccessible(true);
		Collection<Astronaut> activePlayers = (Collection<Astronaut>)activePlayersField.get(engine);
		Collection<Astronaut> corpses = (Collection<Astronaut>)corpsesField.get(engine);
		Astronaut currentPlayer = (Astronaut)currentPlayerField.get(engine);
        List<Astronaut> expected = new ArrayList<Astronaut>(activePlayers); expected.addAll(corpses);
        if (currentPlayer != null && !expected.contains(currentPlayer)) expected.add(currentPlayer);
		expected.sort(null);

		List<Astronaut> actual = engine.getAllPlayers();
		actual.sort(null);
		assertEquals(expected, actual);
	}

	@Test
	public void testGetCurrentPlayer() throws IllegalAccessException, NoSuchFieldException {
		addPlayers(5);
		engine.startGame();
		assertNull(engine.getCurrentPlayer());
		engine.startTurn();
		assertNotNull(engine.getCurrentPlayer());
		Field currentPlayerField = GameEngine.class.getDeclaredField("currentPlayer");
		currentPlayerField.setAccessible(true);
		assertSame((Astronaut)currentPlayerField.get(engine), engine.getCurrentPlayer());
	}

	@Test
	public void testGetFullPlayerCount() {
		addPlayers(3);
		engine.startGame();
		assertEquals(3, engine.getFullPlayerCount());
	}

	@Test
	public void testGetFullPlayerCountMidTurn() {
		addPlayers(3);
		engine.startGame();
		engine.startTurn();
		assertEquals(3, engine.getFullPlayerCount());
	}

	@Test
	public void testGetFullPlayerCountMidTurnWhenCurrentPlayerIsCorpse() {
		addPlayers(3);
		engine.startGame();
		engine.startTurn();
		while (engine.getCurrentPlayer().breathe() > 0);
		assertEquals(3, engine.getFullPlayerCount());
	}

	@Test
	public void testGetFullPlayerCountWithCorpses() {
		addPlayers(3);
		engine.startGame();
		engine.startTurn();
		while (engine.getCurrentPlayer().breathe() > 0);
		engine.endTurn();
		assertEquals(3, engine.getFullPlayerCount());
	}

	@Test
	public void testGetGameDeck() throws IllegalAccessException, NoSuchFieldException {
		Field gameDeckField = GameEngine.class.getDeclaredField("gameDeck");
		gameDeckField.setAccessible(true);
		assertSame((GameDeck)gameDeckField.get(engine), engine.getGameDeck());
	}

	@Test
	public void testGetGameDiscard() throws IllegalAccessException, NoSuchFieldException {
		Field gameDiscardField = GameEngine.class.getDeclaredField("gameDiscard");
		gameDiscardField.setAccessible(true);
		assertSame((GameDeck)gameDiscardField.get(engine), engine.getGameDiscard());
	}

	@Test
	public void testGetSpaceDeck() throws IllegalAccessException, NoSuchFieldException {
		Field spaceDecField = GameEngine.class.getDeclaredField("spaceDeck");
		spaceDecField.setAccessible(true);
		assertSame((SpaceDeck)spaceDecField.get(engine), engine.getSpaceDeck());
	}

	@Test
	public void testGetSpaceDiscard() throws IllegalAccessException, NoSuchFieldException {
		Field spaceDiscardField = GameEngine.class.getDeclaredField("spaceDiscard");
		spaceDiscardField.setAccessible(true);
		assertSame((SpaceDeck)spaceDiscardField.get(engine), engine.getSpaceDiscard());
	}

	@Test
	public void testGetWinnerWhenPlayerAtShip() throws IllegalAccessException, NoSuchFieldException {
		Field currentPlayerField = GameEngine.class.getDeclaredField("currentPlayer");
		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		currentPlayerField.setAccessible(true);
		oxygensField.setAccessible(true);

		addPlayers(3);
		engine.startGame();
		Astronaut terry = null;

		for (int i=0; i<16; ++i) {
    		engine.startTurn();
		    Astronaut currentPlayer = (Astronaut)currentPlayerField.get(engine);
			if (i==0) terry = currentPlayer;
		    List<Oxygen> oxygens = (List<Oxygen>)oxygensField.get(currentPlayer);
		    while (oxygens.size() < 5) oxygens.add(new Oxygen(2));
			if (i<5 || i%3 == 0) engine.travel(currentPlayer);
		    engine.endTurn();
		}
		assertNotNull(engine.getWinner());
		assertEquals(terry, engine.getWinner());
	}

	@Test
	public void testGetWinnerNullWhenPlayersDead() throws IllegalAccessException, NoSuchFieldException {
		Field currentPlayerField = GameEngine.class.getDeclaredField("currentPlayer");
		currentPlayerField.setAccessible(true);

		addPlayers(3);
		engine.startGame();
		for (int i=0; i<3; ++i) {
		    engine.startTurn();
			Astronaut currentPlayer = (Astronaut)currentPlayerField.get(engine);
		    while (currentPlayer.breathe() > 0);
		    engine.endTurn();
		}
		assertNull(engine.getWinner());
	}

	@Test
	public void testGetWinnerWhenLastPlayerAtShip() throws IllegalAccessException, NoSuchFieldException {
		Field currentPlayerField = GameEngine.class.getDeclaredField("currentPlayer");
		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		currentPlayerField.setAccessible(true);
		oxygensField.setAccessible(true);

		addPlayers(3);
		engine.startGame();
		Astronaut terry = null;

		for (int i=0; i<13; ++i) {
    		engine.startTurn();
		    Astronaut currentPlayer = (Astronaut)currentPlayerField.get(engine);
			if (i==0) terry = currentPlayer;
		    List<Oxygen> oxygens = (List<Oxygen>)oxygensField.get(currentPlayer);
			while ((i==7 || i==8) && currentPlayer.breathe() > 0);
			while (i%3 == 0 && oxygens.size() < 5) oxygens.add(new Oxygen(2));
			if (i<5 || i>8) engine.travel(currentPlayer);
		    engine.endTurn();
		}
		assertNotNull(engine.getWinner());
		assertEquals(terry, engine.getWinner());
	}

	@Test
	public void testGetWinnerNullWhenLastPlayerAtShipHasDied() throws IllegalAccessException, NoSuchFieldException {
		Field currentPlayerField = GameEngine.class.getDeclaredField("currentPlayer");
		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		currentPlayerField.setAccessible(true);
		oxygensField.setAccessible(true);

		addPlayers(3);
		engine.startGame();

		for (int i=0; i<12; ++i) {
    		engine.startTurn();
		    Astronaut currentPlayer = (Astronaut)currentPlayerField.get(engine);
		    List<Oxygen> oxygens = (List<Oxygen>)oxygensField.get(currentPlayer);
			while ((i==7 || i==8) && currentPlayer.breathe() > 0);
			while (i%3 == 0 && oxygens.size() < 5) oxygens.add(new Oxygen(2));
			if (i<5 || i>8) engine.travel(currentPlayer);
		    engine.endTurn();
		}

		engine.startTurn();
		Astronaut currentPlayer = (Astronaut)currentPlayerField.get(engine);
		while (currentPlayer.breathe() > 2); engine.travel(currentPlayer);
		assertNull(engine.getWinner());
	}

	@Test
	public void testGetWinnerNullWhenPlayerAtShipHasDied() throws IllegalAccessException, NoSuchFieldException {
		Field currentPlayerField = GameEngine.class.getDeclaredField("currentPlayer");
		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		currentPlayerField.setAccessible(true);
		oxygensField.setAccessible(true);

		addPlayers(3);
		engine.startGame();

		for (int i=0; i<15; ++i) {
    		engine.startTurn();
		    Astronaut currentPlayer = (Astronaut)currentPlayerField.get(engine);
		    List<Oxygen> oxygens = (List<Oxygen>)oxygensField.get(currentPlayer);
		    while (oxygens.size() < 5) oxygens.add(new Oxygen(2));
			if (i<5 || i%3 == 0) engine.travel(currentPlayer);
			if (i == 14) while (currentPlayer.breathe() > 0);
		    engine.endTurn();
		}

		assertNull(engine.getWinner());
	}

	@Test
	public void testGetWinnerNullWhenPlayersAtStart() {
		addPlayers(3);
		engine.startGame();
		engine.startTurn();
		assertNull(engine.getWinner());
	}

	@Test
	public void testGetWinnerNullWhenPlayersAdrift() throws IllegalAccessException, NoSuchFieldException {
		Field currentPlayerField = GameEngine.class.getDeclaredField("currentPlayer");
		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		currentPlayerField.setAccessible(true);
		oxygensField.setAccessible(true);

		addPlayers(3);
		engine.startGame();

		for (int i=0; i<15; ++i) {
    		engine.startTurn();
		    Astronaut currentPlayer = (Astronaut)currentPlayerField.get(engine);
		    List<Oxygen> oxygens = (List<Oxygen>)oxygensField.get(currentPlayer);
		    while (oxygens.size() < 5) oxygens.add(new Oxygen(2));
			if (i<5 || i%3 == 0) engine.travel(currentPlayer);
		    engine.endTurn();
		}
		assertNull(engine.getWinner());
	}

	@Test
	public void testGetWinnerNullWhenLastPlayerAdrift() throws IllegalAccessException, NoSuchFieldException {
		Field currentPlayerField = GameEngine.class.getDeclaredField("currentPlayer");
		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		currentPlayerField.setAccessible(true);
		oxygensField.setAccessible(true);

		addPlayers(3);
		engine.startGame();

		for (int i=0; i<12; ++i) {
    		engine.startTurn();
		    Astronaut currentPlayer = (Astronaut)currentPlayerField.get(engine);
		    List<Oxygen> oxygens = (List<Oxygen>)oxygensField.get(currentPlayer);
			while ((i==7 || i==8) && currentPlayer.breathe() > 0);
			while (i%3 == 0 && oxygens.size() < 5) oxygens.add(new Oxygen(2));
			if (i<5 || i>8) engine.travel(currentPlayer);
		    engine.endTurn();
		}
		assertNull(engine.getWinner());
	}

	@Test
	public void testKillPlayer() throws IllegalAccessException, NoSuchFieldException {
		Field currentPlayerField = GameEngine.class.getDeclaredField("currentPlayer");
		Field activePlayersField = GameEngine.class.getDeclaredField("activePlayers");
		Field corpsesField = GameEngine.class.getDeclaredField("corpses");
		currentPlayerField.setAccessible(true);
		activePlayersField.setAccessible(true);
		corpsesField.setAccessible(true);

		addPlayers(5);
		engine.startGame();
		engine.startTurn();
		Astronaut currentPlayer = (Astronaut)currentPlayerField.get(engine);
		engine.killPlayer(currentPlayer);
		assertFalse(((List<Astronaut>)activePlayersField.get(engine)).contains(currentPlayer));
		assertTrue(((List<Astronaut>)corpsesField.get(engine)).contains(currentPlayer));
	}

	@Test
	public void testLoadResumesSavedState() throws Exception {
		String path = "testLoadResumesSavedState.obj";
		addPlayers(3);
		engine.startGame();
		for (int i=0; i<20; ++i) {
		    engine.startTurn();
		    engine.endTurn();
		}
		engine.saveState(path);

		GameEngine loadedGame = GameEngine.loadState(path);

		// Compare the Players
		List<Astronaut> oldPlayers = engine.getAllPlayers();
		List<Astronaut> newPlayers = loadedGame.getAllPlayers();
		assertEquals(oldPlayers.size(), newPlayers.size());
		for (int i=0; i<oldPlayers.size(); ++i) {
		    assertEquals(oldPlayers.get(i).toString(), newPlayers.get(i).toString());
		    assertEquals(oldPlayers.get(i).getHand().size(), newPlayers.get(i).getHand().size());
		    assertEquals(oldPlayers.get(i).getHandStr(), newPlayers.get(i).getHandStr());
		    assertEquals(oldPlayers.get(i).isAlive(), newPlayers.get(i).isAlive());
		    assertEquals(oldPlayers.get(i).getTrack().size(), newPlayers.get(i).getTrack().size());
		}

		// Compare the Decks
		GameDeck oldGameDeck = engine.getGameDeck();
		GameDeck newGameDeck = loadedGame.getGameDeck();
		assertEquals(oldGameDeck.size(), newGameDeck.size());
		for (int i=0; i<oldGameDeck.size(); ++i) {
			Card oldCard = oldGameDeck.draw();
			Card newCard = newGameDeck.draw();
			assertEquals(oldCard.toString(), newCard.toString());
		}
		oldGameDeck = engine.getGameDiscard();
		newGameDeck = loadedGame.getGameDiscard();
		assertEquals(oldGameDeck.size(), newGameDeck.size());
		for (int i=0; i<oldGameDeck.size(); ++i) {
			Card oldCard = oldGameDeck.draw();
			Card newCard = newGameDeck.draw();
			assertEquals(oldCard.toString(), newCard.toString());
		}
		SpaceDeck oldSpaceDeck = engine.getSpaceDeck();
		SpaceDeck newSpaceDeck = loadedGame.getSpaceDeck();
		assertEquals(oldSpaceDeck.size(), newSpaceDeck.size());
		for (int i=0; i<oldSpaceDeck.size(); ++i) {
			Card oldCard = oldSpaceDeck.draw();
			Card newCard = newSpaceDeck.draw();
			assertEquals(oldCard.toString(), newCard.toString());
		}
		oldSpaceDeck = engine.getSpaceDiscard();
		newSpaceDeck = loadedGame.getSpaceDiscard();
		assertEquals(oldSpaceDeck.size(), newSpaceDeck.size());
		for (int i=0; i<oldSpaceDeck.size(); ++i) {
			Card oldCard = oldSpaceDeck.draw();
			Card newCard = newSpaceDeck.draw();
			assertEquals(oldCard.toString(), newCard.toString());
		}
		Files.delete(Paths.get(path));
	}

	@Test
	public void testMergeDecks() throws IllegalAccessException, NoSuchFieldException {
		Field gameDeckField = GameEngine.class.getDeclaredField("gameDeck");
		Field gameDiscardField = GameEngine.class.getDeclaredField("gameDiscard");
		Field spaceDeckField = GameEngine.class.getDeclaredField("spaceDeck");
		Field spaceDiscardField = GameEngine.class.getDeclaredField("spaceDiscard");
		Field cardsField = Deck.class.getDeclaredField("cards");
		gameDeckField.setAccessible(true);
		gameDiscardField.setAccessible(true);
		spaceDeckField.setAccessible(true);
		spaceDiscardField.setAccessible(true);
		cardsField.setAccessible(true);

		addPlayers(5);
		engine.startGame();

        GameDeck gameDeck = (GameDeck)gameDeckField.get(engine);
        GameDeck gameDiscard = (GameDeck)gameDiscardField.get(engine);
        SpaceDeck spaceDeck = (SpaceDeck)spaceDeckField.get(engine);
        SpaceDeck spaceDiscard = (SpaceDeck)spaceDiscardField.get(engine);

		while (gameDeck.size() > 5) gameDiscard.add(gameDeck.draw());
		int origGameDeckSize = gameDeck.size(); int origGameDiscardSize = gameDiscard.size();
		int origSpaceDeckSize = spaceDeck.size(); int origSpaceDiscardSize = spaceDiscard.size();

		engine.mergeDecks(gameDeck, gameDiscard);
		assertEquals(origSpaceDeckSize, spaceDeck.size());
		assertEquals(origSpaceDiscardSize, spaceDiscard.size());
		assertEquals(origGameDiscardSize+origGameDeckSize, gameDeck.size());
		assertEquals(0, gameDiscard.size());

		while (spaceDeck.size() > 6) spaceDiscard.add(spaceDeck.draw());
		origGameDeckSize = gameDeck.size(); origGameDiscardSize = gameDiscard.size();
		origSpaceDeckSize = spaceDeck.size(); origSpaceDiscardSize = spaceDiscard.size();

		engine.mergeDecks(spaceDeck, spaceDiscard);
		assertEquals(origGameDeckSize, gameDeck.size());
		assertEquals(origGameDiscardSize, gameDiscard.size());
		assertEquals(origSpaceDiscardSize+origSpaceDeckSize, spaceDeck.size());
		assertEquals(0, spaceDiscard.size());
	}

	@Test
	public void testMergeDecksDeck1Empty() throws IllegalAccessException, NoSuchFieldException {
		Field gameDeckField = GameEngine.class.getDeclaredField("gameDeck");
		Field gameDiscardField = GameEngine.class.getDeclaredField("gameDiscard");
		Field spaceDeckField = GameEngine.class.getDeclaredField("spaceDeck");
		Field spaceDiscardField = GameEngine.class.getDeclaredField("spaceDiscard");
		Field cardsField = Deck.class.getDeclaredField("cards");
		gameDeckField.setAccessible(true);
		gameDiscardField.setAccessible(true);
		spaceDeckField.setAccessible(true);
		spaceDiscardField.setAccessible(true);
		cardsField.setAccessible(true);

		addPlayers(5);
		engine.startGame();

        GameDeck gameDeck = (GameDeck)gameDeckField.get(engine);
        GameDeck gameDiscard = (GameDeck)gameDiscardField.get(engine);
        SpaceDeck spaceDeck = (SpaceDeck)spaceDeckField.get(engine);
        SpaceDeck spaceDiscard = (SpaceDeck)spaceDiscardField.get(engine);

		while (gameDeck.size() > 0) gameDiscard.add(gameDeck.draw());
		int origGameDeckSize = gameDeck.size(); int origGameDiscardSize = gameDiscard.size();
		int origSpaceDeckSize = spaceDeck.size(); int origSpaceDiscardSize = spaceDiscard.size();

		engine.mergeDecks(gameDeck, gameDiscard);
		assertEquals(origSpaceDeckSize, spaceDeck.size());
		assertEquals(origSpaceDiscardSize, spaceDiscard.size());
		assertEquals(origGameDiscardSize, gameDeck.size());
		assertEquals(0, gameDiscard.size());

		while (spaceDeck.size() > 0) spaceDiscard.add(spaceDeck.draw());
		origGameDeckSize = gameDeck.size(); origGameDiscardSize = gameDiscard.size();
		origSpaceDeckSize = spaceDeck.size(); origSpaceDiscardSize = spaceDiscard.size();

		engine.mergeDecks(spaceDeck, spaceDiscard);
		assertEquals(origGameDeckSize, gameDeck.size());
		assertEquals(origGameDiscardSize, gameDiscard.size());
		assertEquals(origSpaceDiscardSize, spaceDeck.size());
		assertEquals(0, spaceDiscard.size());
	}

	@Test
	public void testMergeDecksDeck1UnchangedIfDeck2Empty() throws IllegalAccessException, NoSuchFieldException {
		Field gameDeckField = GameEngine.class.getDeclaredField("gameDeck");
		Field gameDiscardField = GameEngine.class.getDeclaredField("gameDiscard");
		Field spaceDeckField = GameEngine.class.getDeclaredField("spaceDeck");
		Field spaceDiscardField = GameEngine.class.getDeclaredField("spaceDiscard");
		Field cardsField = Deck.class.getDeclaredField("cards");
		gameDeckField.setAccessible(true);
		gameDiscardField.setAccessible(true);
		spaceDeckField.setAccessible(true);
		spaceDiscardField.setAccessible(true);
		cardsField.setAccessible(true);

		addPlayers(5);
		engine.startGame();

        GameDeck gameDeck = (GameDeck)gameDeckField.get(engine);
        GameDeck gameDiscard = (GameDeck)gameDiscardField.get(engine);
        SpaceDeck spaceDeck = (SpaceDeck)spaceDeckField.get(engine);
        SpaceDeck spaceDiscard = (SpaceDeck)spaceDiscardField.get(engine);
		int origGameDeckSize = gameDeck.size(); int origGameDiscardSize = gameDiscard.size();
		int origSpaceDeckSize = spaceDeck.size();int origSpaceDiscardSize = spaceDiscard.size();
		assertEquals(origGameDiscardSize, 0);
		assertEquals(origSpaceDiscardSize, 0);

		engine.mergeDecks(gameDeck, gameDiscard);
		assertEquals(origGameDeckSize, gameDeck.size());
		assertEquals(origGameDiscardSize, gameDiscard.size());
		assertEquals(origSpaceDeckSize, spaceDeck.size());
		assertEquals(origSpaceDiscardSize, spaceDiscard.size());

		engine.mergeDecks(spaceDeck, spaceDiscard);
		assertEquals(origGameDeckSize, gameDeck.size());
		assertEquals(origGameDiscardSize, gameDiscard.size());
		assertEquals(origSpaceDeckSize, spaceDeck.size());
		assertEquals(origSpaceDiscardSize, spaceDiscard.size());

		while (gameDeck.size()>0) gameDiscard.add(gameDeck.draw());
		while (spaceDeck.size()>0) spaceDiscard.add(spaceDeck.draw());
		origGameDeckSize = gameDeck.size(); origGameDiscardSize = gameDiscard.size();
		origSpaceDeckSize = spaceDeck.size(); origSpaceDiscardSize = spaceDiscard.size();
		assertEquals(origGameDeckSize, 0);
		assertEquals(origSpaceDeckSize, 0);

		engine.mergeDecks(gameDiscard, gameDeck);
		assertEquals(origGameDeckSize, gameDeck.size());
		assertEquals(origGameDiscardSize, gameDiscard.size());
		assertEquals(origSpaceDeckSize, spaceDeck.size());
		assertEquals(origSpaceDiscardSize, spaceDiscard.size());

		engine.mergeDecks(spaceDiscard, spaceDeck);
		assertEquals(origGameDeckSize, gameDeck.size());
		assertEquals(origGameDiscardSize, gameDiscard.size());
		assertEquals(origSpaceDeckSize, spaceDeck.size());
		assertEquals(origSpaceDiscardSize, spaceDiscard.size());
	}
	
	@Test
	public void testSaveStateWritesFile() throws Exception {
		String path = "testSaveStateWritesFile.obj";
		addPlayers(2);
		engine.startGame();
		engine.startTurn();
		engine.saveState(path);

		// You can't delete a non-existant file, so the test will trigger
		// an Exception and fail
		Files.delete(Paths.get(path));
	}

	@Test
	public void testSaveStateFilesIdentical() throws Exception {
		String path1 = "testSaveStateFilesDiffer_file1.obj";
		String path2 = "testSaveStateFilesDiffer_file2.obj";
		addPlayers(2);
		engine.startGame();
		engine.saveState(path1);
		engine.saveState(path2);
		assertEquals(-1, Files.mismatch(Paths.get(path1), Paths.get(path2)));
		Files.delete(Paths.get(path1));
		Files.delete(Paths.get(path2));
	}

	@Test
	public void testSaveStateFilesDiffer() throws Exception {
		String path1 = "testSaveStateFilesDiffer_file1.obj";
		String path2 = "testSaveStateFilesDiffer_file2.obj";
		addPlayers(2);
		engine.startGame();
		engine.saveState(path1);
		engine.startTurn();
		engine.saveState(path2);
		assertTrue(Files.mismatch(Paths.get(path1), Paths.get(path2)) >= 0);
		Files.delete(Paths.get(path1));
		Files.delete(Paths.get(path2));
	}

	public static Oxygen[] _testSplitOxygen(GameEngine engine) throws IllegalAccessException, NoSuchFieldException {
		Field gameDeckField = GameEngine.class.getDeclaredField("gameDeck");
		Field gameDiscardField = GameEngine.class.getDeclaredField("gameDiscard");
		Field cardsField = Deck.class.getDeclaredField("cards");
		Field oxygensField = GameDeck.class.getDeclaredField("oxygens");
		Field valueField = Oxygen.class.getDeclaredField("value");
		gameDeckField.setAccessible(true);
		gameDiscardField.setAccessible(true);
		cardsField.setAccessible(true);
		oxygensField.setAccessible(true);
		valueField.setAccessible(true);

		GameDeck gameDeck = (GameDeck)gameDeckField.get(engine);
		Collection<Card> gameDeckCards = (Collection<Card>)cardsField.get(gameDeck);
		Collection<Oxygen> gameDeckO2 = (Collection<Oxygen>)oxygensField.get(gameDeck);
		GameDeck gameDiscard = (GameDeck)gameDiscardField.get(engine);
		Collection<Card> gameDiscardCards = (Collection<Card>)cardsField.get(gameDiscard);
		Collection<Oxygen> gameDiscardO2 = (Collection<Oxygen>)oxygensField.get(gameDiscard);

		int oldGameDeckSize = gameDeckCards.size();
		int oldGameDiscardSize = gameDiscardCards.size();
		int oldGameDecKO2Size = gameDeckO2.size();
		int oldGameDiscardO2Size = gameDiscardO2.size();
		int oldGameDecKO2Val = 0; int oldGameDiscardO2Val = 0;

		Iterator<Oxygen> iterator = gameDeckO2.iterator();
        while (iterator.hasNext()) oldGameDecKO2Val += (Integer)valueField.get(iterator.next());
		iterator = gameDiscardO2.iterator();
        while (iterator.hasNext()) oldGameDiscardO2Val += (Integer)valueField.get(iterator.next());

		Oxygen[] rtn = engine.splitOxygen(new Oxygen(2));

		assertEquals(oldGameDeckSize+oldGameDiscardSize-1, gameDeckCards.size()+gameDiscardCards.size());
		assertEquals(oldGameDecKO2Size+oldGameDiscardO2Size-1, gameDeckO2.size()+gameDiscardO2.size());

		int gameDecKO2Val = 0; int gameDiscardO2Val = 0;
		iterator = gameDeckO2.iterator();
        while (iterator.hasNext()) gameDecKO2Val += (Integer)valueField.get(iterator.next());
		iterator = gameDiscardO2.iterator();
        while (iterator.hasNext()) gameDiscardO2Val += (Integer)valueField.get(iterator.next());

		assertEquals(oldGameDecKO2Val+oldGameDiscardO2Val, gameDecKO2Val+gameDiscardO2Val);

		return rtn;
	}

	@Test
	public void testSplitOxygen() throws Exception {
		Field gameDiscardField = GameEngine.class.getDeclaredField("gameDiscard");
		gameDiscardField.setAccessible(true);
		GameDeck gameDiscard = (GameDeck)gameDiscardField.get(engine);

		addPlayers(4);
		engine.startGame();
		for (int i=0; i<5; ++i) gameDiscard.add(new Oxygen(1));

		_testSplitOxygen(engine);
	}

	@Test
	public void testSplitOxygenWhenDeckEmpty() throws Exception {
		Field gameDeckField = GameEngine.class.getDeclaredField("gameDeck");
		Field gameDiscardField = GameEngine.class.getDeclaredField("gameDiscard");
		gameDeckField.setAccessible(true);
		gameDiscardField.setAccessible(true);
		GameDeck gameDeck = (GameDeck)gameDeckField.get(engine);
		GameDeck gameDiscard = (GameDeck)gameDiscardField.get(engine);

		addPlayers(4);
		engine.startGame();
		while (gameDeck.size() > 0) gameDiscard.add(gameDeck.draw());

		_testSplitOxygen(engine);
	}

	@Test
	public void testSplitOxygenWhenDiscardEmpty() throws Exception {
		addPlayers(4);
		engine.startGame();
		_testSplitOxygen(engine);
	}

	@Test
	public void testSplitOxygenWhenDeckContainsOne() throws Exception {
		Field gameDeckField = GameEngine.class.getDeclaredField("gameDeck");
		Field gameDiscardField = GameEngine.class.getDeclaredField("gameDiscard");
		gameDeckField.setAccessible(true);
		gameDiscardField.setAccessible(true);
		GameDeck gameDeck = (GameDeck)gameDeckField.get(engine);
		GameDeck gameDiscard = (GameDeck)gameDiscardField.get(engine);

		addPlayers(4);
		engine.startGame();
		while (gameDeck.size() > 1) gameDiscard.add(gameDeck.draw());

		_testSplitOxygen(engine);
	}

	@Test
	public void testSplitOxygenWhenDiscardContainsOne() throws Exception {
		Field gameDiscardField = GameEngine.class.getDeclaredField("gameDiscard");
		gameDiscardField.setAccessible(true);
		GameDeck gameDiscard = (GameDeck)gameDiscardField.get(engine);

		addPlayers(4);
		engine.startGame();
		gameDiscard.add(new Oxygen(1));

		_testSplitOxygen(engine);
	}

	@Test
	public void testSplitOxygenWhenDeckAndDiscardContainOneEach() throws Exception {
		Field gameDeckField = GameEngine.class.getDeclaredField("gameDeck");
		Field gameDiscardField = GameEngine.class.getDeclaredField("gameDiscard");
		gameDeckField.setAccessible(true);
		gameDiscardField.setAccessible(true);
		GameDeck gameDeck = (GameDeck)gameDeckField.get(engine);
		GameDeck gameDiscard = (GameDeck)gameDiscardField.get(engine);

		addPlayers(4);
		engine.startGame();
		gameDiscard.add(new Oxygen(1));
		while (gameDeck.size() > 0)gameDeck.draw();
		gameDeck.add(new Oxygen(1));

		_testSplitOxygen(engine);
	}

	@Test
	public void testStartGameThreePlayers() throws Exception {
		Field activePlayersField = GameEngine.class.getDeclaredField("activePlayers");
		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		Field actionsField = Astronaut.class.getDeclaredField("actions");
		Field cardsField = Deck.class.getDeclaredField("cards");
		activePlayersField.setAccessible(true);
		oxygensField.setAccessible(true);
		actionsField.setAccessible(true);
		cardsField.setAccessible(true);

		engine = new GameEngine(530886,  "io/ActionCards.txt", "io/SpaceCards.txt");
		List<Card> cards = (List<Card>)cardsField.get(engine.getGameDeck());
		List<Card> markel = new ArrayList<Card>();
		List<Card> sarah = new ArrayList<Card>();
		List<Card> terry = new ArrayList<Card>();
		for (int i=1; i<13; ++i) {
			Card c = cards.get(cards.size()-i);
			List toAdd = (i%3 == 1) ? terry : (i%3 == 2) ? markel : sarah; toAdd.add(c);
		}
		sarah.add(new Oxygen(2)); for (int i=0; i<4; ++i) sarah.add(new Oxygen(1));
		markel.add(new Oxygen(2)); for (int i=0; i<4; ++i) markel.add(new Oxygen(1));
		terry.add(new Oxygen(2)); for (int i=0; i<4; ++i) terry.add(new Oxygen(1));
		sarah.sort(null);
		markel.sort(null);
		terry.sort(null);

		addPlayers(3);
		cards = (List<Card>)cardsField.get(engine.getGameDeck());
		engine.startGame();
		Collection<Astronaut> activePlayers = (Collection<Astronaut>)activePlayersField.get(engine);

		for (Astronaut a: activePlayers) {
			List<Card> actions = (List<Card>)actionsField.get(a);
			List<Oxygen> oxygens = (List<Oxygen>)oxygensField.get(a);
			List<Card> hand = new ArrayList<Card>(actions); hand.addAll(oxygens);
			hand.sort(null);
		    assertEquals(9, actions.size() + oxygens.size());
			if (a.toString().equals("Sarah")) {
				for (int i=0; i<9; ++i) assertEquals(sarah.get(i).toString(), hand.get(i).toString());
			} else if (a.toString().equals("Markel")) {
				for (int i=0; i<9; ++i) assertEquals(markel.get(i).toString(), markel.get(i).toString());
			} else {
				for (int i=0; i<9; ++i) assertEquals(terry.get(i).toString(), terry.get(i).toString());
			}
		}
	}

	@Test
	public void testStartGameTwoPlayers() throws Exception {
		Field activePlayersField = GameEngine.class.getDeclaredField("activePlayers");
		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		Field actionsField = Astronaut.class.getDeclaredField("actions");
		Field cardsField = Deck.class.getDeclaredField("cards");
		activePlayersField.setAccessible(true);
		oxygensField.setAccessible(true);
		actionsField.setAccessible(true);
		cardsField.setAccessible(true);

		engine = new GameEngine(530886,  "io/ActionCards.txt", "io/SpaceCards.txt");
		List<Card> cards = (List<Card>)cardsField.get(engine.getGameDeck());
		List<Card> markel = new ArrayList<Card>();
		List<Card> sarah = new ArrayList<Card>();
		for (int i=1; i<9; ++i) {
			Card c = cards.get(cards.size()-i);
			List toAdd = (i%2 == 1) ? markel : sarah; toAdd.add(c);
		}
		sarah.add(new Oxygen(2)); for (int i=0; i<4; ++i) sarah.add(new Oxygen(1));
		markel.add(new Oxygen(2)); for (int i=0; i<4; ++i) markel.add(new Oxygen(1));
		sarah.sort(null);
		markel.sort(null);

		addPlayers(2);
		cards = (List<Card>)cardsField.get(engine.getGameDeck());
		engine.startGame();
		Collection<Astronaut> activePlayers = (Collection<Astronaut>)activePlayersField.get(engine);

		for (Astronaut a: activePlayers) {
			List<Card> actions = (List<Card>)actionsField.get(a);
			List<Oxygen> oxygens = (List<Oxygen>)oxygensField.get(a);
			List<Card> hand = new ArrayList<Card>(actions); hand.addAll(oxygens);
			hand.sort(null);
		    assertEquals(9, actions.size() + oxygens.size());
			if (a.toString().equals("Sarah")) {
				for (int i=0; i<9; ++i) assertEquals(sarah.get(i).toString(), hand.get(i).toString());
			} else {
				for (int i=0; i<9; ++i) assertEquals(markel.get(i).toString(), markel.get(i).toString());
			}
		}
	}

	@Test
	public void testStartTurnUpdatesPlayerAttributes() throws IllegalAccessException, NoSuchFieldException {
		addPlayers(2);
		engine.startGame();

		Field activePlayersField = GameEngine.class.getDeclaredField("activePlayers");
		Field currentPlayerField = GameEngine.class.getDeclaredField("currentPlayer");
		activePlayersField.setAccessible(true);
		currentPlayerField.setAccessible(true);
		Collection<Astronaut> activePlayers = (Collection<Astronaut>)activePlayersField.get(engine);
		Astronaut currentPlayer = (Astronaut)currentPlayerField.get(engine);

		assertNull(currentPlayer);
		assertEquals(2, activePlayers.size());
		engine.startTurn();
		currentPlayer = (Astronaut)currentPlayerField.get(engine);
		assertNotNull(currentPlayer);
		assertEquals(1, activePlayers.size());
	}

	@Test
	public void testStartTurnIsLIFO() throws IllegalAccessException, NoSuchFieldException {
		addPlayers(3);
		engine.startGame();
		Field currentPlayerField = GameEngine.class.getDeclaredField("currentPlayer");
		currentPlayerField.setAccessible(true);

		engine.startTurn();
		Astronaut currentPlayer = (Astronaut)currentPlayerField.get(engine);
		assertEquals("Terry", currentPlayer.toString());
		engine.endTurn();
		engine.startTurn();
		currentPlayer = (Astronaut)currentPlayerField.get(engine);
		assertEquals("Markel", currentPlayer.toString());
		engine.endTurn();
		engine.startTurn();
		currentPlayer = (Astronaut)currentPlayerField.get(engine);
		assertEquals("Sarah", currentPlayer.toString());
		engine.endTurn();
		engine.startTurn();
		currentPlayer = (Astronaut)currentPlayerField.get(engine);
		assertEquals("Terry", currentPlayer.toString());
		engine.endTurn();
		engine.startTurn();
		currentPlayer = (Astronaut)currentPlayerField.get(engine);
		assertEquals("Markel", currentPlayer.toString());
		engine.endTurn();
		engine.startTurn();
		currentPlayer = (Astronaut)currentPlayerField.get(engine);
		assertEquals("Sarah", currentPlayer.toString());
	}

	@Test
	public void testTravelFirst() throws IllegalAccessException, NoSuchFieldException {
		Field currentPlayerField = GameEngine.class.getDeclaredField("currentPlayer");
		Field spaceDeckField = GameEngine.class.getDeclaredField("spaceDeck");
		Field trackField = Astronaut.class.getDeclaredField("track");
		Field cardsField = Deck.class.getDeclaredField("cards");
		currentPlayerField.setAccessible(true);
		spaceDeckField.setAccessible(true);
		trackField.setAccessible(true);
		cardsField.setAccessible(true);
		addPlayers(3);
		engine.startGame();
		engine.startTurn();
		Astronaut currentPlayer = (Astronaut)currentPlayerField.get(engine);
		List<Card> spaceDeck = (List<Card>)cardsField.get((SpaceDeck)spaceDeckField.get(engine));
		List<Card> track = (List<Card>)trackField.get(currentPlayer);
		Card topmostSpaceDeckCard = spaceDeck.get(spaceDeck.size()-1);
		Card rtn = engine.travel(currentPlayer);
		assertEquals(1, track.size());
		assertSame(topmostSpaceDeckCard, track.get(track.size()-1));
	}

	@Test
	public void testTravelWithTwoOxygen() throws IllegalAccessException, NoSuchFieldException {
		Field currentPlayerField = GameEngine.class.getDeclaredField("currentPlayer");
		Field spaceDeckField = GameEngine.class.getDeclaredField("spaceDeck");
		Field trackField = Astronaut.class.getDeclaredField("track");
		Field cardsField = Deck.class.getDeclaredField("cards");
		currentPlayerField.setAccessible(true);
		spaceDeckField.setAccessible(true);
		trackField.setAccessible(true);
		cardsField.setAccessible(true);
		addPlayers(3);
		engine.startGame();
		engine.startTurn();
		Astronaut currentPlayer = (Astronaut)currentPlayerField.get(engine);
		engine.travel(currentPlayer);

		List<Card> spaceDeck = (List<Card>)cardsField.get((SpaceDeck)spaceDeckField.get(engine));
		List<Card> track = (List<Card>)trackField.get(currentPlayer);
		Card topmostTrackCard = track.get(track.size()-1);
		Card topmostSpaceDeckCard = spaceDeck.get(spaceDeck.size()-1);
		int trackSize = track.size();
		while (currentPlayer.breathe() > 2);
		Card rtn = engine.travel(currentPlayer);
		assertEquals(trackSize+1, track.size());
		assertNotSame(topmostTrackCard, track.get(track.size()-1));
		assertSame(topmostSpaceDeckCard, track.get(track.size()-1));
	}

	@Test
	public void testTravel() throws IllegalAccessException, NoSuchFieldException {
		Field currentPlayerField = GameEngine.class.getDeclaredField("currentPlayer");
		Field spaceDeckField = GameEngine.class.getDeclaredField("spaceDeck");
		Field trackField = Astronaut.class.getDeclaredField("track");
		Field cardsField = Deck.class.getDeclaredField("cards");
		currentPlayerField.setAccessible(true);
		spaceDeckField.setAccessible(true);
		trackField.setAccessible(true);
		cardsField.setAccessible(true);
		addPlayers(4);
		engine.startGame();
		engine.startTurn();
		Astronaut currentPlayer = (Astronaut)currentPlayerField.get(engine);
		engine.travel(currentPlayer);

		List<Card> spaceDeck = (List<Card>)cardsField.get((SpaceDeck)spaceDeckField.get(engine));
		List<Card> track = (List<Card>)trackField.get(currentPlayer);
		Card topmostTrackCard = track.get(track.size()-1);
		Card topmostSpaceDeckCard = spaceDeck.get(spaceDeck.size()-1);
		int trackSize = track.size();
		Card rtn = engine.travel(currentPlayer);
		assertEquals(trackSize+1, track.size());
		assertNotSame(topmostTrackCard, track.get(track.size()-1));
		assertSame(topmostSpaceDeckCard, track.get(track.size()-1));
	}

	@Test
	public void testTravelGravitationAnomalyIsNotAddedToTrack() throws IllegalAccessException, NoSuchFieldException {
		Field currentPlayerField = GameEngine.class.getDeclaredField("currentPlayer");
		Field trackField = Astronaut.class.getDeclaredField("track");
		currentPlayerField.setAccessible(true);
		trackField.setAccessible(true);

		addPlayers(3);
		engine.startGame();
		engine.startTurn();
		Astronaut currentPlayer = (Astronaut)currentPlayerField.get(engine);
		List<Card> track = (List<Card>)trackField.get(currentPlayer);

		for (int i=0; i<9; ++i) engine.getSpaceDeck().draw();
		engine.travel(currentPlayer);
		int trackSize = track.size();
		Card drawn = engine.travel(currentPlayer);
		assertEquals("Gravitational anomaly", drawn.toString());
		assertEquals(trackSize, track.size());
	}

}
