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
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import selfish.deck.Card;
import selfish.deck.Deck;
import selfish.deck.GameDeck;
import selfish.deck.Oxygen;

@Tag("functional")
@Tag("GameDeck")
public class GameDeckTest {

	@Test
	public static void testConstants() throws Exception {
		assertEquals(GameDeck.HACK_SUIT, "Hack suit");
		assertEquals(GameDeck.HOLE_IN_SUIT, "Hole in suit");
		assertEquals(GameDeck.LASER_BLAST, "Laser blast");
		assertEquals(GameDeck.OXYGEN, "Oxygen");
		assertEquals(GameDeck.OXYGEN_1, "Oxygen(1)");
		assertEquals(GameDeck.OXYGEN_2, "Oxygen(2)");
		assertEquals(GameDeck.OXYGEN_SIPHON, "Oxygen siphon");
		assertEquals(GameDeck.ROCKET_BOOSTER, "Rocket booster");
		assertEquals(GameDeck.SHIELD, "Shield");
		assertEquals(GameDeck.TETHER, "Tether");
		assertEquals(GameDeck.TRACTOR_BEAM, "Tractor beam");
	}

	@Test
	public void testEmptyConstructor() throws NoSuchFieldException, IllegalAccessException {
        GameDeck gameDeck = new GameDeck();
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		Collection<Card>cards = (Collection<Card>)cardsField.get(gameDeck);
		assertEquals(0, cards.size());
	}

	@Test
	public static void testStringConstructor() throws Exception {
		GameDeck gameDeck = new GameDeck("io/ActionCards.txt");
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		Collection<Card> cards = (Collection<Card>)cardsField.get(gameDeck);
		assertEquals(78, cards.size());
	}

	@Test
	public void testStringConstructorMaintainsOrder() throws Exception {
		GameDeck gameDeck = new GameDeck("io/ActionCards.txt");
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		List<Card> cards = (List<Card>)cardsField.get(gameDeck);

        for (int i=0; i<78; ++i) {
			if (i<3) {
				assertEquals("Oxygen siphon", cards.get(i).toString());
			} else if (i>=3 && i<7) {
				assertEquals("Shield", cards.get(i).toString());
			} else if (i>=7 && i<10) {
				assertEquals("Hack suit", cards.get(i).toString());
			} else if (i>=10 && i<14) {
				assertEquals("Tractor beam", cards.get(i).toString());
			} else if (i>=14 && i<18) {
				assertEquals("Rocket booster", cards.get(i).toString());
			} else if (i>=18 && i<22) {
				assertEquals("Laser blast", cards.get(i).toString());
			} else if (i>=22 && i<26) {
				assertEquals("Hole in suit", cards.get(i).toString());
			} else if (i>=26 && i<30) {
				assertEquals("Tether", cards.get(i).toString());
			} else if (i>=30 && i<40) {
				assertEquals("Oxygen(2)", cards.get(i).toString());
			} else if (i>=40 && i<78) {
				assertEquals("Oxygen(1)", cards.get(i).toString());
			}
		}
	}
	
	@Test
	public void testAddTwoToEmptyDeck() throws NoSuchFieldException, IllegalAccessException {
        GameDeck gameDeck = new GameDeck();
		gameDeck.add(new Card("Test Name 1", "Test Description 1"));
		gameDeck.add(new Oxygen(1));
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		Collection<Card> cards = (Collection<Card>)cardsField.get(gameDeck);
		assertEquals(2, cards.size());
	}
	
	@Test
	public void testAddTwoToNonEmptyDeck() throws Exception {
		GameDeck gameDeck = new GameDeck("io/ActionCards.txt");
		gameDeck.add(new Card("Test Name 1", "Test Description 1"));
		gameDeck.add(new Oxygen(1));
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		Collection<Card> cards = (Collection<Card>)cardsField.get(gameDeck);
		assertEquals(80, cards.size());
	}
	
	@Test
	public void testAddRemoveSize() throws NoSuchFieldException, IllegalAccessException {
        GameDeck gameDeck = new GameDeck();
		gameDeck.add(new Card("Test Name 1", "Test Description 1"));
		assertEquals(1, gameDeck.size());
		Oxygen toRemove = new Oxygen(2);
		gameDeck.add(toRemove);
		assertEquals(2, gameDeck.size());
		gameDeck.add(new Card("Test Name 3", "Test Description 2"));
		assertEquals(3, gameDeck.size());
		gameDeck.remove(toRemove);
		assertEquals(2, gameDeck.size());
	}

	@Test
	public void testAddManyToEmptyDeck() throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		GameDeck gameDeck = new GameDeck();
		Class clazz = Deck.class;
		Method addMethod = clazz.getDeclaredMethod("add", List.class);
		addMethod.setAccessible(true);

		List<Card> cardsToAdd = new ArrayList<Card>();
		for (int i=0; i<10; ++i) 
    		for (int j=0; j<10; ++j)
			    if ((i+j)%2 == 0)
    			    cardsToAdd.add(new Card("Test " + i+j, "Description"));
				else
    				cardsToAdd.add(new Oxygen((((i+j)%3)%2)+1));
		Card zero = cardsToAdd.get(0);
		Card hero = cardsToAdd.get(99);
		addMethod.invoke(gameDeck, cardsToAdd);

		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		List<Card> cards = (List<Card>)cardsField.get(gameDeck);
		assertEquals(100, cards.size());
		assertSame(zero, cards.get(0));
		assertSame(hero, cards.get(99));
	}
	
	@Test
	public void testAddManyToFullDeck() throws Exception {
		GameDeck gameDeck = new GameDeck("io/ActionCards.txt");
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		Method addMethod = clazz.getDeclaredMethod("add", List.class);
		cardsField.setAccessible(true);
		addMethod.setAccessible(true);
		List<Card> cards = (List<Card>)cardsField.get(gameDeck);
		Card zero = cards.get(0);

		List<Card> cardsToAdd = new ArrayList<Card>();
		for (int i=0; i<10; ++i) 
    		for (int j=0; j<10; ++j)
			    if ((i+j)%2 == 0)
    			    cardsToAdd.add(new Card("Test " + i+j, "Description"));
				else
    				cardsToAdd.add(new Oxygen((((i+j)%3)%2)+1));
		Card newZero = cardsToAdd.get(0);
		Card hero = cardsToAdd.get(99);
		addMethod.invoke(gameDeck, cardsToAdd);

		assertEquals(178, cards.size());
		assertSame(zero, cards.get(0));
		assertSame(newZero, cards.get(78));
		assertSame(hero, cards.get(177));
	}

    @Test
	public void testDrawLastCard() throws NoSuchFieldException, IllegalAccessException {
        GameDeck gameDeck = new GameDeck();
		Card last = new Oxygen(2);
        gameDeck.add(last);
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		Collection<Card> cards = (Collection<Card>)cardsField.get(gameDeck);

		assertEquals(1, cards.size());
		Card card = gameDeck.draw();
		assertSame(last, card);
		assertEquals(0, cards.size());
	}

    @Test
	public void testDraw() throws NoSuchFieldException, IllegalAccessException {
        GameDeck gameDeck = new GameDeck();
		gameDeck.add(new Card("Test Name 1", "Test Description 1"));
        gameDeck.add(new Oxygen(1));
        gameDeck.add(new Oxygen(2));
        gameDeck.add(new Oxygen(1));
		Card last = new Card("Test Name 2", "Test Description 2");
		gameDeck.add(last);
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		Collection<Card> cards = (Collection<Card>)cardsField.get(gameDeck);
		assertEquals(5, cards.size());
		Card card = gameDeck.draw();
		assertSame(last, card);
		assertEquals(4, cards.size());
	}

    @Test
    public void testDrawOxygenFromManyAvailable() throws NoSuchFieldException, IllegalAccessException {
        GameDeck gameDeck = new GameDeck();
		gameDeck.add(new Card("Test Name 1", "Test Description 1"));
        gameDeck.add(new Oxygen(1));
        gameDeck.add(new Oxygen(2));
        gameDeck.add(new Oxygen(2));
        gameDeck.add(new Oxygen(1));
		gameDeck.add(new Card("Test Name 2", "Test Description 2"));
        gameDeck.add(new Oxygen(1));
		gameDeck.drawOxygen(1);

		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		List<Card> cards = (List<Card>)cardsField.get(gameDeck);
		int o2_cards = 0;
		int o2_vals = 0;
		for (Card c: cards) {
			if (c.toString().equals("Oxygen(1)") || c.toString().equals("Oxygen(2)")) {
				++o2_cards;
				++o2_vals;
				if (c.toString().equals("Oxygen(2)")) ++o2_vals;
			}
		}
        assertEquals(4, o2_cards);
        assertEquals(6, o2_vals);
    }

    @Test
    public void testDrawOxygenFromOneAvailable() { 
        GameDeck gameDeck = new GameDeck();
		gameDeck.add(new Card("Test Name 1", "Test Description 1"));
        gameDeck.add(new Oxygen(1));
		Card needle = new Oxygen(2);
        gameDeck.add(needle);
        gameDeck.add(new Oxygen(1));
		gameDeck.add(new Card("Test Name 2", "Test Description 2"));
        assertSame(needle, gameDeck.drawOxygen(2));
    }

    @Test
    public void testDrawOxygenIsLastCard() {
        GameDeck gameDeck = new GameDeck();
		Card needle = new Oxygen(1);
        gameDeck.add(needle);
        assertSame(needle, gameDeck.drawOxygen(1));
    }

	@Test
	public void testRemoveOneFromSingleCardDeck() throws NoSuchFieldException, IllegalAccessException {
		GameDeck gameDeck = new GameDeck();
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		List<Card> cards = (List<Card>)cardsField.get(gameDeck);
		Card card = new Card("A card name", "A card description");
		cards.add(card);
		gameDeck.remove(card);
		assertEquals(0, cards.size());
	}

	@Test
	public void testRemoveIndexZeroFromMultiCardDeck() throws Exception {
		GameDeck gameDeck = new GameDeck("io/ActionCards.txt");
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		List<Card> cards = (List<Card>)cardsField.get(gameDeck);
		Card hero = cards.get(77);
		gameDeck.remove(cards.get(0));
		assertEquals(77, cards.size());
		assertSame(hero, cards.get(76));
	}

	@Test
	public void testRemoveMany() throws Exception {
		GameDeck gameDeck = new GameDeck("io/ActionCards.txt");
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		List<Card> cards = (List<Card>)cardsField.get(gameDeck);
		Card zero = cards.get(0);
		Card hero = cards.get(77);
		for (int i=0; i<2; ++i) {
    		gameDeck.remove(cards.get(26+i));
	    	gameDeck.remove(cards.get(36+i));
		    gameDeck.remove(cards.get(16+i));
	    	gameDeck.remove(cards.get(46+i));
		}
		assertEquals(70, cards.size());
		assertSame(zero, cards.get(0));
		assertSame(hero, cards.get(69));
	}

	@Test
	public void testRemoveCardNotInDeckChangesNothing() throws Exception {
		GameDeck gameDeck = new GameDeck("io/ActionCards.txt");
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		List<Card> cards = (List<Card>)cardsField.get(gameDeck);
		Card zero = cards.get(0);
		Card hero = cards.get(77);
		gameDeck.remove(new Card("A card name", "A card description"));
		assertEquals(78, cards.size());
		assertSame(zero, cards.get(0));
		assertSame(hero, cards.get(77));
	}

	@Test
	public void testRemoveFromEmptyDeckChangesNothing() throws NoSuchFieldException, IllegalAccessException {
		GameDeck gameDeck = new GameDeck();
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		Collection<Card> cards = (Collection<Card>)cardsField.get(gameDeck);
		gameDeck.remove(new Card("A card name", "A card description"));	
		assertEquals(0, cards.size());			
	}

	@Test
	public void testShuffle16412() throws Exception {
		GameDeck gameDeck = new GameDeck("io/ActionCards.txt");
		gameDeck.shuffle(new Random(16412));
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		List<Card> cards = (List<Card>)cardsField.get(gameDeck);
		assertEquals(cards.size(), 78);
		assertEquals("Tether", cards.get(77).toString());
		assertEquals("Oxygen(1)", cards.get(76).toString());
		assertEquals("Rocket booster", cards.get(75).toString());
		assertEquals("Oxygen(1)", cards.get(74).toString());
		assertEquals("Oxygen(1)", cards.get(73).toString());
		assertEquals("Tractor beam", cards.get(13).toString());
		assertEquals("Shield", cards.get(12).toString());
		assertEquals("Oxygen(1)", cards.get(11).toString());
		assertEquals("Oxygen(1)", cards.get(10).toString());
		assertEquals("Laser blast", cards.get(9).toString());
	}

	@Test
	public void testShufflePi() throws Exception {
		GameDeck gameDeck = new GameDeck("io/ActionCards.txt");
		gameDeck.shuffle(new Random(3141592653589793238L));
		Class clazz = Deck.class;
		Field cardsField = clazz.getDeclaredField("cards");
		cardsField.setAccessible(true);
		List<Card> cards = (List<Card>)cardsField.get(gameDeck);
		assertEquals(cards.size(), 78);
		assertEquals("Laser blast", cards.get(62).toString());
		assertEquals("Oxygen(1)", cards.get(8).toString());
		assertEquals("Rocket booster", cards.get(3).toString());
		assertEquals("Tractor beam", cards.get(18).toString());
		assertEquals("Oxygen(1)", cards.get(5).toString());
		assertEquals("Tether", cards.get(30).toString());
		assertEquals("Rocket booster", cards.get(71).toString());
		assertEquals("Oxygen(1)", cards.get(7).toString());
		assertEquals("Hole in suit", cards.get(9).toString());
		assertEquals("Hack suit", cards.get(58).toString());
	}

	@Test
	public void testSizeFull() throws Exception {
		GameDeck gameDeck = new GameDeck("io/ActionCards.txt");
		assertEquals(78, gameDeck.size());
	}

	@Test
	public void testSizeEmpty() {
		GameDeck gameDeck = new GameDeck();
		assertEquals(0, gameDeck.size());
	}

    @Test
    public void testSplitOxygen() throws Exception {
		Field cardsField = Deck.class.getDeclaredField("cards");
		Field oxygensField = GameDeck.class.getDeclaredField("oxygens");
		cardsField.setAccessible(true);
		oxygensField.setAccessible(true);

        GameDeck gameDeck = new GameDeck();
		Oxygen oxygen1 = new Oxygen(1);
		Oxygen oxygen2 = new Oxygen(1);
        gameDeck.add(oxygen1);
        gameDeck.add(oxygen2);

		Oxygen o2 = new Oxygen(2);
        Oxygen[] oxygens = gameDeck.splitOxygen(o2);
		Collection<Oxygen> deckOxygens = (Collection<Oxygen>)oxygensField.get(gameDeck);

		assertEquals(2, oxygens.length);
	    assertSame(oxygen1, oxygens[0]);
	    assertSame(oxygen2, oxygens[1]);
		assertEquals(1, ((Collection<Card>)cardsField.get(gameDeck)).size());
		assertEquals(1, deckOxygens.size());
		Iterator<Oxygen> iterator = deckOxygens.iterator();
		assertSame(o2, (Oxygen)iterator.next());
    }

}