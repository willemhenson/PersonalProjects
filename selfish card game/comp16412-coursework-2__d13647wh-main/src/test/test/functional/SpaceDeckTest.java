package test.functional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import selfish.deck.Card;
import selfish.deck.Deck;
import selfish.deck.SpaceDeck;

@Tag("functional")
@Tag("SpaceDeck")
public class SpaceDeckTest {

	@Test
	public static void testConstants() throws Exception {
		assertEquals(SpaceDeck.ASTEROID_FIELD, "Asteroid Field");
		assertEquals(SpaceDeck.BLANK_SPACE, "Blank space");
		assertEquals(SpaceDeck.COSMIC_RADIATION, "Cosmic radiation");
		assertEquals(SpaceDeck.GRAVITATIONAL_ANOMALY, "Gravitational anomaly");
		assertEquals(SpaceDeck.HYPERSPACE, "Hyperspace");
		assertEquals(SpaceDeck.METEOROID, "Meteoroid");
		assertEquals(SpaceDeck.MYSTERIOUS_NEBULA, "Mysterious nebula");
		assertEquals(SpaceDeck.SOLAR_FLARE, "Solar flare");
		assertEquals(SpaceDeck.USEFUL_JUNK, "Useful junk");
		assertEquals(SpaceDeck.WORMHOLE, "Wormhole");
	}

	@Test
	public void testEmptyConstructor() throws NoSuchFieldException, IllegalAccessException {
        SpaceDeck spaceDeck = new SpaceDeck();
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		Collection<Card> cards = (Collection<Card>)cardsField.get(spaceDeck);
		assertEquals(0, cards.size());
	}

	@Test
	public static void testStringConstructor() throws Exception {
		SpaceDeck spaceDeck = new SpaceDeck("io/SpaceCards.txt");
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		Collection<Card> cards = (Collection<Card>)cardsField.get(spaceDeck);
		assertEquals(42, cards.size());
	}

	@Test
	public void testStringConstructorMaintainsOrder() throws Exception {
		SpaceDeck spaceDeck = new SpaceDeck("io/SpaceCards.txt");
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		List<Card> cards = (List<Card>)cardsField.get(spaceDeck);
		for (int i=0; i<42; ++i) {
			if (i<9) {
				assertEquals("Blank space", cards.get(i).toString());
			} else if (i>=9 && i<14) {
				assertEquals("Useful junk", cards.get(i).toString());
			} else if (i>=14 && i<16) {
				assertEquals("Mysterious nebula", cards.get(i).toString());
			} else if (i>=16 && i<17) {
				assertEquals("Hyperspace", cards.get(i).toString());
			} else if (i>=17 && i<21) {
				assertEquals("Meteoroid", cards.get(i).toString());
			} else if (i>=21 && i<27) {
				assertEquals("Cosmic radiation", cards.get(i).toString());
			} else if (i>=27 && i<29) {
				assertEquals("Asteroid field", cards.get(i).toString());
			} else if (i>=29 && i<33) {
				assertEquals("Gravitational anomaly", cards.get(i).toString());
			} else if (i>=33 && i<37) {
				assertEquals("Wormhole", cards.get(i).toString());
			} else {
				assertEquals("Solar flare", cards.get(i).toString());
			}
		}
	}
	
	@Test
	public void testAddTwoToEmptyDeck() throws NoSuchFieldException, IllegalAccessException {
        SpaceDeck spaceDeck = new SpaceDeck();
		spaceDeck.add(new Card("Test Name 1", "Test Description 1"));
		spaceDeck.add(new Card("Test Name 2", "Test Description 2"));
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		Collection<Card> cards = (Collection<Card>)cardsField.get(spaceDeck);
		assertEquals(2, cards.size());
	}
	
	@Test
	public void testAddTwoToNonEmptyDeck() throws Exception {
        SpaceDeck spaceDeck = new SpaceDeck("io/SpaceCards.txt");
		spaceDeck.add(new Card("Test Name 1", "Test Description 1"));
		spaceDeck.add(new Card("Test Name 2", "Test Description 2"));
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		Collection<Card> cards = (Collection<Card>)cardsField.get(spaceDeck);
		assertEquals(44, cards.size());
	}
	
	@Test
	public void testAddRemoveSize() throws NoSuchFieldException, IllegalAccessException {
        SpaceDeck spaceDeck = new SpaceDeck();
		spaceDeck.add(new Card("Test Name 1", "Test Description 1"));
		assertEquals(1, spaceDeck.size());
		Card toRemove = new Card("Test Name 2", "Test Description 2");
		spaceDeck.add(toRemove);
		assertEquals(2, spaceDeck.size());
		spaceDeck.add(new Card("Test Name 3", "Test Description 2"));
		assertEquals(3, spaceDeck.size());
		spaceDeck.remove(toRemove);
		assertEquals(2, spaceDeck.size());
	}
	
	@Test
	public void testAddManyToEmptyDeck() throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		SpaceDeck spaceDeck = new SpaceDeck();
		Class clazz = Deck.class;
		Method addMethod = clazz.getDeclaredMethod("add", List.class);
		addMethod.setAccessible(true);

		List<Card> cardsToAdd = new ArrayList<Card>();
		for (int i=0; i<100; ++i) cardsToAdd.add(new Card("Test " + i, "Description"));
		Card zero = cardsToAdd.get(0);
		Card hero = cardsToAdd.get(99);
		addMethod.invoke(spaceDeck, cardsToAdd);

		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		List<Card> cards = (List<Card>)cardsField.get(spaceDeck);
		assertEquals(100, cards.size());
		assertSame(zero, cards.get(0));
		assertSame(hero, cards.get(99));
	}
	
	@Test
	public void testAddManyToFullDeck() throws Exception {
		SpaceDeck spaceDeck = new SpaceDeck("io/SpaceCards.txt");
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		Method addMethod = clazz.getDeclaredMethod("add", List.class);
		cardsField.setAccessible(true);
		addMethod.setAccessible(true);
		List<Card> cards = (List<Card>)cardsField.get(spaceDeck);
		Card zero = cards.get(0);

		List<Card> cardsToAdd = new ArrayList<Card>();
		for (int i=0; i<100; ++i) cardsToAdd.add(new Card("Test " + i, "Description"));
		Card newZero = cardsToAdd.get(0);
		Card hero = cardsToAdd.get(99);
		addMethod.invoke(spaceDeck, cardsToAdd);

		assertEquals(142, cards.size());
		assertSame(zero, cards.get(0));
		assertSame(newZero, cards.get(42));
		assertSame(hero, cards.get(141));
	}

    @Test
	public void testDrawLastCard() throws NoSuchFieldException, IllegalAccessException {
        SpaceDeck spaceDeck = new SpaceDeck();
		Card last = new Card("Test Name 1", "Test Description 1");
		spaceDeck.add(last);
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		Collection<Card> cards = (Collection<Card>)cardsField.get(spaceDeck);

		assertEquals(1, cards.size());
		Card card = spaceDeck.draw();
		assertSame(last, card);
		assertEquals(0, cards.size());
	}

    @Test
	public void testDrawMany() throws NoSuchFieldException, IllegalAccessException {
        SpaceDeck spaceDeck = new SpaceDeck();
		spaceDeck.add(new Card("Test Name 1", "Test Description 1"));
		spaceDeck.add(new Card("Test Name 2", "Test Description 2"));
		spaceDeck.add(new Card("Test Name 3", "Test Description 3"));
		spaceDeck.add(new Card("Test Name 4", "Test Description 4"));
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		Collection<Card> cards = (Collection<Card>)cardsField.get(spaceDeck);
		spaceDeck.draw(); // tested in testDrawOne()

		Card card = spaceDeck.draw();
		assertEquals("Test Name 3", card.toString());
		assertEquals(2, cards.size());
		card = spaceDeck.draw();
		assertEquals("Test Name 2", card.toString());
		assertEquals(1, cards.size());
	}

	@Test
	public void testDrawOne() throws NoSuchFieldException, IllegalAccessException {
        SpaceDeck spaceDeck = new SpaceDeck();
		spaceDeck.add(new Card("Test Name 1", "Test Description 1"));
		spaceDeck.add(new Card("Test Name 2", "Test Description 2"));
		spaceDeck.add(new Card("Test Name 3", "Test Description 3"));
		Card last = new Card("Test Name 4", "Test Description 4");
		spaceDeck.add(last);
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		Collection<Card> cards = (Collection<Card>)cardsField.get(spaceDeck);

		assertEquals(4, cards.size());
		Card card = spaceDeck.draw();
		assertSame(last, card);
		assertEquals(3, cards.size());
	}

	@Test
	public void testRemoveOneFromSingleCardDeck() throws NoSuchFieldException, IllegalAccessException {
		SpaceDeck spaceDeck = new SpaceDeck();
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		List<Card> cards = (List<Card>)cardsField.get(spaceDeck);
		Card card = new Card("A card name", "A card description");
		cards.add(card);
		spaceDeck.remove(card);
		assertEquals(0, cards.size());
	}

	@Test
	public void testRemoveIndexNFromMultiCardDeck() throws Exception {
		SpaceDeck spaceDeck = new SpaceDeck("io/SpaceCards.txt");
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		List<Card> cards = (List<Card>)cardsField.get(spaceDeck);
		Card zero = cards.get(0);
		spaceDeck.remove(cards.get(41));
		assertEquals(41, cards.size());
		assertSame(zero, cards.get(0));
	}

	@Test
	public void testRemoveMany() throws Exception {
		SpaceDeck spaceDeck = new SpaceDeck("io/SpaceCards.txt");
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		List<Card> cards = (List<Card>)cardsField.get(spaceDeck);
		Card zero = cards.get(0);
		Card hero = cards.get(41);
		spaceDeck.remove(cards.get(20));
		spaceDeck.remove(cards.get(30));
		spaceDeck.remove(cards.get(10));
		assertEquals(39, cards.size());
		assertSame(zero, cards.get(0));
		assertSame(hero, cards.get(38));
	}

	@Test
	public void testRemoveCardNotInDeckChangesNothing() throws Exception {
		SpaceDeck spaceDeck = new SpaceDeck("io/SpaceCards.txt");
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		List<Card> cards = (List<Card>)cardsField.get(spaceDeck);
		Card zero = cards.get(0);
		Card hero = cards.get(41);
		spaceDeck.remove(new Card("A card name", "A card description"));
		assertEquals(42, cards.size());
		assertSame(zero, cards.get(0));
		assertSame(hero, cards.get(41));
	}

	@Test
	public void testRemoveFromEmptyDeckChangesNothing() throws NoSuchFieldException, IllegalAccessException {
		SpaceDeck spaceDeck = new SpaceDeck();
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		Collection<Card> cards = (Collection<Card>)cardsField.get(spaceDeck);
		spaceDeck.remove(new Card("A card name", "A card description"));	
		assertEquals(0, cards.size());			
	}

	@Test
	public void testShuffle16412() throws Exception {
		SpaceDeck spaceDeck = new SpaceDeck("io/SpaceCards.txt");
		Random rand = new Random(16412);
		spaceDeck.shuffle(rand);
		spaceDeck.shuffle(rand);
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		List<Card> cards = (List<Card>)cardsField.get(spaceDeck);
		assertEquals(42, cards.size());
		assertEquals("Blank space", cards.get(41).toString());
		assertEquals("Wormhole", cards.get(40).toString());
		assertEquals("Blank space", cards.get(39).toString());
		assertEquals("Meteoroid", cards.get(38).toString());
		assertEquals("Blank space", cards.get(37).toString());
		assertEquals("Cosmic radiation", cards.get(11).toString());
		assertEquals("Cosmic radiation", cards.get(10).toString());
		assertEquals("Solar flare", cards.get(9).toString());
		assertEquals("Solar flare", cards.get(8).toString());
		assertEquals("Wormhole", cards.get(7).toString());
	}

	@Test
	public void testShuffleTau() throws Exception {
		SpaceDeck spaceDeck = new SpaceDeck("io/SpaceCards.txt");
		Random rand = new Random(6283185307179586476L);
		spaceDeck.shuffle(rand);
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		List<Card> cards = (List<Card>)cardsField.get(spaceDeck);
		assertEquals(cards.size(), 42);
		assertEquals("Asteroid field", cards.get(9).toString());
		assertEquals("Cosmic radiation", cards.get(2).toString());
		assertEquals("Hyperspace", cards.get(5).toString());
		assertEquals("Useful junk", cards.get(28).toString());
		assertEquals("Cosmic radiation", cards.get(6).toString());
		assertEquals("Meteoroid", cards.get(7).toString());
		spaceDeck.shuffle(rand);
		assertEquals("Cosmic radiation", cards.get(6).toString());
		assertEquals("Mysterious nebula", cards.get(5).toString());
		assertEquals("Blank space", cards.get(9).toString());
		assertEquals("Gravitational anomaly", cards.get(0).toString());
	}

	@Test
	public void testSizeFull() throws Exception {
		SpaceDeck spaceDeck = new SpaceDeck("io/SpaceCards.txt");
		assertEquals(42, spaceDeck.size());
	}

	@Test
	public void testSizeEmpty() {
		SpaceDeck spaceDeck = new SpaceDeck();
		assertEquals(0, spaceDeck.size());
	}

}