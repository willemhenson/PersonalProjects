package test.functional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import selfish.deck.Card;

@Tag("functional")
@Tag("Card")
public class CardTest {
	static Card first;
	static Card middle;
	static Card last;

	@BeforeAll
	public static void setUp() {
		first = new Card("A Card Name", "The Card Description");
		middle = new Card("My Card Name", "My Card Description");
		last = new Card("The Card Name", "A Card Description");
	}

	@Test
	public void testConstructor() throws NoSuchFieldException, IllegalAccessException {
		Field nameField = Card.class.getDeclaredField("name");
		Field descriptionField = Card.class.getDeclaredField("description");
		nameField.setAccessible(true);
		descriptionField.setAccessible(true);

		String name = (String)nameField.get(first);
		String description = (String)descriptionField.get(first);
		assertEquals("A Card Name", name);
		assertEquals("The Card Description", description);

		name = (String)nameField.get(middle);
		description = (String)descriptionField.get(middle);
		assertEquals("My Card Name", name);
		assertEquals("My Card Description",description);

		name = (String)nameField.get(last);
		description = (String)descriptionField.get(last);
		assertEquals("The Card Name", name);
		assertEquals("A Card Description", description);
	}

	@Test
	public void testComparable() {
		Card equalTo = new Card(middle.toString(), middle.getDescription());
		List<Card> cards = new ArrayList<>();
		cards.add(last);
		cards.add(first);
		cards.add(equalTo);
		cards.add(middle);
		Collections.sort(cards);
		assertSame(first, cards.get(0));
		assertEquals(middle.toString(), cards.get(1).toString());
		assertEquals(middle.toString(), cards.get(2).toString());
		assertSame(last, cards.get(3));
	}

	@Test
	public void testcompareToEqualTo() {
		Card equalTo = new Card(middle.toString(), middle.getDescription());
		assertEquals(0, middle.compareTo(equalTo));
	}

	@Test
	public void testcompareToGreaterThan() {
		assertTrue(middle.compareTo(last) < 0);
		assertTrue(first.compareTo(last) < 0);	
		assertTrue(first.compareTo(middle) < 0);
	}

	@Test
	public void testcompareToLessThan() {
		assertTrue(middle.compareTo(first) > 0);
		assertTrue(last.compareTo(first) > 0);
		assertTrue(last.compareTo(middle) > 0);
	}

	@Test
	public void testGetDescription() {
		assertEquals("The Card Description", first.getDescription());
		assertEquals("My Card Description", middle.getDescription());
		assertEquals("A Card Description", last.getDescription());
	}

	@Test
	public void testToString() {
		assertEquals("A Card Name", first.toString());
		assertEquals("My Card Name", middle.toString());
		assertEquals("The Card Name", last.toString());
	}

}