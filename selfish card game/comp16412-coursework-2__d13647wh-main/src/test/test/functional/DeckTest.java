package test.functional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import selfish.deck.Card;
import selfish.deck.Deck;

@Tag("functional")
@Tag("Deck")
public class DeckTest {

	static Class clazz;
	static Method loadCards;
	static Method stringToCards;

    @BeforeAll
	public static void setup() {
		clazz = Deck.class;
		loadCards = null;
		stringToCards = null;
		try {
	    	loadCards = clazz.getDeclaredMethod("loadCards", String.class);
		    stringToCards = clazz.getDeclaredMethod("stringToCards", String.class);
		    loadCards.setAccessible(true);
		    stringToCards.setAccessible(true);
		} catch (NoSuchMethodException e) {}
	}

	@Test
	public void testStringToCardsQuantityZero() throws IllegalAccessException, InvocationTargetException {
		if (stringToCards == null) fail();
		String line = "A Name; A Description; 0";
		Card[] cards = (Card[])stringToCards.invoke(null, line);
		assertEquals(0, cards.length);
	}

	@Test
	public void testStringToCardsQuantityOne() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (stringToCards == null) fail();
		String line = "A Name; A Description; 1";
		Card[] cards = (Card[])stringToCards.invoke(null, line);
		assertEquals(1, cards.length);
		assertEquals("A Name", cards[0].toString());
		assertEquals("A Description", cards[0].getDescription());
	}


	@Test
	public void testStringToCardsQuantityMany() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (stringToCards == null) fail();
		String line = "Another Name; Another Description; 5";
		Card[] cards = (Card[])stringToCards.invoke(null, line);
		assertEquals(5, cards.length);
		for (int i=0; i<5; ++i) assertEquals("Another Name", cards[i].toString());
		for (int i=0; i<5; ++i) assertEquals("Another Description", cards[i].getDescription());
	}

	@Test
	public void testLoadCardsMaintainsOrder() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (loadCards == null) fail();
		List<Card> cards = (List<Card>)loadCards.invoke(null, "io/SpaceCards.txt");
		assertEquals(42, cards.size());
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

}
