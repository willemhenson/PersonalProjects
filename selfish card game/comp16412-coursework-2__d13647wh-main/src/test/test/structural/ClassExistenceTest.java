package test.structural;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("structural")
public class ClassExistenceTest {

	public static Class getClass(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	public static boolean classExists(String className) {
		return getClass(className) != null;
	}

	@Test
	public void testAstronautExists() {
		assertTrue(classExists("selfish.Astronaut"));
	}

	@Test
	public void testCardExists() {
		assertTrue(classExists("selfish.deck.Card"));
	}

	@Test
	public void testDeckExists() {
		assertTrue(classExists("selfish.deck.Deck"));
	}

	@Test
	public void testGameDeckExists() {
		assertTrue(classExists("selfish.deck.GameDeck"));
	}

	@Test
	public void testGameEngineExists() {
		assertTrue(classExists("selfish.GameEngine"));
	}

	@Test
	public void testGameExceptionExists() {
		assertTrue(classExists("selfish.GameException"));
	}

	@Test
	public void testOxygenExists() {
		assertTrue(classExists("selfish.deck.Oxygen"));
	}

	@Test
	public void testSpaceDeckExists() {
		assertTrue(classExists("selfish.deck.SpaceDeck"));
	}

}
