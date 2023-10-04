package test.functional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import selfish.deck.Card;
import selfish.deck.Oxygen;

@Tag("functional")
@Tag("Oxygen")
public class OxygenTest {
	static Oxygen one;
	static Oxygen two;

	@BeforeAll
	public static void setUp() {
		one = new Oxygen(1);
		two = new Oxygen(2);
	}

	@Test
	public void testConstructor() throws NoSuchFieldException, IllegalAccessException {
		Field valueField = Oxygen.class.getDeclaredField("value");
		valueField.setAccessible(true);
		Integer value = (Integer)valueField.get(one);
		assertEquals(1, value.intValue());
		value = (Integer)valueField.get(two);
		assertEquals(2, two.getValue());
	}

	@Test
	public void testComparableMixedTypes() {
		Card cardMy = new Card("My Card Name", "The Card Description");
		Card cardThe = new Card("The Card Name", "A Card Description");
		List<Card> cards = new ArrayList<>();
		cards.add(one);
		cards.add(two);
		cards.add(cardThe);
		cards.add(cardMy);
		Collections.sort(cards);
		assertSame(cardMy, cards.get(0));
		assertSame(one, cards.get(1));
		assertSame(two, cards.get(2));
		assertSame(cardThe, cards.get(3));
	}

	@Test
	public void testComparableOxygenTypes() {
		Oxygen anotherOne = new Oxygen(1);
		Oxygen anotherTwo = new Oxygen(2);
		List<Oxygen> cards = new ArrayList<>();
		cards.add(two);
		cards.add(anotherOne);
		cards.add(one);
		cards.add(anotherTwo);
		Collections.sort(cards);
		assertEquals(one.toString(), cards.get(0).toString());
		assertEquals(one.toString(), cards.get(1).toString());
		assertEquals(two.toString(), cards.get(2).toString());
		assertEquals(two.toString(), cards.get(3).toString());
	}

	@Test
	public void testcompareToEqualTo() {
		assertEquals(0, one.compareTo(new Oxygen(1)));
	}

	@Test
	public void testcompareToGreaterThan() {
		assertTrue(one.compareTo(two) < 0);
	}

	@Test
	public void testcompareToLessThan() {
		assertTrue(two.compareTo(one) > 0);
	}

	@Test
	public void testGetValue() {
		assertEquals(1, one.getValue());
		assertEquals(2, two.getValue());
	}

	@Test
	public void testToString() {
		assertEquals("Oxygen(1)", one.toString());
		assertEquals("Oxygen(2)", two.toString());
	}

}
