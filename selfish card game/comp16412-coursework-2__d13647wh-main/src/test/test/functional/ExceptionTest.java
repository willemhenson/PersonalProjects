package test.functional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import selfish.Astronaut;
import selfish.GameEngine;
import selfish.GameException;
import selfish.deck.Card;
import selfish.deck.Deck;
import selfish.deck.Oxygen;
import selfish.deck.GameDeck;
import selfish.deck.SpaceDeck;

@Tag("functional")
public class ExceptionTest {

	public static GameEngine initGameEngine(int players) throws Exception {
		GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
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
		return engine;
	}

	@Test
	public void testAstronaut__hackCardParamNullThrowsException() throws Exception {
        GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Leonid Kadeniuk", engine);
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToHand(new Card("Laser blast", "Pick another player and knock them back 1 space."));
		astronaut.addToHand(new Card("Hole in suit", "Punch a hole in a rival's suit."));
	
		Field actionsField = Astronaut.class.getDeclaredField("actions");
		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		actionsField.setAccessible(true);
		oxygensField.setAccessible(true);
		List<Card> actions = (List<Card>)actionsField.get(astronaut);
		List<Card> oxygens = (List<Card>)oxygensField.get(astronaut);
		int numCards = actions.size() + oxygens.size();

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			astronaut.hack((Card)null);
		});
		assertEquals(numCards, actions.size() + oxygens.size());		
	}

	@Test
	public void testAstronaut__hackCardParamNotFoundThrowsException() throws Exception {
        GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Leonid Kadeniuk", engine);
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToHand(new Card("Laser blast", "Pick another player and knock them back 1 space."));
		astronaut.addToHand(new Card("Hole in suit", "Punch a hole in a rival's suit."));
	
		Field actionsField = Astronaut.class.getDeclaredField("actions");
		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		actionsField.setAccessible(true);
		oxygensField.setAccessible(true);
		List<Card> actions = (List<Card>)actionsField.get(astronaut);
		List<Card> oxygens = (List<Card>)oxygensField.get(astronaut);
		int numCards = actions.size() + oxygens.size();

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			astronaut.hack(new Card("Laser blast", "Pick another player and knock them back 1 space."));
		});
		assertEquals(numCards, actions.size() + oxygens.size());		
	}

	@Test
	public void testAstronaut__hackStringParamNull() throws Exception {
        GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Leonid Kadeniuk", engine);
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToHand(new Card("Laser blast", "Pick another player and knock them back 1 space."));
		astronaut.addToHand(new Card("Hole in suit", "Punch a hole in a rival's suit."));
	
		Field actionsField = Astronaut.class.getDeclaredField("actions");
		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		actionsField.setAccessible(true);
		oxygensField.setAccessible(true);
		List<Card> actions = (List<Card>)actionsField.get(astronaut);
		List<Card> oxygens = (List<Card>)oxygensField.get(astronaut);
		int numCards = actions.size() + oxygens.size();

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			astronaut.hack((String)null);
		});
		assertEquals(numCards, actions.size() + oxygens.size());
	}

	@Test
	public void testAstronaut__hackStringParamNotFoundThrowsException() throws Exception {
        GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Leonid Kadeniuk", engine);
		astronaut.addToHand(new Oxygen(1));
		astronaut.addToHand(new Card("Laser blast", "Pick another player and knock them back 1 space."));
		astronaut.addToHand(new Card("Hole in suit", "Punch a hole in a rival's suit."));
	
		Field actionsField = Astronaut.class.getDeclaredField("actions");
		Field oxygensField = Astronaut.class.getDeclaredField("oxygens");
		actionsField.setAccessible(true);
		oxygensField.setAccessible(true);
		List<Card> actions = (List<Card>)actionsField.get(astronaut);
		List<Card> oxygens = (List<Card>)oxygensField.get(astronaut);
		int numCards = actions.size() + oxygens.size();

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			astronaut.hack("Tether");
		});
		assertEquals(numCards, actions.size() + oxygens.size());
	}

	@Test
	public void testAstronaut__laserBlastAtStartThrowsException() throws Exception {
        GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");
		Astronaut astronaut = new Astronaut("Ellen Ochoa", engine);
		Oxygen o2 = new Oxygen(2);
		astronaut.addToHand(o2);

		Field trackField = Astronaut.class.getDeclaredField("track");
		trackField.setAccessible(true);
		List<Card> track = (List<Card>)trackField.get(astronaut);
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			Card rtn = astronaut.laserBlast();
		});
	}

	@Test
	public void testGameException__Constructor() {
		Throwable cause = new FileNotFoundException();
		GameException exception = new GameException("Something bad happened", cause);
		assertEquals("Something bad happened", exception.getMessage());
		assertTrue(cause == exception.getCause());
	}

	@Test
	public void testDeck__loadCardsThrowsException() throws Exception {
		Class clazz = Deck.class;
		Method loadCards = clazz.getDeclaredMethod("loadCards", String.class);
		loadCards.setAccessible(true);
		Exception exception = assertThrows(InvocationTargetException.class, () -> {
			loadCards.invoke(null, "thisFileDoesNotExist");
		});
		exception = (Exception)exception.getCause();
		if (!exception.getClass().equals(FileNotFoundException.class)) {
			assertEquals(GameException.class, exception.getClass());
			Throwable cause = exception.getCause();
			assertEquals(FileNotFoundException.class, cause.getClass());
		}
	}

	@Test
	public void testGameDeck__ConstructorThrowsException() throws Exception {
		Exception exception = assertThrows(Exception.class, () -> {
			new GameDeck("thisFileDoesNotExist");
		});
		if (!exception.getClass().equals(FileNotFoundException.class)) {
			assertEquals(GameException.class, exception.getClass());
			Throwable cause = exception.getCause();
			assertEquals(FileNotFoundException.class, cause.getClass());
		}
	}

	@Test
	public void testGameDeck__drawFromEmptyDeckThrowsException() throws NoSuchFieldException, IllegalAccessException {
		GameDeck gameDeck = new GameDeck();
	
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		Collection<Card> cards = (Collection<Card>)cardsField.get(gameDeck);
		assertEquals(0, gameDeck.size());

		Exception exception = assertThrows(IllegalStateException.class, () -> {
		    gameDeck.draw();
		});			
	}

	@Test
	public void testGameDeck__drawOxygenOneFromEmptyDeckThrowsException() throws NoSuchFieldException, IllegalAccessException {
		GameDeck gameDeck = new GameDeck();
	
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		Collection<Card> cards = (Collection<Card>)cardsField.get(gameDeck);
		assertEquals(0, gameDeck.size());

		Exception exception = assertThrows(IllegalStateException.class, () -> {
		    gameDeck.drawOxygen(1);
		});	
	}

	@Test
	public void testGameDeck__drawOxygenOneWhenNoOxygenOneThrowsException() throws NoSuchFieldException, IllegalAccessException {
		GameDeck gameDeck = new GameDeck();
	
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		Collection<Card> cards = (Collection<Card>)cardsField.get(gameDeck);
		assertEquals(0, gameDeck.size());

		gameDeck.add(new Oxygen(2));
		Exception exception = assertThrows(IllegalStateException.class, () -> {
		    gameDeck.drawOxygen(1);
		});	
	}

	@Test
	public void testGameDeck__drawOxygenTwoWhenNoOxygenTwoThrowsException() throws NoSuchFieldException, IllegalAccessException {
		GameDeck gameDeck = new GameDeck();
	
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		Collection<Card> cards = (Collection<Card>)cardsField.get(gameDeck);
		assertEquals(0, gameDeck.size());

		for (int i=0; i<3; ++i) gameDeck.add(new Oxygen(1));
		Exception exception = assertThrows(IllegalStateException.class, () -> {
		    gameDeck.drawOxygen(2);
		});	
	}

	@Test
	public void testGameEngine__splitOxygenWhenDecksInsufficientThrowsException() throws Exception {
		GameEngine engine = new GameEngine(16412,  "io/ActionCards.txt", "io/SpaceCards.txt");

		// Note that we can't use the get methods for deck because they might
		// reasonably try and address an empty deck by shuffling cards in from discard
		GameDeck discard = engine.getGameDiscard();
		Field gameDeckField = GameEngine.class.getDeclaredField("gameDeck");
		gameDeckField.setAccessible(true);
		GameDeck deck = (GameDeck)gameDeckField.get(engine);

		while (discard.size() < 5) {
			Card c = deck.draw();
			if (!(c.toString().equals(GameDeck.OXYGEN_1) || c.toString().equals(GameDeck.OXYGEN_2))) {
				discard.add(c);
			}
		}
		while (deck.size() > 0) deck.draw();

		deck.add(new Oxygen(2));
		discard.add(new Oxygen(2));
		deck.add(new Oxygen(1));

		Exception exception = assertThrows(IllegalStateException.class, () -> {
		    engine.splitOxygen(new Oxygen(2));
		});	
	}

	@Test
	public void testGameDeck__splitOxygenWhenDeckInsufficientThrowsException() throws Exception {
		GameDeck deck = new GameDeck();
		deck.add(new Oxygen(2));
		deck.add(new Oxygen(2));
		deck.add(new Oxygen(1));
		Exception exception = assertThrows(IllegalStateException.class, () -> {
		    deck.splitOxygen(new Oxygen(2));
		});	
	}

	@Test
	public void testGameDeck__splitOxygenOneThrowsException() throws Exception {
		GameDeck deck = new GameDeck();
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			deck.splitOxygen(new Oxygen(1));
		});
	}

    @Test
	public void testGameEngine__addPlayerAfterStartThrowsException() throws Exception {
        GameEngine engine = initGameEngine(3);
		engine.startGame();
		Exception exception = assertThrows(IllegalStateException.class, () -> {
		    engine.addPlayer("Riza");
		});
	}

	@Test
	public void testGameEngine__ConstructorWithActionCardFileNotFoundThrowsException() throws Exception {
		Exception exception = assertThrows(GameException.class, () -> {
		    GameEngine engine = new GameEngine(16412,  "io/thisfiledoesnotexist", "io/SpaceCards.txt");
		});
	}

	@Test
	public void testGameEngine__ConstructorWithSpaceCardFileNotFoundThrowsException() throws Exception {
		Exception exception = assertThrows(GameException.class, () -> {
		    GameEngine engine = new GameEngine(16412, "io/ActionCards.txt",  "io/thisfiledoesnotexist");
		});
	}

	@Test
	public void testGameEngine__addPlayerSixThrowsException() throws Exception {
        GameEngine engine = initGameEngine(5);
		Exception exception = assertThrows(IllegalStateException.class, () -> {
		    engine.addPlayer("Riza");
		});
	}

	@Test
	public void testGameEngine__loadStateThrowsGameException() {
		Exception exception = assertThrows(GameException.class, () -> {
			GameEngine.loadState("thisFileDoesNotExist");
		});
	}

	@Test
	public void testGameEngine__loadStateThrowsGameExceptionWithCause() {
		Exception exception = assertThrows(GameException.class, () -> {
			GameEngine.loadState("thisFileDoesNotExist");
		});
		Throwable cause = exception.getCause();
		assertEquals(FileNotFoundException.class, cause.getClass());
	}

	@Test
	public void testGameEngine__splitOxygenWhenDiscardEmptyAndDeckContainsOneThrowsException() throws Exception {
		Field gameDeckField = GameEngine.class.getDeclaredField("gameDeck");
		Field gameDiscardField = GameEngine.class.getDeclaredField("gameDiscard");
		gameDeckField.setAccessible(true);
		gameDiscardField.setAccessible(true);

		GameEngine engine = initGameEngine(5);
		engine.startGame();	

		GameDeck gameDeck = (GameDeck)gameDeckField.get(engine);
		GameDeck gameDiscard = (GameDeck)gameDiscardField.get(engine);

		while (gameDeck.size() > 0) gameDeck.draw();
		gameDeck.add(new Oxygen(1));

		Exception exception = assertThrows(IllegalStateException.class, () -> {
		    engine.splitOxygen(new Oxygen(2));
		});
	}

	@Test
	public void testGameEngine__splitOxygenWhenDeckEmptyAndDiscardContainsOneThrowsException() throws Exception {
		Field gameDeckField = GameEngine.class.getDeclaredField("gameDeck");
		Field gameDiscardField = GameEngine.class.getDeclaredField("gameDiscard");
		gameDeckField.setAccessible(true);
		gameDiscardField.setAccessible(true);
		
		GameEngine engine = initGameEngine(5);
		engine.startGame();	

		GameDeck gameDeck = (GameDeck)gameDeckField.get(engine);
		GameDeck gameDiscard = (GameDeck)gameDiscardField.get(engine);

		gameDiscard.add(new Oxygen(1));
		while (gameDeck.size() > 0) gameDeck.draw();

		Exception exception = assertThrows(IllegalStateException.class, () -> {
		    engine.splitOxygen(new Oxygen(2));
		});	
	}

	@Test
	public void testGameEngine__startGameTwiceThrowsException() throws Exception {
		GameEngine engine = initGameEngine(2);
		engine.startGame();		
		Exception exception = assertThrows(IllegalStateException.class, () -> {
		    engine.startGame();
		});
	}

	@Test
	public void testGameEngine__startGameWithOnePlayerThrowsException() throws Exception {
		GameEngine engine = initGameEngine(1);
		Exception exception = assertThrows(IllegalStateException.class, () -> {
		    engine.startGame();
		});
	}

	@Test
	public void testGameEngine__startGameWithSixPlayersThrowsException() throws Exception {
		GameEngine engine = initGameEngine(5);
		Field activePlayers = engine.getClass().getDeclaredField("activePlayers");
		activePlayers.setAccessible(true);
		Collection<Astronaut> players = (Collection<Astronaut>)activePlayers.get(engine);
		players.add(new Astronaut("Riza", engine));
		Exception exception = assertThrows(IllegalStateException.class, () -> {
		    engine.startGame();
		});
	}

	@Test
	public void testGameEngine__takeTurnWhenGameNotStartedThrowsException() throws Exception {
		GameEngine engine = initGameEngine(4);
		Exception exception = assertThrows(IllegalStateException.class, () -> {
		    engine.startTurn();
		});
	}

	@Test
	public void testGameEngine__takeTurnWhenTurnInProgressThrowsException() throws Exception {
		GameEngine engine = initGameEngine(4);
		engine.startGame();
		engine.startTurn();
		Exception exception = assertThrows(IllegalStateException.class, () -> {
		    engine.startTurn();
		});
	}

	@Test
	public void testGameEngine__takeTurnEveryoneDeadThrowsException() throws Exception {
		GameEngine engine = initGameEngine(2);
		engine.startGame();

		// There are a couple of things we need to do to make everyone dead
		// 1) Remove all the Oxygen
		Collection<Astronaut> players = engine.getAllPlayers();
		Field oxygens = Astronaut.class.getDeclaredField("oxygens");
		oxygens.setAccessible(true);
		for (Astronaut player: players) {
			List<Oxygen> o2 = (List<Oxygen>)oxygens.get(player);
			o2.clear();
		}

		// 2) Populate corpses
		// (and then clear active players, since dead people don't take turns)
		Field corpseField = GameEngine.class.getDeclaredField("corpses");
		corpseField.setAccessible(true);
		List<Astronaut> corpses = (List<Astronaut>)corpseField.get(engine);
		for (Astronaut player: players) {
			corpses.add(player);
		}
		Field activePlayers = GameEngine.class.getDeclaredField("activePlayers");
		activePlayers.setAccessible(true);
		players = (Collection<Astronaut>)activePlayers.get(engine);
		players.clear();

		// Now everyone is dead we can check for the exception
		Exception exception = assertThrows(IllegalStateException.class, () -> {
		    engine.startTurn();
		});
	}

	@Test
	public void testGameEngine__takeTurnSomeoneWonThrowsException() throws Exception {
		GameEngine engine = initGameEngine(3);
		engine.startGame();
		Astronaut winner = engine.getAllPlayers().get(1);
		Field track = Astronaut.class.getDeclaredField("track");
		track.setAccessible(true);
		Collection<Card> trackCards = (Collection<Card>)track.get(winner);
		while (trackCards.size() < 6) {
			trackCards.add(new Card("Blank Space", "This card is blank. Nothing to see here"));
		}
		Exception exception = assertThrows(IllegalStateException.class, () -> {
		    engine.startTurn();
		});
	}

	@Test
	public void testGameEngine__travelWhenNoOxygenThrowsException() throws Exception {
		GameEngine engine = initGameEngine(3);
		engine.startGame();	
		engine.startTurn();
		Astronaut player = engine.getCurrentPlayer();
		Field oxygens = Astronaut.class.getDeclaredField("oxygens");
		oxygens.setAccessible(true);
		List<Oxygen> o2 = (List<Oxygen>)oxygens.get(player);
		o2.clear();
		Exception exception = assertThrows(IllegalStateException.class, () -> {
		    engine.travel(player);
		});	
	}

	@Test
	public void testGameEngine__travelWhenOnLastOxygenThrowsException() throws Exception {
		GameEngine engine = initGameEngine(3);
		engine.startGame();	
		engine.startTurn();
		Astronaut player = engine.getCurrentPlayer();
		Field oxygens = Astronaut.class.getDeclaredField("oxygens");
		oxygens.setAccessible(true);
		List<Oxygen> o2 = (List<Oxygen>)oxygens.get(player);
		Oxygen o1 = o2.get(0);
		o2.clear();
		o2.add(o1);
		Exception exception = assertThrows(IllegalStateException.class, () -> {
		    engine.travel(player);
		});	
	}

	@Test
	public void testSpaceDeck__ConstructorThrowsException() throws Exception {
		Exception exception = assertThrows(Exception.class, () -> {
			new SpaceDeck("thisFileDoesNotExist");
		});
		if (!exception.getClass().equals(FileNotFoundException.class)) {
			assertEquals(GameException.class, exception.getClass());
			Throwable cause = exception.getCause();
			assertEquals(FileNotFoundException.class, cause.getClass());
		}
	}

}
